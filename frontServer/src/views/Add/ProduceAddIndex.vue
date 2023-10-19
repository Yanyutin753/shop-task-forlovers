<template>
    <van-nav-bar title="添加商品" left-text="" left-arrow @click-left="onClickLeft" />
    <div class="page-container">
        <div class="content_produce">
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
                <meta name="viewport"
                    content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no" />
                <van-uploader v-model="fileList" :after-read="afterRead" />
                <van-field v-model="presetIndex" is-link readonly label="🔖选择预设" placeholder="选择预设"
                    @click="showPicker = true" />
                <van-popup v-model:show="showPicker" round position="bottom ">
                    <van-picker :columns="columns" @cancel="showPicker = false" @confirm="onConfirm" />
                </van-popup>
                <van-field label="📌商品名称" v-model="title" placeholder="请输入商品名称" clearable />
                <van-field label="📝商品详情" v-model="desc" placeholder="请输入商品详情" type="textarea" rows="3" clearable />
                <van-field label="💰积分价格" v-model="credit" placeholder="请输入商品积分价格" clearable />
                <van-field label="🧩商品数量" v-model="num" placeholder="请输入商品数量" clearable />
                <!-- <van-field label="🤖商品对象" v-model="useName" placeholder="请输入商品对象" clearable /> -->
                <van-field v-model="useName" is-link readonly name="picker" label="🤖商品对象" placeholder="点击选择商品对象"
                    @click="showPicker_1 = true" />
                <van-popup v-model:show="showPicker_1" position="bottom">
                    <van-picker :columns="userColumns" @confirm="onConfirm_1" @cancel="showPicker_1 = false" />
                </van-popup>
            </van-form>

            <div class="footer">
                <van-button class="btn-reset" type="default" @click="resetItem">重置</van-button>
                <van-button class="btn-save" type="primary" @click="saveItem">保存</van-button>
            </div>
        </div>
    </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import png from '@/util/Item.gif';
import { useRouter } from 'vue-router';
import { Field, Form, Button, Swipe, SwipeItem } from 'vant';
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
        const images = [png];
        const fileList = ref([]);
        const presetIndex = ref('无预设');
        const title = ref('');
        const desc = ref('');
        const num = ref(0);
        const useName = ref('');
        const credit = ref(0);
        let imageUrl = ref('');
        const showPicker = ref(false);
        const router = useRouter();
        const userColumns = ref([]);
        const showPicker_1 = ref(false);
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
            { text: '薯片', value: '1' },
            { text: '奶茶券', value: '2' },
            { text: '夜宵券', value: '3' },
            { text: '做运动', value: '4' },
            { text: '给饭吃', value: '5' },
            { text: '不赖床', value: '6' },
            { text: '买小礼物', value: '7' },
            { text: '跑腿卷', value: '8' },
        ];

        const onConfirm = ({ selectedOptions }) => {
            showPicker.value = false;
            presetIndex.value = selectedOptions[0].text;
            onPresetChange(presetIndex.value); // 调用 onPresetChange 函数，手动触发值变化
        };

        const onPresetChange = (value) => {
            // 根据选择的预设值来自动填充其他信息
            if (value === '薯片') {
                title.value = '美味薯片';
                desc.value = '诱人的零食，夜宵绝佳伴侣，咔嘣脆！凭此商品可以向对方索要薯片。';

            } else if (value === '奶茶券') {
                title.value = '奶茶权限';
                desc.value = '凭此券可以向对方索要一杯奶茶。';

            } else if (value === '夜宵券') {
                title.value = '夜宵放开闸';
                desc.value = '凭此券可以让自己在夜里狂野干饭。';

            } else if (value === '做运动') {
                title.value = '减肥券';
                desc.value = '凭此券可以逼迫对方做一次运动，以此来达到减肥维持健康的目的。';

            } else if (value === '给饭吃') {
                title.value = '饭票';
                desc.value = '凭此券可以让对方做一次或请一次饭，具体视情况而定。';

            } else if (value === '不赖床') {
                title.value = '早起券';
                desc.value = '凭此券可以让对方早起床一次。熬夜对身体很不好，还是要早点睡觉第二天才能有精神！';

            } else if (value === '买小礼物') {
                title.value = '小礼物盒';
                desc.value = '凭此券可以让对方买点小礼物，像泡泡马特什么的。';

            } else if (value === '跑腿卷') {
                title.value = '跑腿召唤';
                desc.value = '有了我，你再也不需要移动了。拿外卖，拿零食，开空调，开电视，在所不辞。';

            }
        };

        const afterRead = (file) => {
            file.status = 'uploading';
            file.message = '上传中...';

            const formData = new FormData();
            formData.append('file', file.file);

            fetch('http://40.121.58.85:8081/upload', {
                method: 'POST',
                headers: headers, // 添加请求头
                body: formData,
            })
                .then(response => response.json())
                .then(data => {
                    // 这里假设服务器返回一个包含图片URL的JSON对象
                    imageUrl.value = data.data;
                    console.log('上传成功，图片URL：', imageUrl.value);
                    file.message = '上传成功';
                    file.status = '';

                })
                .catch(error => {
                    console.error('上传失败：', error);
                });
        };

        const resetItem = () => {
            presetIndex.value = '无预设'; // 选择预设的值重置为0，使用.value
            title.value = ''; // 任务名称重置为空
            desc.value = ''; // 任务详情重置为空
            credit.value = 0; // 积分价格重置为0
            num.value = 0;
            useName.value = '';
        };

        const saveItem = () => {
            isLoading.value = true;
            // 准备要发送的数据对象
            const updateTime = new Date();
            const registerTime = new Date();
            const task = {
                produceName: title.value,
                founderId: userId,
                produceText: desc.value,
                produceImage: imageUrl.value,
                produceCredit: credit.value,
                produceNum: num.value,
                customer: useName.value,
                updateTime: updateTime,
                registerTime: registerTime

            };

            // 发送 POST 请求到指定的 URL
            fetch('http://40.121.58.85:8081/addProduce', {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify(task),
            })
                .then(response => {
                    if (response.ok) {
                        isLoading.value = false;
                        // 请求成功，可以处理成功的逻辑
                        console.log('任务已保存成功');
                        router.push('/Produce/1');
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


        // 注册 beforeUnmount 生命周期钩子以在组件即将卸载时移除 meta 标签
        return {
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

.van-uploader {
    position: relative;
    display: inline-block;
    /* transform: translateX(158px); */
    display: flex;
    justify-content: center;
}

.content_produce {
    width: 90%;
}

.image-slider {
    width: 100%;
    height: 18hx;
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
