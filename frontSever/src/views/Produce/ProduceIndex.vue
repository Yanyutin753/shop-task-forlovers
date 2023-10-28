<template>
  <div class="content_produce">
    <div class="produce">
      <keep-alive>
        <van-swipe :autoplay="3000" indicator-color="#F19290" lazy-render>
          <van-swipe-item v-for="image in images" :key="image">
            <img :src="image" style="width: 100%; height: 62.5vw" />
          </van-swipe-item>
        </van-swipe>
      </keep-alive>
      <van-dialog
        v-model:show="show_7"
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
      <div style="transform: translate(0px, 2px)">
        <form action="/">
          <meta
            name="viewport"
            content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no"
          />
          <van-search
            v-model="value"
            shape="round"
            background="rgb(250 250 250 / 22%)"
            placeholder="请输入搜索商品关键词"
            @search="onSearch"
          />
        </form>
      </div>
      <van-floating-bubble
        v-model:offset="offset_produce"
        axis="xy"
        icon="add-o"
        @click="onClick"
      />
      <div v-if="produceList.length === 0">
        <!-- 如果为空，渲染 <van-empty> 组件 -->
        <div style="transform: translate(0px, -10px)">
          <van-empty
            image-size="17rem"
            description="😭暂无找到该商品，快去购买一个吧！"
          />
        </div>
      </div>
      <div v-if="produceList.length > 0">
        <div style="transform: translate(6px, -3px)">
          <h1>
            🎁 购买商品
            <van-icon
              name="point-gift-o"
              :badge="produceList.length.toString()"
              :badge-props="{ color: '#ee575e' }"
            />
          </h1>
        </div>
        <div v-for="(item, index) in produceList" :key="index">
          <div
            style="
              margin-top: 12px;
              margin-bottom: 12px;
              position: relative;
              overflow: hidden;
              cursor: grab;
              background-color: #fff;
              box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.025);
              border-radius: 10px;
              width: 96%;
              transform: translateX(2%);
            "
          >
            <van-swipe-cell>
              <van-card
                :price="formatNumber(item.produceCredit)"
                :desc="truncateText(item)"
                :title="item.produceName"
                class="goods-card"
                :thumb="item.produceImage"
                currency="💰"
                @click="disPlay(item.produceId)"
              >
                <template #price>
                  <div
                    style="transform: translate(-3px, -3px); font-size: 14px"
                  >
                    💰{{ formatNumber(item.produceCredit) }}
                  </div>
                </template>
                <template #tag>
                  <div
                    v-if="item.checkLove % 2 == 1"
                    style="
                      transform: translate(calc(96vw - 105px), -6px);
                      opacity: 0.3;
                    "
                  >
                    <van-image
                      :src="imageUrl1"
                      width="85"
                      height="80"
                      class="image-slider"
                    />
                  </div>
                </template>
                <template #tags>
                  <van-tag plain color="#F19290">出售给</van-tag>
                  <van-tag plain color="#F19290">{{ item.customer }}</van-tag>
                </template>
                <template #num>
                  <div style="transform: translate(4px, 4.5px)">
                    x{{ item.produceNum }}
                  </div>
                </template>
              </van-card>
              <template #right>
                <div style="margin-left: 10px">
                  <van-button
                    square
                    icon="gold-coin-o"
                    color="#50A7D9"
                    class="button_produce"
                    @click="buyItem(item.produceId, index, item)"
                  />
                  <van-button
                    square
                    icon="star-o"
                    color="#FFE1B2"
                    class="button_produce"
                    @click="loveItem(item)"
                  />
                  <van-button
                    square
                    icon="delete-o"
                    color="#F19290"
                    class="button_produce"
                    @click="deleteItem(item.produceId, index, item)"
                  />
                </div>
              </template>
            </van-swipe-cell>
          </div>
        </div>
        <br />
        <van-dialog
          v-model:show="show_1"
          @confirm="handleConfirm"
          @cancel="handleCancel"
          title="🥰 温馨提示"
          message="🦄您确定已经删除这件商品吗，删除之后就找不到咯，请您要仔细认真考虑哦！"
          show-cancel-button
        >
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
          v-model:show="show_5"
          title="🥰 温馨提示"
          message="😂您不能删除别人创建的商品哦，快去完成自己的任务，购买商品吧！"
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
        <van-back-top right="83vw" bottom="10vh" />
      </div>
    </div>
    <van-tabbar
      v-model="active"
      active-color="#F19290"
      :safe-area-inset-bottom="true"
    >
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
import png from "@/util/3.gif";
import jwtDecode from "jwt-decode";
import jpg1 from "@/assets/image/produce1.jpg";
import jpg2 from "@/assets/image/produce2.jpg";
import jpg3 from "@/assets/image/produce3.jpg";
import HomeColor from "@/assets/tabBar/HomeIconColor.jpg";
import HomeGrey from "@/assets/tabBar/HomeIconGrey.jpg";
import task1 from "@/assets/tabBar/task1.jpg";
export default {
  setup() {
    const icon = {
      active: HomeColor,
      inactive: HomeGrey,
    };
    const imageUrl1 = [task1];
    const image = [png];
    const show_1 = ref(false);
    const show_2 = ref(false);
    const show_3 = ref(false);
    const show_4 = ref(false);
    const show_5 = ref(false);
    const show_6 = ref(false);
    const show_8 = ref(false);
    const message = ref("");
    const active = ref(1);
    const router = useRouter();
    const show_7 = ref(true); // 初始时显示加载中效果
    const disPlay = (url) => {
      router.replace(`/ProduceDisplay/${url}`);
    };
    const images = [jpg1, jpg2, jpg3];

    var y = window.innerHeight * 0.74;
    var x = window.innerWidth * 0.75;
    const offset_produce = ref({ x: x, y: y });
    const onClick = () => {
      router.replace("/produceAdd/1");
    };
    const produceList = ref([]);
    const value = ref("");
    const val = value.value;
    const onSearch = (val) => {
      show_7.value = true;
      fetchData(val);
    };

    onMounted(() => {
      fetchLoginToken();
      onSearch(val);
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
          console.error("请求loginToken接口失败", error);
          router.replace("/login");
        });
    };

    const fetchData = (val) => {
      // 使用 axios 或 fetch 发起 API 请求，获取商品数据
      // 假设 API 路径为 '/api/products'
      // 在发起数据请求之前
      console.log("Before fetching data. produceList:", produceList.value);

      axios
        .get(`/api/nameSelectProduce?name=${val}`, {
          headers,
        })
        .then((response) => {
          // 请求成功，将获取的数据存储在 produceList.value 数组中
          produceList.value = response.data.data;
          console.log(
            "Data fetched successfully. produceList:",
            produceList.value
          );
          show_7.value = false;
        })
        .catch((error) => {
          console.error("Failed to fetch data:", error);
        });
    };

    let index_tem = "";
    let position_tem = "";
    let item_tem = "";
    const deleteItem = (index, position, item) => {
      if (item.founderId == userId) {
        // 重新赋值
        index_tem = index;
        position_tem = position;
        show_1.value = true;
      } else {
        show_5.value = true;
      }
    };
    const handleConfirm = () => {
      show_7.value = true;
      deleteProduce(index_tem, position_tem);
    };
    const handleCancel = () => {
      return;
    };
    const deleteProduce = (ProduceId, position) => {
      axios
        .delete(`/api/deleteProduce?id=${ProduceId}`, {
          headers,
        })
        .then((response) => {
          // 处理删除成功的逻辑
          console.log("数据删除成功", response);
          // 删除成功后重新加载页面
          produceList.value.splice(position, 1); // 从数组中移除商品项
          message.value = "删除成功!";
        })
        .catch((error) => {
          // 处理删除失败的逻辑
          console.error("数据删除失败", error);
          message.value = "删除失败!";
        });
      show_8.value = true; // 显示
      // 等待两秒后执行关闭操作
      setTimeout(() => {
        show_8.value = false; // 关闭
      }, 1000); // 2000毫秒等于2秒
      show_7.value = false;
    };

    const buyItem = (index, position, item) => {
      axios
        .get(`/api/selectCredit?name=${item.customer}`, {
          headers,
        })
        .then((response) => {
          const nameId = response.data.data.nameId;
          if (nameId == userId) {
            index_tem = index;
            position_tem = position;
            item_tem = item;
            show_2.value = true;
          } else show_6.value = true;
        });
    };
    const checkConfirm = () => {
      show_7.value = true; // 初始时显示加载中效果
      buyProduce(index_tem, position_tem, item_tem);
    };
    const checkCancel = () => {
      return;
    };
    const buyProduce = (ProduceId, position, item) => {
      axios
        .get(`/api/selectCredit?name=${item.customer}`, {
          headers,
        })
        .then((response) => {
          // 处理获取成功的逻辑
          console.log("获取数据成功", response);
          const buyCredit = response.data.data.credit;
          if (buyCredit >= item.produceCredit) {
            // 发送 HTTP PUT 请求来完成购买操作
            axios
              .put(
                `/api/buyProduce?id=${ProduceId}`,
                null,
                {
                  headers,
                }
              )
              .then((response) => {
                // 处理完成成功的逻辑
                console.log("任务已保存成功", response.data);
                show_7.value = false;
                message.value = "购买成功！";
                item.produceNum = item.produceNum - 1;
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
      show_7.value = false;
    };
    const goodItem = () => {
      show_4.value = true;
    };
    const formatNumber = (value) => {
      if (value === null || value === undefined) return "";
      // 使用toFixed方法将数字格式化为带两位小数的字符串
      return value.toFixed(2);
    };
    const truncateText = (item) => {
      if (item.checkLove % 2 == 1)
        return item.produceText.substring(0, 13) + "...";
      // 使用toFixed方法将数字格式化为带两位小数的字符串
      else return item.produceText;
    };
    const loveItem = (item) => {
      axios
        .get(
          `/api/updateLoveProduce?id=${item.produceId}`,
          {
            headers,
          }
        )
        .then((response) => {
          // 请求成功，将获取的数据存储在 productList_1.value 数组中
          item.checkLove = !item.checkLove;
          show_6.value = false;
          console.log("数据收藏成功", response);
        })
        .catch((error) => {
          console.error("Failed to fetch data:", error);
        });
    };
    return {
      formatNumber,
      icon,
      image,
      disPlay,
      show_1,
      show_2,
      show_3,
      show_4,
      show_5,
      show_6,
      goodItem,
      show_7,
      show_8,
      message,
      value,
      offset_produce,
      onClick,
      handleConfirm,
      handleCancel,
      checkConfirm,
      checkCancel,
      deleteItem,
      buyItem,
      produceList,
      onSearch,
      active,
      images,
      truncateText,
      loveItem,
      imageUrl1,
    };
  },
};
</script>
<style>
.produce {
  margin-bottom: 10vh;
  overflow-y: auto;
  /* 显示垂直滚动条 */
  overflow-x: hidden;
  /* 隐藏水平滚动条 */
}
.content_produce {
  background: rgb(250 250 250 / 22%);
  zoom: 1;
  /* 禁止页面内容缩放 */
  width: 100vw;
  /* 设置容器宽度 */
  height: 100vh;
  /* 设置容器高度，使其占满整个视口 */
  overflow-y: auto;
}

:root {
  --van-gray-3: #ffffff;
}

.van-card {
  background: white;
}

.van-tag--mark {
  border-radius: 0 0 0 0;
}

h1 {
  font-family: "Ma Shan Zheng", cursive;
  transform: translateX(2vw);
  font-size: 20px;
  color: #ec8aa4;
}
.van-card__bottom {
  font-size: 18px;
}
.van-card__price {
  transform: translateY(5px);
}
.van-button__icon {
  font-size: 60px;
  line-height: inherit;
}

.van-back-top {
  background-color: #b55151;
}

.van-button--mini {
  border: var(--van-button-border-width) solid #f19290;
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
.button_produce {
  margin: 10px;
  width: 60px;
  height: 60px;
  border-radius: 50%;
  transform: translateY(12px);
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
.van-tabbar {
  height: 10vh;
}

.van-dialog__confirm,
.van-dialog__confirm:active {
  color: #f19290;
}
</style>
