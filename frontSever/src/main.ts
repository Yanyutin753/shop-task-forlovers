import { createApp, ref } from 'vue';
import App from './App.vue';
import 'vant/lib/index.css';
//转向用的
import router from './router';
//标签栏
import { Tabbar, TabbarItem } from 'vant';
//加载图片懒模式
import { Swipe, SwipeItem } from 'vant';
import { NavBar } from 'vant';
import { ContactList } from 'vant';
import { AddressEdit } from 'vant';
import { FloatingBubble } from 'vant';
import { Button } from 'vant';
import { Form, Field, CellGroup } from 'vant';
import { Uploader } from 'vant';
import { Loading } from 'vant';
import { Search } from 'vant';
import { NoticeBar } from 'vant';
import { Dialog } from 'vant';
import { BackTop } from 'vant';
import { TextEllipsis } from 'vant';
import { Slider } from 'vant';
import { Toast } from 'vant';
import { Notify } from 'vant';
import { Checkbox, CheckboxGroup } from 'vant';
import { Grid, GridItem } from 'vant';
import {
    Skeleton,
    SkeletonTitle,
    SkeletonImage,
    SkeletonAvatar,
    SkeletonParagraph,
} from 'vant';
import { ShareSheet } from 'vant';
import { ActionSheet } from 'vant';
import { Empty } from 'vant';
import store from './store'


//滑动单元格

const app = createApp(App).use(store);
app.use(AddressEdit);
//滑动单元格
//积分和代办栏
app.use(ContactList);
// 主题栏
app.use(NavBar);
app.use(router);//转向用的
//标签栏
app.use(Tabbar);
app.use(TabbarItem);
//加载图片懒模式
app.use(Swipe);

app.use(SwipeItem);

app.use(FloatingBubble);
app.use(Button);

app.use(Form);
app.use(Field);
app.use(CellGroup);

// 加载
app.use(Loading)

// 下载
app.use(Uploader);

// 通知栏
app.use(NoticeBar);

// 搜索
app.use(Search);

// 弹框
app.use(Dialog);

app.use(BackTop);

app.use(TextEllipsis);

app.use(Slider);

app.use(Toast);

app.use(Notify);

app.use(Checkbox);

app.use(CheckboxGroup);

app.use(Grid);

app.use(GridItem);

app.use(Skeleton);
app.use(SkeletonTitle);
app.use(SkeletonImage);
app.use(SkeletonAvatar);
app.use(SkeletonParagraph);

app.use(ShareSheet);
app.use(ActionSheet);

app.use(Empty);

// 注册密钥
const loginLock = ref('小羊是帅哥');

app.provide('loginLock', loginLock);

// 海报文字
const displayItem = ref('❤在一起');

app.provide('displayItem', displayItem);

// 海报距离日期
const displayDay = ref('2022-10-06');

app.provide('displayDay', displayDay);

// 海报右下角名字
const displayLogo = ref('🏰捏捏的魔仙堡');

app.provide('displayLogo', displayLogo);


app.use(store);

app.mount('#app');
