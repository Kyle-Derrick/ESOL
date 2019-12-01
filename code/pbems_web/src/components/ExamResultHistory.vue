<!-- 历史考试结果列表组件 -->
<template>
  <div class="list">
    <!-- 弹出框标题 -->
    <h2 class="title">历史答卷</h2>
    <div class="list-item" v-for="(item, i) in listDataTmp" :key="i">
      <div class="list-title">
        <span>{{ title }} ({{ titleNo + i }})</span>
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
    <van-pagination
      v-model="currentPage"
      :page-count="pageCount"
      mode="simple"
    />
  </div>
</template>

<script>
export default {
  name: "examResultHistory",
  props: ["list", "title"],
  data() {
    return {
      // 列表数组
      listData: [],
      // 当前页数
      currentPage: 1
    };
  },
  computed: {
    // 当前列数组
    listDataTmp() {
      if (this.list == null) return [];
      let start = (this.currentPage - 1) * 3;
      return this.list.slice(start, start + 3);
    },
    // 总页数
    pageCount() {
      return this.list == null ? 0 : Math.ceil(this.list.length / 3);
    },
    // 标题编号
    titleNo() {
      return (this.currentPage - 1) * 3 + 1;
    }
  },
  methods: {
    // 跳转到详细结果页面
    toAnswer(resultId) {
      this.$get(this, "/exam/result/" + resultId, data => {
        this.$router.push({ name: "answer", params: data.data });
      });
    }
  }
};
</script>

<style scoped>
/* 列表主div*/
.list {
  /* background-color: #f4f4f4; */
  padding-bottom: 1rem;
}
/* 标题 */
.title {
  font-size: 1.3rem;
}
/* 答卷列表每一项样式 */
.list-item {
  background-color: white;
  margin: 0.5rem;
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
