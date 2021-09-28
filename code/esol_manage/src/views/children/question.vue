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
            placeholder="请输入标题"
          >
          </el-input>
        </el-col>
        <el-col :span="3">
          <el-select v-model="param.kind" size="medium" placeholder="全部">
            <el-option label="全部" :value="null"> </el-option>
            <el-option label="单选题" :value="1"> </el-option>
            <el-option label="多选题" :value="2"> </el-option>
            <el-option label="判断题" :value="3"> </el-option>
          </el-select>
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
        <el-table-column prop="title" label="题干" width="180">
        </el-table-column>
        <el-table-column
          prop="questionKind"
          label="题型"
          :formatter="handleKind"
          width="80"
        >
        </el-table-column>
        <el-table-column prop="refAnswer" label="参考答案" width="80">
        </el-table-column>
        <el-table-column prop="analysis" label="解析" width="180">
        </el-table-column>
        <el-table-column prop="itemA" label="选项A" width="180">
        </el-table-column>
        <el-table-column prop="itemB" label="选项B" width="180">
        </el-table-column>
        <el-table-column prop="itemC" label="选项C" width="180">
        </el-table-column>
        <el-table-column prop="itemD" label="选项D" width="180">
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

      <el-dialog title="题目信息" :visible.sync="dialogFormVisible">
        <el-form :model="datainfo" style="text-align: left;">
          <el-form-item label="题干" label-width="120px">
            <el-input
              v-model="datainfo.title"
              placeholder="请输入题干"
            ></el-input>
          </el-form-item>
          <el-form-item label="题型" label-width="120px">
            <el-select
              v-model="datainfo.questionKind"
              size="medium"
              placeholder="请选择"
            >
              <el-option label="单选题" :value="1"> </el-option>
              <el-option label="多选题" :value="2"> </el-option>
              <el-option label="判断题" :value="3"> </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="参考答案" label-width="120px">
            <!--            <el-input-->
            <!--              v-model="datainfo.refAnswer"-->
            <!--              placeholder="请输入"-->
            <!--            ></el-input>-->
            <el-radio-group
              v-if="datainfo.questionKind === 1"
              v-model="datainfo.refAnswer"
            >
              <el-radio label="A">A</el-radio>
              <el-radio label="B">B</el-radio>
              <el-radio label="C">C</el-radio>
              <el-radio label="D">D</el-radio>
            </el-radio-group>
            <el-checkbox-group
              v-else-if="datainfo.questionKind === 2"
              v-model="datainfoRefAnswerArr"
            >
              <el-checkbox label="A">A</el-checkbox>
              <el-checkbox label="B">B</el-checkbox>
              <el-checkbox label="C">C</el-checkbox>
              <el-checkbox label="D">D</el-checkbox>
            </el-checkbox-group>
            <el-switch
              v-else
              v-model="datainfo.refAnswer"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-value="T"
              active-text="正确"
              inactive-value="F"
              inactive-text="错误"
            />
          </el-form-item>
          <el-form-item label="解析" label-width="120px">
            <el-input
              v-model="datainfo.analysis"
              type="textarea"
              placeholder="请输入"
            ></el-input>
          </el-form-item>
          <template v-if="datainfo.questionKind !== 3">
            <el-form-item label="选项A" label-width="120px">
              <el-input
                v-model="datainfo.itemA"
                placeholder="请输入"
              ></el-input>
            </el-form-item>
            <el-form-item label="选项B" label-width="120px">
              <el-input
                v-model="datainfo.itemB"
                placeholder="请输入"
              ></el-input>
            </el-form-item>
            <el-form-item label="选项C" label-width="120px">
              <el-input
                v-model="datainfo.itemC"
                placeholder="请输入"
              ></el-input>
            </el-form-item>
            <el-form-item label="选项D" label-width="120px">
              <el-input
                v-model="datainfo.itemD"
                placeholder="请输入"
              ></el-input>
            </el-form-item>
          </template>
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
  name: "question",
  components: {},
  data() {
    return {
      listData: [],
      multipleSelection: [],
      questionType: ["未知", "单选题", "多选题", "判断题"],
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
  computed: {
    datainfoRefAnswerArr: {
      get() {
        let A = "A",
          B = "B",
          C = "C",
          D = "D";
        let reg = /\[([ABCD], ?){0,3}[ABCD]\]/g;
        if (reg.test(this.datainfo.refAnswer)) {
          return eval(this.datainfo.refAnswer);
        } else {
          return [];
        }
      },
      set(v) {
        this.datainfo.refAnswer = JSON.stringify(v).replaceAll('"', "");
        console.log(v, this.datainfo.refAnswer);
      }
    }
  },
  methods: {
    handleKind(row, column, cellValue, index) {
      if (cellValue < 0 || cellValue > 3) {
        return this.questionType[0];
      }
      return this.questionType[cellValue];
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.search();
    },
    handleSelectionChange(val) {
      let arr = [];
      val.forEach(item => {
        arr.push(item.questionLibId);
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
          that.delete_request([row.questionLibId]);
        })
        .catch(() => {});
    },
    editData(row) {
      this.datainfo = JSON.parse(JSON.stringify(row));
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
      this.datainfo = {
        questionKind: 1
      };
      this.dialogFormVisible = true;
    },
    saveData() {
      let that = this;
      console.log(this.datainfo);
      this.$post(this, "/question/save", this.datainfo, data => {
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
        "/question/list",
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
      this.$post(this, "/question/delete", ids, data => {
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
