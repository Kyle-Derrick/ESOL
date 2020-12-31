<!-- 历史考试结果列表组件 -->
<template>
  <div class="list-div">
    <div class="list">
      <!-- 弹出框标题 -->
      <div class="list-item" v-for="(item, i) in listData" :key="i">
        <div class="list-title">
          <span>{{ title }} ({{ i + 1 }})</span>
        </div>
        <!-- 内容 -->
        <div class="list-content">
          <van-row type="flex" justify="space-between">
            <van-col span="6">
              <div class="subhead">
                <span>答题时间</span>
              </div>
              <div class="content">
                <span>{{ item.resultDate }}</span>
              </div>
            </van-col>
            <van-col span="6">
              <div class="subhead">
                <span>答题时长</span>
              </div>
              <div class="content">
                <span>{{ item.duration }}分钟</span>
              </div>
            </van-col>
            <van-col span="6">
              <div class="subhead">
                <span>最终得分</span>
              </div>
              <div class="content">
                <span>{{ item.sorce }}分</span>
              </div>
            </van-col>
          </van-row>
          <van-row type="flex" justify="space-between">
            <van-col span="6">
              <div class="subhead">
                <span>正确率</span>
              </div>
              <div class="content">
                <span
                  >{{
                    parseInt(
                      (item.sucessNum / (item.sucessNum + item.errorNum)) * 100
                    )
                  }}%</span
                >
              </div>
            </van-col>
            <van-col span="6">
              <div class="subhead">
                <span>错误数</span>
              </div>
              <div class="content">
                <span>{{ item.errorNum }}</span>
              </div>
            </van-col>

            <van-col span="6">
              <a
                href="javascript:;"
                class="text-btn"
                @click="toAnswer(item.resultId)"
                ><span>查看详情</span></a
              >
            </van-col>
          </van-row>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "examResultHistory",
  data() {
    return {
      // 列表数组
      listData: [],
      title: ""
    };
  },
  mounted() {
    this.$store.state.nav.title = "历史考试答卷";
    this.listData = this.$route.params.data;
    this.title = this.$route.params.title;
  },
  methods: {
    // 跳转到详细结果页面
    toAnswer(resultId) {
      this.$get(this, "/exam/result/" + resultId, data => {
        this.$router.replace({ name: "answer", params: data.data });
      });
    }
  }
};
</script>

<style scoped>
/* 列表主div*/
.list-div{
  background-color: #f4f4f4;
  min-height: 100vh;
}
.list {
  /* background-color: #f4f4f4; */

  padding: 1rem;
  padding-top: 2.875rem;
  padding-bottom: 2.875rem;
}
/* 答卷列表每一项样式 */
.list-item {
  background-color: white;
  margin-top: 1rem;
  border: #cecece solid 0.0625rem;
  padding: 0.5rem;

  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  /*    border-radius: 5px;
    overflow: hidden; */
}
/* 考试列表每一项的标题 */
.list-title {
  /* text-align: left; */
}
.list-title span {
  font-size: 0.875rem;
}
/* 每一项的内容 */
.list-content {
  margin-top: 0.5rem;
  /* text-align: center; */
  border: ;
}
.list-content .van-row {
  padding-top: 0.5rem;
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
  text-align: center;
}
.list-content .content span {
  font-size: 0.625rem;
  color: #9e9e9e;
}

.text-btn {
  height: 100%;
  display: block;
  /* height: 100%; */
  padding-top: 0.75rem;
  font-size: 0.75rem;
  color: #f99c0a;
}
</style>
