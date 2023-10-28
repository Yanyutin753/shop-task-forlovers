<template>
  <div class="content_login">
    <div style="z-index: 1">
      <van-swipe :autoplay="4000" indicator-color="#F19290" lazy-render>
        <van-swipe-item v-for="image in images" :key="image">
          <img :src="image" style="width: 100vw; height: 110vh" />
        </van-swipe-item>
      </van-swipe>
    </div>
  </div>
  <div class="wrapper_login" @click="loginButton">
    <div style="display: flex; justify-content: center; align-items: center">
      <h6 v-if="height == calculatedHeight">WELCOME</h6>
      <h4 v-else>Click It</h4>
    </div>
  </div>

  <div style="z-index: 1; height: 100vh">
    <van-floating-panel v-model:height="height" :anchors="anchors">
      <div
        style="
          display: flex;
          justify-content: center;
          transform: translateY(-26.5px);
          z-index: 2;
          overflow: hidden;
        "
      >
        <h4 style="line-height: 0; font-size: 10vw">Login on KABI</h4>
      </div>

      <div
        style="
          display: flex;
          justify-content: center;
          align-items: center;
          transform: translateY(-3vh);
          overflow: hidden;
        "
      >
        <van-image
          width="83%"
          height="5%"
          fit="contain"
          position="center"
          :src="image"
        />
      </div>
      <meta
        name="viewport"
        content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no"
      />
      <div style="text-align: center; width: 80vw; padding: 10vw">
        <div
          style="
          display: flex;
          justify-content: center;
          align-items: center;
          transform: translateY(-2vh);
          width:80vw
          overflow: hidden;
        "
        >
          <van-form @submit="onSubmit">
            <van-field
              v-model="username"
              name="用户名"
              label="🦄用户名"
              placeholder="用户名"
              :rules="[{ required: true, message: '请填写用户名' }]"
            />
            <van-field
              v-model="password"
              type="password"
              name="密码"
              label="🔑密码"
              placeholder="密码"
              :rules="[{ required: true, message: '请填写密码' }]"
            />
            <div style="margin-top: 2.5vw; overflow: hidden">
              <van-checkbox
                class="remember"
                v-model="checked"
                checked-color="#F19290"
                icon-size="3.5vw"
              >
                <h9
                  style="
                    font-size: 3.5vw;
                    color: #f19290;
                    transform: translate(17px, 0px);
                  "
                  >记住密码</h9
                ></van-checkbox
              >
            </div>

            <div
              style="
                margin: 2.5vw;
                overflow: hidden;
                transform: translateY(1vw);
              "
            >
              <van-button size="large" round block color="#F19290" native-type="submit">
                <h7>LOGIN IN KABI'S HOUSE</h7>
              </van-button>
            </div>
            <div style="justify-content: center; transform: translateY(-2vw)">
              <h5>
                Don't have an account?
                <router-link replace to="/userAdd" class="custom-link"
                  >Sign up</router-link
                >
              </h5>
            </div>
          </van-form>
        </div>
      </div>
    </van-floating-panel>
  </div>
  <van-dialog v-model:show="show" title="😭提示" message="账号或密码错误">
  </van-dialog>
  <van-dialog
    v-model:show="show_1"
    title="🥰恭喜"
    message="登录成功！"
    width="70vw"
    height="30vw"
    :show-cancel-button="false"
    :show-confirm-button="false"
  >
  </van-dialog>
  <link
    rel="stylesheet"
    href="https://fonts.googleapis.com/css?family=Young+Serif"
  />
</template>
  
  <script>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import png from "@/util/love.gif";
import jpg1 from "@/assets/image/login1.gif";
import jpg2 from "@/assets/image/login2.jpg";
import jpg3 from "@/assets/image/login3.jpg";
import jpg5 from "@/assets/image/login4.jpg";
import jpg6 from "@/assets/image/login6.jpg";
export default {
  setup() {
    const title = ref("Click It");
    const images = [jpg1, jpg5, jpg6, jpg2, jpg3];
    const checked = ref(false);
    const image = png;
    const show = ref(false);
    const show_1 = ref(false);
    const anchors = [
      Math.round(0.0 * window.innerHeight),
      Math.round(0.895 * window.innerHeight),
    ];
    const height = ref(anchors[0]);
    const username = ref("");
    const password = ref("");
    const router = useRouter();
    const calculatedHeight = ref(Math.round(0.895 * window.innerHeight));

    onMounted(() => {
      const savedUsername = localStorage.getItem("savedUsername");
      const savedPassword = localStorage.getItem("savedPassword");
      const savedRemember = localStorage.getItem("savedRemember");

      if (savedRemember === "true") {
        username.value = savedUsername || "";
        password.value = savedPassword || "";
        checked.value = true;
      }
    });

    let count = 0;
    const loginButton = () => {
      count = !count;
      if (count == 1) {
        title.value = "WELCOME";
        height.value = Math.round(0.895 * window.innerHeight);
      } else {
        title.value = "Click It";
        height.value = Math.round(0.0 * window.innerHeight);
      }
    };
    // 切换 checked 状态的方法
    const onSubmit = () => {
      // 处理登录逻辑
      if (checked.value) {
        // 如果记住密码被选中，将用户名和密码保存到本地存储
        localStorage.setItem("savedUsername", username.value);
        localStorage.setItem("savedPassword", password.value);
        localStorage.setItem("savedRemember", "true");
      } else {
        // 如果不记住密码，清除本地存储中的信息
        localStorage.removeItem("savedUsername");
        localStorage.removeItem("savedPassword");
        localStorage.removeItem("savedRemember");
      }
      const user = {
        name: username.value,
        password: password.value,
      };
      fetch("/api/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(user),
      })
        .then((response) => response.json()) // 将 .json() 放在正确的位置
        .then((data) => {
          if (data.code == 1) {
            console.log("登录成功", data.data);
            show_1.value = true;
            setTimeout(() => {
              router.replace("/");
            }, 1000);
            const token = data.data;

            localStorage.setItem("jwtToken", token);
          } else {
            show.value = true;
            console.error("登录失败");
          }
        })
        .catch((error) => {
          show.value = true;
          console.error("登录时出现错误:", error);
        });
    };

    return {
      calculatedHeight,
      title,
      loginButton,
      images,
      anchors,
      height,
      checked,
      show,
      show_1,
      username,
      password,
      onSubmit,
      image,
    };
  },
};
</script>
<style scoped>
.content_login {
  background-size: fill; /* 根据需要调整背景图片的尺寸 */
  background-repeat: no-repeat;
  background-position: center;
  width: 100vw;
  height: 100vh;
  position: fixed; /* 让背景固定在视窗中 */
  zoom: 1;
  /* 设置容器高度，使其占满整个视口 */
  overflow-y: hidden;
  /* 显示垂直滚动条 */
  overflow-x: hidden;
  /* 隐藏水平滚动条 */
}

h3 {
  font-size: 50px;
  color: #f19290;
  z-index: 2;
}

h4 {
  font-size: 5vh;
  color: #f19290;
  font-family: "Young Serif", cursive;
}

h6 {
  font-size: 4vh;
  color: #f19290;
  font-family: "Young Serif", cursive;
}

.wrapper_login {
  transform: translate(17.5vw, 0px);
  position: fixed;
  background-color: #fff;
  box-shadow: 0px 0px 8px rgba(0, 0, 0, 0.2);
  border-radius: 20px;
  width: 65vw;
  height: 9vh;
  transition: background-color 0.3s ease, box-shadow 0.3s ease;
  display: flex;
  justify-content: center;
}

h5 {
  font-size: 3.5vw;
  color: #f19290;
}

h7 {
  font-size: 3.5vw;
  color: white;
}
.custom-link {
  font-size: 3.5vw;
  color: #b95b66;
  /* 设置为深色 */
  /* 其他样式属性 */
}
.remember {
  transform: translate(17px, 0px);
  height: 4vw;
}

.img-icon {
  height: 20px;
}

.van-cell {
  width: 80vw;
  font-size: 3.5vw;
}
.van-button--block {
    display: block;
    height: 8vw;
    width: 100%;
}
</style>
  
  