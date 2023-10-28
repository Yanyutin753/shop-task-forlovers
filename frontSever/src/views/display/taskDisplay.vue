<template>
  <div style="--van-nav-bar-icon-color: #f19290">
    <van-nav-bar
      title="任务"
      left-text=""
      left-arrow
      @click-left="onClickLeft"
    />
  </div>
  <div class="page-container">
    <div class="display_produce">
      <br />
      <div v-if="resData === null">
        <!-- 如果为空，渲染 <van-empty> 组件 -->
        <div style="transform: translate(0px, -10px)">
          <van-empty
            image-size="26rem"
            description="😭任务不见啦，快去让你的小可爱再创建一个吧！"
          />
        </div>
      </div>
      <div v-if="resData">
        <van-form class="form-container">
          <div
            style="display: flex; justify-content: center; align-items: center"
          >
            <van-image
              width="50vw"
              height="50vw"
              position="center"
              fit="cover"
              round
              :src="imageUrl"
              @click="picture"
            />
          </div>
          <br />
          <div style="transform: translate(0px, 8px)">
            <meta
              name="viewport"
              content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no"
            />
            <van-field label="📌任务名称" v-model="title" :readonly="true" />
            <van-field
              label="📝任务详情"
              v-model="desc"
              type="textarea"
              rows="3"
              :readonly="true"
            />
            <van-field label="💰积分价格" v-model="credit" :readonly="true" />
            <van-field
              label="🦄创建对象"
              v-model="founderName"
              :readonly="true"
            />
            <van-field label="🤖任务对象" v-model="useName" :readonly="true" />
            <van-field
              label="🎁完成情况"
              v-model="checkTask"
              :readonly="true"
            />
            <van-field
              label="⏱更新时间"
              v-model="updateTime"
              :readonly="true"
            />
            <van-field
              label="📅注册时间"
              v-model="registerTime"
              :readonly="true"
            />
          </div>
        </van-form>
      </div>
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
// import jwtDecode from 'jwt-decode';

export default {
  setup() {
    const picturewidth = ref();
    picturewidth.value = 0.8 * window.innerWidth;
    const show = ref(false);
    const show_1 = ref(true);
    const picture = () => (show.value = true);
    const router = useRouter();
    const onClickLeft = () => router.replace("/Task");
    const imageUrl = ref("");
    const title = ref("");
    const desc = ref("");
    const credit = ref("");
    const checkTask = ref("");
    const founderName = ref("");
    const useName = ref("");
    const registerTime = ref("");
    const updateTime = ref("");
    const resData = ref("");

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
    // const decodedToken = jwtDecode(token);
    // // 从解码后的令牌中获取特定的数据
    // const userId = decodedToken.id; // 从令牌中获取用户ID

    const id = router.currentRoute.value.params.id;

    let strippedId = id.replace(":", "");

    const fetchDataAndFillForm = async () => {
      try {
        const response = await axios.get(
          `/api/selectTask?id=${strippedId}`,
          { headers }
        );
        resData.value = response.data.data;
        const data = response.data.data; // 假设服务器返回的数据是一个包含上述字段的对象

        // 填充表单字段
        imageUrl.value = data.userImage;
        title.value = data.taskName;
        if (data.taskText == "") {
          desc.value = "😂这个人很懒，没有填写相关的任务详情！";
        } else desc.value = data.taskText;
        credit.value = data.taskCredit;
        useName.value = data.useName;
        console.log(data.checkProduce);
        if (data.checkTask == true) {
          checkTask.value = "🥰任务已成功完成";
        } else checkTask.value = "🧸还没完成任务呢，快去完成任务吧！";
        registerTime.value = data.registerTime;
        updateTime.value = data.updateTime;
        const founderId = data.founderId;
        const res = await axios.get(
          `/api/selectUser?id=${founderId}`,
          { headers }
        );
        const resDate = res.data.data; // 假设服务器返回的数据是一个包含上述字段的对象
        founderName.value = resDate.name;
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

    return {
      resData,
      picturewidth,
      show,
      show_1,
      picture,
      onClickLeft,
      imageUrl,
      title,
      desc,
      credit,
      checkTask,
      founderName,
      useName,
      registerTime,
      updateTime,
      fetchDataAndFillForm,
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
  margin-bottom: 10vh;
}

.form-container {
  margin-top: 20px;
}
</style>
