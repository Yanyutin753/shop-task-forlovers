<template>
    <div class="content_produce">
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

        <van-floating-bubble v-model:offset="offset_produce" axis="xy" icon="add-o" @click="onClick" />

        <h1>🎁 购买商品</h1>
        <div v-for="(item, index) in produceList" :key="index">
            <van-swipe-cell>
                <van-card :num="item.produceNum" :price="item.produceCredit" :desc="item.produceText"
                    :title="item.produceName" class="goods-card" :thumb="item.produceImage" currency="💰"
                    :thumb-link="`/#/ProduceDisplay/${item.produceId}`">

                    <template #tags>
                        <van-tag plain color="#F19290">出售给</van-tag>
                        <van-tag plain color="#F19290">{{ item.customer }}</van-tag>
                    </template>

                    <template #footer>
                        <van-button size="mini" @click="goodItem()">🎁</van-button>
                    </template>

                </van-card>
                <template #right>
                    <van-button square text="购买" color="#FFE1B2" class="buy-button"
                        @click="buyItem(item.produceId, index, item)" />
                    <!-- <van-button square text="收藏" type="warning" class="love-button" @click="loveItem(item)" /> -->
                    <van-button square text="删除" color="#F19290" class="delete-button"
                        @click="deleteItem(item.produceId, index, item)" />
                </template>

            </van-swipe-cell>
        </div>
        <van-dialog v-model:show="show_1" @confirm="handleConfirm" @cancel="handleCancel" title="🥰 温馨提示"
            message="🦄您确定已经删除这件商品吗，删除之后就找不到咯，请您要仔细认真考虑哦！" show-cancel-button>
        </van-dialog>
        <van-dialog v-model:show="show_2" @confirm="checkConfirm" @cancel="checkCancel" title="🥰 温馨提示"
            message="🦄您确定要购买这件商品了嘛？" show-cancel-button>
        </van-dialog>
        <van-dialog v-model:show="show_3" title="购买商品失败" message="😭您的积分不够，快去完成任务获取积分吧啊💪" show-cancel-button>
        </van-dialog>

        <!-- 弹出层 -->
        <van-dialog v-model:show="show_4" title="💖 感谢您的心心 💖" width="308" show-cancel-button>
            <img src="https://www.yyandywt99.love/ybb/aac0ec9b80ee4c4aa5c216434451b4ad.gif" />
        </van-dialog>
        <van-dialog v-model:show="show_5" title="🥰 温馨提示" message="😂您不能删除别人创建的商品哦，快去完成自己的任务，购买商品吧！">
        </van-dialog>
        <van-dialog v-model:show="show_6" title="🥰 温馨提示" message="😂您不能购买别人的专属商品哦，快去完成自己的任务，购买属于自己的商品吧！">
        </van-dialog>
        <van-back-top right="83vw" bottom="10vh" />
    </div>

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
        const show_6 = ref(false);
        const active = ref(2);
        const router = useRouter();
        const isLoading = ref(true); // 初始时显示加载中效果
        const images = [
            'https://www.yyandywt99.love/yy2/MarketCover.jpg',
            'https://www.yyandywt99.love/yy2/MarketCover01.jpg',
            'https://www.yyandywt99.love/yy2/MarketCover02.jpg',
        ];

        const offset_produce = ref({ x: 300, y: 520 });
        const onClick = () => {
            window.location.href = "#/produceAdd/1";
        };
        const produceList = ref([]);
        const value = ref('');
        const val = value.value;
        const onSearch = (val) => {
            isLoading.value = true;
            fetchData(val);
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


        const fetchData = (val) => {
            // 使用 axios 或 fetch 发起 API 请求，获取商品数据
            // 假设 API 路径为 '/api/products'
            // 在发起数据请求之前
            console.log('Before fetching data. produceList:', produceList.value);

            axios.get(`http://40.121.58.85:8081/nameSelectProduce?name=${val}`, { headers })
                .then(response => {
                    // 请求成功，将获取的数据存储在 produceList.value 数组中
                    produceList.value = response.data.data;
                    console.log('Data fetched successfully. produceList:', produceList.value);
                    isLoading.value = false;

                })
                .catch(error => {
                    console.error('Failed to fetch data:', error);
                });
        };

        let index_tem = '';
        let position_tem = '';
        let item_tem = '';
        const deleteItem = (index, position,item) => {
            if(item.founderId == userId){
                // 重新赋值
                index_tem = index;
                position_tem = position;
                show_1.value = true;
            }
            else {
                show_5.value = true;
            }

        };
        const handleConfirm = () => {
            isLoading.value = true;
            deleteProduce(index_tem, position_tem)
        }
        const handleCancel = () => {
            return;
        }
        const deleteProduce = (ProduceId, position) => {
            axios.delete(`http://40.121.58.85:8081/deleteProduce?id=${ProduceId}`, { headers })
                .then(response => {
                    // 处理删除成功的逻辑
                    console.log('数据删除成功', response);
                    // 删除成功后重新加载页面
                    produceList.value.splice(position, 1); // 从数组中移除商品项
                    isLoading.value = false;
                })
                .catch(error => {
                    // 处理删除失败的逻辑
                    console.error('数据删除失败', error);
                });
        };

        const buyItem = (index, position, item) => {
            axios.get(`http://40.121.58.85:8081/selectCredit?name=${item.customer}`, { headers })
                .then(response =>{
                    const nameId = response.data.data.nameId;
                    if(nameId == userId){
                        index_tem = index;
                        position_tem = position;
                        item_tem = item
                        show_2.value = true;
                    }
                    else show_6.value = true;
                })

        };
        const checkConfirm = () => {
            isLoading.value = true // 初始时显示加载中效果
            buyProduce(index_tem, position_tem, item_tem);
        }
        const checkCancel = () => {
            return;
        }
        const buyProduce = (ProduceId, position, item) => {
            axios.get(`http://40.121.58.85:8081/selectCredit?name=${item.customer}`, { headers })
                .then(response => {
                    // 处理获取成功的逻辑
                    console.log('获取数据成功', response);
                    const buyCredit = response.data.data.credit;
                    const nameId = response.data.data.nameId;
                    console.log(buyCredit);
                    if (buyCredit > item.produceCredit) {
                        // 发送 HTTP PUT 请求来完成购买操作
                        axios.put(`http://40.121.58.85:8081/buyProduce?id=${ProduceId}`, null, { headers })
                            .then(response => {
                                // 处理完成成功的逻辑
                                console.log('数据完成成功', response);
                                // 完成成功后重新加载页面
                                const task = {
                                    produceId: item.produceId,
                                    founderId: nameId,
                                    produceName: item.produceName,
                                    produceText: item.produceText,
                                    produceImage: item.produceImage,
                                    produceNum: 1,
                                    checkProduce: false,
                                    useName: item.customer,
                                };

                                // 发送 POST 请求到指定的 URL
                                fetch('http://40.121.58.85:8081/addRoom', {
                                    method: 'POST',
                                    headers: {
                                        'Content-Type': 'application/json',
                                        'Authorization': `Bearer ${token}`
                                    },
                                    body: JSON.stringify(task),
                                })
                                    .then(response => {
                                        if (response.ok) {
                                            // 请求成功，可以处理成功的逻辑
                                            if (item.produceNum <= 0) {
                                                deleteProduce(ProduceId, position);
                                            }
                                            isLoading.value = false;
                                            console.log('任务已保存成功');
                                        } else {
                                            // 请求失败，处理错误逻辑
                                            console.error('保存任务失败');
                                        }
                                    })
                                    .catch(error => {
                                        // 捕获异常
                                        console.error('保存任务时出现错误:', error);
                                    });
                                item.produceNum = item.produceNum - 1;
                            })
                            .catch(error => {
                                // 处理完成失败的逻辑
                                console.error('数据完成失败', error);
                            });

                    } else {
                        show_3.value = true;
                        isLoading.value = false;

                    }
                })
                .catch(error => {
                    // 处理获取失败的逻辑
                    console.error('获取数据失败', error);
                });
        };
        const goodItem = () => {
            show_4.value = true;
        }


        return {
            show_1,
            show_2,
            show_3,
            show_4,
            show_5,
            show_6,
            goodItem,
            isLoading,
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
            images
        };
    },

};

</script>
<style>
.content_produce {
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

.delete-button {
    height: 100%;
}

.van-card {
    background: white;
}

.van-tag--mark {
    border-radius: 0 0 0 0;
}

h1 {
    transform: translateX(2vw);
    font-size: 20px;
    color: #ff66cc;
}

.love-button {
    height: 100%;
}

.van-back-top {
    background-color: #B55151;
}

.van-floating-bubble {
    background: #F19290;

}
.buy-button {
    height: 100%;
}
</style>
