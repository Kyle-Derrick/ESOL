import Vue from "vue";

/**
 * 对于调用axios的get或者post等方法后的promise根据传入参数进行操作,
 * 比如未登录直接跳转回登录页
 * @param {Object} that 组件中的this指向的对象
 * @param {Object} pos promise对象
 * @param {Object} sucsessFun 成功后回调函数
 * @param {Object} errorFun 失败后回调函数
 * @param {Object} finallyFun 最后回调finally块
 */
Vue.prototype.$afterHttp = function(
  that,
  pos,
  sucsessFun,
  failFun,
  finallyFun,
  errorFun
) {
  pos
    .then(res => {
      let data = res.data;
      if (data.code === 1) {
        if (sucsessFun) sucsessFun(data);
      } else if (data.code === 403) {
        that.$message({
          showClose: true,
          message: '未登录',
          type: 'error'
        });
        that.$router.replace("/");
      } else {
        if (failFun) failFun(data);
        else that.$message({
          showClose: true,
          message: data.msg,
          type: 'error'
        });
      }
    })
    .catch(err => {
      if (errorFun) errorFun(err);
      else that.$message({
          showClose: true,
          message: err.message,
          type: 'error'
        });
    })
    .finally(() => {
      if (finallyFun) finallyFun();
    });
};
/**
 * 包装后的get请求
 * @param {Object} that 组件中的this指向的对象
 * @param {Object} url 请求url地址
 * @param {Object} sucsessFun 成功后回调函数
 * @param {Object} errorFun 失败后回调函数
 * @param {Object} finallyFun 最后回调finally块
 */
Vue.prototype.$get = function(
  that,
  url,
  sucsessFun,
  failFun,
  finallyFun,
  errorFun
) {
  let pos = that.$http.get(url);
  that.$afterHttp(that, pos, sucsessFun, failFun, finallyFun, errorFun);
};
/**
 * 包装后的post请求
 * @param {Object} that 组件中的this指向的对象
 * @param {Object} url 请求url地址
 * @param {Object} data 请求参数
 * @param {Object} sucsessFun 成功后回调函数
 * @param {Object} errorFun 失败后回调函数
 * @param {Object} finallyFun 最后回调finally块
 */
Vue.prototype.$post = function(
  that,
  url,
  data,
  sucsessFun,
  failFun,
  finallyFun,
  errorFun
) {
  let pos = that.$http.post(url, data);
  that.$afterHttp(that, pos, sucsessFun, failFun, finallyFun, errorFun);
};
