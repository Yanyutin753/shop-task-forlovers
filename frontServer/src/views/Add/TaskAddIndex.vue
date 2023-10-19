<template>
    <van-nav-bar title="添加任务" left-text="" left-arrow @click-left="onClickLeft" />
    <div class="page-container">
        <div class="content_addIndex">

            <van-swipe :autoplay="3000">
                <van-swipe-item v-for="image in images" :key="image">
                    <img :src="image" class="image-slider" alt="商品图片" />
                </van-swipe-item>
            </van-swipe>

            <van-loading vertical v-if="isLoading" class="loading-overlay">
                <template #icon>
                    <van-icon name="star-o" size="30" />
                </template>
                加载中...
            </van-loading>

            <van-form class="form-container">
                <div>
                    <meta name="viewport"
                        content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no" />
                    <!-- <van-field label="🔖选择预设" v-model="presetIndex" :options="presets" @change="onPresetChange($event)" /> -->
                    <van-field v-model="presetIndex" is-link readonly label="🔖选择预设" placeholder="选择预设"
                        @click="showPicker = true" />
                    <van-popup v-model:show="showPicker" round position="bottom ">
                        <van-picker :columns="columns" @cancel="showPicker = false" @confirm="onConfirm" clearable />
                    </van-popup>
                    <van-field label="📌任务名称" v-model="title" placeholder="请输入任务名称" :autofocus="false" clearable />
                    <van-field label="📝任务详情" v-model="desc" placeholder="请输入任务详情" type="textarea" rows="3"
                        :autofocus="false" clearable />
                    <!-- <van-field label="🤖任务对象" v-model="useName" placeholder="请输入任务对象" :autofocus="false" clearable/> -->
                    <van-field v-model="useName" is-link readonly name="picker" label="🤖商品对象" placeholder="点击选择商品对象"
                        @click="showPicker_1 = true" />
                    <van-popup v-model:show="showPicker_1" position="bottom">
                        <van-picker :columns="userColumns" @confirm="onConfirm_1" @cancel="showPicker_1 = false" />
                    </van-popup>
                    <van-field label="💰积分价格" v-model="credit" placeholder="请输入任务名称" :autofocus="false" clearable />
                </div>
            </van-form>

            <div class="footer">
                <van-button class="btn-reset" type="default" @click="resetItem">重置</van-button>
                <van-button class="btn-save" type="primary" @click="saveItem">保存</van-button>
            </div>
        </div>
    </div>
</template>

<script>
import { ref, onMounted } from 'vue'; // 添加了 onUnmounted
import axios from 'axios';
import png from '@/util/Mission.gif';
import { useRouter } from 'vue-router';
import { Field, Form, Button, Swipe, SwipeItem } from 'vant';
// 解析Jwt令牌
import jwtDecode from 'jwt-decode';

export default {
    components: {
        VanField: Field,
        VanForm: Form,
        VanButton: Button,
        VanSwipe: Swipe,
        VanSwipeItem: SwipeItem,
    },

    setup() {
        const isLoading = ref(false);
        const onClickLeft = () => history.back();
        const presetIndex = ref('无预设');
        const showPicker = ref(false);
        const title = ref('');
        const desc = ref('');
        const useName = ref('')
        const userColumns = ref([]);
        const showPicker_1 = ref(false);
        const credit = ref(0);

        const router = useRouter();
        const images = [png];
        onMounted(() => {
            fetchLoginToken();
            fetchUserData();
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
        const fetchUserData = async () => {
            try {
                const response = await axios.get('http://40.121.58.85:8081/user', { headers }); // 替换为实际的API端点
                const userData = response.data.data; // 假设API返回一个包含用户数据的数组
                // 2. 将用户数据转换为选择器的列格式
                userColumns.value = userData.map(user => ({
                    text: user.name,
                    value: user.nameId,
                }));
            } catch (error) {
                console.error('获取用户数据时出错', error);
            }
        };

        const onConfirm_1 = ({ selectedOptions }) => {
            useName.value = selectedOptions[0]?.text;
            showPicker_1.value = false;
        };
        const columns = [
            { text: '无预设', value: '0' },
            { text: '早睡早起', value: '1' },
            { text: '打扫房间', value: '2' },
            { text: '健康运动', value: '3' },
            { text: '戒烟戒酒', value: '4' },
            { text: '请客吃饭', value: '5' },
            { text: '买小礼物', value: '6' },
            { text: '洗碗洗碟', value: '7' },
            { text: '帮拿东西', value: '8' },
        ];

        const onConfirm = ({ selectedOptions }) => {
            showPicker.value = false;
            presetIndex.value = selectedOptions[0].text;
            console.log(selectedOptions[0].value)
            onPresetChange(selectedOptions[0].text); // 调用 onPresetChange 函数，手动触发值变化
        };

        const onPresetChange = (value) => {
            // 根据选择的预设值来自动填充其他信息
            if (value === '早睡早起') {
                title.value = value;
                desc.value = '熬夜对身体很不好，还是要早点睡觉第二天才能有精神！';

            } else if (value === '打扫房间') {
                title.value = value;
                desc.value = '有一段时间没有打扫房间了，一屋不扫，何以扫天下？';

            } else if (value === '健康运动') {
                title.value = value;
                desc.value = '做一些健身运动吧，跳绳，跑步，训练动作什么的。';

            } else if (value === '戒烟戒酒') {
                title.value = value;
                desc.value = '维持一段时间不喝酒，不抽烟，保持健康生活！';

            } else if (value === '请客吃饭') {
                title.value = value;
                desc.value = '好吃的有很多，我可以让你尝到其中之一，好好享受吧！';

            } else if (value === '买小礼物') {
                title.value = value;
                desc.value = '买点小礼物，像泡泡马特什么的。';

            } else if (value === '洗碗洗碟') {
                title.value = value;
                desc.value = '有我洗碗洗碟子，有你吃饭无它事。';

            } else if (value === '帮拿东西') {
                title.value = value;
                desc.value = '有了我，你再也不需要移动了。拿外卖，拿零食，开空调，开电视，在所不辞。';

            }
        };


        const resetItem = () => {
            presetIndex.value = '无预设'; // 选择预设的值重置为0，使用.value
            title.value = ''; // 任务名称重置为空
            desc.value = ''; // 任务详情重置为空
            credit.value = 0; // 积分价格重置为0
            useName.value = ''
        };

        const saveItem = () => {
            isLoading.value = true;
            // 准备要发送的数据对象
            const updateTime = new Date();
            const registerTime = new Date();
            console.log(userId);
            const task = {
                taskName: title.value,
                // 创建人的id
                founderId: userId,
                taskText: desc.value,
                taskCredit: credit.value,
                useName: useName.value,
                updateTime: updateTime,
                registerTime: registerTime

            };

            // 发送 POST 请求到指定的 URL
            fetch('http://40.121.58.85:8081/tasks', {
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
                        isLoading.value = false;
                        console.log('任务已保存成功');
                        router.push('/Task/1');
                    } else {
                        // 请求失败，处理错误逻辑
                        console.error('保存任务失败');
                    }
                })
                .catch(error => {
                    // 捕获异常
                    console.error('保存任务时出现错误:', error);
                });
        };

        return {
            isLoading,
            columns,
            onConfirm,
            showPicker,
            presetIndex,
            title,
            desc,
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

<style scoped>
.page-container {
    display: flex;
    justify-content: center;
    /* align-items: center; */
    height: 100%;
    width: 100%;
    overflow-y: hidden;
    /* 显示垂直滚动条 */
    overflow-x: hidden;
    /* 隐藏水平滚动条 */
    zoom: 1;
    /* 禁止页面内容缩放 */
}

.content_addIndext {
    padding: 20px;
    max-width: 100%;
    max-height: 80%;
    overflow-y: auto;
    /* 显示垂直滚动条 */
    overflow-x: hidden;
    /* 隐藏水平滚动条 */
}

.image-slider {
    width: 100%;
    height: 250px;
    object-fit: cover;
}

.form-container {
    margin-top: 20px;
}

.footer {
    display: flex;
    justify-content: space-between;
    margin-top: 20px;
}

.content_addIndex {
    width: 90%;
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
</style>
