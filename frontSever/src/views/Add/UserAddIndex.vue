<template>
  <div class="content_useHome">
    <div style="--van-nav-bar-icon-color: #f19290">
      <van-nav-bar
        title="注册用户信息"
        left-text=""
        left-arrow
        @click-left="onClickLeft"
      />
    </div>
    <div
      style="
        display: flex;
        justify-content: center;
        align-items: center;
        transform: translateY(10px);
      "
    >
      <van-image
        width="80%"
        height="80%"
        fit="cover"
        position="center"
        :src="image"
      />
    </div>
    <van-loading vertical v-if="isLoading" class="loading-overlay">
      <template #icon>
        <van-icon name="star-o" size="30" />
      </template>
      加载中...
    </van-loading>
    <div style="z-index: 2; transform: translate(0vw, 1vh)">
      <van-form @submit="onSubmit">
        <meta
          name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no"
        />
        <van-uploader v-model="fileList" :after-read="afterRead" upload-text="上传头像" />

        <van-cell-group inset>
          <van-field
            v-model="keyword"
            name="用户名"
            label="🦄注册密钥"
            placeholder="注册密钥(必填)"
            clearable
          />
          <van-field
            v-model="username"
            name="更改用户名"
            label="🧩用户名"
            placeholder="用户名（必填）"
            clearable
          />
          <van-field
            v-model="desc"
            name="更改用户详情"
            label="📝用户详情"
            type="textarea"
            rows="2"
            placeholder="用户详情（选填）"
            clearable
          />
          <van-field
            v-model="password"
            type="password"
            name="注册密码"
            label="💥注册密码"
            placeholder="用户密码（必填）"
            clearable
          />
        </van-cell-group>
        <div style="margin: 16px; transform: translate(0vw, 0.8vh)">
          <van-button round block color="#F19290" native-type="submit">
            提交
          </van-button>
        </div>
      </van-form>
    </div>
    <van-dialog v-model:show="show" title="😭提示" message="注册密钥错误！">
    </van-dialog>
    <van-dialog
      v-model:show="show_1"
      title="🥰恭喜"
      message="注册成功！"
      width="70vw"
      height="30vw"
      :show-cancel-button="false"
      :show-confirm-button="false"
    >
    </van-dialog>
    <van-dialog v-model:show="show_2" title="😭提示" message="请完整填好上述信息！">
    </van-dialog>
  </div>
</template>

<script>
import { inject } from 'vue'
import { ref } from "vue";
import png from "@/util/useAdd.gif";
import { useRouter } from "vue-router";

export default {
  setup() {
    const myVariable = inject('loginLock') // 调用全局变量
    console.log(myVariable.value) // 输出：Hello world
    const image = [png];
    const router = useRouter();
    const show = ref(false);
    const show_1 = ref(false);
    const show_2 = ref(false);
    const onClickLeft = () => router.replace("/login");
    const isLoading = ref(false);

    const keyword = ref("");
    const username = ref("");
    const desc = ref("");
    const password = ref("");
    const fileList = ref([]);
    let imageUrl = ref("");

    const afterRead = (file) => {
      file.status = "uploading";
      file.message = "上传中...";

      const formData = new FormData();
      formData.append("file", file.file);

      fetch("/api/upload", {
        method: "POST",
        body: formData,
      })
        .then((response) => response.json())
        .then((data) => {
          // 这里假设服务器返回一个包含图片URL的JSON对象
          imageUrl.value = data.data;
          file.message = "上传成功";
          file.status = "";
        })
        .catch((error) => {
          console.error("上传失败：", error);
        });
    };

    const onSubmit = () => {
      if (keyword.value != myVariable.value) {
        show.value = true;
        return;
      }
      if (desc.value == "") {
        desc.value = "😂这个人很懒，没有填写相关的用户详情！";
      }
      if(username.value == "" || password.value == "" || imageUrl.value == ""){
        show_2.value = true;
        return;
      }
      // 获取URL中的参数
      // 创建包含参数的请求体
      const user = {
        signPassword:keyword.value,
        name: username.value,
        userImage: imageUrl.value,
        userText: desc.value,
        password: password.value,
      };

      isLoading.value = true;
      // 发送 POST 请求到指定的 URL
      fetch("/api/addUser", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(user),
      })
        .then((response) => {
          console.log(response.data);
          try {
            show_1.value = true;
            // 等待3秒后执行路由跳转
            setTimeout(() => {
              router.replace("/login");
            }, 2000);
          } catch (error) {
            console.error("请求addUser接口失败", error);
          }
        })
        .catch((error) => {
          console.error("请求requireUser接口失败", error);
          // 处理请求失败的情况
        });
      isLoading.value = false;
    };

    return {
      image,
      show,
      show_1,
      show_2,
      keyword,
      onClickLeft,
      username,
      desc,
      password,
      onSubmit,
      isLoading,
      fileList,
      afterRead,
    };
  },
};
</script>

<style>
.van-uploader {
  position: relative;
  display: inline-block;
  /* transform: translateX(158px); */
  display: flex;
  justify-content: center;
}

.content_useHome {
  zoom: 1;
  /* 禁止页面内容缩放 */
  width: 100%;
  height: 100vh;
  overflow-y: auto;
  /* 显示垂直滚动条 */
  overflow-x: hidden;
  /* 隐藏水平滚动条 */
}

.image-slider {
  justify-content: center;
}
</style>