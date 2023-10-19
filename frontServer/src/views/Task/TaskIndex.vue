<template>
    <div class="content_task">

        <van-swipe :autoplay="3000">
            <van-swipe-item v-for="image in images" :key="image">
                <img :src="image" style="width: 100%; height: 250px;" />
            </van-swipe-item>
        </van-swipe>
        <van-loading vertical v-if="isLoading">
            <template #icon>
                <van-icon name="star-o" size="30" />
            </template>
            加载中...
        </van-loading>

        <form action="/">
            <meta name="viewport"
                content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no" />
            <van-search v-model="value" placeholder="请输入搜索关键词" @search="onSearch" />
        </form>

        <van-floating-bubble v-model:offset="offset_task" axis="xy" icon="add-o" @click="onClick" />

        <h1>💪 未完成任务</h1>
        <div v-for="(item, index) in productList_0" :key="index">
            <van-swipe-cell>
                <van-card :price="item.taskCredit" :desc="item.taskText" :title="item.taskName" class="goods-card"
                    :thumb="item.userImage" currency="💰" :thumb-link="`/#/TaskDisplay/${item.taskId}`">

                    <template #tags>
                        <van-tag plain color="#F19290">{{ item.useName }}</van-tag>
                        <van-tag plain color="#F19290">执行</van-tag>
                    </template>

                    <template #footer>
                        <van-button size="mini" @click="goodItem()">👑</van-button>
                    </template>
                </van-card>
                <template #right>
                    <van-button square text="完成" color="#8EB982" class="completed-button"
                        @click="completedItem(item.taskId, index, item)" />
                    <van-button square text="删除" color="#F19290" class="delete-button"
                        @click="deleteItem(item.taskId, index, item)" />
                </template>

                <!-- </van-card> -->
            </van-swipe-cell>
        </div>

        <div class="separator"></div>

        <h1>🥰 完成任务</h1>
        <div v-for="(item, index) in productList_1" :key="index">
            <van-swipe-cell>
                <van-card :price="item.taskCredit" :desc="item.taskText" :title="item.taskName" class="goods-card"
                    :thumb="item.userImage" currency="💰" :thumb-link="`/#/TaskDisplay/${item.taskId}`">
                    <template #tags>
                        <van-tag plain color="#F19290">{{ item.useName }}</van-tag>
                        <van-tag plain color="#F19290">执行</van-tag>
                    </template>

                    <template #footer>
                        <van-button size="mini" @click="goodItem()">🌈</van-button>
                    </template>
                </van-card>

                <template #right>
                    <van-button square text="删除" color="#F19290" class="delete-button"
                        @click="deleteItem(item.taskId, index, item)" />
                </template>


            </van-swipe-cell>
        </div>
        <van-dialog v-model:show="show_1" @confirm="handleConfirm" @cancel="handleCancel" title="🥰 温馨提示"
            message="🦄您确定要删除这个任务吗，删除之后就找不到咯，请您要仔细认真考虑哦！" show-cancel-button>
        </van-dialog>
        <van-dialog v-model:show="show_2" @confirm="checkConfirm" @cancel="checkCancel" title="🥰 温馨提示"
            message="🦄您确定您完成了这个任务了吗？" show-cancel-button>
        </van-dialog>
        <!-- 其他内容 -->
        <van-back-top right="83vw" bottom="10vh" />
    </div>

    <!-- 弹出层 -->
    <van-dialog v-model:show="show_3" title="💖 感谢您的心心 💖" width="308" show-cancel-button>
        <img src="https://www.yyandywt99.love/ybb/v2-0b6360bf3879ad4ebf8a8e8052634042_b.webp" />
    </van-dialog>

    <van-dialog v-model:show="show_4" title="🥰 温馨提示" message="😂您不能删除别人创建的任务哦，快去完成自己的任务吧！">
    </van-dialog>

    <van-dialog v-model:show="show_5" title="🥰 温馨提示" message="😂您不能完成别人创建的任务哦，快去完成自己的任务吧！">
    </van-dialog>

    <!-- 其他内容 -->
    <van-tabbar v-model="active" active-color="#F19290">
        <van-tabbar-item replace to="/" icon="home-o">主页</van-tabbar-item>
        <van-tabbar-item replace to="/Task/1" icon="coupon-o">任务</van-tabbar-item>
        <van-tabbar-item replace to="/Produce/1" icon="point-gift-o">商品</van-tabbar-item>
        <van-tabbar-item replace to="/Room/1" icon="cart-o">仓库</van-tabbar-item>
    </van-tabbar>
</template>

<script>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';
// jwt解析插件
import jwtDecode from 'jwt-decode';

export default {
    setup() {
        const show_1 = ref(false);
        const show_2 = ref(false);
        const show_3 = ref(false);
        const show_4 = ref(false);
        const show_5 = ref(false);
        const active = ref(1);
        const router = useRouter();
        const isLoading = ref(true); // 初始时显示加载中效果
        const images = [
            'https://www.yyandywt99.love/yy2/MissionCover01.jpg',
            'https://www.yyandywt99.love/yy2/MissionCover02.jpg',
            'https://www.yyandywt99.love/yy2/MissionCover03.jpg',
        ];

        const offset_task = ref({ x: 300, y: 520 });
        const onClick = () => {
            window.location.href = "#/TaskAdd/1";
        };

        const productList_0 = ref([]);
        const productList_1 = ref([]);
        const value = ref('');
        const val = value.value;
        const onSearch = (val) => {
            isLoading.value = true;
            fetchData_0(val);
            fetchData_1(val);
        };

        onMounted(() => {
            fetchLoginToken();
            onSearch(val);
        });
        const token = localStorage.getItem('jwtToken'); // 从localStorage获取JWT令牌
        if (!token) {
            router.push('/login');
        }
        console.log(token);
        const headers = {
            Authorization: `Bearer ${token}`
        };
        console.log(headers);
        let userId = "";
        const fetchLoginToken = () => {
            axios.post('http://40.121.58.85:8081/loginToken?token=' + token)
                .then(response => {
                    if (response.data.code == 0) {
                        console.error(response.data.data);
                        router.push('/login');
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
                .catch(error => {
                    console.error('请求loginToken接口失败', error);
                    router.push('/login');
                });
        };

        const fetchData_0 = (val) => {
            // 使用 axios 或 fetch 发起 API 请求，获取商品数据
            // 假设 API 路径为 '/api/products'
            // 在发起数据请求之前
            console.log('Before fetching data. productList_0:', productList_0.value);

            axios.get(`http://40.121.58.85:8081/selectCheckTask?id=0&taskName=${val}`, { headers })
                .then(response => {
                    // 请求成功，将获取的数据存储在 productList_0.value 数组中
                    productList_0.value = response.data.data;
                    console.log('Data fetched successfully. productList_0:', productList_0.value);
                    isLoading.value = false;
                })
                .catch(error => {
                    console.error('Failed to fetch data:', error);
                });
        };
        // 使用 ref 创建响应式数据
        const fetchData_1 = (val) => {
            // 使用 axios 或 fetch 发起 API 请求，获取商品数据
            // 假设 API 路径为 '/api/products'
            // 在发起数据请求之前
            console.log('Before fetching data. productList_1:', productList_1.value);

            axios.get(`http://40.121.58.85:8081/selectCheckTask?id=1&taskName=${val}`, { headers })
                .then(response => {
                    // 请求成功，将获取的数据存储在 productList_1.value 数组中
                    productList_1.value = response.data.data;
                    console.log('Data fetched successfully. productList_1:', productList_1.value);
                    isLoading.value = false;
                })
                .catch(error => {
                    console.error('Failed to fetch data:', error);
                });
        };

        let index_tem = '';
        let position_tem = '';
        let checkTask_tem = '';
        const deleteItem = (index, position, item) => {
            console.log(item.founderId);
            if (userId == item.founderId) {
                // 重新赋值
                index_tem = index;
                position_tem = position;
                checkTask_tem = item.checkTask;
                show_1.value = true;
                console.log(checkTask_tem)
            }
            else {
                show_4.value = true;
            }
        };
        const handleConfirm = () => {
            isLoading.value = true // 显示加载中效果
            deleteTask(index_tem, position_tem, checkTask_tem)
        }
        const handleCancel = () => {
            return;
        }
        const deleteTask = (taskId, position, checkTask) => {
            // 发送 HTTP DELETE 请求来删除数据
            axios.delete(`http://40.121.58.85:8081/tasks?id=${taskId}`, { headers })
                .then(response => {
                    if (checkTask == 0) {
                        productList_0.value.splice(position, 1); // 从数组中移除任务项
                    }
                    else if (checkTask == 1) {
                        productList_1.value.splice(position, 1); // 从数组中移除任务项
                    }
                    console.log('数据删除成功', response);
                    isLoading.value = false;
                })
                .catch(error => {
                    // 处理删除失败的逻辑
                    console.error('数据删除失败', error);
                });
        };

        const completedItem = (index, position, item) => {
            console.log(userId);
            console.log(item.founderId);
            if (userId == item.founderId) {
                // 重新赋值
                index_tem = index;
                position_tem = position;
                show_2.value = true;
            }
            else {
                show_5.value = true;
            }
        };
        const checkConfirm = () => {
            isLoading.value = true // 显示加载中效果
            completeTask(index_tem, position_tem);
        }
        const checkCancel = () => {
            return;
        }
        const completeTask = (taskId, index) => {
            // 发送 HTTP DELETE 请求来完成数据
            axios.put(`http://40.121.58.85:8081/tasks?id=${taskId}`, null, { headers })
                .then(response => {
                    // 处理完成成功的逻辑
                    console.log('数据完成成功', response);
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
                        useName:deletedItem.useName,
                        // 完成标准
                        checkTask: "true"
                    };
                    productList_0.value.splice(index, 1); // 从数组中移除任务项
                    productList_1.value.unshift(newItem); // 将新项添加到 productList_1 中
                    isLoading.value = false;
                })
                .catch(error => {
                    // 处理完成失败的逻辑
                    console.error('数据完成失败', error);
                });
        };
        const goodItem = () => {
            show_3.value = true;
        }


        return {
            show_1,
            show_2,
            show_3,
            show_4,
            show_5,
            goodItem,
            isLoading,
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
        };
    },

};

</script>
<style>
.content_task {
    zoom: 1;
    /* 禁止页面内容缩放 */
    width: 100%;
    /* 设置容器宽度 */
    height: 92vh;
    /* 设置容器高度，使其占满整个视口 */
    overflow-y: auto;
    /* 显示垂直滚动条 */
    overflow-x: hidden;
    /* 隐藏水平滚动条 */
}

.notice-swipe {
    height: 40px;
    line-height: 40px;
}

.van-floating-bubble {
    width: 50px;
    height: 50px;
}

.van-floating-bubble__icon {
    font-size: 30px;
}

h1 {
    transform: translateX(2vw);
    font-size: 20px;
    color: #ff66cc;
}

.van-back-top {
    background-color: #B95B66;
}

.delete-button {
    height: 100%;
}

.van-card {
    background: white;
}

.van-floating-bubble {
    background: #F19290;

}

.completed-button {
    height: 100%;
}
</style>
