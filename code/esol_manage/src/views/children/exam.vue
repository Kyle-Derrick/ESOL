<!-- 主页面 -->
<template>
  <el-container style="padding-top: 1rem; height: 100%; box-sizing: border-box">
    <el-header height="80px">
      <!-- 搜索栏 -->
      <el-row style="text-align: left;" :gutter="20">
        <el-col :span="6">
          <el-input
            v-model="param.title"
            size="medium"
            prefix-icon="el-icon-user"
            placeholder="请输入用户名"
          >
          </el-input>
        </el-col>
        <el-col :span="2">
          <el-button size="medium" type="success" @click="search">
            <i class="el-icon-search"></i>
          </el-button>
        </el-col>
      </el-row>
      <!-- 操作栏 -->
      <div style="text-align: left;padding-top: 1rem;">
        <el-button size="mini" type="success" @click="add()">添加</el-button>
        <el-button size="mini" type="danger" @click="delete_all()"
          >删除</el-button
        >
      </div>
    </el-header>
    <el-main>
      <!-- 表格 -->
      <el-table
        :data="listData"
        height="100%"
        @selection-change="handleSelectionChange"
        style="width: 100%;"
      >
        <el-table-column type="selection" width="55"> </el-table-column>
        <el-table-column prop="title" label="标题" width="180">
        </el-table-column>
        <el-table-column prop="startDate" label="开始时间" width="180">
          <template slot-scope="scope">
            <div class="block">
              <el-date-picker
                v-model="scope.row.startDate"
                size="mini"
                type="date"
                style="width: 150px;"
                readonly
                placeholder="空"
              >
              </el-date-picker>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="endDate" label="截止时间" width="180">
          <template slot-scope="scope">
            <el-date-picker
              v-model="scope.row.endDate"
              size="mini"
              type="date"
              style="width: 150px;"
              readonly
              placeholder="空"
            >
            </el-date-picker>
          </template>
        </el-table-column>
        <el-table-column prop="duration" label="考试时长" width="180">
        </el-table-column>
        <el-table-column prop="maxNum" label="考试次数" width="180">
        </el-table-column>
        <el-table-column
          prop="sorceRule"
          label="分数结算规则"
          :formatter="handleSorceType"
          width="180"
        >
        </el-table-column>
        <el-table-column prop="totalSorce" label="总分" width="180">
        </el-table-column>
        <el-table-column
          prop="questionRule"
          label="试卷生成规则"
          :formatter="handleExamType"
          width="180"
        >
        </el-table-column>
        <el-table-column prop="radioNum" label="单选题数" width="180">
        </el-table-column>
        <el-table-column prop="mulitpleNum" label="多选题数" width="180">
        </el-table-column>
        <el-table-column prop="judgeNum" label="判断题数" width="180">
        </el-table-column>
        <el-table-column prop="totalNum" label="总题数" width="180">
        </el-table-column>
        <el-table-column label="操作" fixed="right" width="180">
          <template slot-scope="scope">
            <el-button size="mini" @click="editData(scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="deleteData(scope.row)"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </el-main>
    <el-footer height="64px">
      <div class="page-block">
        <el-pagination
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-size="pageSize"
          layout="total, prev, pager, next, jumper"
          :total="totalPage"
        >
        </el-pagination>
      </div>

      <!-- 添加或修改对话框 -->
      <!-- <el-button type="text" @click="dialogFormVisible = true">打开嵌套表单的 Dialog</el-button> -->

      <el-dialog title="考试信息" :visible.sync="dialogFormVisible">
        <el-form :model="datainfo" style="text-align: left;">
          <el-form-item label="标题" label-width="120px">
            <el-input v-model="datainfo.title" placeholder="请输入"></el-input>
          </el-form-item>
          <el-form-item label="开始时间" label-width="120px">
            <el-date-picker
              v-model="datainfo.startDate"
              value-format="yyyy-MM-dd"
              type="date"
              placeholder="选择日期"
            >
            </el-date-picker>
            <!-- <el-input v-model="datainfo.startDate" placeholder="请输入"></el-input> -->
          </el-form-item>
          <el-form-item label="截止时间" label-width="120px">
            <el-date-picker
              v-model="datainfo.endDate"
              value-format="yyyy-MM-dd"
              type="date"
              placeholder="选择日期"
            >
            </el-date-picker>
            <!-- <el-input v-model="datainfo.endDate" placeholder="请输入"></el-input> -->
          </el-form-item>
          <el-form-item label="考试时长" label-width="120px">
            <el-input
              v-model="datainfo.duration"
              type="number"
              min="0"
              step="1"
              placeholder="请输入"
            ></el-input>
          </el-form-item>
          <el-form-item label="考试次数" label-width="120px">
            <el-input
              v-model="datainfo.maxNum"
              type="number"
              min="0"
              step="1"
              placeholder="请输入"
            ></el-input>
          </el-form-item>
          <el-form-item label="分数结算规则" label-width="120px">
            <el-select
              v-model="datainfo.sorceRule"
              size="medium"
              placeholder="请选择"
            >
              <el-option label="最高分" :value="1"> </el-option>
              <el-option label="平均分" :value="2"> </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="总分" label-width="120px">
            <el-input
              v-model="datainfo.totalSorce"
              type="number"
              min="0"
              step="1"
              placeholder="请输入"
            ></el-input>
          </el-form-item>
          <el-form-item label="试卷生成规则" label-width="120px">
            <el-select
              v-model="datainfo.questionRule"
              size="medium"
              placeholder="请选择"
            >
              <el-option label="预先生成" :value="1"> </el-option>
              <el-option label="随机生成" :value="2"> </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="单选题数" label-width="120px">
            <el-input
              v-model="datainfo.radioNum"
              type="number"
              min="0"
              step="1"
              placeholder="请输入"
            ></el-input>
          </el-form-item>
          <el-form-item label="多选题数" label-width="120px">
            <el-input
              v-model="datainfo.mulitpleNum"
              type="number"
              min="0"
              step="1"
              placeholder="请输入"
            ></el-input>
          </el-form-item>
          <el-form-item label="判断题数" label-width="120px">
            <el-input
              v-model="datainfo.judgeNum"
              type="number"
              min="0"
              step="1"
              placeholder="请输入"
            ></el-input>
          </el-form-item>
          <!-- <el-form-item label="总题数" label-width="120px">
				  <el-input v-model="totalNum" type="number" readonly placeholder="请输入"></el-input>
				</el-form-item> -->
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="saveData">确 定</el-button>
        </div>
      </el-dialog>
    </el-footer>
  </el-container>
</template>

<script>
export default {
  name: "exam",
  components: {},
  data() {
    return {
      listData: [],
      multipleSelection: [],
      sorceType: ["未知", "最高分", "平均分"],
      examType: ["未知", "预先生成", "随机生成"],
      currentPage: 1,
      pageSize: 20,
      totalPage: 0,
      param: {
        title: ""
      },
      dialogFormVisible: false,
      datainfo: {}
    };
  },
  mounted() {
    this.search();
  },
  methods: {
    handleExamType(row, column, cellValue, index) {
      if (cellValue < 0 || cellValue > 2) {
        return this.examType[0];
      }
      return this.examType[cellValue];
    },
    handleSorceType(row, column, cellValue, index) {
      if (cellValue < 0 || cellValue > 2) {
        return this.sorceType[0];
      }
      return this.sorceType[cellValue];
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.search();
    },
    handleSelectionChange(val) {
      let arr = [];
      val.forEach(item => {
        arr.push(item.testPaperId);
      });
      this.multipleSelection = arr;
    },
    deleteData(row) {
      let that = this;
      this.$confirm("确定删除吗?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          that.delete_request([row.testPaperId]);
        })
        .catch(() => {});
    },
    editData(row) {
      this.datainfo = row;
      console.log(row);
      this.dialogFormVisible = true;
    },
    delete_all() {
      let that = this;
      this.$confirm("确定删除吗?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          that.delete_request(this.multipleSelection);
        })
        .catch(() => {});
    },
    add() {
      this.datainfo = {};
      this.dialogFormVisible = true;
    },
    saveData() {
      let that = this;
      console.log(this.datainfo);
      this.$post(this, "/exam/save", this.datainfo, data => {
        that.$message({
          showClose: true,
          message: data.msg,
          type: "success"
        });
        that.search();
        that.dialogFormVisible = false;
        that.datainfo = {};
      });
    },
    search() {
      let that = this;
      this.$post(
        this,
        "/exam/list",
        this.$stringify({
          title: this.param.title,
          kind: this.param.kind,
          pageIndex: this.currentPage,
          pageSize: this.pageSize
        }),
        data => {
          that.currentPage = data.data.pageIndex + 1;
          that.pageSize = data.data.pageSize;
          that.totalPage = data.data.total;
          that.listData = data.data.list;
        }
      );
    },
    delete_request(ids) {
      let that = this;
      this.$post(this, "/exam/delete", ids, data => {
        that.$message({
          showClose: true,
          message: data.msg,
          type: "success"
        });
        that.search();
      });
    }
  }
};
</script>

<style scoped>
.page-block {
  padding-top: 2rem;
}
</style>
