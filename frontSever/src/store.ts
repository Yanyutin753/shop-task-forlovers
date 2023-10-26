import { createStore } from 'vuex';

const store = createStore({
  state: {
    keyword: '小羊是帅哥',
  },
  mutations: {
    setKeyword(state, newKeyword) {
      state.keyword = newKeyword;
    },
  },
});

export default store;
