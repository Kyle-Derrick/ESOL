<!-- 考试界面 -->
<template>
  <div class="exam-top">
    <div class="exam-test radius shadow">
      <div class="exam-title_div">
        <!-- 计时器 -->
        <van-icon :name="require('@/assets/icon/exam_time.png')"></van-icon>
        <span>距离答题结束还剩：</span>
        <van-count-down
          class="time-div"
          :time="time"
          ref="countDown"
          @finish="commit"
        >
          <template v-slot="timeData">
            <span class="time-span">
              <span class="time-item">{{ timeData.hours }}</span>
              :
              <span class="time-item">{{ timeData.minutes }}</span>
              :
              <span class="time-item">{{ timeData.seconds }}</span>
            </span>
          </template>
        </van-count-down>
      </div>

      <!-- 标题 -->
      <div class="content">
        <div class="content-title">
          <h2>{{ examData.examTitle }}</h2>
          <van-row>
            <van-col span="12">
              <span
                >试题总数:
                <span class="highlight">{{
                  examData.examQuestionCount
                }}</span></span
              >
            </van-col>
            <van-col span="12">
              <span
                >总分:
                <span class="highlight">{{ examData.examScore }}</span></span
              >
            </van-col>
          </van-row>
        </div>
        <ExamQuestionList
          :examQuestions="examData.examQuestions"
          v-model="answer"
        ></ExamQuestionList>
        <!-- {{answer}} -->
        <van-button color="#F92121" block @click="showCommitDialogDiv"
          >交卷</van-button
        >
      </div>
    </div>

    <van-dialog
      v-model="showDialog"
      title="退出考试"
      show-cancel-button
      @confirm="backExam"
    >
      <span>确定要退出答题？</span>
      <span>退出历史答题将不会保存</span>
    </van-dialog>
    <van-dialog
      v-model="showCommitDialog"
      title="交卷"
      show-cancel-button
      @confirm="commit"
    >
      <span>确定要交卷吗？</span>
      <span>{{
        answerNum > 0
          ? "您还有" + answerNum + "道题没有完成!"
          : "您已完成所有题目!"
      }}</span>
    </van-dialog>
  </div>
</template>

<script>
import ExamQuestionList from "@/components/ExamQuestionList.vue";
export default {
  name: "examTest",
  components: {
    ExamQuestionList
  },
  mounted() {
    let that = this;
    let examId = this.$route.query.examId;
    this.resNum = this.$route.query.resNum;
    // 获取考试试卷详情
    this.$get(
      this,
      "/exam/" + examId,
      data => {
        that.examData = data.data;
        that.time = that.examData.examDuration * 60 * 1000;
        // that.$refs.countDown.reset();
        that.countDownReStart();
      },
      data => {
        that.$Notify({ type: "danger", message: data.msg });
        that.$router.go(-1);
      },
      null,
      err => {
        that.$Notify({ type: "danger", message: err.message });
        that.$router.go(-1);
      }
    );

    this.$store.state.nav.title = "考试";
  },
  // 组件数据
  data() {
    return {
      time: 30 * 1000,
      answer: {},
      examData: {},
      showDialog: false,
      canBack: false,
      showCommitDialog: false,
      answerNum: 0,
      resNum: 0
    };
  },
  //离开路由时触发钩子
  beforeRouteLeave(to, from, next) {
    if (this.canBack || to.path === "/" || to.path === "/exam/result") {
      if (this.canBack) this.canBack = false;
      next();
    } else {
      this.showDialogDiv();
      next(false);
    }
  },
  methods: {
    // 提交答卷
    commit() {
      let that = this;
      this.$post(this, "/exam", this.answer, data => {
        data.data.resNum = that.resNum - 1;
        that.$router.replace({ name: "result", params: data.data });
      });
    },
    // 计时器重置
    countDownReStart() {
      this.$refs.countDown.reset();
    },
    // 弹出框
    showDialogDiv() {
      this.showDialog = true;
    },
    showCommitDialogDiv() {
      this.answerNum =
        this.examData.examQuestionCount - Object.keys(this.answer).length;
      this.showCommitDialog = true;
    },
    // 退出考试
    backExam() {
      this.$get(this, "/exam/back");
      this.canBack = true;
      this.$router.go(-1);
    }
  }
};
</script>

<style scoped>
.exam-test {
  background-color: white;
}
.exam-title_div {
  padding-top: 1rem;
  padding-bottom: 1.3rem;
  border-bottom: 0.01rem #cacaca solid;

  font-size: 0.875rem;
  line-height: 0.875rem;
  color: #8e8e8e;
}
.exam-title_div > * {
  vertical-align: middle;
}
.exam-title_div > span {
  margin-left: 0.3rem;
}
.time-div {
  display: inline-block;
}
.time-span {
  color: red;
  text-align: center;
  font-weight: bold;
}
.time-span > * {
  vertical-align: middle;
}
.time-item {
  font-weight: normal;
  display: inline-block;
  width: 22px;
  /* margin-right: 5px; */
  color: #fff;
  background-color: red;

  border-radius: 3px;
  overflow: hidden;
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
}
.content-title .van-row {
  color: #6e6e6e;
}
/* 返回弹出框 */
.showDialog span {
  color: #333333;
}
</style>
