<!-- 主页面 -->
<template>
  <div class="home-list-main-div">
    <div class="home-list">
      <!-- 主页面广告轮播图 -->
      <van-swipe :autoplay="5000" class="home-list-swipe">
        <van-swipe-item v-for="(ads, i) in adsList" :key="i">
          <a :href="ads.herfUrl">
            <van-image
              :src="ads.imgUrl"
              width="100%"
              height="26vh"
              :title="ads.title"
            />
            <!-- <a href="https://www.baidu.com">
            <van-image :src="require('@/assets/img/2-1.png')" width="100%" /> -->
          </a>
        </van-swipe-item>
      </van-swipe>

      <!-- 主页考试列表 -->
      <div class="list-main-div">
        <div class="title">
          <van-icon :name="require('@/assets/icon/001.png')"></van-icon>
          <span class="title-text">考试列表</span>
        </div>

        <HomeListItems :list="listData"></HomeListItems>
      </div>
    </div>
  </div>
</template>

<script>
// 引入考试列表组件
import HomeListItems from "@/components/HomeListItems.vue";
export default {
  name: "homeList",
  components: {
    HomeListItems
  },
  data() {
    return {
      // 广告数据数组
      adsList: [],
      // 考试列表数组
      listData: []
    };
  },
  mounted() {
    let that = this;
    //从服务器获取广告数据
    this.$get(this, "/ads", data => {
      that.adsList = data.data;
    });
    // 从服务器获取考试列表
    this.$get(this, "/exam", data => {
      that.listData = data.data.reverse();
    });
  }
};
</script>

<style scoped>
.home-list-main-div {
  background-color: white;
  min-height: 100vh;
}
.home-list-swipe {
  height: 26vh;
}
.home-list {
  /* padding: 1rem; */
  width: 95%;
  padding-top: 0.3rem;
  margin: auto;
  /* position: absolute; */

  font-family: "黑体";
}
.list-main-div {
  width: 95%;
  margin: auto;
  text-align: left;
  padding-top: 1rem;
}
.title {
  /* height: 1.25rem; */
  font-size: 1.2rem;
  line-height: 1.2rem;
}
.title > * {
  vertical-align: middle;
  height: 1.2rem;
}
.title span {
  padding-left: 0.5rem;
  font-weight: bold;
  display: inline-block;
}
a {
  display: block;
}
</style>
