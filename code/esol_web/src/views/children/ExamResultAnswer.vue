<!-- 考试结果详情界面 -->
<template>
  <div class="exam-top">
    <div class="exam-result-answer radius shadow">
      <div class="content">
        <!-- 标题 -->
        <div class="content-title">
          <h2>{{ getTestPaperValue(result, "title") }}</h2>
          <van-row>
            <van-col span="8">
              <span
                >用时：<span class="highlight">{{ getDur(result) }}</span></span
              >
            </van-col>
            <van-col span="8">
              <span
                >得分：<span class="highlight">{{ result.sorce }}</span></span
              >
            </van-col>
            <van-col span="8">
              <span
                >总分：<span class="highlight">{{
                  getTestPaperValue(result, "totalSorce")
                }}</span></span
              >
            </van-col>
          </van-row>
          <van-row>
            <van-col span="8">
              <span
                >正确率：
                <span class="highlight">
                  {{
                    parseInt(
                      (result.sucessNum /
                        (result.sucessNum + result.errorNum)) *
                        100
                    )
                  }}%
                </span>
              </span>
            </van-col>
            <van-col span="8">
              <span
                >错误数：<span class="highlight">{{
                  result.errorNum
                }}</span></span
              >
            </van-col>
            <van-col span="8">
              <span
                >试题总数：<span class="highlight">{{
                  getTestPaperValue(result, "totalNum")
                }}</span></span
              >
            </van-col>
          </van-row>
        </div>
        <!-- 详细内容 -->
        <ExamQuestionList
          :examQuestions="result.examQuestions"
          v-model="getAnswer"
          disabled
        ></ExamQuestionList>
        <!-- {{answer}} -->
      </div>
    </div>
  </div>
</template>

<script>
import ExamQuestionList from "@/components/ExamQuestionList.vue";
export default {
  name: "examTest",
  components: {
    ExamQuestionList
  },
  data() {
    return {
      result: {}
    };
  },
  mounted() {
    this.result = this.$route.params;
    if (!this.result.examQuestions) {
      this.$router.go(-1);
    }
  },
  computed: {
    getAnswer() {
      return {
        "12": "B"
      };
    }
  },
  methods: {
    // 获取时长字符串
    getDur(result) {
      let date = new Date(57600000 + result.duration * 1000);
      return (
        date.getHours() + "：" + date.getMinutes() + "：" + date.getSeconds()
      );
    },
    // 获取试卷信息
    getTestPaperValue(result, key) {
      return result.testPaper ? result.testPaper[key] : "";
    }
  }
};
</script>

<style scoped>
.exam-result-answer {
  background-color: white;
}
.content {
  padding: 0.5rem;
}
.content-title h2 {
  font-size: 1.2rem;
}
.content-title {
  font-size: 0.5rem;
  padding-bottom: 1rem;

  border-bottom: 0.5px #cacaca dotted;
}
.content-title .van-row {
  margin-top: 1rem;
  color: #6e6e6e;
}
</style>
