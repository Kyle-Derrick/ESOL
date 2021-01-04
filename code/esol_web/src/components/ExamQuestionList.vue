<!-- 答案详情组件 -->
<template>
  <div class="questionPage">
    <div class="questions" v-for="(qs, i) in examQuestions" :key="i">
      <!-- 试卷标题 -->
      <div class="title">
        <div class="title-icon"></div>
        <span
          >{{ btitle[qs.questionType - 1] }}题( {{ qs.questionScore }}×{{
            qs.questionCount
          }}={{ qs.questionCount * qs.questionScore }}分 )</span
        >
      </div>
      <!-- 试卷题列表 -->
      <div class="question-item" v-for="(q, l) in qs.questions" :key="l">
        <div class="question-title">
          <van-icon
            :name="!answerIsTrue(q) ? 'cross' : 'success'"
            :class="!answerIsTrue(q) ? 'cross_icon' : 'success_icon'"
            v-if="disabled"
            color="#FFF"
          ></van-icon>
          <!-- 试题题目 -->
          <span>{{ l + 1 }}. {{ q.title }}</span>
        </div>
        <div class="question-option">
          <!-- 单选 -->
          <template v-if="qs.questionType == 1">
            <van-radio-group
              v-model="answer[q.questionLibId + '']"
              :disabled="disabled"
            >
              <van-radio name="" disabled v-show="false" />
              <van-radio name="A" checked-color="#F00">{{ q.itemA }}</van-radio>
              <van-radio name="B" checked-color="#F00">{{ q.itemB }}</van-radio>
              <van-radio name="C" checked-color="#F00">{{ q.itemC }}</van-radio>
              <van-radio name="D" checked-color="#F00">{{ q.itemD }}</van-radio>
            </van-radio-group>
          </template>
          <!-- 多选 -->
          <template v-else-if="qs.questionType == 2">
            <van-checkbox-group
              v-model="answer[q.questionLibId + '']"
              :disabled="disabled"
            >
              <van-checkbox name="" disabled v-show="false" />
              <van-checkbox name="A" checked-color="#F00" shape="square">{{
                q.itemA
              }}</van-checkbox>
              <van-checkbox name="B" checked-color="#F00" shape="square">{{
                q.itemB
              }}</van-checkbox>
              <van-checkbox name="C" checked-color="#F00" shape="square">{{
                q.itemC
              }}</van-checkbox>
              <van-checkbox name="D" checked-color="#F00" shape="square">{{
                q.itemD
              }}</van-checkbox>
            </van-checkbox-group>
          </template>
          <!-- 判断 -->
          <template v-else>
            <van-radio-group
              v-model="answer[q.questionLibId + '']"
              :disabled="disabled"
            >
              <van-row type="flex" justify="space-around">
                <van-radio name="" disabled v-show="false" />
                <van-col span="8">
                  <van-radio name="T" checked-color="#F00">正确</van-radio>
                </van-col>
                <van-col span="8">
                  <van-radio name="F" checked-color="#F00">错误</van-radio>
                </van-col>
              </van-row>
            </van-radio-group>
          </template>
        </div>
        <!-- 若为答案详情页面即且该题错误,即展示答案和解析 -->
        <div class="question-answer" v-if="disabled && !answerIsTrue(q)">
          <div class="answer">参考答案：{{ q.refAnswer }}</div>
          <div class="analysis">解析：{{ q.analysis }}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "examQuestionList",
  // 答案对象
  props: {
    value: {
      type: Object
    },
    // 问题数组
    examQuestions: {
      type: Array
    },
    // 是否禁用选择
    disabled: {
      type: Boolean,
      default: false
    }
  },
  // ["value", "examQuestions", "disable"],
  data() {
    return {
      // 题型
      btitle: ["单选", "多选", "判断"],
      // 答案
      answer: this.value
    };
  },
  watch: {
    // 监听答案变化并向父级响应
    answer(nv) {
      this.$emit("input", nv);
    }
  },
  methods: {
    strToArray(str) {
      if (str) {
        str = str.replace(/[\s\[\]]/g, "");
        return str.split(",");
      }
      return [];
    },
    answerIsTrue(answer) {
      let refa = answer.refAnswer;
      let a = answer.answer;
      if (a === null) return false;
      if (answer.questionKind === 2) {
        let aArr = this.strToArray(a);
        this.answer[answer.questionLibId + ""] = aArr;
        refa = JSON.stringify(this.strToArray(refa));
        a = JSON.stringify(aArr);
      } else {
        this.answer[answer.questionLibId + ""] = a;
      }
      return refa === a;
    }
  }
};
</script>

<style scoped>
.questionPage {
  margin-top: 1rem;
  text-align: left;
  color: #2c3e50 !important;
  font-family: "宋体";
}
.questions {
  margin-top: 0.5rem;
  padding-bottom: 1.5rem;
}
/* 试卷标题 */
.title {
  font-size: 0.9375rem;
  line-height: 0.9375rem;
  font-weight: bold;
}
.title > * {
  vertical-align: text-bottom;
}
.title > span {
  margin-left: 0.5rem;
}
.title-icon {
  display: inline-block;
  width: 0.1875rem;
  height: 0.9375rem;
  background-color: red;
}
/* 试卷试题 */
.question-item {
  margin-top: 1.5rem;
  font-size: 0.875rem;
}
/* 试题选项 */
.question-option {
  margin-top: 1rem;
  /* padding-bottom: .5rem; */
}
/* 试题题目 */
.question-title {
  position: relative;
  line-height: 1.5rem;
  /* height: 1.5rem; */
}
.question-title .van-icon {
  position: absolute;
  top: 0.3rem;
  /* top: 50%; */
  /* transform: translateY(-50%); */
}
.question-title > * {
  vertical-align: middle;
}
.question-title span {
  padding-left: 1.5rem;
}
.van-radio,
.van-checkbox {
  margin-top: 1rem;
}

/* 试卷正确答案 */
.question-answer {
  border: red 0.5px dotted;
  margin-top: 1rem;
  padding: 0.5rem;
}
.question-answer * {
  /* margin-top: 1rem; */
  color: red;
}
.question-answer .analysis {
  margin-top: 0.5rem;
}
/* 答案正确图标 */
.success_icon {
  background-color: #13cc6f;
}
/* 答案错误图标 */
.cross_icon {
  background-color: #f92121;
}
</style>
