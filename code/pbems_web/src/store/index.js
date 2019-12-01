import Vue from "vue";
import Vuex from "vuex";
import config from "@/assets/js/config.js";

Vue.use(Vuex);
/**
 * 前端Vuex 配置
 */
export default new Vuex.Store({
  state: {
    user: {},
    host: config.baseApiUrl,
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
  mutations: {},
  actions: {},
  modules: {}
});
