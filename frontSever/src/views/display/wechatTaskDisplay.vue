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
      <div v-if="data === null">
        <!-- 如果为空，渲染 <van-empty> 组件 -->
        <div style="transform: translate(0px, -10px)">
          <van-empty
            image-size="26rem"
            description="😭商品已售完，快去创建一
            个吧！"
          />
        </div>
      </div>
      <div v-if="data">
        <van-form class="form-container">
            <div
            style="display: flex; justify-content: center; align-items: center"
            >
            <van-image
                width="48vw"
                height="48vw"
                position="center"
                fit="cover"
                round
                :src="imageUrl"
                @click="picture"
            />
            </div>
            <br />
            <meta
            name="viewport"
            content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no"
            />
            <van-field label="📌任务名称" v-model="title" :readonly="true" />
            <van-field
            label="📝任务详情"
            v-model="desc"
            type="textarea"
            rows="2"
            :readonly="true"
            />
            <van-field label="💰积分价格" v-model="credit" :readonly="true" />
            <van-field label="🦄创建对象" v-model="founderName" :readonly="true" />
            <van-field label="🤖任务对象" v-model="useName" :readonly="true" />
            <van-field label="🎁完成情况" v-model="checkTask" :readonly="true" />
            <van-field label="⏱更新时间" v-model="updateTime" :readonly="true" />
            <van-field label="📅注册时间" v-model="registerTime" :readonly="true" />
            <div style="margin: 8px; transform: translateY(9px)">
            <van-button
                @click="
                data.checkTask
                    ? haveCompletedItem()
                    : completedItem(data.taskId, data.founderId)
                "
                round
                block
                color="#F19290"
                native-type="submit"
            >
                {{ data.checkTask ? "已完成任务" : "完成任务" }}
            </van-button>
            <br />
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
  <van-dialog
    v-model:show="show_7"
    title="🥰提示"
    :message="message"
    width="70vw"
    height="30vw"
    :close-on-click-overlay="true"
    :show-cancel-button="false"
    :show-confirm-button="false"
  >
  </van-dialog>
  <van-dialog
    v-model:show="show_2"
    @confirm="checkConfirm"
    @cancel="checkCancel"
    title="🥰 温馨提示"
    message="🦄您确定您的小可爱完成了这个任务了吗？"
    show-cancel-button
  >
  </van-dialog>
  <van-dialog
    v-model:show="show_5"
    title="🥰 温馨提示"
    message="😂您不能完成别人创建的任务哦，快去完成自己的任务吧！"
  >
  </van-dialog>
  <van-dialog
    v-model:show="show_3"
    title="🥰 温馨提示"
    message="你的小可爱已完成任务啦，快去完成自己的任务吧！"
  >
  </van-dialog>
</template>
    
    <script>
import { ref, onMounted } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";
import jwtDecode from "jwt-decode";

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
    const show_3 = ref(false);
    const show_2 = ref(false);
    const show_5 = ref(false);
    const show_7 = ref(false);
    const message = ref("");
    const checkTask = ref("");
    const founderName = ref("");
    const useName = ref("");
    const registerTime = ref("");
    const updateTime = ref("");
    const data = ref("");

    const token = localStorage.getItem("jwtToken"); // 从localStorage获取JWT令牌
    if (!token) {
      router.replace("/login");
    }

    const headers = {
      Authorization: `Bearer ${token}`,
    };

    let userId = "";
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
      const decodedToken = jwtDecode(token);
      // 从解码后的令牌中获取特定的数据
      userId = decodedToken.id; // 从令牌中获取用户ID
    };

    const id = router.currentRoute.value.params.id;

    let strippedId = id.replace(":", "");
    console.log(strippedId);
    const fetchDataAndFillForm = async () => {
      try {
        const response = await axios.get(
          `/api/selectTask?id=${strippedId}`,
          { headers }
        );
        data.value = response.data.data; // 假设服务器返回的数据是一个包含上述字段的对象
        console.log(response.data.data.taskId);
        // 填充表单字段
        imageUrl.value = data.value.userImage;
        title.value = data.value.taskName;
        if (data.value.taskText == "") {
          desc.value = "😂这个人很懒，没有填写相关的任务详情！";
        } else desc.value = data.value.taskText;
        credit.value = data.value.taskCredit;
        useName.value = data.value.useName;
        console.log(data.value.checkTask);
        if (data.value.checkTask == true) {
          checkTask.value = "🥰任务已成功完成";
        } else checkTask.value = "🧸还没完成任务呢，快去完成任务吧！";
        registerTime.value = data.value.registerTime;
        updateTime.value = data.value.updateTime;
        const founderId = data.value.founderId;
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
    let item = "";
    const completedItem = (taskId, founderId) => {
      if (userId == founderId) {
        // 重新赋值
        item = taskId;
        console.log(item);
        show_2.value = true;
      } else {
        show_5.value = true;
      }
    };
    const checkConfirm = () => {
      completeTask(item);
    };
    const checkCancel = () => {
      return;
    };
    const completeTask = (item) => {
      // 发送 HTTP DELETE 请求来完成数据
      axios
        .put(`/api/tasks?id=${item}`, null, {
          headers,
        })
        .then((response) => {
          // 处理完成成功的逻辑
          console.log("数据完成成功", response);
          message.value = "您的小可爱任务完成成功！";
          checkTask.value = "🥰任务已成功完成";
        })
        .catch((error) => {
          // 处理完成失败的逻辑
          console.error("数据完成失败", error);
          message.value = "您的小可爱任务完成失败！";
        });
      show_7.value = true; // 显示
      // 等待两秒后执行关闭操作
      setTimeout(() => {
        show_7.value = false;
        router.replace("/Task");
      }, 1000);
    };
    const haveCompletedItem = () => {
      show_3.value = true;
      setTimeout(() => {
        show_3.value = false;
      }, 3000);
    };
    return {
      show_1,
      show_2,
      show_3,
      show_5,
      show_7,
      message,
      data,
      picturewidth,
      show,
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
      checkConfirm,
      checkCancel,
      completedItem,
      haveCompletedItem,
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
  margin-top: 0px;
}
</style>
    