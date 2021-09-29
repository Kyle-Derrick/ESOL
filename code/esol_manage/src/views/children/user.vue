<!-- 主页面 -->
<template>
  <el-container style="padding-top: 1rem; height: 100%; box-sizing: border-box">
    <el-header height="80px">
      <!-- 搜索栏 -->
      <el-row style="text-align: left;" :gutter="20">
        <el-col :span="6">
          <el-input
            v-model="param.username"
            size="medium"
            prefix-icon="el-icon-user"
            placeholder="请输入用户名"
          >
          </el-input>
        </el-col>
        <el-col :span="3">
          <el-select v-model="param.kind" size="medium" placeholder="全部">
            <el-option label="全部" :value="null"> </el-option>
            <el-option label="管理员" :value="1"> </el-option>
            <el-option label="学生" :value="2"> </el-option>
            <el-option label="老师" :value="3"> </el-option>
          </el-select>
        </el-col>
        <el-col :span="2">
          <el-button size="medium" type="success" @click="search()">
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
        <el-table-column prop="userId" label="ID" width="0" v-if="false">
        </el-table-column>
        <el-table-column type="selection" width="55"> </el-table-column>
        <el-table-column prop="userName" label="用户名" width="180">
        </el-table-column>
        <el-table-column prop="name" label="姓名" width="180">
        </el-table-column>
        <el-table-column
          prop="userKind"
          :formatter="userKindHandle"
          label="用户类型"
        >
        </el-table-column>
        <el-table-column prop="dept" :formatter="deptHandle" label="部门">
        </el-table-column>
        <el-table-column label="操作" fixed="right" width="180">
          <template slot-scope="scope">
            <el-button size="mini" @click="editUser(scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="deleteUser(scope.row)"
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

      <el-dialog title="用户信息" :visible.sync="dialogFormVisible">
        <el-form :model="userinfo" style="text-align: left;">
          <el-form-item label="用户名" label-width="120px">
            <el-input
              v-model="userinfo.userName"
              placeholder="请输入用户名"
            ></el-input>
          </el-form-item>
          <el-form-item label="姓名" label-width="120px">
            <el-input
              v-model="userinfo.name"
              placeholder="请输入姓名"
            ></el-input>
          </el-form-item>
          <el-form-item label="密码" label-width="120px">
            <el-input
              v-model="userinfo.passWord"
              show-password
              placeholder="请输入密码"
            ></el-input>
          </el-form-item>
          <el-form-item label="用户类型" label-width="120px">
            <el-select v-model="userinfo.userKind" placeholder="请选择">
              <el-option label="管理员" :value="1"> </el-option>
              <el-option label="学生" :value="2"> </el-option>
              <el-option label="老师" :value="3"> </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="用户部门" label-width="120px">
            <!--            <el-input v-model="userinfo.deptId"></el-input>-->
            <dept-selecter
              v-model="userinfo.deptId"
              :selected_dept="userinfo.selected_dept"
            ></dept-selecter>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="saveUser">确 定</el-button>
        </div>
      </el-dialog>
    </el-footer>
  </el-container>
</template>

<script>
import deptSelecter from "../../components/deptSelecter";
export default {
  name: "user",
  components: { deptSelecter },
  data() {
    return {
      listData: [],
      userType: ["未知", "管理员", "学生", "老师"],
      multipleSelection: [],
      currentPage: 1,
      pageSize: 20,
      totalPage: 0,
      param: {
        username: ""
      },
      dialogFormVisible: false,
      userinfo: {}
    };
  },
  mounted() {
    this.search();
  },
  methods: {
    deptHandle(row, column, cellValue, index) {
      return cellValue.name ? cellValue.name : "";
    },
    userKindHandle(row, column, cellValue, index) {
      if (cellValue < 0 || cellValue > 3) {
        return this.userType[0];
      }
      return this.userType[cellValue];
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.search();
    },
    handleSelectionChange(val) {
      let arr = [];
      val.forEach(item => {
        arr.push(item.userId);
      });
      this.multipleSelection = arr;
    },
    deleteUser(row) {
      let that = this;
      this.$confirm("确定删除吗?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          that.delete_request([row.userId]);
        })
        .catch(() => {});
    },
    editUser(row) {
      this.userinfo = JSON.parse(JSON.stringify(row));
      this.userinfo.deptId = row.dept.deptId;
      this.userinfo.selected_dept = row.dept.name;
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
      this.userinfo = {};
      this.dialogFormVisible = true;
    },
    saveUser() {
      let that = this;
      console.log(this.userinfo);
      this.userinfo.dept = { deptId: this.userinfo.deptId };
      this.$post(this, "/user/save", this.userinfo, data => {
        that.$message({
          showClose: true,
          message: data.msg,
          type: "success"
        });
        that.search();
        that.dialogFormVisible = false;
        that.userinfo = {};
      });
    },
    search() {
      let that = this;
      this.$post(
        this,
        "/user/list",
        this.$stringify({
          username: this.param.username,
          kind: this.param.kind,
          pageIndex: this.currentPage,
          pageSize: this.pageSize
        }),
        data => {
          console.log(data);
          that.currentPage = data.data.pageIndex;
          that.pageSize = data.data.pageSize;
          that.totalPage = data.data.total;
          that.listData = data.data.list;
        }
      );
    },
    delete_request(ids) {
      let that = this;
      this.$post(this, "/user/delete", ids, data => {
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
