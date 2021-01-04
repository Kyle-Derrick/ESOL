<!-- 考试模块下所有路由 -->
<template>
  <div>
    <!-- 考试模块顶部导航栏 -->
    <van-nav-bar :title="navTitle" left-arrow fixed @click-left="navBack" />

    <div class="exam">
      <transition name="van-fade" mode="out-in">
        <!-- 考试模块子路由 -->
        <router-view></router-view>
      </transition>
    </div>
    <!-- <van-button color="#BEBEBE" @click="back" block>退出</van-button> -->
  </div>
</template>

<script>
export default {
  name: "exam",
  data() {
    return {};
  },
  created() {
    // 设置导航栏默认配置
    this.$store.state.nav.setDefault(this.back);
  },
  computed: {
    // 获取导航栏标题
    navTitle() {
      return this.$store.state.nav.title;
    },
    // 获取导航栏左侧图标点击事件
    navBack() {
      return this.$store.state.nav.back;
    }
  },
  methods: {
    back() {
      //返回路由
      // this.$router.back(-1);
      this.$router.go(-1);
    }
  }
};
</script>
