<template>
  <van-field v-model="result" is-link readonly name="picker" label="选择器" placeholder="点击选择用户"
    @click="showPicker = true" />
  <van-popup v-model:show="showPicker" position="bottom">
    <van-picker :columns="userColumns" @confirm="onConfirm" @cancel="showPicker = false" />
  </van-popup>
</template>

<script>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';

export default {
  setup() {
    const result = ref('');
    const showPicker = ref(false);
    const userColumns = ref([]);
    const router = useRouter();
    const token = localStorage.getItem('jwtToken'); // 从localStorage获取JWT令牌
    if (!token) {
      router.push('/login');
    }
    console.log(token);
    const headers = {
      Authorization: `Bearer ${token}`
    };
    // 1. 创建一个函数来从接口获取用户数据
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

    const onConfirm = ({ selectedOptions }) => {
      result.value = selectedOptions[0]?.text;
      showPicker.value = false;
    };

    // 3. 在组件挂载时获取用户数据
    onMounted(() => {
      fetchUserData();
    });

    return {
      result,
      userColumns,
      onConfirm,
      showPicker,
    };
  },
};
</script>
