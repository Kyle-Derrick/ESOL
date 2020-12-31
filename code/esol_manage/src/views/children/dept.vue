<!-- 主页面 -->
<template>
	<div style="padding-top: 1rem;">
		<!-- 搜索栏 -->
		<el-row style="text-align: left;" :gutter="20">
			<el-col :span="6">
			  <el-input v-model="param.name" size="medium" placeholder="请输入部门名">
			  </el-input>
			</el-col>
			<el-col :span="2">
			  <el-button
				  size="medium"
				  type="success"
				  @click="search()">
					<i class="el-icon-search"></i>
			  </el-button>
			</el-col>
		</el-row>
		<!-- 操作栏 -->
		<div style="text-align: left;padding-top: 1rem;">
			<el-button
			  size="mini"
			  type="success"
			  @click="add()">添加</el-button>
			<el-button
			  size="mini"
			  type="danger"
			  @click="delete_all()">删除</el-button>
		</div>
		<!-- 表格 -->
		<el-table
		  :data="listData"
		  height="360px"
		  @selection-change="handleSelectionChange"
		  style="width: 100%;">
		  <el-table-column
			type="selection"
			width="55">
		  </el-table-column>
		  <el-table-column
			prop="name"
			label="部门名"
			width="180">
		  </el-table-column>
		  <el-table-column
			prop="leader"
			label="部门领导"
			width="180">
		  </el-table-column>
		  <el-table-column
			prop="info"
			label="部门信息"
			width="180">
		  </el-table-column>
		  <el-table-column
			prop="parentId"
			:formatter="deptHandle"
			label="父级部门">
		  </el-table-column>
		  <el-table-column
			label="操作">
			<template slot-scope="scope">
				<el-button
				  size="mini"
				  @click="editData(scope.row)">编辑</el-button>
				<el-button
				  size="mini"
				  type="danger"
				  @click="deleteData(scope.row)">删除</el-button>
			</template>
		  </el-table-column>
		</el-table>
		<div class="page-block">
			<el-pagination
			  @current-change="handleCurrentChange"
			  :current-page="1"
			  :page-size="pageSize"
			  layout="total, prev, pager, next, jumper"
			  :total="1">
			</el-pagination>
		</div>
		
		<!-- 添加或修改对话框 -->
		<!-- <el-button type="text" @click="dialogFormVisible = true">打开嵌套表单的 Dialog</el-button> -->
		
		<el-dialog title="部门信息" :visible.sync="dialogFormVisible">
		  <el-form :model="datainfo"  style="text-align: left;">
		    <el-form-item label="部门名称" label-width="120px">
		      <el-input v-model="datainfo.name" placeholder="请输入"></el-input>
		    </el-form-item>
		    <el-form-item label="部门领导" label-width="120px">
		      <el-input v-model="datainfo.leader" placeholder="请输入"></el-input>
		    </el-form-item>
		    <el-form-item label="部门信息" label-width="120px">
		      <el-input v-model="datainfo.info" type="textarea" placeholder="请输入"></el-input>
		    </el-form-item>
		    <el-form-item label="父级部门" label-width="120px">
			  <el-select v-model="datainfo.parentId" filterable placeholder="请选择">
				  <el-option
					label="根级"
					:value="0">
				  </el-option>
				  <el-option
				    v-for="item in listData"
					:key="item.deptId"
					:label="item.name"
					:value="item.deptId">
				  </el-option>
			  </el-select>
		    </el-form-item>
		  </el-form>
		  <div slot="footer" class="dialog-footer">
		    <el-button @click="dialogFormVisible = false">取 消</el-button>
		    <el-button type="primary" @click="saveData">确 定</el-button>
		  </div>
		</el-dialog>
	</div>
</template>

<script>
export default {
  name: "dept",
  components: {
  },
  data() {
    return {
      listData: [],
	  multipleSelection: [],
	  currentPage: -1,
	  pageSize: 20,
	  totalPage: 0,
	  param: {
		  name: ''
	  },
	  dialogFormVisible: false,
	  datainfo: {}
    };
  },
  mounted() {
    this.search();
  },
  methods: {
	  deptHandle(row, column, cellValue, index){
		  var name = '';
		  this.listData.forEach(item => {
			  if(cellValue === item.deptId) {
				  name = item.name;
				  return false;
			  }
		  })
		  return name;
	  },
	  handleCurrentChange(val) {
		  console.log(`当前页: ${val}`);
	  },
      handleSelectionChange(val) {
		let arr = [];
		val.forEach(item => {
			arr.push(item.deptId);
		})
        this.multipleSelection = arr;
      },
	  deleteData(row) {
		let that = this;
		this.$confirm('确定删除吗?', '提示', {
			  confirmButtonText: '确定',
			  cancelButtonText: '取消',
			  type: 'warning'
			}).then(() => {
			  that.delete_request([row.deptId]);
			}).catch(()=>{});
	  },
	  editData(row) {
		  this.datainfo = row;
		  this.dialogFormVisible = true;
	  },
	  delete_all(){
		let that = this;
		this.$confirm('确定删除吗?', '提示', {
			  confirmButtonText: '确定',
			  cancelButtonText: '取消',
			  type: 'warning'
			}).then(() => {
				that.delete_request(this.multipleSelection);
			}).catch(()=>{});
	  },
	  add(){
		   this.datainfo = {};
		   this.dialogFormVisible = true;
	  },
	  saveData(){
		let that = this;
		console.log(this.datainfo);
		this.$post(this, "/dept/save", this.datainfo, data => {
			that.$message({
				showClose: true,
				message: data.msg,
				type: 'success'
			});
			that.search();
			that.dialogFormVisible = false;
			that.datainfo = {};
		});
  },
	  search(){
		let that = this;
		this.$post(this, "/dept/list", this.$stringify({
			name: this.param.name,
			pageIndex: this.currentPage
		}), data => {
			that.pageSize= data.data.length;
			that.listData = data.data;
		});
	  },
	  delete_request(ids){
		let that = this;
		this.$post(this, "/dept/delete", ids, data => {
			that.$message({
				showClose: true,
				message: data.msg,
				type: 'success'
		    });
			that.search();
		});
	  },
  }
};
</script>

<style scoped>
	.page-block {
		padding-top: 2rem;
	}
</style>
