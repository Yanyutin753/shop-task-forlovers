<template>
  <div class="content_user">
    <div class="user">
      <van-swipe :autoplay="3000" indicator-color="#F19290" lazy-render>
        <van-swipe-item v-for="image in images" :key="image">
          <img :src="image" style="width: 100%; height: 62.5vw" />
        </van-swipe-item>
      </van-swipe>
      <div class="wrapper_user" @click="linkUser()">
        <div style="transform: translateY(3vw)" position="center">
          <van-skeleton :round="true" :animate="false">
            <template #template>
              <div :style="{ display: 'flex', width: '88%' }">
                <div style="transform: translateX(2vw)">
                  <van-image
                    width="25vw"
                    height="25vw"
                    position="center"
                    fit="cover"
                    round
                    :src="imageUrl"
                  />
                </div>
                <div style="flex: 1 1 0%; transform: translateX(10vw)">
                  <div
                    style="
                      display: flex;
                      flex-direction: column;
                      transform: translateY(20vw)
                      width:80vw;
                    "
                  >
                    <div style="transform: translateY(2.5vw)">
                      <h10>🧸欢迎！{{ name }}宝贝</h10>
                      <div style="transform: translateY(2.5vw)">
                        <h10>💰积分：{{ credit }}</h10>
                        <div style="transform: translateY(2.5vw)">
                          <h10><van-text-ellipsis :content="desc" /></h10>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </template>
          </van-skeleton>
        </div>
      </div>
      <div style="transform: translateY(-80px); margin-top: 18px">
        <div
          style="
            position: relative;
            overflow: hidden;
            cursor: grab;
            background-color: #fff;
            box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.2);
            border-radius: 20px;
            width: 90%;
            transform: translateX(5vw);
          "
        >
          <div style="margin: 4vw">
            <van-grid clickable :column-num="3" :icon-size="calculateIconSize">
              <van-grid-item
                icon="coupon-o"
                text="任务列表"
                replace
                to="/Task"
              />
              <van-grid-item
                icon="shop-collect-o"
                text="商城"
                replace
                to="/Produce"
              />
              <van-grid-item icon="cart-o" text="商品订单" replace to="/Room" />
              <van-grid-item icon="wap-home-o" text="积分榜" replace to="/" />
              <van-grid-item
                icon="setting-o"
                @click="requireItem()"
                text="设置"
              />
              <van-grid-item
                icon="peer-pay"
                text="退出登录"
                @click="logout"
              />
            </van-grid>
          </div>
        </div>
      </div>
      <div style="transform: translateY(3.5vw); margin-bottom: 10vh">
        <div class="wrapper_user1">
          <div style="transform: translateY(2vw)">
            <van-cell
              class="copy"
              title="生成海报"
              is-link
              replace
                to="/display"
            />
          </div>
          <div style="transform: translateY(8px)">
            <van-cell
              class="copy"
              title="分享给朋友"
              is-link
              @click="copyToClipboard"
            />
          </div>
        </div>
      </div>
      <van-dialog
        v-model:show="show"
        title="🥰 温馨提示"
        message="链接成功已复制到剪贴板"
      >
      </van-dialog>
      <van-dialog
        v-model:show="show_1"
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
</template>

<script>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";
import jwtDecode from "jwt-decode";
import jpg1 from "@/assets/image/user1.jpg";
import jpg2 from "@/assets/image/user2.jpg";
import jpg3 from "@/assets/image/user3.jpg";
import HomeColor from "@/assets/tabBar/HomeIconColor.jpg";
import HomeGrey from "@/assets/tabBar/HomeIconGrey.jpg";

export default {
  setup() {
    const icon = {
      active: HomeColor,
      inactive: HomeGrey,
    };
    const images = [jpg1, jpg2, jpg3];
    const calculateIconSize = 0.08 * window.innerWidth;
    const imageUrl = ref("");
    const name = ref("");
    const show = ref(false);
    const show_1 = ref(true);
    const desc = ref("");
    const credit = ref("");
    const active = ref(4);
    const router = useRouter();
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
          const decodedToken = jwtDecode(token);
          // 从解码后的令牌中获取特定的数据
          userId = decodedToken.id; // 从令牌中获取用户ID

          console.log(response.data.data);
          fetchDataAndFillForm();
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
    const fetchDataAndFillForm = async () => {
      try {
        const response = await axios.get(
          `/api/selectUser?id=${userId}`,
          { headers }
        );
        const data = response.data.data; // 假设服务器返回的数据是一个包含上述字段的对象
        imageUrl.value = data.userImage;
        name.value = data.name;
        desc.value = data.userText;
        credit.value = data.credit;
      } catch (error) {
        console.error("获取数据失败", error);
      }
      show_1.value = false;
    };
    const copyToClipboard = () => {
      const generateShareLink = () => {
        return `https://github.com/Yanyutin753/shop-task-forlovers`;
      };
      const shareLink = generateShareLink();
      const tempInput = document.createElement("input");
      tempInput.value = shareLink;
      document.body.appendChild(tempInput);
      tempInput.select();
      document.execCommand("copy");
      document.body.removeChild(tempInput);
      show.value = true;
    };
    const requireItem = () => {
      router.replace("/Setting");
    };
    const linkUser = () => {
      router.replace(`/UserDisplay/${userId}`);
    };
    // 在组件加载完成后自动触发数据加载和填充
    onMounted(() => {
      fetchLoginToken();
    });
    const logout = () => {
      // 删除本地存储的 token
      localStorage.removeItem("jwtToken");

      // 使用 Vue Router 进行页面跳转到登录页面
      router.replace("/login");
    };
    return {
      calculateIconSize,
      icon,
      show,
      show_1,
      active,
      name,
      desc,
      credit,
      imageUrl,
      images,
      requireItem,
      copyToClipboard,
      linkUser,
      logout,
    };
  },
};
</script>
<style>
.content_user {
  background: rgb(250 250 250 / 22%);
  zoom: 1;
  /* 禁止页面内容缩放 */
  width: 100vw;
  /* 设置容器宽度 */
  overflow-y: auto;
  /* 显示垂直滚动条 */
  overflow-x: hidden;
  /* 隐藏水平滚动条 */
}

.van-card__bottom {
  font-size: 18px;
}

.van-tabbar-item__icon {
    font-size: 5.9vw;
}
.van-tabbar-item__icon img {
  display: block;
  height: 14vw;
  transform: translateY(-2.7vw);
}
.circle-wrapper {
  z-index: 2;
}
/* 创建白色长方形包装并添加阴影 */
.wrapper_user {
  background-color: #fff;
  box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.2);
  height: 31vw;
  border-radius: 20px;
  width: 90%;
  left: 12px;
  margin: 0 auto; /* 设置左右外边距为 auto，实现水平居中 */
  transform: translate(0px, -85px);
  transition: background-color 0.3s ease, box-shadow 0.3s ease;
}

.wrapper_user1 {
  background-color: #fff;
  box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.2);
  border-radius: 20px;
  width: 90%;
  height: 108px;
  margin-bottom: 10vh;
  left: 12px;
  margin: 0 auto;
  transform: translate(0px, -68px);
  transition: background-color 0.3s ease, box-shadow 0.3s ease;
}
/* 鼠标悬停时改变背景颜色 */
.wrapper:hover {
  background-color: #f0f0f0;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.3); /* 增加阴影模糊半径和透明度 */
}
.copy {
  width: 94%;
  transform: translate(10px, 2px);
}
h10 {
  font-size: 3.5vw;
}
:root {
  --van-gray-3: #ffffff;
}
.van-tabbar {
  height: 10vh;
}

.van-dialog__confirm, .van-dialog__confirm:active {
    color: #f19290;
}
</style>
