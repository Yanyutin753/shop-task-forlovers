<template>
  <div class="content_remind_home">
    <div style="--van-nav-bar-icon-color: #f19290">
      <van-nav-bar
        title="主页通知设置"
        left-text=""
        left-arrow
        @click-left="onClickLeft"
      />
    </div>

    <van-floating-bubble
      v-model:offset="offset_produce"
      axis="xy"
      icon="add-o"
      @click="onClick"
    />

    <van-notice-bar
        color="#333333"
        background="#FFB6C1"
        left-icon="volume-o"
        :scrollable="false"
      >
        <van-swipe
          vertical
          class="notice-swipe"
          :autoplay="3000"
          :touchable="true"
          :show-indicators="false"
        >
          <van-swipe-item
            v-for="(message, index) in noticeMessages"
            :key="index"
            >{{ message.broadCastText }}</van-swipe-item
          >
        </van-swipe>
      </van-notice-bar>

      <van-swipe :autoplay="3000" indicator-color="#F19290" lazy-render>
        <van-swipe-item v-for="image in images" :key="image">
          <img :src="image" style="width: 100%; height: 62.5vw" />
        </van-swipe-item>
      </van-swipe>

    <div
      style="
        transform: translate(5vw, 4vh);
        position: relative;
        overflow: hidden;
        cursor: grab;
        background-color: #fff;
        box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.2);
        border-radius: 20px;
        width: 90%;
  margin-bottom: 10vh;
        margin-bottom : 8vh
      "
    >
    <div
      style="margin-top : 2vh; margin-bottom : 2vh
      "
    >
      <van-swipe-cell v-for="(item, index) in noticeMessages" :key="index">
        <van-cell :border="false" :title="item.broadCastText" />
        <template #right>
          <van-button
            square
            icon="edit"
            color="#FFE1B2"
            class="button_homeRequire"
            @click="requireItem(item)"
          />

          <van-button
            square
            icon="delete-o"
            color="#F19290"
            class="button_homeRequire"
            @click="deleteItem(item.broadCastId, index)"
          />
        </template>
      </van-swipe-cell>
    </div>
    </div>
  </div>
  <van-dialog
    v-model:show="show_2"
    title="添加主页通知"
    @confirm="checkConfirm"
    @cancel="checkCancel"
    show-cancel-button
  >
    <div style="margin-top: 3vh; margin-bottom: 4vh">
      <van-cell-group inset>
        <van-cell-group inset>
          <van-field
            v-model="value"
            rows="2"
            autosize
            label="留言"
            type="textarea"
            maxlength="50"
            placeholder="请输入留言"
            show-word-limit
          />
        </van-cell-group>
      </van-cell-group>
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
    v-model:show="show_3"
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
    v-model:show="show_4"
    title="修改主页通知"
    @confirm="reCheckConfirm"
    @cancel="reCheckCancel"
    show-cancel-button
  >
    <div style="margin-top: 3vh; margin-bottom: 4vh">
      <van-cell-group inset>
        <van-cell-group inset>
          <van-field
            v-model="reValue"
            rows="2"
            autosize
            label="留言"
            type="textarea"
            maxlength="50"
            placeholder="请输入留言"
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
import jpg1 from "@/assets/image/home1.jpg";
import jpg2 from "@/assets/image/home2.jpg";
import jpg3 from "@/assets/image/home3.jpg";
import jwtDecode from "jwt-decode";
export default {
  setup() {
    const images = [jpg1, jpg2, jpg3];
    const value = ref("");
    const reValue = ref("");
    const show = ref(false);
    const show_1 = ref(false);
    const show_2 = ref(false);
    const show_3 = ref(false);
    const show_4 = ref(false);
    const checked = ref();
    const router = useRouter();
    const currentTime = ref(["12", "00"]);
    const userId = ref("");
    const remindSwitch = ref("");
    const token = localStorage.getItem("jwtToken"); // 从localStorage获取JWT令牌
    const noticeMessages = ref("");
    var y = window.innerHeight * 0.74;
    var x = window.innerWidth * 0.8;
    const offset_produce = ref({ x: x, y: y });
    const message = ref("");
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
          // 在这里处理登录令牌接口的响应
          // 如果需要执行一些特定的操作，可以在这里添加代码
        })
        .catch((error) => {
          console.error("请求loginToken接口失败", error);
          router.replace("/login");
        });
      show_1.value = false;
    };
    const fetchDataAndFillForm = () => {
      axios
        .get("/api/findAllBroadcast", { headers })
        .then((response) => {
          // 请求成功，将获取的数据存储在 userList.value 数组中
          noticeMessages.value = response.data.data;
          show_2.value = false;
        })
        .catch((error) => {
          console.error("Failed to fetch data:", error);
        });
    };
    const onClick = () => {
      show_2.value = true;
    };
    const checkConfirm = () => {
      const broadCast = {
        broadCastText: value.value,
      };
      fetch("/api/addBroadcast", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(broadCast),
      })
        .then((response) => response.json()) // 将 .json() 放在正确的位置
        .then((data) => {
          if (data.code == 1) {
            console.log("添加成功", data.data);
            const broadCastId = data.data;
            broadCast.broadCastId = broadCastId;
            noticeMessages.value.push(broadCast);
            message.value = "添加成功";
          } else {
            message.value = "添加失败";
          }
        })
        .catch((error) => {
          message.value = "添加失败";
          console.log(error);
        });
      show_3.value = true; // 显示
      // 等待两秒后执行关闭操作
      setTimeout(() => {
        show_3.value = false; // 关闭
      }, 1000); // 2000毫秒等于2秒
      value.value = "";
    };

    const checkCancel = () => {
      return;
    };
    const onClickLeft = () => router.replace("/setting");

    let item_tem = "";
    const requireItem = (item) => {
      show_4.value = true;
      item_tem = item;
      return;
    };
    const reCheckConfirm = () => {
      const broadCast = {
        broadCastId: item_tem.broadCastId,
        broadCastText: reValue.value,
      };
      console.log(item_tem.broadCastId);
      fetch("/api/requireBroadcast", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(broadCast),
      })
        .then((response) => response.json()) // 将 .json() 放在正确的位置
        .then((data) => {
          if (data.code == 1) {
            console.log("修改成功", data.data);
            item_tem.broadCastText = broadCast.broadCastText;
            message.value = "修改成功";
          } else {
            message.value = "修改失败";
          }
        })
        .catch((error) => {
          message.value = "修改失败";
          console.log(error);
        });
      show_3.value = true; // 显示
      // 等待两秒后执行关闭操作
      setTimeout(() => {
        show_3.value = false; // 关闭
      }, 1000); // 2000毫秒等于2秒
      reValue.value = "";
    };

    const reCheckCancel = () => {
      return;
    };

    const deleteItem = (broadCastId, position) => {
      axios
        .get(`/api/deleteBroadcast?id=${broadCastId}`, {
          headers,
        })
        .then((response) => {
          // 请求成功，将获取的数据存储在 userList.value 数组中
          message.value = response.data.data;
          console.log(response.data);
          noticeMessages.value.splice(position, 1); // 从数组中移除任务项
        })
        .catch((error) => {
          console.error("Failed to fetch data:", error);
          message.value = "删除失败";
        });
      show_3.value = true; // 显示
      // 等待两秒后执行关闭操作
      setTimeout(() => {
        show_3.value = false; // 关闭
      }, 1000); // 2000毫秒等于2秒
    };

    return {
      images,
      value,
      show,
      show_1,
      show_2,
      show_3,
      show_4,
      checked,
      currentTime,
      remindSwitch,
      onClickLeft,
      noticeMessages,
      offset_produce,
      onClick,
      checkConfirm,
      checkCancel,
      requireItem,
      reValue,
      reCheckConfirm,
      reCheckCancel,
      deleteItem,
      message,
    };
  },
};
</script>
  
<style>
.content_remind_home {
  background: rgb(250 250 250 / 22%);
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
}

.button_homeRequire {
  margin: 4.5px;
  width: 35px;
  height: 35px;
  border-radius: 50%;
}
.van-floating-bubble {
  width: 13vw;
  height: 13vw;
}
.van-floating-bubble {
  background: #f19290;
}
.van-floating-bubble__icon {
  font-size: 8vw;
}
.button_homeRequire .van-icon {
  /* 你的自定义样式 */
  font-size: 35px;
}
:root {
  --van-gray-3: #ffffff;
}

.notice-swipe {
  height: 25px;
  line-height: 25px;
}

.van-dialog__confirm, .van-dialog__confirm:active {
    color: #f19290;
}
</style>