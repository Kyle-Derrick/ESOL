<!-- 主页面考试列表组件 -->
<template>
  <div class="list">
    <!-- 列表项 -->
    <div
      class="list-item"
      v-for="(item, i) in list"
      :key="i"
      @click="
        getIsInvalid(item) ? toExam(item.examId, item.examResNum) : canNotExam()
      "
    >
      <!-- 标题 -->
      <div class="list-title">
        <span v-text="item.examTitle"></span>
        <!-- 标签 -->
        <div :class="lableClass(item)"></div>
        <!-- <div class="lable-div lable-div_finish"></div> -->
      </div>
      <!-- 内容 -->
      <div class="list-content">
        <van-row type="flex" justify="space-between">
          <van-col span="8">
            <div class="subhead">
              <van-icon :name="require('@/assets/icon/time.png')"></van-icon>
              <span>答题时间</span>
            </div>
            <div class="content">
              <span
                >{{ getDate(item.examStartTime) }}-{{
                  getDate(item.examEndTime)
                }}</span
              >
            </div>
          </van-col>
          <van-col span="5.5rem">
            <div class="subhead">
              <van-icon :name="require('@/assets/icon/dur.png')"></van-icon>
              <span>答题时长</span>
            </div>
            <div class="content">
              <span>{{ item.examDuration }}分钟</span>
            </div>
          </van-col>
          <van-col span="5.5rem">
            <div class="subhead">
              <van-icon
                :name="require('@/assets/icon/answer_num.png')"
              ></van-icon>
              <span>答题次数</span>
            </div>
            <div class="content">
              <span :class="numClass(item.examDoneNum)"
                >{{ item.examDoneNum }}次</span
              >
            </div>
          </van-col>
        </van-row>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "homeListItems",
  props: ["list"],
  methods: {
    // 根据数据具体值返回标签的class
    lableClass(i) {
      let startDate = new Date(i.examStartTime);
      let endDate = new Date(i.examEndTime);
      let nowDate = new Date();
      let doneNum = i.examDoneNum;
      let className = "lable-div_need-checking";
      if (startDate > nowDate) {
        className = "lable-div_not-start";
      } else if (endDate <= nowDate) {
        className = "lable-div_end";
      } else if (doneNum > 0) {
        className = "lable-div_finish";
      }

      return "lable-div " + className;
    },
    // 考试次数显示颜色
    numClass(num) {
      return num > 0 ? "commited" : "need-checking";
    },
    canNotExam() {
      this.$Toast.fail("不在考试有效期内或考试次数已用完");
    },
    // 获取考试起止日期
    getDate(dateStr) {
      let date = new Date(dateStr);
      let month = date.getMonth() + 1;
      let day = date.getDate();
      return month + "月" + day + "日";
    },
    getIsInvalid(i) {
      let startDate = new Date(i.examStartTime);
      let endDate = new Date(i.examEndTime);
      let nowDate = new Date();
      let resNum = i.examResNum;
      if (startDate < nowDate && endDate > nowDate && resNum > 0) return true;
      return false;
    },
    toExam(examId, resNum) {
      if (examId) {
        this.$router.push({ path: "/exam/test", query: { examId, resNum } });
      }
    }
  }
};
</script>

<style scoped>
/* 考试列表部分 */
.list {
  padding-bottom: 4rem;
}
/* 考试列表每一项样式 */
.list-item {
  border: #cecece solid 0.0625rem;
  padding: 0.5rem;
  margin-top: 1rem;

  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  /*    border-radius: 5px;
    overflow: hidden; */
}
.list-item:hover {
  background-color: #f4f4f4;
}
/* 考试列表每一项的标题 */
.list-title {
  position: relative;
}
.list-title span {
  font-size: 0.875rem;
}
/* 每一项的内容 */
.list-content {
  margin-top: 1.5rem;
  /* text-align: center; */
}

.list-content .subhead {
  font-size: 0.75rem;
  line-height: 0.75rem;
}
.list-content .subhead > * {
  vertical-align: middle;
}
.list-content .subhead span {
  padding-left: 0.4rem;
}
.list-content .content {
  padding-top: 0.3rem;
  text-align: center;
}
.list-content .content span {
  font-size: 0.625rem;
  color: #9e9e9e;
}

.need-checking {
  color: #db0303 !important;
}
.commited {
  color: #1296db !important;
}

/* 每一项标题的标签 */
.lable-div {
  width: 3.0625rem;
  position: absolute;
  right: -0.3rem;
  top: 0;
  text-align: center;
  color: white;
  font-size: 0.625rem;
  line-height: 1rem;
}
.lable-div::after {
  padding-left: 0.5rem;
  width: 2.5625rem;
  height: 1rem;

  position: absolute;
  position: absolute;
  top: 0;
  left: 0;
}
.lable-div::before {
  /* z-index: -1; */
  content: "";
  position: absolute;
  top: 0;
  left: 0;

  width: 0;
  height: 0;
  border-width: 0.5rem 2.5625rem 0.5rem 0.5rem;
  /* padding-right: 2.0625rem; */
  border-style: solid;
}

/* 各不同标签样式 */
.lable-div_end::after {
  content: "已结束";
}
.lable-div_end::before {
  border-color: #db0303 #db0303 #db0303 transparent;
}
.lable-div_not-start::after {
  content: "未开始";
}
.lable-div_not-start::before {
  border-color: gray gray gray transparent;
}
.lable-div_need-checking::after {
  content: "待考";
}
.lable-div_need-checking::before {
  border-color: #1d9edc #1d9edc #1d9edc transparent;
}
.lable-div_finish::after {
  content: "已交卷";
}
.lable-div_finish::before {
  border-color: #13cc6f #13cc6f #13cc6f transparent;
}
</style>
