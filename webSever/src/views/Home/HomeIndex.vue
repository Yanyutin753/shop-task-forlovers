<template>
    <div class="content_home">
        <van-notice-bar color="#333333" background="#FFB6C1" left-icon="volume-o" :scrollable="false">
            <van-swipe vertical class="notice-swipe" :autoplay="3000" :touchable="true" :show-indicators="false">
                <van-swipe-item>✨欢迎来到卡比的世界！</van-swipe-item>
                <van-swipe-item>🧸奋斗吧！奥利给！</van-swipe-item>
                <van-swipe-item>🌈今天要完成什么任务呢</van-swipe-item>
                <van-swipe-item>🎁今天要买什么礼物呢</van-swipe-item>
            </van-swipe>
        </van-notice-bar>

        <van-swipe :autoplay="3000">
            <van-swipe-item v-for="image in images" :key="image">
                <img :src="image" style="width: 100%; height: 250px;" />
            </van-swipe-item>
        </van-swipe>


        <van-loading vertical v-if="isLoading" class="loading-overlay">
            <template #icon>
                <van-icon name="star-o" size="30" />
            </template>
            加载中...
        </van-loading>

        <h1>💰 积分榜</h1>
        <div v-for="(item, index) in userList" :key="index">
            <van-swipe-cell>
                <van-card :num="item.credit" :title="`${item.name}积分💰`" :desc="item.userText" class="goods-card"
                    :thumb="item.userImage" :thumb-link="`/#/userDisplay/${item.nameId}`">


                    <template #tags>
                        <van-tag plain color="#F19290">{{ item.name }}</van-tag>
                        <van-tag plain color="#F19290">宝宝</van-tag>
                    </template>
                    <template #footer>
                        <van-button size="mini" @click="goodItem()">💖</van-button>
                    </template>
                </van-card>
                <template #right>
                    <van-button square text="修改" color="#F19290" class="require-button" @click="requireItem(item)" />
                </template>
            </van-swipe-cell>
        </div>
        <h1>🐻 欢迎回来</h1>
        <van-swipe-cell>
            <div v-if="userList.length >= 2">
                <!-- 只有在用户列表长度大于等于2时才渲染欢迎消息 -->
                <van-cell :border="false" class="goods-card">
                    <template #title>
                        🧸欢迎！{{ userList[0].name }}宝贝和{{ userList[1].name }}宝贝！
                    </template>
                </van-cell>
            </div>
            <template #right>
                <van-button square text="点赞" type="primary" class="good-button" @click="goodItem()" />
            </template>
        </van-swipe-cell>
        <van-swipe-cell>
            <van-cell :border="false" title="🌈今天想做什么呢🦄" class="goods-card" />
            <template #right>
                <van-button square text="点赞" type="primary" class="good-button" @click="goodItem()" />
            </template>
        </van-swipe-cell>
        <!--     //导航栏 -->
        <!-- <van-tabbar v-model="active" class="bottomTabber"> -->
        <van-tabbar v-model="active" active-color="#F19290">
            <van-tabbar-item replace to="/" icon="home-o">主页</van-tabbar-item>
            <!-- <van-tabbar-item>
                <span>主页</span>
                <template #icon="props">
                    <img :src="props.active ? icon.active : icon.inactive" />
                </template>
            </van-tabbar-item> -->
            <van-tabbar-item replace to="/Task/1" icon="coupon-o">任务</van-tabbar-item>
            <van-tabbar-item replace to="/Produce/1" icon="point-gift-o">商品</van-tabbar-item>
            <van-tabbar-item replace to="/Room/1" icon="cart-o">仓库</van-tabbar-item>
        </van-tabbar>

        <!-- 弹出层 -->
        <van-dialog v-model:show="show" title="💖 感谢您的心心 💖" width="308" show-cancel-button>
            <img src="https://www.yyandywt99.love/ybb/v2-87adb28be761213742af047322e0b2d5_b.webp" />
        </van-dialog>
        <van-dialog v-model:show="show_1" title="🥰 温馨提示" message="😂您不能修改别的用户信息哦！">
        </van-dialog>
        <van-back-top right="83vw" bottom="10vh" />

    </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';
import jwtDecode from 'jwt-decode';
// import HomeColor from '@/assets/tabBar/AccountIconColor.jpg';
// import HomeGrey from '@/assets/tabBar/AccountIconGrey.jpg';
export default {
    setup() {
        // const icon = {
        //     active: HomeGrey,
        //     inactive: HomeColor,
        // };
        const show = ref(false);
        const show_1 = ref(false);
        const isLoading = ref(true); // 初始时显示加载中效果
        const router = useRouter();
        const active = ref(0);
        const images = [
            'https://www.yyandywt99.love/yy2/HomeCover01.jpg',
            'https://www.yyandywt99.love/yy2/HomeCover02.jpg',
            'https://www.yyandywt99.love/yy2/HomeCover03.jpg',
        ];
        const userList = ref([]);
        // 使用 onMounted 钩子来模拟 created 钩子，发起 API 请求
        onMounted(() => {
            fetchLoginToken();
            fetchData();
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
                    // 删除本地存储的 token
                    localStorage.removeItem('jwtToken');
                    router.push('/login');
                    console.error('请求loginToken接口失败', error);
                });
        };


        const fetchData = () => {
            // 使用 axios 或 fetch 发起 API 请求，获取商品数据
            // 假设 API 路径为 '/api/products'
            // 在发起数据请求之前
            console.log('Before fetching data. userList:', userList.value);
            axios.get('http://localhost:8081/user', { headers })
                .then(response => {
                    // 请求成功，将获取的数据存储在 userList.value 数组中
                    userList.value = response.data.data;
                    console.log('Data fetched successfully. userList:', userList.value);
                    isLoading.value = false;
                })
                .catch(error => {
                    console.error('Failed to fetch data:', error);
                });

        };
        const requireItem = (item) => {
            console.log(userId);
            if (userId == item.nameId) {
                const nameIdToDelete = item.nameId;
                isLoading.value = true;
                // 调用 deleteTask 函数来发送 HTTP DELETE 请求
                requireTask(nameIdToDelete);
            }
            else show_1.value = true;
        };
        const requireTask = (nameId) => {
            router.push(`/UserRequire/${nameId}`);
            isLoading.value = false;

        };
        const goodItem = () => {
            show.value = true;
        }


        return {
            // icon,
            show,
            show_1,
            token,
            isLoading,
            userList,
            active,
            images,
            goodItem,
            requireItem
        };
    }
};

</script>

<style>
.content_home {
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

.van-tabbar {
    height: 58px;
}

.good-button {
    height: 100%;
}

h1 {
    transform: translateX(2vw);
    font-size: 20px;
    color: #ff66cc;
}

.bottomTabber {
    position: fixed;
}

.van-card {
    background: white;
}

.van-card__bottom {
    font-size: 18px;
    line-height: 36px;
}

.van-back-top {
    background-color: #B55151;
}


/* .van-floating-bubble {
    background: #F19290;

}  */


.require-button {
    height: 100%;
}
</style>
