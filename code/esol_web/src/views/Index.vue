<!-- 登录页 -->
<template>
  <div class="index">
    <div class="center-center">
      <!-- 登录页上方图片 -->
      <van-image :src="require('@/assets/img/1-1.png')" />
      <!-- 登录帐号密码框 -->
      <van-cell-group class="login-cell" @keyup.enter="login">
        <van-field
          input-align="center"
          size="large"
          placeholder="请输入用户名"
          v-model="form.username"
        >
          <van-icon
            class="index_icon"
            :name="require('@/assets/icon/user.png')"
            slot="left-icon"
          />
        </van-field>
        <van-field
          input-align="center"
          size="large"
          placeholder="请输入密码"
          v-model="form.password"
        >
          <van-icon
            class="index_icon"
            :name="require('@/assets/icon/password.png')"
            slot="left-icon"
          />
        </van-field>
        <!-- 登录按钮 -->
        <van-button
          type="default"
          size="large"
          class="index_btn radius"
          block
          color="#DB0303"
          @click="login"
          :loading="loginning"
          >登录</van-button
        >
      </van-cell-group>
    </div>
  </div>
</template>

<script>
export default {
  name: "index",
  data() {
    return {
      //登录请求参数数据
      form: {},
      //设置登录按钮是否为加载状态
      loginning: false
    };
  },
  mounted() {
    this.$store.state.user = {};
  },
  methods: {
    //登录逻辑
    login() {
      this.loginning = true;
      let that = this;

      this.$post(
        this,
        "/login",
        this.$stringify(this.form),
        () => {
          that.$router.replace("/home/list");
        },
        null,
        () => {
          that.loginning = false;
        }
      );
    }
  }
};
</script>

<style scoped>
.center-center {
  width: 80%;
  margin: auto;
}
/* 单元格 */
.login-cell {
  margin-top: 2rem;
}
/* 输入框 */
.van-field {
  margin-top: 1rem;
  padding-left: 0;
  border-bottom: 0.0625rem solid #606060;
}
/* 输入框图标 */
.index_icon {
  padding-right: 1rem;
  border-right: 0.01rem solid #606060;
}
/* 登录按钮 */
.index_btn {
  margin-top: 2rem;
}
/* 背景图片 */
.index {
  top: 0px;
  left: 0px;
  width: 100vw;
  height: 100vh;
  z-index: -1;

  background-image: url(../assets/img/1-2.png);
  background-attachment: fixed;
  background-repeat: no-repeat;
  background-position: bottom;
  background-size: contain;
}
</style>
