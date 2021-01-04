<!-- 考试结果界面 -->
<template>
  <div class="exam-top">
    <div class="examResult radius shadow">
      <div class="exam-result-title">
        <span class="title">本次答题得分</span>
        <span>{{ result.sorce }}分</span>
      </div>
      <div class="exam-result-content">
        <van-row>
          <van-col span="10">
            <span
              >正确率：<span class="highlight"
                >{{
                  parseInt(
                    (result.sucessNum / (result.sucessNum + result.errorNum)) *
                      100
                  )
                }}%</span
              ></span
            >
          </van-col>
          <van-col span="14">
            <span
              >答题用时：<span class="highlight">{{
                getDur(result)
              }}</span></span
            >
          </van-col>
        </van-row>
        <van-row>
          <van-col span="10">
            <span
              >错题数：<span class="highlight">{{
                result.errorNum
              }}</span></span
            >
          </van-col>
          <van-col span="14">
            <span
              >剩余答题次数：<span class="highlight">{{
                result.resNum
              }}</span></span
            >
          </van-col>
        </van-row>
        <div class="details-div">
          <van-button
            size="mini"
            plain
            hairline
            color="#8E8E8E"
            class="details"
            @click="toAnswer"
          >
            <span>查看详情</span>
            <van-icon name="arrow"></van-icon>
          </van-button>
        </div>
      </div>

      <div class="exam-result-footer">
        <van-row type="flex" justify="space-around">
          <van-col span="10">
            <van-button color="#8E8E8E" block plain hairline @click="back"
              >返回</van-button
            >
          </van-col>
          <van-col span="10">
            <van-button
              color="#FA6744"
              block
              :disabled="result.resNum <= 0"
              @click="reExam(result.testPaper.testPaperId, result.resNum)"
              >重新答题</van-button
            >
          </van-col>
        </van-row>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "examResult",
  data() {
    return {
      result: {}
    };
  },
  mounted() {
    this.$store.state.nav.title = "考试结果";
    this.result = this.$route.params;
    if (!this.result.testPaper) this.$router.go(-1);
  },
  methods: {
    // 获取时长字符串
    getDur(result) {
      let date = new Date(57600000 + result.duration * 1000);
      return (
        date.getHours() + "：" + date.getMinutes() + "：" + date.getSeconds()
      );
    },
    // 跳转到答案详情页面
    toAnswer() {
      this.$router.replace({ name: "answer", params: this.result });
    },
    back() {
      this.$router.go(-1);
    },
    // 重新考试
    reExam(examId, resNum) {
      this.$router.replace({ path: "/exam/test", query: { examId, resNum } });
    }
  }
};
</script>

<style scoped>
.examResult {
  text-align: left;
  font-family: "黑体";
  background-color: white;
}
.exam-result-title {
  height: 10.9375rem;
  background-image: url(../../assets/img/4-1.png);
  background-attachment: scroll;
  background-repeat: no-repeat;
  background-position: top;
  background-size: 100.5% auto;
}
.exam-result-title > * {
  display: block;
  color: #f9ddcc;
  padding-left: 1rem;
  padding-top: 1.5rem;
  font-size: 1.5rem;
}
.exam-result-title .title {
  font-size: 1rem;
}
.exam-result-content {
  color: #6e6e6e;
  font-size: 0.9375rem;

  margin-top: 2rem;
  padding-left: 1rem;
}
.exam-result-content .van-row {
  margin-top: 1.5rem;
}
.exam-result-footer {
  margin-top: 3rem;
  font-size: 1rem;
  padding-bottom: 1rem;
}
.details-div {
  text-align: right;
}
.details {
  margin: 1rem;
  margin-bottom: 0;
  font-size: 0.75rem;
  line-height: 0.75rem;
  padding: 0 0.3rem;
}
.details span > * {
  display: inline-block;
  vertical-align: text-bottom;
}
</style>
