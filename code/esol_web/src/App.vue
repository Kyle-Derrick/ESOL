<template>
  <div id="app">
    <transition name="van-fade" mode="out-in">
      <router-view />
    </transition>

    <van-button
      @click="showPopup"
      size="mini"
      plain
      hairline
      icon="ellipsis"
      block
      color="#999"
      class="moreBtn"
    ></van-button>
    <van-popup v-model="show" class="more-div-popup" closeable>
      <div class="more-div" @keyup.enter="changeHost">
        <van-field size="small" v-model="host"></van-field>
        <van-button
          plain
          hairline
          block
          color="#999"
          size="small"
          @click="changeHost"
          >确定</van-button
        >
      </div>
    </van-popup>

    <!-- 开启 safe-area-inset-bottom 属性 -->
    <van-number-keyboard safe-area-inset-bottom />
  </div>
</template>

<script>
export default {
  name: "app",
  data() {
    return {
      show: false,
      host: this.$store.state.host
    };
  },
  methods: {
    changeHost() {
      this.$store.state.host = this.host;
      this.$http.defaults.baseURL = this.host + this.$store.state.path;
      this.show = false;
    },
    showPopup() {
      this.show = true;
    }
  }
};
</script>
<style lang="less">
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
.van-button.moreBtn {
  position: fixed;
  top: 0.5rem;
  right: 0.5rem;
  width: 3rem;
  opacity: 0.1;
}
.more-div-popup {
  border-radius: 5px;
  overflow: hidden;
  width: 70vw;
}
.more-div {
  padding: 1rem;
}
.more-div .van-field {
  border-bottom: #606060 1px solid;
}
.more-div .van-button {
  margin-top: 0.5rem;
}
</style>
