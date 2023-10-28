<template>
  <div class="content_home">
    <div class="home">
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

      <van-dialog
        v-model:show="show_2"
        :show-cancel-button="false"
        :show-confirm-button="false"
        width="50vw"
      >
        <br />
        <br />
        <van-loading size="10vw" vertical text-size="4vw" color="#f19290"
          >🚀拼命加载中...</van-loading
        >
        <br />
        <br />
      </van-dialog>

      <div style="transform: translate(6px, 0px)">
        <h1>💰 积分榜</h1>
      </div>
      <div v-for="(item, index) in userList" :key="index">
        <div
          style="
            margin-top: 0px;
            margin-bottom: 12px;
            position: relative;
            overflow: hidden;
            cursor: grab;
            background-color: #fff;
            box-shadow: 0px 0px 8px rgba(0, 0, 0, 0.025);
            border-radius: 10px;
            width: 96%;
            transform: translateX(2%);
          "
        >
          <van-swipe-cell>
            <van-card
              :price="formatNumber(item.credit)"
              currency="💰"
              :title="`${item.name}积分`"
              :desc="item.userText.substring(0, 10)"
              class="goods-card"
              :thumb="item.userImage"
              @click="disPlay(item.nameId)"
            >
              <template #price>
                <div style="transform: translate(-3px, -3px); font-size: 14px">
                  💰{{ formatNumber(item.credit) }}
                </div>
              </template>
              <template #tag>
                <div style="transform: translateX(-14px)">
                  <van-badge color="#F19290" :content="index + 1"></van-badge>
                </div>

                <div
                  v-if="item.nameId % 2 == 1"
                  style="
                    transform: translate(calc(96vw - 122px), -13px);
                    opacity: 0.7;
                  "
                >
                  <van-image :src="imageUrl1" width="103" height="105" />
                </div>
                <div
                  v-else
                  style="
                    transform: translate(calc(96vw - 128px), -7px);
                    opacity: 0.7;
                  "
                >
                  <van-image :src="imageUrl2" width="103" height="80" />
                </div>
              </template>

              <template #tags>
                <div style="transform: translateY(3px)">
                  <van-tag plain color="#F19290">{{ item.name }}</van-tag>
                  <van-tag plain color="#F19290">宝宝</van-tag>
                </div>
              </template>
            </van-card>
            <template #right>
              <van-button
                square
                icon="thumb-circle-o"
                color="#F19290"
                class="require-button"
                @click="goodItem()"
              />
            </template>
          </van-swipe-cell>
        </div>
      </div>
      <div style="transform: translate(12px, 0px)">
        <h1>🐻 欢迎回来</h1>
      </div>
      <div style="transform: translateY(-5px)">
        <div
          style="
            margin-top: 0px;
            margin-bottom: 15px;
            position: relative;
            overflow: hidden;
            cursor: grab;
            background-color: #fff;
            box-shadow: 0px 0px 8px rgba(0, 0, 0, 0.025);
            border-radius: 10px;
            width: 96%;
            transform: translateX(2%);
          "
        >
          <div v-if="userList != null && userList.length >= 2">
            <van-swipe-cell>
              <van-cell :border="false" class="goods-card" @click="goodItem()">
                <template #title>
                  🧸欢迎！{{ userList[0].name }}宝贝和{{
                    userList[1].name
                  }}宝贝！
                </template>
              </van-cell>
            </van-swipe-cell>
          </div>
        </div>
      </div>
      <br />
      <van-dialog
        v-model:show="show"
        :close-on-click-overlay="true"
        :show-cancel-button="false"
        :show-confirm-button="false"
        :width="picturewidth"
      >
        <div
          style="display: flex; justify-content: center; align-items: center"
        >
          <h1>💖感谢您的点赞</h1>
        </div>
        <div
          style="display: flex; justify-content: center; align-items: center"
        >
          <van-image
            width="100vw"
            height="100vw"
            position="center"
            fit="cover"
            :src="image"
          />
        </div>
      </van-dialog>
      <van-back-top right="83vw" bottom="10vh" />
    </div>
    <van-tabbar v-model="active" active-color="#F19290">
      <van-tabbar-item replace to="/Task" icon="coupon-o">任务</van-tabbar-item>
      <van-tabbar-item replace to="/Produce" icon="shop-collect-o"
        >商城</van-tabbar-item
      >
      <div class="circle-wrapper">
        <van-tabbar-item replace to="/">
          <template #icon="props">
            <img
              class="homeIcon"
              :src="props.active ? icon.active : icon.inactive"
            />
          </template>
        </van-tabbar-item>
      </div>
      <van-tabbar-item replace to="/Room" icon="cart-o">仓库</van-tabbar-item>
      <van-tabbar-item replace to="/User" icon="user-o">我的</van-tabbar-item>
    </van-tabbar>
  </div>
  <link
    rel="stylesheet"
    href="https://fonts.googleapis.com/css?family=Ma+Shan+Zheng"
  />
</template>

<script>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";
import jwtDecode from "jwt-decode";
import png from "@/util/3.gif";
import jpg1 from "@/assets/image/home1.jpg";
import jpg2 from "@/assets/image/home2.jpg";
import jpg3 from "@/assets/image/home3.jpg";
import Home1 from "@/assets/tabBar/home4.jpg";
import Home2 from "@/assets/tabBar/home3.jpg";
import HomeColor from "@/assets/tabBar/HomeIconColor.jpg";
import HomeGrey from "@/assets/tabBar/HomeIconGrey.jpg";
export default {
  setup() {
    const icon = {
      active: HomeColor,
      inactive: HomeGrey,
    };
    const imageUrl1 = [Home1];
    const imageUrl2 = [Home2];
    const image = [png];
    const noticeMessages = ref("");
    const show = ref(false);
    const show_1 = ref(false);
    const show_2 = ref(true); // 初始时显示加载中效果
    const router = useRouter();
    const active = ref(2);
    const images = [jpg1, jpg2, jpg3];
    const userList = ref([]);
    // 使用 onMounted 钩子来模拟 created 钩子，发起 API 请求
    onMounted(() => {
      fetchLoginToken();
      fetchData();
    });
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
            return;
          }
          console.log(response.data.data);
          const decodedToken = jwtDecode(token);
          // 从解码后的令牌中获取特定的数据
          userId = decodedToken.id; // 从令牌中获取用户ID
          // 在这里处理登录令牌接口的响应
          // 如果需要执行一些特定的操作，可以在这里添加代码
        })
        .catch((error) => {
          // 删除本地存储的 token
          localStorage.removeItem("jwtToken");
          router.replace("/login");
          console.error("请求loginToken接口失败", error);
        });
    };

    const fetchData = () => {
      // 使用 axios 或 fetch 发起 API 请求，获取商品数据
      // 假设 API 路径为 '/api/products'
      // 在发起数据请求之前
      console.log("Before fetching data. userList:", userList.value);
      axios
        .get("/api/user", { headers })
        .then((response) => {
          // 请求成功，将获取的数据存储在 userList.value 数组中
          userList.value = response.data.data;
          console.log("Data fetched successfully. userList:", userList.value);
        })
        .catch((error) => {
          console.error("Failed to fetch data:", error);
        });
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
    const disPlay = (url) => {
      router.replace(`/HomeUserDisplay/${url}`);
    };
    const requireItem = (item) => {
      if (userId == item.nameId) {
        const nameIdToDelete = item.nameId;
        show_2.value = true;
        // 调用 deleteTask 函数来发送 HTTP DELETE 请求
        requireTask(nameIdToDelete);
      } else show_1.value = true;
    };
    const requireTask = (nameId) => {
      router.replace(`/UserRequire/${nameId}`);
      show_2.value = false;
    };
    const goodItem = () => {
      show.value = true;
    };
    const formatNumber = (value) => {
      if (value === null || value === undefined) return "";
      // 使用toFixed方法将数字格式化为带两位小数的字符串
      return value.toFixed(2);
    };
    return {
      formatNumber,
      imageUrl1,
      imageUrl2,
      icon,
      image,
      disPlay,
      show,
      show_1,
      token,
      show_2,
      userList,
      active,
      images,
      goodItem,
      requireItem,
      noticeMessages,
    };
  },
};
</script>

<style>
.content_home {
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
.van-divider {
  --van-divider-line-height: 0px;
  --van-divider-content-left-width: 0%;
  --van-divider-content-right-width: 5%;
}
.home {
  margin-bottom: 10vh;
}

.notice-swipe {
  height: 25px;
  line-height: 25px;
}

h1 {
  font-family: "Ma Shan Zheng", cursive;
  transform: translateX(2vw);
  font-size: 20px;
  color: #ec8aa4;
}

.van-card {
  background: white;
}

.van-card__bottom {
  font-size: 18px;
}

.van-back-top {
  background-color: #b55151;
}
.van-card__price {
  transform: translateY(5px);
}
.van-button--mini {
  border: var(--van-button-border-width) solid #f19290;
}

.van-button__icon {
  font-size: 60px;
  line-height: inherit;
}

.require-button {
  margin: 20px;
  width: 60px;
  height: 60px;
  border-radius: 50%;
  transform: translateY(2px);
}

.van-tabbar-item__icon {
    font-size: 5.9vw;
}
.van-tabbar-item__icon img {
  display: block;
  height: 14vw;
  transform: translateY(-2.7vw);
}
.van-tabbar {
  height: 10vh;
}
.circle-wrapper {
  z-index: 2;
}
:root {
  --van-gray-3: #ffffff;
}

.van-dialog__confirm, .van-dialog__confirm:active {
    color: #f19290;
}
</style>