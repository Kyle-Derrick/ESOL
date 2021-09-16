import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);
/**
 * 前端Vuex 配置
 */
export default new Vuex.Store({
  state: {
    user: {},
    host: kyle_config.baseApiUrl,
    path: kyle_config.basePath,
    nav: {
      title: "",
      back: null,
      set(t, b) {
        if (t) this.title = t;
        if (b) this.back = b;
      },
      reSetToDefault() {
        this.title = this.titleD;
        this.back = this.backD;
      },
      setDefault(b) {
        if (b) this.back = this.backD = b;
      }
    }
  },
  mutations: {
    updateUser(state, user) {
      state.user = user;
    }
  },
  actions: {},
  modules: {},
  plugins: [
      createPersistedState({
        key: "esol_state",
        storage: window.sessionStorage
      })
  ]
});
