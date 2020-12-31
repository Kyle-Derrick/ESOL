<!-- 登录页 -->
<template>
  <div class="index">
    <div class="center-center">
      <!-- 登录帐号密码框 -->
	  <el-card class="login-cell">
		  <el-form :model="form" @keypress.native.enter="login">
			<h2>登录</h2>
			<el-form-item>
			  <el-input v-model="form.username" placeholder="请输入账号">
					<i slot="prepend" class="el-icon-user"></i>
			  </el-input>
			</el-form-item>
			<el-form-item>
			  <el-input v-model="form.password" show-password placeholder="请输入密码">
					<i slot="prepend" class="el-icon-user"></i>
			  </el-input>
			</el-form-item>
			<!-- 登录按钮 -->
			<el-form-item>
				<el-button type="primary" 
				  class="index_btn radius"
				   size="medium"
				  @click="login"
				  :loading="loginning"
				  round
				  > 登录 </el-button
				>
			</el-form-item>
		  </el-form>
	  </el-card>
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
        data => {
		  this.$store.state.user = data.data;
          that.$router.replace("/home/user");
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
  width: 22rem;
  margin: 0 auto;
  background-color: #F2F6FC;
  padding: 2rem;
  border-radius: 5px;
}
.login-cell h2 {
	 color: #606266;
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

/*  background-image: linear-gradient(#67C23A, #13CC6F); */
}
</style>
