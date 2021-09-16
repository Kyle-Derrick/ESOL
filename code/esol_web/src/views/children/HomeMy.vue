<!-- 个人中心界面 -->
<template>
  <div class="home-my">
    <div class="info-div">
      <div class="card">
        <div class="info">
          <div class="info_left">
            <div class="info_t">
              <!-- 用户姓名 -->
              <span class="first">{{ user.name }}</span>
            </div>
            <div class="info_c">
              <van-row>
                <van-col span="10">
                  <span
                    >累计答题<span class="highlight">{{ user.examNum }}</span
                    >次</span
                  >
                </van-col>
                <van-col span="14">
                  <span
                    >答题正确率<span class="highlight"
                      >{{ user.answerAccuracy }}%</span
                    ></span
                  >
                </van-col>
              </van-row>
            </div>
          </div>
          <!-- 排名 -->
          <div class="info_right">
            <div class="rank">{{ user.rank }}</div>
            <span>当前排名</span>
          </div>
        </div>
        <!-- 所在部门名 -->
        <div class="dept">
          <span>{{ user.deptName }}</span>
        </div>
      </div>
    </div>

    <van-cell-group class="history-exam-cell-group">
      <van-cell is-link to="/exam/history" class="history-exam">
        <!-- 历史试卷 -->
        <div slot="title">
          <van-icon
            :name="require('@/assets/icon/history_exam.png')"
          ></van-icon>
          <span>历史试卷</span>
        </div>
      </van-cell>
      <van-cell is-link @click="logout" class="history-exam">
        <!-- 注销 -->
        <div slot="title">
          <van-icon :name="require('@/assets/icon/logout.png')"></van-icon>
          <span>注销登录</span>
        </div>
      </van-cell>
    </van-cell-group>
  </div>
</template>

<script>
export default {
  name: "my",
  data() {
    return {};
  },
  // 获取用户信息,如果本地有,将不会请求服务器
  mounted() {
    if (!this.user.name) {
      let that = this;
      this.$get(this, "/user", data => {
        // that.$store.state.user = data.data;
        that.$store.commit("updateUser", data.data);
      });
    }
  },
  computed: {
    // 从vuex中获取用户对象
    user() {
      return this.$store.state.user;
    }
  },
  methods: {
    // 注销登录
    logout() {
      let that = this;
      this.$post(this, "/logout", null, () => {
        that.$router.replace("/");
      });
    }
  }
};
</script>

<style scoped>
.home-my {
  text-align: left;
  color: #444444;
  font-family: "黑体";
}
.info-div {
  background-color: #fff;
  padding: 1rem 1.5rem 2.5rem 1.5rem;
}
/* 用户信息卡片显示 */
.card {
  font-size: 0.9375rem;
  border-radius: 5px;
  overflow: hidden;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);

  background-image: url(../../assets/img/1-2.png);
  background-attachment: scroll;
  background-repeat: no-repeat;
  background-position: bottom;
  background-size: cover;
}
/* 用户信息 */
.info {
  position: relative;
  padding: 1.5rem;
  border-bottom: 0.001rem solid #bebebe;
}
.info_left {
  padding-right: 3rem;
}
.info_t {
  font-size: 1.125rem;
  font-weight: bold;
}
.info_t .first {
  /* border-right: 0.01rem #a4a4a4 solid; */
  padding-right: 0.6rem;
  margin-right: 0.6rem;
}
.info_c {
  margin-top: 1.5rem;
}
.info_right {
  width: 3rem;
  height: 3.6875rem;
  position: absolute;
  top: 1.5rem;
  right: 1.5rem;
}

.info_right span {
  margin-top: 0.3rem;
  font-family: "黑体";
  font-size: 0.625rem;
  color: #909090;
  display: block;
}
/* 部门 */
.dept {
  padding: 0.2rem 1.5rem;
  padding-bottom: 0.5rem;
}
/* 排名显示块 */
.rank {
  width: 1.9375rem;
  height: 2.2375rem;
  text-align: center;
  padding-top: 0.45rem;
  color: white;
  font-weight: bold;
  margin: auto;

  background-image: url(../../assets/img/rank.png);
  background-attachment: scroll;
  background-repeat: no-repeat;
  background-position: center;
  background-size: contain;
}

/* 单元格组 */
.history-exam-cell-group {
  margin-top: 1.5rem;
}
/* 单元格中选项 */
.history-exam {
  padding-left: 1.5rem;
  font-size: 1.125rem;
  height: 3.5rem;
}
.history-exam * {
  line-height: 2.3125rem;
  vertical-align: text-bottom;
}
.history-exam span {
  padding-left: 1rem;
}
</style>
