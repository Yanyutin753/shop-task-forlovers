<template>
  <div class="content_historyDisplay">
    <div style="--van-nav-bar-icon-color: #f19290">
      <van-nav-bar
        title="总结报告中心"
        left-text=""
        left-arrow
        @click-left="onClickLeft"
      />
    </div>
    <div class="historyDisplay">
      <div style="z-index: 999; transform: translate(1.5vh, 27vh)">
        <van-swipe indicator-color="aquamarine" lazy-render>
          <van-swipe-item
            :autoplay="3000"
            lazy-render
            v-for="image in images"
            :key="image"
            show-indicators="false"
          >
            <div
              style="
                z-index: 99;
                position: flex;
                transform: translate(12.5vh, 0px);
              "
            >
              <img :src="image" style="width: 20vh; height: 50vh" />
            </div>
          </van-swipe-item>
        </van-swipe>
      </div>
      <div style="z-index: 2; transform: translate(2vh, -49vh); width: 60%">
        <div style="font-size: 4.5vh">
          <h14>#{{ today }}</h14>
        </div>
        <div
          style="
            z-index: 2;
            transform: translate(35vw, -5vh);
            font-size: 3vh;
            margin: 0px;
            text-align: right;
            margin-right: 3.5vh;
          "
        >
          <h14>ID:{{ userData.name }}</h14>
        </div>
        <div
          style="z-index: 2; transform: translate(0px, -4vh); font-size: 3.5vh"
        >
          <h14>总结任务报告</h14>
        </div>
        <div
          style="z-index: 2; transform: translate(0px, -3vh); font-size: 2.8vh"
        >
          <h14>💰积分总数：{{ data.dayCredit }}</h14>
          <div
            style="
              z-index: 2;
              transform: translate(22vh, -7.5vh);
              font-size: 2vh;
              background-color: #fff;
              box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.15);
              border-radius: 20px;
              width: 20vh;
              height: 14vh;
              line-height: 2.5vh; /* 使文本在容器中垂直居中 */
              text-align: center; /* 水平居中 */
            "
          >
            <div style="z-index: 2; transform: translate(0vw, 3vh)">
              <h14>{{ userData.remindText }}</h14>
            </div>
          </div>
        </div>
        <div
          style="
            z-index: 2;
            transform: translate(1.5vh, -13vh);
            font-size: 2.5vh;
            background-color: #fff;
            box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.15);
            border-radius: 50px;
            width: 15vh;
            height: 10.3vh;
          "
        >
          <div
            style="
              z-index: 2;
              transform: translate(0vw, 1.5vh);
              text-align: center;
              font-size: 2vh;
            "
          >
            <h14>🏆任务完成数</h14>
          </div>
          <div
            style="
              z-index: 2;
              transform: translate(0vw, 1.5vh);
              text-align: center;
              font-size: 4vh;
            "
          >
            <h14>{{ data.dayCompleteTask }}</h14>
          </div>
        </div>
        <br />
        <div
          style="
            z-index: 2;
            transform: translate(29.5vh, -21vh);
            font-size: 2vh;
            background-color: #fff;
            box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.15);
            border-radius: 30px;
            width: 12.5vh;
            height: 10.3vh;
          "
        >
          <div
            style="
              z-index: 2;
              transform: translate(0vw, 1.5vh);
              text-align: center;
              font-size: 2vh;
            "
          >
            <h14>⭐任务总数</h14>
          </div>
          <div
            style="
              z-index: 2;
              transform: translate(0vw, 1.5vh);
              text-align: center;
              font-size: 4vh;
            "
          >
            <h14>{{ data.dayAddTask }}</h14>
          </div>
        </div>
        <div
          style="
            z-index: 2;
            transform: translate(0vw, -19vh);
            font-size: 2.5vh;
            background-color: #fff;
            box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.15);
            border-radius: 40px;
            width: 14vh;
            height: 10.3vh;
          "
        >
          <div
            style="
              z-index: 2;
              transform: translate(0vw, 1.5vh);
              text-align: center;
              font-size: 1.75vh;
            "
          >
            <h14>🎁添加商品数</h14>
          </div>
          <div
            style="
              z-index: 2;
              transform: translate(0vw, 1.5vh);
              text-align: center;
              font-size: 4vh;
              text-align: center; /* 水平居中 */
            "
          >
            <h14>{{ data.dayAddProduce }}</h14>
          </div>
        </div>
        <div
          style="
            z-index: 2;
            transform: translate(30vh, -24.5vh);
            font-size: 2.5vh;
            background-color: #fff;
            box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.15);
            border-radius: 50px;
            width: 13.5vh;
            height: 10.3vh;
            align-items: center;
          "
        >
          <div
            style="
              z-index: 2;
              transform: translate(0vw, 1.5vh);
              text-align: center;
              font-size: 1.75vh;
            "
          >
            <h14>🧸兑换商品数</h14>
          </div>
          <div
            style="
              z-index: 2;
              transform: translate(0vw, 1.5vh);
              text-align: center;
              font-size: 4vh;
            "
          >
            <h14>{{ data.dayReduceRoom }}</h14>
          </div>
        </div>
        <div
          style="
            z-index: 2;
            transform: translate(1vw, -23.5vh);
            font-size: 2.5vh;
            background-color: #fff;
            box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.15);
            border-radius: 30px;
            width: 12.5vh;
            height: 10.3vh;
            align-items: center;
          "
        >
          <div
            style="
              z-index: 2;
              transform: translate(0vw, 0vh);
              text-align: center;
              font-size: 1.75vh;
            "
          >
            <div
              style="
                z-index: 2;
                transform: translate(0vw, 1vh);
                text-align: center;
                font-size: 1.75vh;
              "
            >
              <h14>🌈购买商品数</h14>
            </div>
            <div
              style="
                z-index: 2;
                transform: translate(0vw, 1vh);
                text-align: center;
                font-size: 4vh;
              "
            >
              <h14>{{ data.dayBuyProduce }}</h14>
            </div>
          </div>
        </div>
        <div
          style="
            z-index: 2;
            transform: translate(30vh, -26.5vh);
            font-size: 2.5vh;
            background-color: #fff;
            box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.15);
            border-radius: 35px;
            width: 13vh;
            height: 10.3vh;
            align-items: center;
          "
        >
          <div
            style="
              z-index: 2;
              transform: translate(0vw, 0vh);
              text-align: center;
              font-size: 1.75vh;
            "
          ></div>
          <div
            style="
              z-index: 2;
              transform: translate(0vw, 1vh);
              text-align: center;
              font-size: 1.75vh;
            "
          >
            <h14>{{ userData.displayItem }}</h14>
          </div>
          <div
            style="
              z-index: 2;
              transform: translate(0.25vh, 1.5vh);
              text-align: center;
              font-size: 4vh;
            "
          >
            <h14>{{ day }}天</h14>
          </div>
        </div>
      </div>
      <div
        style="
          z-index: 2;
          transform: translate(0vw, -50vh);
          font-size: 2.5vh;
          margin-bottom: 10vh;
        "
      >
        <div style="z-index: 2; transform: translate(2vw, -26vh)">
          <div
            style="
              z-index: 2;
              transform: translate(3.35vh, 0px);
              font-size: 1.5vh;
            "
          >
            <van-image width="12.5vh" height="12.5vh" :src="imageUrl" />
          </div>
          <div
            style="z-index: 2; transform: translate(2vh, 3px); font-size: 1.5vh"
          >
            <h14>扫描二维码，关注更多吧！</h14>
          </div>
          <div style="z-index: 2; transform: translate(25vh, -3vh); width: 50%">
            <h14> {{ userData.displayLogo }}</h14>
          </div>
        </div>
      </div>
    </div>
  </div>
  <link
    rel="stylesheet"
    href="https://fonts.googleapis.com/css?family=Ma+Shan+Zheng"
  />
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
import jpg1 from "@/assets/display/display01.jpg";
import jpg2 from "@/assets/display/display02.jpg";
import jpg3 from "@/assets/display/display03.jpg";
import jpg4 from "@/assets/display/display04.jpg";
import jwtDecode from "jwt-decode";
import { ref, onMounted } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";

export default {
  setup() {
    const today = ref("");
    const day = ref("");
    const imageUrl = ref("");
    const show_1 = ref(false);
    const data = ref("");
    const userData = ref("");
    const userId = ref("");
    const router = useRouter();
    const x = window.innerWidth;
    const y = window.innerHeight;
    const xy = x / y;

    const images = [jpg1, jpg2, jpg3, jpg4];
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
          const decodedToken = jwtDecode(token);
          // 从解码后的令牌中获取特定的数据
          userId.value = decodedToken.id; // 从令牌中获取用户ID
          // 如果需要执行一些特定的操作，可以在这里添加代码
          fetchDataAndFillForm();
        })
        .catch((error) => {
          console.error("请求loginToken接口失败", error);
          router.replace("/login");
        });
    };

    const fetchDataAndFillForm = async () => {
      try {
        const response = await axios.get(
          `/api/selectUserAllRecord?id=${userId.value}`,
          { headers }
        );
        data.value = response.data.data; // 假设服务器返回的数据是一个包含上述字段的对象
        axios
          .get(`/api/selectUser?id=${userId.value}`, {
            headers,
          })
          .then((response) => {
            userData.value = response.data.data; // 假设服务器返回的数据是一个包含上述字段的对象
            console.log(userData.value);
            axios
              .get(`/api/generateQRCode?text=${userData.value.displayUrl}`, {
                headers,
              })
              .then((response) => {
                console.log(userData.value.displayUrl);
                imageUrl.value = response.data.data; // 假设服务器返回的数据是一个包含上述字段的对象
              });
            const timeDiff = new Date(userData.value.displayDay) - new Date();
            day.value = -Math.floor(timeDiff / (1000 * 60 * 60 * 24));
            const timeDay = new Date();
            today.value =
              timeDay.getFullYear() +
              "-" +
              (timeDay.getMonth() + 1) +
              "-" +
              timeDay.getDate();
          });
      } catch (error) {
        console.error("获取数据失败", error);
      }
      show_1.value = false;
    };
    // 在组件加载完成后自动触发数据加载和填充
    onMounted(() => {
      fetchLoginToken();
    });
    const onClickLeft = () => router.replace("/User");
    return {
      today,
      day,
      images,
      userId,
      data,
      userData,
      show_1,
      imageUrl,
      onClickLeft,
      xy,
    };
  },
};
</script>

<style scoped>
.content_historyDisplay {
  background-size: cover; /* 根据需要调整背景图片的尺寸 */
  background-repeat: no-repeat;
  background-position: center;
  width: 100vw;
  height: 100vh;
  zoom: 1;
  /* 禁止页面内容缩放 */
  /* 设置容器高度，使其占满整个视口 */
  /* 显示垂直滚动条 */
  /* 隐藏水平滚动条 */
  background-color: aquamarine;
}
.historyDisplay {
  border-radius: 10px;
  margin: 5%;
  zoom: 1;
  /* 禁止页面内容缩放 */
  width: 90vw;
  height: 88vh;
  /* 设置容器高度，使其占满整个视口 */
  overflow-y: hidden;
  /* 显示垂直滚动条 */
  overflow-x: hidden;
  /* 隐藏水平滚动条 */
  background-color: white;
}
h14 {
  font-family: "Ma Shan Zheng", cursive;
  color: #ec8aa4;
  justify-content: center;
  align-items: center;
}
</style>