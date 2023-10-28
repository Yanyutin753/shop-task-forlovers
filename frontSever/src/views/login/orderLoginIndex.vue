<template>
  <div class="content_login">
    <div
      style="
        display: flex;
        justify-content: center;
        align-items: center;
        transform: translateY(-4.5vh);
      "
    >
      <h3>YUBB.</h3>
    </div>
    <div
      style="
        display: flex;
        justify-content: center;
        align-items: center;
        transform: translateY(-15.5vh);
      "
    >
      <h4>Login on YUBB :⭐:</h4>
    </div>
    <div
      style="
        display: flex;
        justify-content: center;
        align-items: center;
        transform: translateY(-17.5vh);
      "
    >
      <van-image width="83%" height="5%" fit="contain" position="center" :src="image" />
    </div>
    <div
      style="
        display: flex;
        justify-content: center;
        transform: translateY(-16vh);
        align-items: center;
      "
    >
      <van-form @submit="onSubmit">
        <meta
          name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no"
        />

        <van-field
          v-model="username"
          name="用户名"
          label="🦄用户名"
          placeholder="用户名"
        />
        <van-field
          v-model="password"
          type="password"
          name="密码"
          label="🔑密码"
          placeholder="密码"
        />

        <van-checkbox
          class="remember"
          v-model="checked"
          checked-color="#F19290"
          icon-size="15px"
        >
          <h9 style="font-size: 13px; color: #f19290">记住密码</h9></van-checkbox
        >

        <div style="margin: 15px; transform: translateY(11px);">
          <van-button round block color="#F19290" native-type="submit">
            LOGIN IN MOXIANBAO
          </van-button>
        </div>
      </van-form>
    </div>
    <div
      style="
        display: flex;
        justify-content: center;
        align-items: center;
        transform: translateY(-18.4vh);
      "
    >
      <h5>
        Don't have an account?
        <router-link to="/userAdd" class="custom-link">Sign up</router-link>
      </h5>
    </div>
    <van-dialog v-model:show="show" title="😭提示" message="账号或密码错误"> </van-dialog>
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
  </div>
</template>
<script>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import png from "@/util/love.gif";
export default {
  setup() {
    const checked = ref(false);
    const image = png;
    const show = ref(false);
    const show_1 = ref(false);
    const username = ref("");
    const password = ref("");
    const router = useRouter();
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

<style>
.content_login {
  zoom: 1;
  /* 禁止页面内容缩放 */
  width: 100%;
  /* 设置容器宽度 */
  height: 100vh;
  /* 设置容器高度，使其占满整个视口 */
  overflow-y: hidden;
  /* 显示垂直滚动条 */
  overflow-x: hidden;
  /* 隐藏水平滚动条 */
}

h3 {
  font-size: 50px;
  color: #f19290;
}

h4 {
  font-size: 30px;
  color: #f19290;
}

h5 {
  font-size: 15px;
  color: #f19290;
}

.custom-link {
  font-size: 15px;
  color: #b95b66;
  /* 设置为深色 */
  /* 其他样式属性 */
}
.remember {
  transform: translate(17px, 13px);
}

.img-icon {
  height: 20px;
}
</style>
