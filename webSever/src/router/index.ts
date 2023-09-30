import { createRouter, createWebHashHistory } from 'vue-router';

const routes = [
    { path: '/', component: () => import('@/views/Home/HomeIndex.vue') },
    { path: '/Task/:id', component: () => import('@/views/Task/TaskIndex.vue') },
    { path: '/Produce/:id', component: () => import('@/views/Produce/ProduceIndex.vue') },
    { path: '/Room/:id', component: () => import('@/views/Room/RoomIndex.vue') },
    
    { path: '/UserRequire/:id', component: () => import('@/views/Add/UserRequireIndex.vue') },
    { path: '/TaskAdd/:id', component: () => import('@/views/Add/TaskAddIndex.vue') },
    { path: '/ProduceAdd/:id', component: () => import('@/views/Add/ProduceAddIndex.vue') },
    
    { path: '/ProduceDisplay/:id', component: () => import('@/views/display/produceDisplay.vue') },
    { path: '/RoomDisplay/:id', component: () => import('@/views/display/roomDisplay.vue') },
    { path: '/TaskDisplay/:id', component: () => import('@/views/display/taskDisplay.vue') },
    { path: '/UserDisplay/:id', component: () => import('@/views/display/userDisplay.vue') },
    
    // 登录
    { path: '/login', component: () => import('@/views/login/loginIndex.vue') },
    // 注册
    { path: '/userAdd', component: () => import('@/views/Add/UserAddIndex.vue') },

    { path: '/test', component: () => import('@/views/Test/TestIndex.vue') },
];

const router = createRouter({
    history: createWebHashHistory(),
    routes
});

export default router;
