<template>
  <div class="content_task">
    <div class="task">
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
            placeholder="请输入搜索任务关键词"
            @search="onSearch"
          />
        </form>
      </div>
        <van-floating-bubble
          v-model:offset="offset_task"
          axis="xy"
          icon="add-o"
          @click="onClick"
        />
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
            💪 未完成
            <van-icon
              name="coupon-o"
              :badge="productList_0.length.toString()"
              :badge-props="{ color: '#ee575e' }"
            />
          </h1>
        </div>
        <div v-for="(item, index) in productList_0" :key="index">
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
                :price="formatNumber(item.taskCredit)"
                :desc="truncateText(item)"
                :title="item.taskName"
                :thumb="item.userImage"
                currency="💰"
                @click-thumb="disPlay(item.taskId)"
              >
                <template #price>
                  <div style="transform: translate(-3px, 4px); font-size: 14px">
                    💰{{ formatNumber(item.taskCredit) }}
                  </div>
                </template>
                <template #tag>
                  <div
                    v-if="item.checkLove % 2 == 1"
                    style="
                      transform: translate(calc(96vw - 79.5px), -11.9px);
                      opacity: 0.35;
                    "
                  >
                    <van-image
                      :src="imageUrl1"
                      width="65"
                      height="65"
                      class="image-slider"
                    />
                  </div>
                </template>
                <template #tags>
                  <van-tag plain color="#F19290">{{
                    formatDate(item.updateTime)
                  }}</van-tag>
                  <van-tag plain color="#F19290">{{ item.useName }}</van-tag>
                  <van-tag plain color="#F19290">执行</van-tag>
                </template>
                <template #num>
                  <div style="transform: translate(7px, 3px)">
                    <van-button class="mini" size="mini" @click="goodItem()"
                      >👑</van-button
                    >
                  </div>
                </template>
              </van-card>
              <template #right>
                <div style="margin-left: 10px;">
                  <van-button
                    square
                    icon="passed"
                    color="#8EB982"
                    class="button_task"
                    @click="completedItem(item.taskId, index, item)"
                  />
                  <van-button
                    square
                    icon="phone-circle-o"
                    color="#FBC2EB"
                    class="button_task"
                    @click="remindItem(item)"
                  />
                  <van-button
                    square
                    icon="star-o"
                    color="#FFE1B2"
                    class="button_task"
                    @click="loveItem(item)"
                  />
                  <van-button
                    square
                    icon="delete-o"
                    color="#F19290"
                    class="button_task"
                    @click="deleteItem(item.taskId, index, item)"
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
            🥰 完成
            <van-icon
              name="coupon-o"
              :badge="productList_1.length.toString()"
              :badge-props="{ color: '#ee575e' }"
            />
          </h1>
        </div>
        <div v-for="(item, index) in productList_1" :key="index">
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
                :price="formatNumber(item.taskCredit)"
                currency="💰"
                :desc="truncateText(item)"
                :title="item.taskName"
                :thumb="item.userImage"
                @click-thumb="disPlay(item.taskId)"
              >
                <template #price>
                  <div style="transform: translate(-3px, 4px); font-size: 14px">
                    💰{{ formatNumber(item.taskCredit) }}
                  </div>
                </template>
                <template #tag>
                  <div
                    v-if="item.checkLove % 2 == 1"
                    style="
                      transform: translate(calc(96vw - 79.5px), -11.9px);
                      opacity: 0.35;
                    "
                  >
                    <van-image
                      :src="imageUrl1"
                      width="65"
                      height="65"
                      class="image-slider"
                    />
                  </div>
                </template>
                <template #tags>
                  <div style="transform: translateY(3px)">
                    <van-tag plain color="#F19290">{{
                      formatDate(item.updateTime)
                    }}</van-tag>
                    <van-tag plain color="#F19290">{{ item.useName }}</van-tag>
                    <van-tag plain color="#F19290">执行</van-tag>
                  </div>
                </template>

                <template #num>
                  <div style="transform: translate(7px, 3px)">
                    <van-button class="mini" size="mini" @click="goodItem()"
                      >🌈</van-button
                    >
                  </div>
                </template>
              </van-card>

              <template #right>
                <div style="margin-left: 10px;">
                  <van-button
                    square
                    icon="star-o"
                    color="#FFE1B2"
                    class="button_task"
                    @click="loveItem(item)"
                  />
                  <van-button
                    square
                    icon="delete-o"
                    color="#F19290"
                    class="button_task"
                    @click="deleteItem(item.taskId, index, item)"
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
          message="🦄您确定要删除这个任务吗，删除之后就找不到咯，请您要仔细认真考虑哦！"
          show-cancel-button
        >
        </van-dialog>
        <van-dialog
          v-model:show="show_2"
          @confirm="checkConfirm"
          @cancel="checkCancel"
          title="🥰 温馨提示"
          message="🦄您确定您的小可爱完成了这个任务了吗？"
          show-cancel-button
        >
        </van-dialog>
        <!-- 其他内容 -->
        <van-back-top right="83vw" bottom="10vh" />
      </div>

      <van-dialog
        v-model:show="show_3"
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
            :src="image"
          />
        </div>
      </van-dialog>
      <van-dialog
        v-model:show="show_4"
        title="🥰 温馨提示"
        message="😂您不能删除别人创建的任务哦，快去完成自己的任务吧！"
      >
      </van-dialog>

      <van-dialog
        v-model:show="show_5"
        title="🥰 温馨提示"
        message="😂您不能完成别人创建的任务哦，快去完成自己的任务吧！"
      >
      </van-dialog>
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
  <link
    rel="stylesheet"
    href="https://fonts.googleapis.com/css?family=Ma+Shan+Zheng"
  />
</template>

<script>
import moment from "moment";
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";
// jwt解析插件
import jwtDecode from "jwt-decode";
import png from "@/util/2.webp";
import jpg1 from "@/assets/image/task1.jpg";
import jpg2 from "@/assets/image/task2.jpg";
import jpg3 from "@/assets/image/task3.jpg";
import HomeColor from "@/assets/tabBar/HomeIconColor.jpg";
import HomeGrey from "@/assets/tabBar/HomeIconGrey.jpg";
import task2 from "@/assets/tabBar/home1.jpg";
import task3 from "@/assets/image/Star.png";

export default {
  setup() {
    const icon = {
      active: HomeColor,
      inactive: HomeGrey,
    };
    const imageUrl1 = [task3];
    const imageUrl2 = [task2];
    const image = [png];
    const picturewidth = ref();
    picturewidth.value = 0.8 * window.innerWidth;
    const show_1 = ref(false);
    const show_2 = ref(false);
    const show_3 = ref(false);
    const show_4 = ref(false);
    const show_5 = ref(false);
    const active = ref(0);
    const router = useRouter();
    const show_6 = ref(true); // 初始时显示加载中效果
    const show_7 = ref(false);
    const message = ref("");
    const images = [jpg1, jpg2, jpg3];
    var y = window.innerHeight * 0.74;
    var x = window.innerWidth * 0.75;
    const offset_task = ref({ x: x, y: y });
    const onClick = () => {
      router.replace("/TaskAdd/1");
    };
    const disPlay = (url) => {
      router.replace(`/TaskDisplay/${url}`);
    };
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
          // 获取id
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
        .get(
          `/api/selectCheckTask?id=0&taskName=${val}`,
          {
            headers,
          }
        )
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
        .get(
          `/api/selectCheckTask?id=1&taskName=${val}`,
          {
            headers,
          }
        )
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
    let checkTask_tem = "";
    const deleteItem = (index, position, item) => {
      if (userId == item.founderId) {
        // 重新赋值
        index_tem = index;
        position_tem = position;
        checkTask_tem = item.checkTask;
        show_1.value = true;
      } else {
        show_4.value = true;
      }
    };
    const handleConfirm = () => {
      deleteTask(index_tem, position_tem, checkTask_tem);
    };
    const handleCancel = () => {
      return;
    };
    const notificationContent = ref("");
    const deleteTask = (taskId, position, checkTask) => {
      // 发送 HTTP DELETE 请求来删除数据
      axios
        .delete(`/api/tasks?id=${taskId}`, { headers })
        .then((response) => {
          if (checkTask == 0) {
            productList_0.value.splice(position, 1); // 从数组中移除任务项
          } else if (checkTask == 1) {
            productList_1.value.splice(position, 1); // 从数组中移除任务项
          }
          console.log("数据删除成功", response);
          message.value = "任务删除成功！";
        })
        .catch((error) => {
          // 处理删除失败的逻辑
          console.error("数据删除失败", error);
          message.value = "任务删除失败！";
        });
      show_7.value = true; // 显示
      // 等待两秒后执行关闭操作
      setTimeout(() => {
        show_7.value = false; // 关闭
      }, 1000); // 2000毫秒等于2秒
    };

    const completedItem = (index, position, item) => {
      if (userId == item.founderId) {
        // 重新赋值
        index_tem = index;
        position_tem = position;
        show_2.value = true;
      } else {
        show_5.value = true;
      }
    };
    const checkConfirm = () => {
      completeTask(index_tem, position_tem);
    };
    const checkCancel = () => {
      return;
    };
    const completeTask = (taskId, index) => {
      // 发送 HTTP DELETE 请求来完成数据
      axios
        .put(`/api/tasks?id=${taskId}`, null, { headers })
        .then((response) => {
          // 处理完成成功的逻辑
          console.log("数据完成成功", response);
          // 完成成功后重新加载页面
          const deletedItem = productList_0.value[index];
          const newItem = {
            // 转移图像
            userImage: deletedItem.userImage,
            // 转移积分
            taskCredit: deletedItem.taskCredit,
            // 转移任务文案
            taskText: deletedItem.taskText,
            // 转移任务名字
            taskName: deletedItem.taskName,
            // 转移任务对象
            useName: deletedItem.useName,
            // 完成标准
            checkTask: "true",
            checkLove: deletedItem.checkLove,
            taskId: taskId,
            updateTime: new Date(),
          };
          productList_0.value.splice(index, 1); // 从数组中移除任务项
          productList_1.value.unshift(newItem); // 将新项添加到 productList_1 中
          message.value = "您的小可爱任务完成成功！";
        })
        .catch((error) => {
          // 处理完成失败的逻辑
          console.error("数据完成失败", error);
          message.value = "您的小可爱任务完成失败！";
        });
      show_7.value = true; // 显示
      // 等待两秒后执行关闭操作
      setTimeout(() => {
        show_7.value = false; // 关闭
      }, 1000); // 2000毫秒等于2秒
    };
    const goodItem = () => {
      show_3.value = true;
    };
    const loveItem = (item) => {
      axios
        .get(`/api/updateLoveTask?id=${item.taskId}`, {
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
    const formatDate = (value) => {
      if (!value) return "";
      return moment(value).format("YYYY.MM.DD ");
    };
    const formatNumber = (value) => {
      if (value === null || value === undefined) return "";
      // 使用toFixed方法将数字格式化为带两位小数的字符串
      return value.toFixed(2);
    };

    const truncateText = (item) => {
      if (item.checkLove % 2 == 1)
        return item.taskText.substring(0, 15) + "...";
      // 使用toFixed方法将数字格式化为带两位小数的字符串
      else return item.taskText;
    };

    const remindItem = (item) => {
      axios
        .get(`/api/remindTask?id=${item.taskId}`, {
          headers,
        })
        .then((response) => {
          // 请求成功，将获取的数据存储在 productList_1.value 数组中
          console.log("数据提醒成功", response);
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
      imageUrl1,
      imageUrl2,
      icon,
      image,
      disPlay,
      show_1,
      show_2,
      show_3,
      show_4,
      show_5,
      notificationContent,
      goodItem,
      loveItem,
      show_6,
      show_7,
      message,
      value,
      productList_0,
      productList_1,
      deleteItem,
      handleConfirm,
      handleCancel,
      checkConfirm,
      checkCancel,
      completedItem,
      offset_task,
      onClick,
      onSearch,
      active,
      images,
      formatDate,
      formatNumber,
      picturewidth,
      truncateText,
    };
  },
};
</script>
<style>
.content_task {
  background: rgb(250 250 250 / 22%);
  zoom: 1;
  /* 禁止页面内容缩放 */
  width: 100%vw;
  /* 设置容器宽度 */
  height: 100vh;
  /* 设置容器高度，使其占满整个视口 */
  overflow-y: auto;
  /* 显示垂直滚动条 */
  overflow-x: hidden;
  /* 隐藏水平滚动条 */
}

.task {
  margin-bottom: 10vh;
}

.van-floating-bubble {
  width: 13vw;
  height: 13vw;
}

.van-floating-bubble__icon {
  font-size: 8vw;
}

h1 {
  font-family: "Ma Shan Zheng", cursive;
  transform: translateX(2vw);
  font-size: 20px;
  color: #ec8aa4;
}

:root {
  --van-gray-3: #ffffff;
}
.van-card__price {
  transform: translateY(5px);
}
.van-button__icon {
  font-size: 60px;
  line-height: inherit;
}
.van-back-top {
  background-color: #b95b66;
}

.button_task {
  margin: 10px;
  width: 60px;
  height: 60px;
  border-radius: 50%;
  transform: translateY(12px);
}
.van-card__bottom {
  font-size: 18px;
}

.van-card {
  background: white;
}

.van-floating-bubble {
  background: #f19290;
}

.van-button--mini {
  border: var(--van-button-border-width) solid #f19290;
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
h11 {
  color: #969799;
}
.van-dialog__confirm, .van-dialog__confirm:active {
    color: #f19290;
}
</style>
