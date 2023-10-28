<template>
  <div style="--van-nav-bar-icon-color: #f19290">
    <van-nav-bar
      title="超市商品"
      left-text=""
      left-arrow
      @click-left="onClickLeft"
    />
  </div>
  <div class="page-container">
    <div class="display_produce">
      <br />
      <div v-if="data === null">
        <!-- 如果为空，渲染 <van-empty> 组件 -->
        <div style="transform: translate(0px, -10px)">
          <van-empty
            image-size="26rem"
            description="😭商品已售完，快去创建一
            个吧！"
          />
        </div>
      </div>
      <div v-if="data">
        <van-form class="form-container">
          <div
            style="display: flex; justify-content: center; align-items: center"
          >
            <van-image
              width="22vh"
              height="22vh"
              position="center"
              fit="cover"
              round
              :src="imageUrl"
              @click="picture"
            />
          </div>
          <br />
          <meta
            name="viewport"
            content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no"
          />
          <van-field label="📌商品名称" v-model="title" :readonly="true" />
          <van-field
            label="📝商品详情"
            v-model="desc"
            type="textarea"
            rows="3"
            :readonly="true"
          />
          <van-field label="💰积分价格" v-model="credit" :readonly="true" />
          <van-field label="🧩商品数量" v-model="num" :readonly="true" />
          <van-field
            label="🦄创建对象"
            v-model="founderName"
            :readonly="true"
          />
          <van-field label="🤖商品对象" v-model="useName" :readonly="true" />
          <van-field label="⏱更新时间" v-model="updateTime" :readonly="true" />
          <van-field
            label="📅注册时间"
            v-model="registerTime"
            :readonly="true"
          />
          <div style="margin: 8px; transform: translateY(9px)">
            <van-button
              @click="data.produceNum > 0 ? buyItem(data) : haveBuyItem()"
              round
              block
              color="#F19290"
              native-type="submit"
            >
              {{ data.produceNum > 0 ? "购买商品" : "已售完" }}
            </van-button>
            <br />
          </div>
        </van-form>
      </div>
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
          <van-image
            width="100vw"
            height="100vw"
            position="center"
            fit="cover"
            :src="imageUrl"
          />
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
        v-model:show="show_2"
        @confirm="checkConfirm"
        @cancel="checkCancel"
        title="🥰 温馨提示"
        message="🦄您确定要购买这件商品了嘛？"
        show-cancel-button
      >
      </van-dialog>
      <van-dialog
        v-model:show="show_3"
        title="购买商品失败"
        message="😭您的积分不够，快去完成任务获取积分吧啊💪"
        show-cancel-button
      >
      </van-dialog>
      <van-dialog
        v-model:show="show_6"
        title="🥰 温馨提示"
        message="😂您不能购买别人的专属商品哦，快去完成自己的任务，购买属于自己的商品吧！"
      >
      </van-dialog>
      <van-dialog
        v-model:show="show_8"
        title="🥰恭喜"
        :message="message"
        width="70vw"
        height="30vw"
        :close-on-click-overlay="true"
        :show-cancel-button="false"
        :show-confirm-button="false"
      >
      </van-dialog>
    </div>
  </div>
</template>
  
  <script>
import { ref, onMounted } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";
import jwtDecode from "jwt-decode";

export default {
  setup() {
    const picturewidth = ref();
    picturewidth.value = 0.8 * window.innerWidth;
    const router = useRouter();
    const message = ref("");
    const show = ref(false);
    const show_1 = ref(false);
    const show_2 = ref(false);
    const show_3 = ref(false);
    const show_6 = ref(false);
    const show_8 = ref(false);

    const picture = () => (show.value = true);

    const onClickLeft = () => router.replace("/Produce");
    const imageUrl = ref("");
    const title = ref("");
    const desc = ref("");
    const credit = ref("");
    const num = ref("");
    const founderName = ref("");
    const useName = ref("");
    const registerTime = ref("");
    const updateTime = ref("");
    const data = ref("");
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
          }
          console.log(response.data.data);
          // 在这里处理登录令牌接口的响应
          // 如果需要执行一些特定的操作，可以在这里添加代码
        })
        .catch((error) => {
          console.error("请求loginToken接口失败", error);
          router.replace("/login");
        });
      const decodedToken = jwtDecode(token);
      // 从解码后的令牌中获取特定的数据
      userId = decodedToken.id; // 从令牌中获取用户ID
    };

    const id = router.currentRoute.value.params.id;

    let strippedId = id.replace(":", "");

    const fetchDataAndFillForm = async () => {
      try {
        const response = await axios.get(
          `/api/idSelectProduce?id=${strippedId}`,
          { headers }
        );
        data.value = response.data.data; // 假设服务器返回的数据是一个包含上述字段的对象

        // 填充表单字段
        imageUrl.value = data.value.produceImage;
        title.value = data.value.produceName;
        if (data.value.produceText == "") {
          desc.value = "😂这个人很懒，没有填写相关的商品详情！";
        } else desc.value = data.value.produceText;
        credit.value = data.value.produceCredit;
        num.value = data.value.produceNum;
        useName.value = data.value.customer;
        registerTime.value = data.value.registerTime;
        updateTime.value = data.value.updateTime;
        const founderId = data.value.founderId;

        const res = await axios.get(
          `/api/selectUser?id=${founderId}`,
          { headers }
        );
        const resDate = res.data.data; // 假设服务器返回的数据是一个包含上述字段的对象
        founderName.value = resDate.name;
      } catch (error) {
        console.error("获取数据失败", error);
      }
      show_1.value = false;
    };
    // 在组件加载完成后自动触发数据加载和填充
    onMounted(() => {
      fetchDataAndFillForm();
      fetchLoginToken();
    });

    let data_tem = "";
    const buyItem = (data) => {
      axios
        .get(`/api/selectCredit?name=${data.customer}`, {
          headers,
        })
        .then((response) => {
          const nameId = response.data.data.nameId;
          if (nameId == userId) {
            data_tem = data;
            show_2.value = true;
          } else show_6.value = true;
        });
    };
    const checkConfirm = () => {
      show_1.value = true; // 初始时显示加载中效果
      buyProduce(data_tem);
    };
    const checkCancel = () => {
      return;
    };
    const buyProduce = (data_tem) => {
      axios
        .get(
          `/api/selectCredit?name=${data_tem.customer}`,
          {
            headers,
          }
        )
        .then((response) => {
          // 处理获取成功的逻辑
          console.log("获取数据成功", response);
          const buyCredit = response.data.data.credit;
          if (buyCredit >= data_tem.produceCredit) {
            // 发送 HTTP PUT 请求来完成购买操作
            axios
              .put(
                `/api/buyProduce?id=${data_tem.produceId}`,
                null,
                {
                  headers,
                }
              )
              .then((response) => {
                // 处理完成成功的逻辑
                console.log("任务已保存成功", response.data);
                show_1.value = false;
                message.value = "购买成功！";
                num.value = num.value - 1;
                data.value.produceNum = data.value.produceNum - 1;
              })
              .catch((error) => {
                // 处理完成失败的逻辑
                console.error("数据完成失败", error);
                message.value = "购买失败！";
              });
          } else {
            show_3.value = true;
          }
        })
        .catch((error) => {
          // 处理获取失败的逻辑
          console.error("获取数据失败", error);
        });
      show_8.value = true; // 显示
      // 等待两秒后执行关闭操作
      setTimeout(() => {
        show_8.value = false; // 关闭
      }, 1000); // 2000毫秒等于2秒
      show_1.value = false;
    };

    const haveBuyItem = () => {
      return;
    };
    return {
      message,
      data,
      picturewidth,
      show,
      show_1,
      show_2,
      show_3,
      show_6,
      show_8,
      picture,
      onClickLeft,
      imageUrl,
      title,
      desc,
      credit,
      num,
      founderName,
      useName,
      registerTime,
      updateTime,
      fetchDataAndFillForm,
      checkConfirm,
      checkCancel,
      buyItem,
      haveBuyItem,
    };
  },
};
</script>
  
  <style>
.page-container {
  display: flex;
  justify-content: center;
  align-items: center;
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

.display_produce {
  width: 90%;
  margin-bottom: 10vh;
}

.form-container {
  margin-top: 0px;
}
</style>
  