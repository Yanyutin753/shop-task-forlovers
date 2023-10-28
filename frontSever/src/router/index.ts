import { createRouter, createWebHashHistory } from 'vue-router';

const routes = [
    { path: '/', component: () => import('@/views/Home/HomeIndex.vue'), meta: { title: '🦄欢迎来到卡比之家',keepAlive: true }},
    { path: '/Task', component: () => import('@/views/Task/TaskIndex.vue'), meta: { title: '🦄欢迎来到卡比的任务中心',keepAlive: true } },
    { path: '/Produce', component: () => import('@/views/Produce/ProduceIndex.vue'), meta: { title: '🦄欢迎来到卡比商城',keepAlive: true } },
    { path: '/Room', component: () => import('@/views/Room/RoomIndex.vue'), meta: { title: '🦄欢迎来到卡比仓库',keepAlive: true } },
    { path: '/User', component: () => import('@/views/User/UserIndex.vue'), meta: { title: '🦄欢迎来到专属用户中心',keepAlive: true } },
    
    { path: '/UserRequire/:id', component: () => import('@/views/Add/UserRequireIndex.vue'), meta: { title: '🧩用户设置中心' } },
    { path: '/TaskAdd/:id', component: () => import('@/views/Add/TaskAddIndex.vue'), meta: { title: '📌任务添加中心' } },
    { path: '/ProduceAdd/:id', component: () => import('@/views/Add/ProduceAddIndex.vue'), meta: { title: '🎁商品添加中心' } },
    
    { path: '/ProduceDisplay/:id', component: () => import('@/views/display/produceDisplay.vue'), meta: { title: '欢迎-来到商品展示中心' } },
    { path: '/RoomDisplay/:id', component: () => import('@/views/display/roomDisplay.vue'), meta: { title: '欢迎-来到仓库展示中心' } },
    { path: '/TaskDisplay/:id', component: () => import('@/views/display/taskDisplay.vue'), meta: { title: '欢迎-来到任务展示中心' } },
    { path: '/HomeUserDisplay/:id', component: () => import('@/views/display/homeUserDisplay.vue'), meta: { title: '欢迎-来到用户展示中心' } },
    { path: '/UserDisplay/:id', component: () => import('@/views/display/userDisplay.vue'), meta: { title: '欢迎-来到用户中心' } },

    { path: '/display', component: () => import('@/views/display/historyDisplay.vue'), meta: { title: '欢迎-来到总结报告中心' } },
    
    //微信链接
    { path: '/wechatProduceDisplay/:id', component: () => import('@/views/display/wechatProduceDisplay.vue'), meta: { title: '欢迎-来到商品展示中心' } },
    { path: '/wechatRoomDisplay/:id', component: () => import('@/views/display/wechatRoomDisplay.vue'), meta: { title: '欢迎-来到仓库展示中心' } },
    { path: '/wechatTaskDisplay/:id', component: () => import('@/views/display/wechatTaskDisplay.vue'), meta: { title: '欢迎-来到任务展示中心' } },

    // 登录
    { path: '/login', component: () => import('@/views/login/loginIndex.vue'), meta: { title: '登录-欢迎访问卡比之家' } },
    // 注册
    { path: '/userAdd', component: () => import('@/views/Add/UserAddIndex.vue'), meta: { title: '注册-欢迎注册卡比之家' } },

    { path: '/test/:id', component: () => import('@/views/Test/TestIndex.vue'), meta: { title: '欢迎-来到测试中心' } },

    { path: '/setting', component: () => import('@/views/Setting/settingIndex.vue'), meta: { title: '欢迎-来到设置中心' } },

    { path: '/displaySetting', component: () => import('@/views/Setting/displaySetting.vue'), meta: { title: '欢迎-来到海报展示设置中心' } },

    { path: '/homeRequire', component: () => import('@/views/Home/homeRequire.vue'), meta: { title: '欢迎-来到主页设置中心' } },
];

const router = createRouter({
    history: createWebHashHistory(),
    routes
});

router.beforeEach((to, from, next) => {
    // 检查是否存在to.meta.title，如果存在则设置页面标题为to.meta.title，否则使用默认标题
    if (to.meta.title) {
        document.title = `${to.meta.title}`; // 使用字符串模板将其转换为字符串
    } else {
        document.title = '🦄欢迎来到卡比之家';
    }
    next();
});

export default router;
