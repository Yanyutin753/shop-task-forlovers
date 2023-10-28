<template>
  <div class="content_displaySetting">
    <meta
      name="viewport"
      content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no"
    />
    <div style="--van-nav-bar-icon-color: #f19290">
      <van-nav-bar
        title="海报展示设置中心"
        left-text=""
        left-arrow
        @click-left="onClickLeft"
      />
    </div>

    <div
      style="
        transform: translate(5vw, 7vh);
        position: relative;
        overflow: hidden;
        cursor: grab;
        background-color: #fff;
        box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.2);
        border-radius: 20px;
        width: 90%;
      "
    >
      <div style="margin-top: 2vh; margin-bottom: 2vh">
        <van-cell center title="海报展示设置"> </van-cell>
        <van-cell-group inset>
          <van-cell
            title="海报事件名称"
            :label="displayItem"
            is-link
            @click="requireName()"
          />

          <van-cell
            title="海报事件时间"
            :label="displayDay"
            is-link
            @click="requireTime()"
          />

          <van-cell
            title="海报名称"
            :label="displayLogo"
            is-link
            @click="requireLogo()"
          />

          <van-cell
            title="海报二维码内容"
            :label="displayUrl"
            is-link
            @click="requireUrl()"
          />
        </van-cell-group>
      </div>
    </div>
  </div>
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
    v-model:show="show_2"
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
    v-model:show="show_3"
    :title="title"
    @confirm="userCheckConfirm"
    @cancel="userCheckCancel"
    show-cancel-button
  >
    <div style="margin-top: 3vh; margin-bottom: 4vh">
      <van-cell-group inset>
        <van-cell-group inset>
          <van-field
            v-model="userValue"
            rows="3"
            autosize
            :label="key"
            type="textarea"
            :maxlength="maxlength"
            :placeholder="keyValue"
            show-word-limit
          />
        </van-cell-group>
      </van-cell-group>
    </div>
  </van-dialog>
</template>
  
  <script>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";
import jwtDecode from "jwt-decode";
export default {
  setup() {
    const displayUrl = ref("");
    const displayLogo = ref("");
    const displayItem = ref("");
    const displayDay = ref("");
    const userData = ref("");
    const title = ref("");
    const key = ref("");
    const maxlength = ref("");
    const keyValue = ref("");
    const userValue = ref("");
    const message = ref("");
    const userId = ref("");
    const show_1 = ref(false);
    const show_2 = ref(false);
    const show_3 = ref(false);
    const router = useRouter();
    const token = localStorage.getItem("jwtToken"); // 从localStorage获取JWT令牌
    if (!token) {
      router.replace("/login");
    }

    const headers = {
      Authorization: `Bearer ${token}`,
    };
    onMounted(() => {
      show_1.value = true;
      fetchLoginToken();
    });
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
          const decodedToken = jwtDecode(token);
          // 从解码后的令牌中获取特定的数据
          userId.value = decodedToken.id; // 从令牌中获取用户ID
          fetchDataAndFillForm();
        })
        .catch((error) => {
          console.error("请求loginToken接口失败", error);
          router.replace("/login");
        });
    };
    const fetchDataAndFillForm = async () => {
      try {
        axios
          .get(`/api/selectUser?id=${userId.value}`, {
            headers,
          })
          .then((response) => {
            userData.value = response.data.data; // 假设服务器返回的数据是一个包含上述字段的对象
            console.log(userData.value);
            displayUrl.value = userData.value.displayUrl;
            displayLogo.value = userData.value.displayLogo;
            displayItem.value = userData.value.displayItem;
            displayDay.value = userData.value.displayDay;
          });
      } catch (error) {
        console.error("获取数据失败", error);
      }
      show_1.value = false;
    };
    const requireName = () => {
      title.value = "修改海报事件名称";
      key.value = "海报事件名称";
      maxlength.value = 7;
      keyValue.value = "请输入事件名称";
      show_3.value = true;
      return;
    };

    const requireTime = () => {
      title.value = "修改海报事件起始时间";
      key.value = "海报事件起始时间";
      maxlength.value = 20;
      keyValue.value = "请输入海报事件起始时间（例如：2022-10-06）";
      show_3.value = true;
      return;
    };

    const requireLogo = () => {
      title.value = "修改海报Logo名称";
      key.value = "海报Logo名称";
      maxlength.value = 10;
      keyValue.value = "请输入海报Logo名称";
      show_3.value = true;
      return;
    };

    const requireUrl = () => {
      title.value = "修改海报二维码链接";
      key.value = "海报二维码链接";
      maxlength.value = 50;
      keyValue.value = "请输入海报二维码链接";
      show_3.value = true;
      return;
    };

    const userCheckConfirm = () => {
      const user = {
        nameId: userId.value,
        displayItem: "",
        displayDay: "",
        displayLogo: "",
        displayUrl: "",
      };
      console.log(user);
      if (title.value == "修改海报二维码链接") {
        user.displayUrl = userValue.value;
        displayUrl.value = userValue.value;
      } else if (title.value == "修改海报Logo名称") {
        user.displayLogo = userValue.value;
        displayLogo.value = userValue.value;
      } else if (title.value == "修改海报事件起始时间") {
        user.displayDay = userValue.value;
        displayDay.value = userValue.value;
      } else if (title.value == "修改海报事件名称") {
        user.displayItem = userValue.value;
        displayItem.value = userValue.value;
      }
      // 发送 POST 请求到指定的 URL
      fetch("/api/requireUser", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(user),
      })
        .then((response) => {
          console.log(response.json().data);
          message.value = "保存成功";
        })
        .catch((error) => {
          console.error("请求requireUser接口失败", error);
          message.value = "保存失败";
          // 处理请求失败的情况
        });
      show_2.value = true; // 显示
      // 等待两秒后执行关闭操作
      setTimeout(() => {
        show_2.value = false; // 关闭
      }, 1000); // 2000毫秒等于2秒
      userValue.value = "";
    };

    const userCheckCancel = () => {
      userValue.value = "";
      return;
    };

    const onClickLeft = () => router.replace("/Setting");
    return {
      title,
      key,
      maxlength,
      keyValue,
      requireName,
      requireTime,
      requireLogo,
      requireUrl,
      displayUrl,
      displayLogo,
      displayItem,
      displayDay,
      userValue,
      userData,
      userId,
      show_1,
      show_2,
      show_3,
      onClickLeft,
      message,
      userCheckConfirm,
      userCheckCancel,
    };
  },
};
</script>
  
  <style>
.content_displaySetting {
  background-image: url("../../assets/image/setting.jpg");
  zoom: 1;
  /* 禁止页面内容缩放 */
  width: 100vw;
  /* 设置容器宽度 */
  height: 100vh;
  /* 设置容器高度，使其占满整个视口 */
  overflow-y: auto;
  /* 显示垂直滚动条 */
  overflow-x: hidden;
  /* 隐藏水平滚动条 */
  background-size: cover;
  background-position: center -70px;
}

.van-dialog__confirm,
.van-dialog__confirm:active {
  color: #f19290;
}

:root {
  --van-gray-3: #ffffff;
}

.van-picker__confirm {
  color: #f19290;
}
</style>