let baseApiUrl = "http://127.0.0.1:8080";
let basePath = "/api";
/**
 * 前端的一些配置,比如跨域请求的服务器地址;
 */
export default {
  baseApiUrl,
  basePath,
  proApiUrl: baseApiUrl + basePath,
  devApiUrl: basePath
};
