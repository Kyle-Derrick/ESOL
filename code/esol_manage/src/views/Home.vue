<!-- 主页模块所有路由 -->
<template>
  <el-container class="home">
    <el-header>
      <el-row>
        <el-col :span="12">在线考试系统</el-col>
        <el-col
          :span="12"
          style="text-align: right;box-sizing: border-box;padding-top: 0.5rem;"
        >
          <el-dropdown size="medium" @command="handleCommand">
            <el-avatar
              src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
            ></el-avatar>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item icon="el-icon-lock" command="u"
                >修改密码</el-dropdown-item
              >
              <el-dropdown-item icon="el-icon-connection" command="l"
                >注销</el-dropdown-item
              >
            </el-dropdown-menu>
          </el-dropdown>
        </el-col>
      </el-row>
    </el-header>
    <el-container>
      <el-aside width="200px">
        <el-menu
          router
          :default-active="user.kind"
          class="el-menu-left"
          background-color="rgb(255,255,255,0)"
          text-color="#fff"
          active-text-color="#ffd04b"
        >
          <el-menu-item index="1" route="user" v-if="user.kind === 1">
            <i class="el-icon-user"></i>
            <span slot="title">用户管理</span>
          </el-menu-item>
          <el-menu-item index="2" route="dept" v-if="user.kind === 1">
            <i class="el-icon-menu"></i>
            <span slot="title">部门管理</span>
          </el-menu-item>
          <el-menu-item index="3" route="question">
            <i class="el-icon-coin"></i>
            <span slot="title">题库管理</span>
          </el-menu-item>
          <el-menu-item index="4" route="exam">
            <i class="el-icon-document"></i>
            <span slot="title">试卷管理</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-main style="border-radius: 5px 0 0 0">
        <transition name="el-fade-in" mode="out-in">
          <!-- 主页面子路由 -->
          <router-view />
        </transition>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: "home",
  data() {
    return {};
  },
  mounted() {},
  computed: {
    // 从vuex中获取用户对象
    user() {
      return this.$store.state.user;
    }
  },
  methods: {
    handleCommand(command) {
      switch (command) {
        case "u":
          this.updatePassWord();
          break;
        case "l":
          this.logout();
          break;
      }
    },
    updatePassWord() {
      let that = this;
      this.$prompt("请输入新密码", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消"
      })
        .then(({ value }) => {
          that.$post(
            that,
            "/user/save",
            {
              userId: that.user.userId,
              passWord: value
            },
            () => {
              that.$message({
                type: "success",
                message: "更改成功"
              });
            }
          );
        })
        .catch(() => {});
    },
    logout() {
      let that = this;
      this.$post(this, "/logout", null, () => {
        that.$router.replace("/");
      });
    }
  }
};
</script>

<style scoped>
.home {
  height: 100vh;
}
.el-menu-left {
  border: 0;
}
.el-header {
  /* background-color: #25A18E; */
  background-image: linear-gradient(rgba(0, 0, 0, 0.4), rgba(255, 255, 255, 0));
  color: #9fffcb;
  text-align: left;
  padding-left: 2rem;
  font-weight: bold;
  line-height: 60px;
}

.el-aside {
  /* background-color: #7AE582; */
  text-align: left;
  line-height: 200px;
  /*border-right: #fff 1px solid;*/
}

.el-main {
  /* background-color: #9FFFCB; */
  background-color: #fff;
  text-align: center;
  /* line-height: 160px; */
}
</style>
