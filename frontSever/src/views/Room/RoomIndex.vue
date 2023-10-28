<template>
  <div class="content_room">
    <div class="room">
      <van-swipe :autoplay="3000" indicator-color="#F19290" lazy-render>
        <van-swipe-item v-for="image in images" :key="image">
          <img :src="image" style="width: 100%; height: 62.5vw" />
        </van-swipe-item>
      </van-swipe>
      <van-dialog
        v-model:show="show_6"
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
      <div v-if="productList_0.length === 0 && productList_1.length === 0">
        <!-- 如果为空，渲染 <van-empty> 组件 -->
        <div style="transform: translate(0px, -10px)">
          <van-empty
            image-size="17rem"
            description="😭暂无找到该商品，快去创建一个吧！"
          />
        </div>
      </div>
      <div v-if="productList_0.length > 0 || productList_1.length > 0">
        <div style="transform: translate(6px, -3px)">
          <h1>
            🌈 未兑换
            <van-icon
              name="cart-o"
              :badge="productList_0.length.toString()"
              :badge-props="{ color: '#ee575e' }"
            />
          </h1>
        </div>
        <div v-for="(item, index) in productList_0" :key="index">
          <div
            style="
              margin-top: 14px;
              margin-bottom: 14px;
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
                :desc="truncateText(item)"
                :title="item.produceName"
                class="goods-card"
                :thumb="item.produceImage"
                @click="disPlay(item.produceId)"
              >
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
                  <div style="transform: translateY(9px)">
                    <van-tag plain color="#F19290"
                      >📅 {{ formatDate(item.registerTime) }}</van-tag
                    >
                  </div>
                  <div style="transform: translateY(19px)">
                    <van-tag plain color="#F19290"
                      >💰 {{ item.useName }}</van-tag
                    >
                  </div>
                </template>

                <template #num>
                  <div style="transform: translate(0px, 4.5px)">
                    x{{ item.produceNum }}
                  </div>
                </template>
              </van-card>
              <template #right>
                <div style="margin-left: 10px">
                  <van-button
                    square
                    icon="point-gift-o"
                    color="#F19290"
                    class="delete-button_room"
                    @click="checkItem(item.produceId, index, item)"
                  />
                  <van-button
                    square
                    icon="star-o"
                    color="#FFE1B2"
                    class="delete-button_room"
                    @click="loveItem(item)"
                  />
                  <van-button
                    square
                    icon="phone-circle-o"
                    color="#FBC2EB"
                    class="delete-button_room"
                    @click="remindItem(item)"
                  />
                </div>
              </template>
            </van-swipe-cell>
          </div>
        </div>

        <div
          style="
            transform: translateX(6px);
            margin-top: 20px;
            margin-bottom: 15px;
          "
        >
          <h1>
            🤖 已兑换
            <van-icon
              name="cart-o"
              :badge="productList_1.length.toString()"
              :badge-props="{ color: '#ee575e' }"
            />
          </h1>
        </div>
        <div v-for="(item, index) in productList_1" :key="index">
          <div
            style="
              margin-top: 14px;
              margin-bottom: 14px;
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
                :desc="truncateText(item)"
                :title="item.produceName"
                class="goods-card"
                :thumb="item.produceImage"
                @click="disPlay(item.produceId)"
              >
                <template #num>
                  <div style="transform: translate(0px, 4.5px)">
                    x{{ item.produceNum }}
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
                  <div style="transform: translateY(9px)">
                    <van-tag plain color="#F19290"
                      >📅 {{ formatDate(item.registerTime) }}</van-tag
                    >
                  </div>
                  <div style="transform: translateY(19px)">
                    <van-tag plain color="#F19290"
                      >{{ item.useName }} 已兑换</van-tag
                    >
                  </div>
                </template>
              </van-card>
              <template #right>
                <div style="margin-left: 10px">
                  <van-button
                    square
                    icon="star-o"
                    color="#FFE1B2"
                    class="delete-button_room"
                    @click="loveItem(item)"
                  />
                  <van-button
                    square
                    icon="delete-o"
                    color="#F19290"
                    class="delete-button_room"
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
          message="🦄您确定已经拿到商品了吗，删除之后就找不到咯，请您要仔细检查哦！"
          show-cancel-button
        >
        </van-dialog>
        <van-dialog
          v-model:show="show_2"
          @confirm="checkConfirm"
          @cancel="checkCancel"
          title="🥰 温馨提示"
          message="🦄您确定已经兑换商品了吗，请您要仔细检查哦！"
          show-cancel-button
        >
        </van-dialog>

        <!-- 弹出层 -->
        <van-dialog
          v-model:show="show_3"
          title="💖 感谢您的心心 💖"
          width="308"
          show-cancel-button
        >
          <img :src="image" />
        </van-dialog>
        <van-dialog
          v-model:show="show_4"
          title="🥰 温馨提示"
          message="😂您不能兑换别人购买的商品哦，快去完成自己的任务，购买属于自己的商品吧！"
        >
        </van-dialog>
        <van-dialog
          v-model:show="show_5"
          title="🥰 温馨提示"
          message="😂您不能删除别人兑换的商品哦，快去完成自己的任务，购买属于自己的商品吧！"
        >
        </van-dialog>
      </div>
      <van-back-top right="83vw" bottom="10vh" />
      <!-- 其他内容 -->
      <!-- 其他内容 -->
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
  <van-dialog
    v-model:show="show_7"
    title="🥰提示"
    :message="message"
    width="70vw"
    height="30vw"
    :close-on-click-overlay="true"
    :show-cancel-button="false"
    :show-confirm-button="false"
  >
  </van-dialog>
</template>

<script>
import moment from "moment";
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";
import png from "@/util/4.gif";
import jwtDecode from "jwt-decode";
import jpg1 from "@/assets/image/room1.jpg";
import jpg2 from "@/assets/image/room2.jpg";
import jpg3 from "@/assets/image/room3.jpg";
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
    const show_6 = ref(true); // 初始时显示加载中效果
    const show_7 = ref(false);
    const message = ref("");
    const active = ref(3);
    const router = useRouter();
    const images = [jpg1, jpg2, jpg3];
    const disPlay = (url) => {
      router.replace(`/RoomDisplay/${url}`);
    };
    const offset = ref({ x: 300, y: 550 });
    const productList_0 = ref([]);
    const productList_1 = ref([]);
    const value = ref("");
    const val = value.value;
    const onSearch = (val) => {
      show_6.value = true;
      fetchData_0(val);
      fetchData_1(val);
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

    const fetchData_0 = (val) => {
      // 使用 axios 或 fetch 发起 API 请求，获取商品数据
      // 假设 API 路径为 '/api/products'
      // 在发起数据请求之前
      console.log("Before fetching data. productList_0:", productList_0.value);

      axios
        .get(`/api/nameNoSelectRoom?name=${val}`, {
          headers,
        })
        .then((response) => {
          // 请求成功，将获取的数据存储在 productList_0.value 数组中
          productList_0.value = response.data.data;
          console.log(
            "Data fetched successfully. productList_0:",
            productList_0.value
          );
          show_6.value = false;
        })
        .catch((error) => {
          console.error("Failed to fetch data:", error);
        });
    };
    // 使用 ref 创建响应式数据
    const fetchData_1 = (val) => {
      // 使用 axios 或 fetch 发起 API 请求，获取商品数据
      // 假设 API 路径为 '/api/products'
      // 在发起数据请求之前
      console.log("Before fetching data. productList_1:", productList_1.value);

      axios
        .get(`/api/nameYesSelectRoom?name=${val}`, {
          headers,
        })
        .then((response) => {
          // 请求成功，将获取的数据存储在 productList_1.value 数组中
          productList_1.value = response.data.data;
          console.log(
            "Data fetched successfully. productList_1:",
            productList_1.value
          );
          show_6.value = false;
        })
        .catch((error) => {
          console.error("Failed to fetch data:", error);
        });
    };

    let index_tem = "";
    let position_tem = "";
    let checkProduce_tem = "";
    let item_tem = "";

    const deleteItem = (index, position, item) => {
      if (item.founderId == userId) {
        // 重新赋值
        index_tem = index;
        position_tem = position;
        checkProduce_tem = item.checkProduce;
        show_1.value = true;
      } else show_5.value = true;
    };

    const handleConfirm = () => {
      show_6.value = true;
      deleteProduce(index_tem, position_tem, checkProduce_tem);
    };
    const handleCancel = () => {
      return;
    };
    const deleteProduce = async (produceId, position, checkProduce_tem) => {
      return new Promise((resolve) => {
        // 发送 HTTP DELETE 请求来删除数据
        if (checkProduce_tem == 0) {
          productList_0.value.splice(position, 1); // 从数组中移除任务项
        } else if (checkProduce_tem == 1) {
          productList_1.value.splice(position, 1); // 从数组中移除任务项
        }
        axios
          .delete(`/api/deleteRoom?id=${produceId}`, {
            headers,
          })
          .then((response) => {
            // 处理删除成功的逻辑
            console.log("数据删除成功", response);
            message.value = "删除成功!";
            show_7.value = true; // 显示
            // 等待两秒后执行关闭操作
            setTimeout(() => {
              show_7.value = false; // 关闭
            }, 1000); // 2000毫秒等于2秒
            resolve(); // 解析Promise，表示删除完成
          })
          .catch((error) => {
            // 处理删除失败的逻辑
            console.error("数据删除失败", error);
            resolve(); // 解析Promise，表示删除完成
          });
        show_6.value = false;
      });
    };

    const checkItem = (index, position, item) => {
      console.log(item.founderId);
      if (item.founderId == userId) {
        // 重新赋值
        index_tem = index;
        position_tem = position;
        item_tem = item;
        show_2.value = true;
      } else show_4.value = true;
    };

    const checkConfirm = () => {
      show_6.value = true;
      checkProduce(index_tem, position_tem, item_tem);
    };
    const checkCancel = () => {
      return;
    };
    const checkProduce = async (produceId, position, item) => {
      const produce = {
        founderId: item.founderId,
        produceName: item.produceName,
        produceText: item.produceText,
        produceImage: item.produceImage,
        produceNum: 1,
        checkProduce: true,
        useName: item.useName,
      };
      console.log(produce);
      fetch(`/api/reduceRoom?id=${produceId}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      })
        .then((response) => response.json()) // 将 .json() 放在正确的位置
        .then((data) => {
          if (data.code == 1) {
            const resProduceId = data.data;
            console.log(resProduceId);
            produce.produceId = resProduceId;
            produce.checkLove = item.checkLove;
            productList_1.value.unshift(produce);
            show_6.value = false;
            item.produceNum -= 1;
            if (item.produceNum <= 0) {
              productList_0.value.splice(position, 1); // 从数组中移除任务项
            }
            message.value = "兑换成功!";
            show_7.value = true; // 显示
            // 等待两秒后执行关闭操作
            setTimeout(() => {
              show_7.value = false; // 关闭
            }, 1000); // 2000毫秒等于2秒
          }
        })
        .catch((error) => {
          // 处理获取失败的逻辑
          console.error("获取数据失败", error);
        });
    };
    const goodItem = () => {
      show_3.value = true;
    };
    const formatDate = (value) => {
      if (!value) return "";
      return moment(value).format("YYYY.MM.DD HH:mm:ss");
    };
    const truncateText = (item) => {
      if (item.checkLove % 2 == 1)
        return item.produceText.substring(0, 13) + "...";
      // 使用toFixed方法将数字格式化为带两位小数的字符串
      else return item.produceText;
    };
    const loveItem = (item) => {
      axios
        .get(`/api/updateLoveRoom?id=${item.produceId}`, {
          headers,
        })
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
    const remindItem = (item) => {
      axios
        .get(`/api/remindRoom?id=${item.produceId}`, {
          headers,
        })
        .then((response) => {
          // 请求成功，将获取的数据存储在 productList_1.value 数组中
          console.log("数据收藏成功", response);
          if(response.data.code == 0){
            message.value = response.data.msg;
          }
          else message.value = response.data.data;
        })
        .catch((error) => {
          console.error("Failed to fetch data:", error);
          message.value = "提醒失败!";
        });
      show_7.value = true; // 显示
      // 等待两秒后执行关闭操作
      setTimeout(() => {
        show_7.value = false; // 关闭
      }, 1000); // 2000毫秒等于2秒
    };
    return {
      remindItem,
      loveItem,
      truncateText,
      message,
      formatDate,
      icon,
      image,
      disPlay,
      show_1,
      show_2,
      show_3,
      show_4,
      show_5,
      show_6,
      show_7,
      goodItem,
      value,
      productList_0,
      productList_1,
      deleteItem,
      handleConfirm,
      handleCancel,
      checkConfirm,
      checkCancel,
      offset,
      active,
      onSearch,
      checkItem,
      images,
      imageUrl1,
    };
  },
};
</script>
<style>
.content_room {
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
.room {
  margin-bottom: 10vh;
}

.delete-button_room {
  margin: 10px;
  width: 60px;
  height: 60px;
  border-radius: 50%;
  transform: translateY(12px);
}
.van-button__icon {
  font-size: 60px;
  line-height: inherit;
}

.van-card {
  background: white;
}

.van-button--mini {
  border: var(--van-button-border-width) solid #f19290;
}

h1 {
  font-family: "Ma Shan Zheng", cursive;
  transform: translateX(2vw);
  font-size: 20px;
  color: #ec8aa4;
}

.van-back-top {
  background-color: #b55151;
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
.van-card__bottom {
  font-size: 18px;
}
.van-card__price {
  transform: translateY(5px);
}
:root {
  --van-gray-3: #ffffff;
}

.van-dialog__confirm, .van-dialog__confirm:active {
    color: #f19290;
}
</style>
