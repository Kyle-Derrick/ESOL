<template>
  <el-popover placement="right" :width="300" trigger="click">
    <template #reference>
      <el-button v-text="selected_dept_name"></el-button>
    </template>
    <el-scrollbar
      style="height: 200px"
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
  </el-popover>
</template>

<script>
export default {
  name: "deptSelecter",
  model: {
    prop: "value"
  },
  props: {
    value: {
      type: Number,
      default: 0
    },
    selected_dept: {
      type: String,
      default: "全部"
    }
  },
  data() {
    return {
      treeProps: {
        label: "name",
        children: "zones",
        isLeaf: "leaf"
      },
      selected_dept_name: ""
    };
  },
  mounted() {
    this.selected_dept_name = this.selected_dept;
  },
  watch: {
    selected_dept(newV) {
      this.selected_dept_name = newV;
    }
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
      this.selected_dept_name = data.name;
      this.$emit("input", data.deptId); //触发 input 事件，并传入新值
    }
  }
};
</script>

<style scoped></style>
