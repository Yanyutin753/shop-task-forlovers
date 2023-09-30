<template>
    <div class="content_room">
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

        <h1>🌈 未兑换商品</h1>
        <div v-for="(item, index) in productList_0" :key="index">
            <van-swipe-cell>
                <van-card :num="item.produceNum" :price="item.produceCredit" :desc="item.produceText"
                    :title="item.produceName" class="goods-card" :thumb="item.produceImage"
                    :thumb-link="`/#/RoomDisplay/${item.produceId}`">

                    <template #tags>
                        <van-tag plain color="#F19290">购买人</van-tag>
                        <van-tag plain color="#F19290">{{ item.useName }}</van-tag>
                    </template>

                    <template #footer>
                        <van-button size="mini" @click="goodItem()"> 🔥 </van-button>
                    </template>

                </van-card>
                <template #right>
                    <van-button square text="兑换" color="#F19290" class="check-button"
                        @click="checkItem(item.produceId, index, item)" />
                </template>

                <!-- </van-card> -->
            </van-swipe-cell>
        </div>

        <div class="separator"></div>

        <h1>🤖 已兑换商品</h1>
        <div v-for="(item, index) in productList_1" :key="index">
            <van-swipe-cell>
                <van-card :num="item.produceNum" :price="item.produceCredit" :desc="item.produceText"
                    :title="item.produceName" class="goods-card" :thumb="item.produceImage"
                    :thumb-link="`/#/RoomDisplay/${item.produceId}`">
                    <template #tags>
                        <van-tag plain color="#F19290">{{ item.useName }}</van-tag>
                        <van-tag plain color="#F19290">兑换</van-tag>
                    </template>

                    <template #footer>
                        <van-button size="mini" @click="goodItem()">🧸</van-button>
                    </template>
                </van-card>
                <template #right>
                    <van-button square text="删除" color="#F19290" class="delete-button"
                        @click="deleteItem(item.produceId, index, item)" />
                </template>
            </van-swipe-cell>
        </div>
        <van-dialog v-model:show="show_1" @confirm="handleConfirm" @cancel="handleCancel" title="🥰 温馨提示"
            message="🦄您确定已经拿到商品了吗，删除之后就找不到咯，请您要仔细检查哦！" show-cancel-button>
        </van-dialog>
        <van-dialog v-model:show="show_2" @confirm="checkConfirm" @cancel="checkCancel" title="🥰 温馨提示"
            message="🦄您确定已经兑换商品了吗，请您要仔细检查哦！" show-cancel-button>
        </van-dialog>

        <!-- 弹出层 -->
        <van-dialog v-model:show="show_3" title="💖 感谢您的心心 💖" width="308" show-cancel-button>
            <img src="https://www.yyandywt99.love/ybb/1d292334349b033b1183e89102ce36d3d539bd71.gif" />
        </van-dialog>
        <van-dialog v-model:show="show_4" title="🥰 温馨提示" message="😂您不能兑换别人购买的商品哦，快去完成自己的任务，购买属于自己的商品吧！">
        </van-dialog>
        <van-dialog v-model:show="show_5" title="🥰 温馨提示" message="😂您不能删除别人兑换的商品哦，快去完成自己的任务，购买属于自己的商品吧！">
        </van-dialog>
        <van-back-top right="83vw" bottom="10vh" />
    </div>
    <!-- 其他内容 -->
    <!-- 其他内容 -->
    <van-tabbar v-model="active" active-color="#F19290">
        <van-tabbar-item replace to="/" icon="home-o">主页</van-tabbar-item>
        <van-tabbar-item replace to="/task/1" icon="coupon-o">任务</van-tabbar-item>
        <van-tabbar-item replace to="/Produce/1" icon="point-gift-o">商品</van-tabbar-item>
        <van-tabbar-item replace to="/Room/1" icon="cart-o">仓库</van-tabbar-item>
    </van-tabbar>
</template>

<script>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';
import jwtDecode from 'jwt-decode';
export default {

    setup() {
        const show_1 = ref(false);
        const show_2 = ref(false);
        const show_3 = ref(false);
        const show_4 = ref(false);
        const show_5 = ref(false);
        const active = ref(3);
        const router = useRouter();
        const isLoading = ref(true); // 初始时显示加载中效果
        const images = [
            'https://www.yyandywt99.love/yy2/MissionCover02.jpg',
            'https://www.yyandywt99.love/yy2/MarketCover03.jpg',
            'https://www.yyandywt99.love/yy2/HomeCover01.jpg',
        ];
        const offset = ref({ x: 300, y: 550 });
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
        let userId = "";
        console.log(headers);
        const fetchLoginToken = () => {
            axios.post('http://localhost:8081/loginToken?token=' + token)
                .then(response => {
                    if (response.data.code == 0) {
                        console.error(response.data.data);
                        router.push('/login');
                        return;
                    }
                    console.log(response.data.data);
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

            axios.get(`http://localhost:8081/nameNoSelectRoom?name=${val}`, { headers })
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

            axios.get(`http://localhost:8081/nameYesSelectRoom?name=${val}`, { headers })
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
        let checkProduce_tem = '';
        let item_tem = '';

        const deleteItem = (index, position, item) => {
            if (item.founderId == userId) {
                // 重新赋值
                index_tem = index;
                position_tem = position;
                checkProduce_tem = item.checkProduce;
                show_1.value = true;
            }
            else show_5.value = true;
        };

        const handleConfirm = () => {
            isLoading.value = true;
            deleteProduce(index_tem, position_tem, checkProduce_tem)
        }
        const handleCancel = () => {
            return;
        }
        const deleteProduce = (produceId, position, checkProduce_tem) => {
            // 发送 HTTP DELETE 请求来删除数据
            if (checkProduce_tem == 0) {
                productList_0.value.splice(position, 1); // 从数组中移除任务项
            }
            else if (checkProduce_tem == 1) {
                productList_1.value.splice(position, 1); // 从数组中移除任务项
            }
            axios.delete(`http://localhost:8081/deleteRoom?id=${produceId}`, { headers })
                .then(response => {
                    // 处理删除成功的逻辑
                    console.log('数据删除成功', response);
                    // 删除成功后重新加载页面
                    isLoading.value = false;
                })
                .catch(error => {
                    // 处理删除失败的逻辑
                    console.error('数据删除失败', error);
                });
        };



        const checkItem = (index, position, item) => {
            if (item.founderId == userId) {
                // 重新赋值
                index_tem = index;
                position_tem = position;
                item_tem = item
                show_2.value = true;
            }
            else show_4.value = true;
        };

        const checkConfirm = () => {
            isLoading.value = true;
            checkProduce(index_tem, position_tem, item_tem);
        }
        const checkCancel = () => {
            return;
        }
        const checkProduce = async (produceId, position, item) => {
            try {
                const response = await axios.get(`http://localhost:8081/idSelectRoom?id=${item.produceId}`, { headers });
                console.log('获取数据成功', response);
                const checkCredit = response.data.data;
                console.log(checkCredit);

                if (checkCredit > 0) {
                    const updateTime = new Date();
                    const registerTime = new Date();
                    const produce = {
                        produceName: item.produceName,
                        produceText: item.produceText,
                        produceImage: item.produceImage,
                        produceNum: 1,
                        checkProduce: true,
                        useName: item.useName,
                        updateTime: updateTime,
                        registerTime: registerTime,
                    };
                    console.log(produce);

                    const res = await axios.put(`http://localhost:8081/reduceRoom?id=${item.produceId}`, null, { headers });
                    if (res.data.code == 1) {
                        item.produceNum = item.produceNum - 1;
                        if (item.produceNum <= 0) {
                            deleteProduce(produceId, position, item.checkProduce);
                        }
                    }

                    const postResponse = await fetch('http://localhost:8081/addRoom', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization': `Bearer ${token}`
                        },
                        body: JSON.stringify(produce),
                    });
                    console.log(postResponse);
                    console.log('任务已保存成功');
                    const selectResponse = await axios.get(`http://localhost:8081/selectId`, { headers });
                    console.log('获取数据成功', selectResponse);
                    const resProduceId = selectResponse.data.data;
                    console.log(resProduceId);

                    produce.produceId = resProduceId;
                    productList_1.value.unshift(produce);
                    isLoading.value = false;
                } else {
                    isLoading.value = false;
                }
            } catch (error) {
                console.error('发生错误:', error);
            }
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
            offset,
            active,
            onSearch,
            checkItem,
            images
        };
    },

};

</script>
<style>
.content_room {
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

.check-button {
    height: 100%;
}

.delete-button {
    height: 100%;
}

.van-card {
    background: white;
}

h1 {
    transform: translateX(2vw);
    font-size: 20px;
    color: #ff66cc;
}

.van-back-top {
    background-color: #B55151;
}
</style>
