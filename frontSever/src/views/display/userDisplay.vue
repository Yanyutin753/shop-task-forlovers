<template>
  <div style="--van-nav-bar-icon-color: #f19290">
    <van-nav-bar
      :title="title_head"
      left-text=""
      left-arrow
      @click-left="onClickLeft"
    />
  </div>
  <div class="page-container">
    <div class="display_produce">
      <van-form class="form-container">
        <div
          style="display: flex; justify-content: center; align-items: center"
        >
          <van-image
            width="64vw"
            height="64vw"
            position="center"
            fit="cover"
            round
            :src="imageUrl"
            @click="picture"
          />
        </div>
        <meta
          name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no"
        />
        <div style="transform: translateY(3.5vh)">
          <van-field label="📌用户名称" v-model="title" :readonly="true" />
          <van-field
            label="📝用户详情"
            v-model="desc"
            type="textarea"
            rows="3"
            :readonly="true"
          />
          <van-field label="💰积分总数" v-model="credit" :readonly="true" />
          <van-field label="⏱更新时间" v-model="updateTime" :readonly="true" />
          <van-field
            label="📅注册时间"
            v-model="registerTime"
            :readonly="true"
          />
          <div style="transform: translateY(3.5vh)">
            <!-- <van-action-bar>
                    <van-action-bar-icon icon="like-o" text="小羊主页" />
                    <van-action-bar-button color="#F19290" text="退出登录" />
                    <van-action-bar-button type="warning" text="注销账号" />
                </van-action-bar> -->
            <van-button
              round
              block
              color="#F19290"
              native-type="submit"
              @click="logout"
            >
              退出登录
            </van-button>
          </div>
        </div>
      </van-form>
    </div>
  </div>
  <van-dialog
    v-model:show="show"
    :close-on-click-overlay="true"
    :show-cancel-button="false"
    :show-confirm-button="false"
    :width="picturewidth"
  >
    <div style="display: flex; justify-content: center; align-items: center">
      <van-image
        width="100vw"
        height="100vw"
        position="center"
        fit="cover"
        :src="imageUrl"
      />
    </div>
  </van-dialog>

  <van-dialog
    v-model:show="show_1"
    :show-cancel-button="false"
    :show-confirm-button="false"
    width="50vw"
  >
    <br />
    <br />
    <van-loading size="40px" vertical text-size="15px" color="#f19290"
      >🚀拼命加载中...</van-loading
    >
    <br />
    <br />
  </van-dialog>
</template>

<script>
import { ref, onMounted } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";

export default {
  setup() {
    const picturewidth = ref();
    picturewidth.value = 0.8 * window.innerWidth;
    const title_head = ref("");
    const router = useRouter();
    const show = ref(false);
    const show_1 = ref(true);
    const onClickLeft = () => router.replace("/User");
    const picture = () => (show.value = true);
    const imageUrl = ref("");
    const title = ref("");
    const desc = ref("");
    const credit = ref("");
    const registerTime = ref("");
    const updateTime = ref("");

    const token = localStorage.getItem("jwtToken"); // 从localStorage获取JWT令牌
    if (!token) {
      router.replace("/login");
    }

    const headers = {
      Authorization: `Bearer ${token}`,
    };

    const fetchLoginToken = () => {
      axios
        .post("/api/loginToken?token=" + token)
        .then((response) => {
          if (response.data.code == 0) {
            console.error(response.data.data);
            router.replace("/login");
            return;
          }
          console.log(response.data.data);
          // 在这里处理登录令牌接口的响应
          // 如果需要执行一些特定的操作，可以在这里添加代码
        })
        .catch((error) => {
          console.error("请求loginToken接口失败", error);
          router.replace("/login");
        });
    };

    const id = router.currentRoute.value.params.id;
    let strippedId = id.replace(":", "");

    const fetchDataAndFillForm = async () => {
      try {
        const response = await axios.get(
          `/api/selectUser?id=${strippedId}`,
          { headers }
        );
        const data = response.data.data; // 假设服务器返回的数据是一个包含上述字段的对象

        // 填充表单字段
        imageUrl.value = data.userImage;
        title.value = data.name;
        title_head.value = title.value;
        if (data.userText == "") {
          desc.value = "😂这个人很懒，没有填写相关的任务详情！";
        }
        desc.value = data.userText;
        credit.value = data.credit;
        registerTime.value = data.registerTime;
        updateTime.value = data.updateTime;
      } catch (error) {
        console.error("获取数据失败", error);
      }
      show_1.value = false;
    };
    // 在组件加载完成后自动触发数据加载和填充
    onMounted(() => {
      fetchLoginToken();
      fetchDataAndFillForm();
    });
    const logout = () => {
      // 删除本地存储的 token
      localStorage.removeItem("jwtToken");

      // 使用 Vue Router 进行页面跳转到登录页面
      router.replace("/login");
    };

    return {
      picturewidth,
      title_head,
      show,
      show_1,
      picture,
      onClickLeft,
      imageUrl,
      title,
      desc,
      credit,
      registerTime,
      updateTime,
      fetchDataAndFillForm,
      logout,
    };
  },
};
</script>

<style>
.page-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  width: 100%;
  overflow-y: auto;
  /* 显示垂直滚动条 */
  overflow-x: hidden;
  /* 隐藏水平滚动条 */
  zoom: 1;
  /* 禁止页面内容缩放 */
}

.van-uploader {
  position: relative;
  display: inline-block;
  /* transform: translateX(158px); */
  display: flex;
  justify-content: center;
}

.display_produce {
  width: 90%;
  margin-bottom: 10px;
  height: 95%;
}

.form-container {
  margin-top: 20px;
  height: 85vh;
  transform: translateY(1vh);
}
</style>
