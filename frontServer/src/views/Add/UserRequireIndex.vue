<template>
    <div class="content_useHome">
        <van-nav-bar title="修改用户信息" left-text="" left-arrow @click-left="onClickLeft" />
        <div style="display: flex; justify-content: center; align-items: center; transform: translateY(10px);">
            <van-image width="60%" height="5%" fit="cover" position="center"
                src="https://mxb-yyandywt99.oss-cn-guangzhou.aliyuncs.com/ba504f38-45dc-45f6-8586-8215a9828d11.gif" />
        </div>
        <van-loading vertical v-if="isLoading" class="loading-overlay">
            <template #icon>
                <van-icon name="star-o" size="30" />
            </template>
            加载中...
        </van-loading>

        <van-form @submit="onSubmit">
            <br>
            <br>
            <meta name="viewport"
                content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no" />
            <van-uploader v-model="fileList" :after-read="afterRead" />

            <van-cell-group inset>
                <van-field v-model="username" name="更改用户名" label="🧩用户名" placeholder="更改用户名（选填）" clearable/>
                <van-field v-model="desc" name="更改用户详情" label="📝用户详情" type="textarea" rows="2" placeholder="更改用户详情（选填）" clearable/>
                <van-field v-model="password" type="password" name="更改密码" label="💥更改密码" placeholder="更改用户密码（选填）" clearable/>
            </van-cell-group>
            <div style="margin: 16px;">
                <van-button round block type="primary" native-type="submit">
                    提交
                </van-button>
            </div>
        </van-form>
    </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';


export default {
    setup() {
        const isLoading = ref(false);
        const router = useRouter();
        onMounted(() => {
            fetchLoginToken();
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
        const fetchLoginToken = () => {
            axios.post('http://40.121.58.85:8081/loginToken?token=' + token)
                .then(response => {
                    if (response.data.code == 0) {
                        console.error(response.data.data);
                        router.push('/login');
                    }
                    console.log(response.data.data);
                    // 在这里处理登录令牌接口的响应
                    // 如果需要执行一些特定的操作，可以在这里添加代码
                })
                .catch(error => {
                    console.error('请求loginToken接口失败', error);
                    router.push('/login');
                });
        };

        const onClickLeft = () => history.back();
        var url = window.location.href;

        // 使用正则表达式提取URL中的数字部分
        var match = url.match(/\/(\d+)$/);

        // 检查是否匹配到数字
        if (match && match[1]) {
            // 将匹配到的数字赋值给一个变量
            var id = parseInt(match[1]);
            console.log(id); // 这里输出1
        } else {
            console.log("没有找到数字");
        }

        const username = ref('');
        const desc = ref('');
        const password = ref('');
        const fileList = ref([]);
        let imageUrl = ref('');


        const afterRead = (file) => {
            file.status = 'uploading';
            file.message = '上传中...';

            const formData = new FormData();
            formData.append('file', file.file);

            fetch('http://40.121.58.85:8081/upload', {
                method: 'POST',
                headers: headers, // 添加请求头
                body: formData
            })
                .then(response => response.json())
                .then(data => {
                    // 这里假设服务器返回一个包含图片URL的JSON对象
                    imageUrl.value = data.data;
                    console.log('上传成功，图片URL：', imageUrl);
                    file.message = '上传成功';
                    file.status = '';

                })
                .catch(error => {
                    console.error('上传失败：', error);
                });
        };
        const onSubmit = () => {
            isLoading.value = true;
            // 获取URL中的参数
            // 创建包含参数的请求体       
            const user = {
                // id不能加value
                nameId: id,
                name: username.value,
                userImage: imageUrl.value,
                userText: desc.value,
                password: password.value,
            };

            // 发送 POST 请求到指定的 URL
            fetch('http://40.121.58.85:8081/requireUser', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify(user),
            })
                .then(response => {
                    console.log('从requireUser接口获取到的数据', response.json().data);
                    try {
                        isLoading.value = false;
                        router.push('/');
                    }
                    catch (error) {
                        console.error('请求requireUser接口失败', error);
                    }

                })
                .catch(error => {
                    console.error('请求requireUser接口失败', error);
                    // 处理请求失败的情况
                });
        }


        return {
            onClickLeft,
            username,
            desc,
            password,
            onSubmit,
            fileList,
            afterRead
        };
    },
};


</script>

<style>
.van-uploader {
    position: relative;
    display: inline-block;
    /* transform: translateX(158px); */
    display: flex;
    justify-content: center;
}

.content_useHome {
    zoom: 1;
    /* 禁止页面内容缩放 */
    width: 100%;
    height: 100%;
    overflow-y: auto;
    /* 显示垂直滚动条 */
    overflow-x: hidden;
    /* 隐藏水平滚动条 */
}

.image-slider {
    justify-content: center;
}
</style>