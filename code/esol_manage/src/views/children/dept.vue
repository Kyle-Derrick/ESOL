<!-- 主页面 -->
<template>
  <el-container
    style="padding-top: 1rem; height: 100%; box-sizing: border-box;"
  >
    <el-aside
      class="scroll_fix"
      style="height: 100%; position: relative"
      width="15%"
    >
      <el-scrollbar
        style="position: relative; height: 100%; border: 1px solid rgb(238, 238, 238); box-sizing: border-box; border-radius: 4px"
        wrap-style="position: absolute; top: 0; bottom: 0; right: 0; left: 0;overflow-x: hidden;"
      >
        <el-tree
          ref="deptTree"
          :props="treeProps"
          :load="loadTreeNode"
          lazy
          @node-click="treeNodeClick"
          highlight-current
        ></el-tree>
      </el-scrollbar>
    </el-aside>
    <el-main>
      <el-container style="height: 100%">
        <el-header height="80px">
          <!-- 搜索栏 -->
          <el-row style="text-align: left;" :gutter="20">
            <el-col :span="6">
              <el-input
                v-model="param.name"
                size="medium"
                placeholder="请输入部门名"
              >
              </el-input>
            </el-col>
            <el-col :span="2">
              <el-button size="medium" type="success" @click="search()">
                <i class="el-icon-search"></i>
              </el-button>
            </el-col>
          </el-row>
          <!-- 操作栏 -->
          <div style="text-align: left;padding-top: 1rem;">
            <el-button size="mini" type="success" @click="add()"
              >添加</el-button
            >
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
            <el-table-column prop="name" label="部门名" width="180">
            </el-table-column>
            <el-table-column prop="leader" label="部门领导" width="180">
            </el-table-column>
            <el-table-column prop="info" label="部门信息" width="180">
            </el-table-column>
            <el-table-column
              prop="parentId"
              :formatter="deptHandle"
              label="父级部门"
            >
            </el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-button size="mini" @click="editData(scope.row)"
                  >编辑</el-button
                >
                <el-button
                  size="mini"
                  type="danger"
                  @click="deleteData(scope.row)"
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
              :current-page="1"
              :page-size="pageSize"
              layout="total, prev, pager, next, jumper"
              :total="1"
            >
            </el-pagination>
          </div>

          <!-- 添加或修改对话框 -->
          <!-- <el-button type="text" @click="dialogFormVisible = true">打开嵌套表单的 Dialog</el-button> -->

          <el-dialog title="部门信息" :visible.sync="dialogFormVisible">
            <el-form :model="datainfo" style="text-align: left;">
              <el-form-item label="部门名称" label-width="120px">
                <el-input
                  v-model="datainfo.name"
                  placeholder="请输入"
                ></el-input>
              </el-form-item>
              <el-form-item label="部门领导" label-width="120px">
                <el-input
                  v-model="datainfo.leader"
                  placeholder="请输入"
                ></el-input>
              </el-form-item>
              <el-form-item label="部门信息" label-width="120px">
                <el-input
                  v-model="datainfo.info"
                  type="textarea"
                  placeholder="请输入"
                ></el-input>
              </el-form-item>
              <!--              <el-form-item label="父级部门" label-width="120px">-->
              <!--                <el-select-->
              <!--                  v-model="datainfo.parentId"-->
              <!--                  filterable-->
              <!--                  placeholder="请选择"-->
              <!--                >-->
              <!--                  <el-option label="根级" :value="0"> </el-option>-->
              <!--                  <el-option-->
              <!--                    v-for="item in listData"-->
              <!--                    :key="item.deptId"-->
              <!--                    :label="item.name"-->
              <!--                    :value="item.deptId"-->
              <!--                  >-->
              <!--                  </el-option>-->
              <!--                </el-select>-->
              <!--              </el-form-item>-->
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogFormVisible = false">取 消</el-button>
              <el-button type="primary" @click="saveData">确 定</el-button>
            </div>
          </el-dialog>
        </el-footer>
      </el-container>
    </el-main>
  </el-container>
</template>

<script>
export default {
  name: "dept",
  components: {},
  data() {
    return {
      treeProps: {
        label: "name",
        children: "zones",
        isLeaf: "leaf"
      },
      listData: [],
      multipleSelection: [],
      currentPage: -1,
      pageSize: 20,
      totalPage: 0,
      param: {
        name: "",
        rootId: 0
      },
      dialogFormVisible: false,
      datainfo: {}
    };
  },
  mounted() {
    this.search();
  },
  methods: {
    loadTreeNode(node, resolve) {
      if (node.level === 0) {
        return resolve([
          {
            name: "全部",
            deptId: 0
          }
        ]);
      }

      this.$post(
        this,
        "/dept/childList/".concat(node.data.deptId),
        {},
        data => {
          resolve(data.data);
        }
      );
    },
    treeNodeClick(data) {
      this.param.rootId = data.deptId;
      this.search();
    },
    flushTreeNode() {
      let node = this.$refs.deptTree.currentNode.node;
      node.loaded = false;
      node.expand();
    },
    deptHandle(row, column, cellValue /*, index*/) {
      let name = "";
      this.listData.forEach(item => {
        if (cellValue === item.deptId) {
          name = item.name;
          return false;
        }
      });
      return name;
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
    },
    handleSelectionChange(val) {
      let arr = [];
      val.forEach(item => {
        arr.push(item.deptId);
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
          that.delete_request([row.deptId]);
        })
        .catch(() => {});
    },
    editData(row) {
      this.datainfo = row;
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
      this.datainfo.parentId = this.param.rootId;
      this.$post(this, "/dept/save", this.datainfo, data => {
        that.$message({
          showClose: true,
          message: data.msg,
          type: "success"
        });
        that.flushTreeNode();
        that.search();
        that.dialogFormVisible = false;
        that.datainfo = {};
      });
    },
    search() {
      let that = this;
      this.param.currentPage = this.currentPage;
      this.param.pageSize = this.pageSize;
      this.$post(this, "/dept/list", this.$stringify(this.param), data => {
        // that.pageSize = data.data.length;
        // that.listData = data.data;
        that.currentPage = data.data.pageIndex;
        that.pageSize = data.data.pageSize;
        that.totalPage = data.data.total;
        that.listData = data.data.list;
      });
    },
    delete_request(ids) {
      let that = this;
      this.$post(this, "/dept/delete", ids, data => {
        that.$message({
          showClose: true,
          message: data.msg,
          type: "success"
        });
        that.flushTreeNode();
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
