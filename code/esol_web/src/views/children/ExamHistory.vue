<!-- 历史考试面 -->
<template>
  <div class="exam-history">
    <div class="list-main-div">
      <div
        class="history-item radius shadow"
        v-for="(item, i) in listData"
        :key="i"
      >
        <!-- 若为缺考,显示缺考 -->
        <h2>
          {{ item.examTitle }} (总分:{{ item.totalSorce }})<van-tag
            v-if="item.testNum === 0"
            >缺考</van-tag
          >
        </h2>
        <van-row gutter="4">
          <van-col span="8">
            <span
              >剩余答题<span class="highlight_gary">{{ item.examResNum }}</span
              >次</span
            >
          </van-col>
          <van-col span="12">
            <span
              >最终得分<span class="highlight">{{ item.examSorce }}</span
              >分</span
            >
          </van-col>
        </van-row>
        <div class="item-footer">
          <span>最后一次答题日期：{{ item.examLastDate }}</span>
          <a
            v-if="item.testNum !== 0"
            href="javascript:;"
            class="text-btn"
            @click="toHistoryTest(item)"
            ><span>查看详情</span></a
          >
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "homeList",
  mounted() {
    let that = this;
    // this.$store.state.nav.title = "历史考试";
    this.$store.commit("updateNavTitle", "历史考试");
    // 获取历史列表
    this.$get(this, "/exam/history", data => {
      that.listData = data.data.reverse();
    });
  },
  data() {
    return {
      // 列表数组
      listData: []
    };
  },
  methods: {
    // 跳转到答卷列表页面
    toHistoryTest(item) {
      if (item.examId !== null) {
        let that = this;
        this.$get(this, "/exam/history/" + item.examId, data => {
          that.$router.push({
            name: "resultHistory",
            params: { data: data.data, title: item.examTitle }
          });
        });
      }
    }
  }
};
</script>

<style scoped>
.exam-history {
  min-height: 100vh;
  background-color: #f4f4f4;
}
.list-main-div {
  /* width: 95%; */
  margin: auto;
  text-align: left;
  padding: 1rem;
  padding-top: 2.875rem;
}
.history-item {
  position: relative;
  background-color: #fff;
  padding: 0.5rem 1rem;
  margin-top: 1.2rem;
  font-size: 0.875rem;
  line-height: 0.875rem;
}
.history-item h2 {
  color: #444;
  font-size: 0.875rem;
}
.history-item span {
  margin-top: 0.3rem;
}
.history-item .item-footer {
  margin-top: 0.5rem;
  position: relative;
}
.history-item .item-footer > * {
  vertical-align: middle;
}
.text-btn {
  position: absolute;
  right: 0;
  top: 0;
  height: 100%;
  display: block;
  height: 100%;
  color: #f99c0a;
}
.van-tag {
  position: absolute;
  top: 0;
  right: 0;
  border-radius: 999px 0 0 999px;
  padding-left: 0.6rem;
}
</style>
