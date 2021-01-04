<!-- 主页模块所有路由 -->
<template>
  <div class="home">
    <transition name="van-fade" mode="out-in">
      <!-- 主页面子路由 -->
      <router-view />
    </transition>

    <!-- 页面底部标签栏 -->
    <van-tabbar
      active-color="#EA3D3D"
      inactive-color="#BFBFBF"
      route
      v-model="active"
    >
      <van-tabbar-item :icon="getEditIcon" name="list" replace to="/home/list"
        >考试</van-tabbar-item
      >
      <van-tabbar-item :icon="getMyIcon" name="my" replace to="/home/my"
        >我的</van-tabbar-item
      >
    </van-tabbar>
  </div>
</template>

<script>
// @ is an alias to /src
// import HelloWorld from "@/components/HelloWorld.vue";

// 导入相关图片资源
let editIcon = require("@/assets/icon/edit.png");
let editRedIcon = require("@/assets/icon/edit_red.png");
let myIcon = require("@/assets/icon/my.png");
let myRedIcon = require("@/assets/icon/my_red.png");

export default {
  name: "home",
  data() {
    return {
      active: "list"
    };
  },
  mounted() {
    // 初始化底部标签栏图标
    this.active = this.$route.name;
  },

  computed: {
    // 获取考试页图标(区分选中和未选中)
    getEditIcon() {
      let icon = editRedIcon;
      if (this.active === "my") {
        icon = editIcon;
      }
      return icon;
    },
    // 获取个人中心页图标(区分选中和未选中)
    getMyIcon() {
      let icon = myIcon;
      if (this.active === "my") {
        icon = myRedIcon;
      }
      return icon;
    }
  }
};
</script>

<style scoped>
.home {
  background-color: #f4f4f4;
  min-height: 100vh;
}
</style>
