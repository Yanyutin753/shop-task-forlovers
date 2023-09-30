<template>
    <div class="content_login">
        <div style="display: flex; justify-content: center; align-items: center;transform: translateY(-4.5vh);">
            <h3>KABI.</h3>
        </div>
        <div style="display: flex; justify-content: center; align-items: center; transform: translateY(-15.5vh);">
            <h4>Log in on KABI :⭐.</h4>
        </div>
        <div style="display: flex; justify-content: center; align-items: center; transform: translateY(-17.5vh);">
            <van-image width="83%" height="5%" fit="contain" position="center" :src="image" />
        </div>
        <div style="display: flex; justify-content: center; transform: translateY(-14vh); align-items: center;">

            <van-form @submit="onSubmit">
                <meta name="viewport"
                    content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no" />

                <van-field v-model="username" name="用户名" label="🦄用户名" placeholder="用户名" />
                <van-field v-model="password" type="password" name="密码" label="🔑密码" placeholder="密码" />

                <div style="margin: 16px;">
                    <van-button round block color="#F19290" native-type="submit">
                        LOGIN IN KABI'S WORLD
                    </van-button>
                </div>
            </van-form>
        </div>
        <div style="display: flex; justify-content: center; transform: translateY(-18.3vh); align-items: center;">
            <h5>Don't have an account? <router-link to="/userAdd" class="custom-link">Sign up</router-link></h5>
        </div>
        <van-dialog v-model:show="show" title="😭提示" message="账号或密码错误">
        </van-dialog>
    </div>
</template>
<script>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import png from '@/util/love.gif';
export default {
    setup() {
        const image = [png];
        const show = ref(false);
        const username = ref('');
        const password = ref('');
        const router = useRouter();
        const onSubmit = () => {
            const user = {
                name: username.value,
                password: password.value
            };
            fetch('http://localhost:8081/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(user),
            })
                .then(response => response.json()) // 将 .json() 放在正确的位置
                .then(data => {
                    if (data.code == 1) {
                        console.log('登录成功', data.data);
                        router.push('/');
                        const token = data.data;
                        console.log(token);
                        localStorage.setItem('jwtToken', token);
                    } else {
                        show.value = true;
                        console.error('登录失败');
                    }
                })
                .catch(error => {
                    show.value = true;
                    console.error('登录时出现错误:', error);
                });

        };

        return {
            show,
            username,
            password,
            onSubmit,
            image,
        };
    },
};
</script>

<style>
.content_login {
    zoom: 1;
    /* 禁止页面内容缩放 */
    width: 100%;
    /* 设置容器宽度 */
    height: 100vh;
    /* 设置容器高度，使其占满整个视口 */
    overflow-y: hidden;
    /* 显示垂直滚动条 */
    overflow-x: hidden;
    /* 隐藏水平滚动条 */
}

h3 {
    font-size: 50px;
    color: #F19290;
}

h4 {
    font-size: 30px;
    color: #F19290;
}

h5 {
    font-size: 15px;
    color: #F19290;
}

.custom-link {
    font-size: 15px;
    color: #B95B66;
    /* 设置为深色 */
    /* 其他样式属性 */
}
</style>