<template>
  <div class="content_remind">
    <meta
      name="viewport"
      content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no"
    />
    <div style="--van-nav-bar-icon-color: #f19290">
      <van-nav-bar
        title="设置中心"
        left-text=""
        left-arrow
        @click-left="onClickLeft"
      />
    </div>
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
      "
    >
      <div style="margin-top: 2vh; margin-bottom: 2vh">
        <van-cell center title="定时提醒"> </van-cell>
        <van-cell-group inset>
          <van-cell center title="每日总结报告">
            <template #right-icon>
              <van-switch
                active-color="#f19290"
                v-model="checked"
                @change="change"
              />
            </template>
          </van-cell>
          <div v-if="checked == true">
            <van-cell title="提醒时间" :value="time" @click="changeTime" />
            <van-cell
              title="提醒结尾文案"
              :label="text"
              is-link
              @click="requireItem()"
            />
          </div>
          <div v-if="checked == true"></div>
        </van-cell-group>
      </div>
    </div>

    <div
      style="
        transform: translate(5vw, 8vh);
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
        <van-cell center title="用户设置"> </van-cell>
        <van-cell-group inset>
          <van-cell
            title="海报展示设置"
            replace
            :to="`/displaySetting`"
            is-link
          />

          <van-cell
            title="用户信息设置"
            replace
            :to="`/UserRequire/${userId}`"
            is-link
          />

          <van-cell title="主页通知设置" replace to="/homeRequire" is-link />
          <van-cell
            title="用户注册设置"
            :label="userLogin"
            is-link
            @click="requireUser()"
          />
        </van-cell-group>
      </div>
    </div>
    <van-popup v-model:show="show" round position="bottom">
      <van-time-picker
        v-model="currentTime"
        title="选择时间"
        @confirm="confirm(currentTime)"
        @cancel="cancel"
        cancel-button-text=" "
        confirm-button-text="完成"
      />
    </van-popup>
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
    title="修改提醒结尾"
    @confirm="reCheckConfirm"
    @cancel="reCheckCancel"
    show-cancel-button
  >
    <div style="margin-top: 3vh; margin-bottom: 4vh">
      <van-cell-group inset>
        <van-cell-group inset>
          <van-field
            v-model="reValue"
            rows="3"
            autosize
            label="提醒结尾文案"
            type="textarea"
            maxlength="100"
            placeholder="请输入提醒结尾文案"
            show-word-limit
          />
        </van-cell-group>
      </van-cell-group>
    </div>
  </van-dialog>
  <van-dialog
    v-model:show="show_4"
    title="修改用户注册密钥"
    @confirm="userCheckConfirm"
    @cancel="userCheckCancel"
    show-cancel-button
  >
    <div style="margin-top: 3vh; margin-bottom: 4vh">
      <van-cell-group inset>
        <van-cell-group inset>
          <van-field
            v-model="userValue"
            rows="2"
            autosize
            label="注册密钥"
            type="textarea"
            maxlength="20"
            placeholder="请输入用户注册密钥"
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
    const show = ref(false);
    const show_1 = ref(false);
    const show_2 = ref(false);
    const show_3 = ref(false);
    const show_4 = ref(false);
    const message = ref("");
    const checked = ref();
    const router = useRouter();
    const currentTime = ref(["12", "00"]);
    const time = ref("");
    const text = ref("");
    const userId = ref("");
    const remindSwitch = ref("");
    const reValue = ref("");
    const userValue = ref("");
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
          // 在这里处理登录令牌接口的响应
          // 如果需要执行一些特定的操作，可以在这里添加代码
        })
        .catch((error) => {
          console.error("请求loginToken接口失败", error);
          router.replace("/login");
        });
    };
    const userLogin = ref("");
    const fetchDataAndFillForm = () => {
      try {
        console.log(userId);
        axios
          .get(`/api/selectUser?id=${userId.value}`, {
            headers,
          })
          .then((response) => {
            const data = response.data.data; // 假设服务器返回的数据是一个包含上述字段的对象
            console.log(data);
            remindSwitch.value = data.remindSwitch;
            const parts = data.remindTime.split(" ");
            const minutes = parts[1];
            const hours = parts[2];
            checked.value = remindSwitch.value;
            // 格式化为 "hh:mm" 格式
            time.value = `${hours}:${minutes}`;
            text.value = data.remindText;
            currentTime.value = [hours, minutes];
          });
        axios
          .get("/api/signPassword", {
            headers,
          })
          .then((response) => {
            const data = response.data.data; // 假设服务器返回的数据是一个包含上述字段的对象
            console.log(data);
            userLogin.value = data;
          });
      } catch (error) {
        console.error("获取数据失败", error);
      }
      show_1.value = false;
    };
    const change = () => {
      if (checked.value == true) {
        axios
          .get("/api/openRemind", { headers })
          .then((response) => {
            const data = response.data.data; // 假设服务器返回的数据是一个包含上述字段的对象
            console.log(data);
            checked.value = true;
            remindSwitch.value = true;
          });
      } else {
        axios
          .get("/api/closeRemind", { headers })
          .then((response) => {
            const data = response.data.data; // 假设服务器返回的数据是一个包含上述字段的对象
            console.log(data);
            checked.value = false;
            remindSwitch.value = false;
          });
      }
    };
    const confirm = (currentTime) => {
      console.log(currentTime);
      const minutes = currentTime[1];
      const hours = currentTime[0];
      const cronExpression = `0 ${minutes} ${hours} * * ?`;
      axios
        .get(`/api/changeRemind?time=${cronExpression}`, {
          headers,
        })
        .then((response) => {
          const data = response.data.data; // 假设服务器返回的数据是一个包含上述字段的对象
          console.log(data);
          time.value = `${hours}:${minutes}`;
          message.value = data;
          show.value = false;
        });
      show_2.value = true; // 显示
      // 等待两秒后执行关闭操作
      setTimeout(() => {
        show_2.value = false; // 关闭
      }, 1000); // 2000毫秒等于2秒
    };

    const changeTime = () => {
      show.value = true;
    };

    const cancel = () => {
      show.value = false;
    };
    const requireItem = () => {
      show_3.value = true;
      return;
    };
    const reCheckConfirm = () => {
      const user = {
        nameId: userId.value,
        remindText: reValue.value,
      };
      fetch("/api/requireUser", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(user),
      })
        .then((response) => response.json()) // 将 .json() 放在正确的位置
        .then((data) => {
          if (data.code == 1) {
            console.log(data.data);
            try {
              message.value = data.data;
              text.value = reValue.value;
            } catch (error) {
              console.error("请求requireUser接口失败", error);
            }
          }
        })
        .catch((error) => {
          console.error("请求requireUser接口失败", error);
          // 处理请求失败的情况
        });
      show_2.value = true; // 显示
      // 等待两秒后执行关闭操作
      setTimeout(() => {
        show_2.value = false; // 关闭
      }, 1000); // 2000毫秒等于2秒
    };

    const reCheckCancel = () => {
      return;
    };

    const requireUser = () => {
      show_4.value = true;
    };
    const userCheckConfirm = () => {
      console.log(userValue.value);
      axios
        .get(
          `/api/RequireSignPassword?password=${userValue.value}`,
          {
            headers,
          }
        )
        .then((response) => {
          const data = response.data.data; // 假设服务器返回的数据是一个包含上述字段的对象
          message.value = data;
          userLogin.value = userValue.value;
        });
      show_2.value = true; // 显示
      // 等待两秒后执行关闭操作
      setTimeout(() => {
        show_2.value = false; // 关闭
      }, 1000); // 2000毫秒等于2秒
    };
    const userCheckCancel = () => {
      return;
    };
    const onClickLeft = () => router.replace("/User");
    return {
      reValue,
      changeTime,
      show,
      show_1,
      show_2,
      show_3,
      show_4,
      confirm,
      cancel,
      userId,
      text,
      time,
      change,
      checked,
      currentTime,
      remindSwitch,
      onClickLeft,
      message,
      requireItem,
      reCheckConfirm,
      reCheckCancel,
      userLogin,
      userValue,
      requireUser,
      userCheckConfirm,
      userCheckCancel,
    };
  },
};
</script>

<style>
.content_remind {
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