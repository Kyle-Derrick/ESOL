import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import axios from "axios";
import "./plugins/element.js";
import qs from "qs";
import "@/assets/js/setPrototype.js";

Vue.config.productionTip = false;

axios.defaults.baseURL =
  process.env.NODE_ENV === "production" ? kyle_config.proApiUrl : kyle_config.devApiUrl;

axios.defaults.withCredentials = true;
axios.defaults.timeout = 30000;
Vue.prototype.$http = axios;

Vue.prototype.$stringify = data => qs.stringify(data);

Vue.prototype.$mui = mui;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
