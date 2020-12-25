import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import axios from "axios";
import "./plugins/vant.js";
import config from "./assets/js/config.js";
import qs from "qs";
import { Notify } from "vant";
import { Toast } from "vant";
import "@/assets/js/setPrototype.js";
// import mui from "/js/mui.min.js";

Vue.config.productionTip = false;

axios.defaults.baseURL =
  process.env.NODE_ENV === "production" ? config.proApiUrl : config.devApiUrl;

axios.defaults.withCredentials = true;
axios.defaults.timeout = 30000;
Vue.prototype.$http = axios;
Vue.prototype.$Notify = Notify;
Vue.prototype.$Toast = Toast;

Vue.prototype.$stringify = data => qs.stringify(data);

Vue.prototype.$mui = mui;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
