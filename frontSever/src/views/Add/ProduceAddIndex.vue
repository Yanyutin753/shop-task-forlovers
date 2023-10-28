<template>
  <div style="--van-nav-bar-icon-color: #f19290">
    <van-nav-bar
      title="添加商品"
      left-text=""
      left-arrow
      @click-left="onClickLeft"
    />
  </div>
  <div class="page-container">
    <div class="add_produce">
      <van-swipe :autoplay="3000" indicator-color="#F19290" lazy-render>
        <van-swipe-item v-for="image in images" :key="image">
          <div
            style="display: flex; justify-content: center; align-items: center"
          >
            <img :src="image" class="image-slider" alt="商品图片" />
          </div>
        </van-swipe-item>
      </van-swipe>
      <van-loading vertical v-if="isLoading" class="loading-overlay">
        <template #icon>
          <van-icon name="star-o" size="30" />
        </template>
        加载中...
      </van-loading>
      <van-form class="form-container_produceAdd">
        <meta
          name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no"
        />
        <van-uploader
          v-model="fileList"
          :after-read="afterRead"
          upload-text="商品图片"
          reupload
          max-count="1"
        />
        <van-field
          v-model="presetIndex"
          is-link
          readonly
          label="🔖选择预设"
          placeholder="选择预设"
          @click="showPicker = true"
        />
        <van-popup v-model:show="showPicker" round position="bottom ">
          <van-picker
            :columns="columns"
            @cancel="showPicker = false"
            @confirm="onConfirm"
          />
        </van-popup>
        <van-field
          label="📌商品名称"
          v-model="title"
          placeholder="请输入商品名称"
          clearable
        />
        <van-field
          label="📝商品详情"
          v-model="desc"
          placeholder="请输入商品详情"
          type="textarea"
          clearable
        />
        <van-field
          label="💰积分价格"
          v-model="credit"
          placeholder="请输入商品积分价格(整数)"
          clearable
        />
        <van-field
          label="🧩商品数量"
          v-model="num"
          placeholder="请输入商品数量"
          clearable
        />
        <!-- <van-field label="🤖商品对象" v-model="useName" placeholder="请输入商品对象" clearable /> -->
        <van-field
          v-model="useName"
          is-link
          readonly
          name="picker"
          label="🤖商品对象"
          placeholder="点击选择商品对象"
          @click="showPicker_1 = true"
        />
        <van-popup v-model:show="showPicker_1" position="bottom">
          <van-picker
            :columns="userColumns"
            @confirm="onConfirm_1"
            @cancel="showPicker_1 = false"
          />
        </van-popup>
      </van-form>

      <div class="footer_produce">
        <van-button class="btn-reset" type="default" @click="resetItem"
          >重置</van-button
        >
        <van-button
          class="btn-save"
          color="#F19290"
          type="primary"
          @click="saveItem"
          >保存</van-button
        >
      </div>
    </div>
    <van-dialog
      v-model:show="show"
      title="🥰 温馨提示"
      :message="message"
      :show-cancel-button="false"
    >
    </van-dialog>
    <van-dialog
      v-model:show="show_1"
      title="🥰恭喜"
      :message="message"
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
import axios from "axios";
import png from "@/util/Item.gif";
import { useRouter } from "vue-router";
import { Field, Form, Button, Swipe, SwipeItem } from "vant";
import jwtDecode from "jwt-decode";

export default {
  components: {
    VanField: Field,
    VanForm: Form,
    VanButton: Button,
    VanSwipe: Swipe,
    VanSwipeItem: SwipeItem,
  },

  setup() {
    const show = ref(false);
    const show_1 = ref(false);
    const message = ref("");
    const isLoading = ref(false);
    const images = [png];
    const fileList = ref([]);
    const presetIndex = ref("无预设");
    const title = ref("");
    const desc = ref("");
    const num = ref("");
    const useName = ref("");
    const credit = ref("");
    let imageUrl = ref("");
    const showPicker = ref(false);
    const router = useRouter();
    const onClickLeft = () => router.replace("/Produce");
    const userColumns = ref([]);
    const showPicker_1 = ref(false);
    onMounted(() => {
      fetchLoginToken();
      fetchUserData();
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
          const decodedToken = jwtDecode(token);
          // 从解码后的令牌中获取特定的数据
          userId = decodedToken.id; // 从令牌中获取用户ID
          // 在这里处理登录令牌接口的响应
          // 如果需要执行一些特定的操作，可以在这里添加代码
        })
        .catch((error) => {
          console.error("请求loginToken接口失败", error);
          router.replace("/login");
        });
    };

    const fetchUserData = async () => {
      try {
        const response = await axios.get("/api/user", {
          headers,
        }); // 替换为实际的API端点
        const userData = response.data.data; // 假设API返回一个包含用户数据的数组
        // 2. 将用户数据转换为选择器的列格式
        userColumns.value = userData
          .filter((user) => user.nameId !== userId)
          .map((user) => ({
            text: user.name,
            value: user.nameId,
          }));
      } catch (error) {
        console.error("获取用户数据时出错", error);
      }
    };

    const onConfirm_1 = ({ selectedOptions }) => {
      useName.value = selectedOptions[0]?.text;
      showPicker_1.value = false;
    };
    const columns = [
      { text: "无预设", value: "0" },
      { text: "薯片", value: "1" },
      { text: "奶茶券", value: "2" },
      { text: "夜宵券", value: "3" },
      { text: "做运动", value: "4" },
      { text: "吃顿好的", value: "5" },
      { text: "不赖床", value: "6" },
      { text: "买小礼物", value: "7" },
      { text: "跑腿券", value: "8" },
      { text: "衣服裤子", value: "9" },
      { text: "好看鞋子", value: "10" },
      { text: "小吃一样", value: "11" },
    ];

    const onConfirm = ({ selectedOptions }) => {
      showPicker.value = false;
      presetIndex.value = selectedOptions[0].text;
      onPresetChange(presetIndex.value); // 调用 onPresetChange 函数，手动触发值变化
    };

    const onPresetChange = (value) => {
      // 根据选择的预设值来自动填充其他信息
      if (value === "薯片") {
        title.value = "美味薯片";
        desc.value =
          "诱人的零食，夜宵绝佳伴侣，咔嘣脆！凭此商品可以向对方索要薯片。";
      } else if (value === "奶茶券") {
        title.value = "奶茶权限";
        desc.value = "凭此券可以向对方索要一杯奶茶。";
      } else if (value === "夜宵券") {
        title.value = "夜宵放开闸";
        desc.value = "凭此券可以让自己在夜里狂野干饭。";
      } else if (value === "做运动") {
        title.value = "减肥券";
        desc.value =
          "凭此券可以逼迫对方做一次运动，以此来达到减肥维持健康的目的。";
      } else if (value === "吃顿好的") {
        title.value = "饭票";
        desc.value = "凭此券可以让对方做一次或请一次饭，具体视情况而定。";
      } else if (value === "不赖床") {
        title.value = "早起券";
        desc.value =
          "凭此券可以让对方早起床一次。熬夜对身体很不好，还是要早点睡觉第二天才能有精神！";
      } else if (value === "买小礼物") {
        title.value = "小礼物盒";
        desc.value = "凭此券可以让对方买点小礼物，像泡泡马特什么的。";
      } else if (value === "跑腿券") {
        title.value = "跑腿召唤";
        desc.value =
          "有了我，你再也不需要移动了。拿外卖，拿零食，开空调，开电视，在所不辞。";
      } else if (value === "衣服裤子") {
        title.value = "衣服裤子券";
        desc.value =
          "凭此券可以购买衣服或者裤子一件，买好看的衣服，变得更好看！";
      } else if (value === "好看鞋子") {
        title.value = "好看鞋子券";
        desc.value = "凭此卷可以购买一双心仪已久的鞋子，价格看具体情况而定！";
      } else if (value === "小吃一样") {
        title.value = "小吃一样";
        desc.value = "小小奖励一下自己，可以吃点好吃的，放松一下！";
      }
    };

    const afterRead = (file) => {
      file.status = "uploading";
      file.message = "上传中...";

      const formData = new FormData();
      formData.append("file", file.file);

      fetch("/api/upload", {
        method: "POST",
        headers: headers, // 添加请求头
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

    const resetItem = () => {
      presetIndex.value = "无预设"; // 选择预设的值重置为0，使用.value
      title.value = ""; // 任务名称重置为空
      desc.value = ""; // 任务详情重置为空
      credit.value = ""; // 积分价格重置为0
      num.value = "";
      useName.value = "";
    };

    const saveItem = () => {
      isLoading.value = true;
      if (desc.value == "") {
        desc.value = "😂这个人很懒，没有填写相关的商品详情！";
      }
      if (useName.value == "" || imageUrl.value == "" || title.value == "") {
        message.value = "请完整填好上述信息！";
        show.value = true;
        isLoading.value = false;
        return;
      }
      const value = parseFloat(credit.value);
      if (!Number.isInteger(value)) {
        message.value = "积分只能是整数哦，请重新输入！";
        show.value = true;
        isLoading.value = false;
        return;
      }
      // 准备要发送的数据对象
      const task = {
        produceName: title.value,
        founderId: userId,
        produceText: desc.value,
        produceImage: imageUrl.value,
        produceCredit: credit.value,
        produceNum: num.value,
        customer: useName.value,
      };

      // 发送 POST 请求到指定的 URL
      fetch("/api/addProduce", {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(task),
      })
        .then((response) => {
          if (response.ok) {
            isLoading.value = false;
            // 请求成功，可以处理成功的逻辑
            message.value = "保存商品成功!";
          } else {
            // 请求失败，处理错误逻辑
            console.error("保存商品失败");
            message.value = "保存商品失败!";
          }
        })
        .catch((error) => {
          // 捕获异常
          console.error("保存任务时出现错误:", error);
        });
      show_1.value = true;
      setTimeout(() => {
        router.replace("/Produce");
      }, 1000); // 2000毫秒等于2秒
      isLoading.value = false;
    };

    // 注册 beforeUnmount 生命周期钩子以在组件即将卸载时移除 meta 标签
    return {
      show,
      show_1,
      message,
      isLoading,
      columns,
      onConfirm,
      showPicker,
      afterRead,
      fileList,
      presetIndex,
      title,
      desc,
      num,
      useName,
      credit,
      resetItem,
      saveItem,
      onClickLeft,
      images,
      userColumns,
      onConfirm_1,
      showPicker_1,
    };
  },
};
</script>

<style>
.van-picker__confirm {
  color: #f19290;
}

.page-container {
  display: flex;
  justify-content: center;
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

.add_produce {
  margin-bottom: 5vh;
  width: 90%;
}

.image-slider {
  width: 30vh;
  height: 30vh;
  object-fit: cover;
  justify-content: center;
}

.form-container_produceAdd {
  margin-top: 13px;
}

.footer_produce {
  display: flex;
  justify-content: space-between;
  margin-top: 15px;
}

.btn-reset {
  flex: 1;
  margin-right: 10px;
}

.btn-save {
  flex: 1;
  margin-left: 10px;
}

.bottom-tabbar {
  position: fixed;
  bottom: 0;
  width: 100%;
  background-color: #fff;
  border-top: 1px solid #ccc;
}
:root {
  --van-gray-3: #ffffff;
}
</style>
