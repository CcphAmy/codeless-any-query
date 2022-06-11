<template>

  <div style="height: 100%;width: 100%">

    <div class="main-left">

      <el-input style="margin-bottom: 10px;margin-right: 10px;display: inline-block;width: 150px" placeholder="关键字"
                v-model="filterText">
      </el-input>

      <el-button type="primary" @click="genTableHandle">+</el-button>

      <el-button type="danger" @click="deleteTableHandle(selectedTableName)"
                 :disabled="tableOptions.length <= 0 || selectedTableName === void 0">-
      </el-button>
      <gen-table-box @close-event="getDbTableList" :action="$http.adornUrl('/aq/table/create')"
                     ref="genTableBox"></gen-table-box>
      <!--            selectedTableName-->

      <div>
        <el-tree
            class="filter-tree"
            :data="tableOptions"
            :props="defaultProps"
            default-expand-all
            highlight-current
            :filter-node-method="filterNode"
            @current-change="selectedTree"
            ref="tree">
        </el-tree>
      </div>

    </div>

    <div class="main-right">
      <div v-if="selectedTableName !== void 0" class="mod-log">
        <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">

          <el-form-item>
            <el-select style="width: 120px" placeholder="搜索" v-model="dataForm.fieldName">
              <el-option
                  v-for="item in columnList"
                  :key="item.tableName"
                  :label="item.columnComment"
                  :value="item.columnName">
              </el-option>
            </el-select>
          </el-form-item>

          <el-form-item>
            <el-input v-model="dataForm.searchValue" placeholder="关键字" clearable></el-input>
          </el-form-item>
          <el-form-item>
            <el-button @click="getDataList(true)">查询</el-button>
            <!--          <el-button type="primary" @click="addOrUpdateHandle()">新增</el-button>-->
            <el-button type="danger" @click="deleteHandle()"
                       :disabled="dataListSelections.length <= 0">批量删除
            </el-button>
            <el-button type="primary" @click="importHandle">导入数据</el-button>
            <!--          <el-button type="primary" @click="exportExcel">导出数据</el-button>-->
          </el-form-item>
        </el-form>
        <el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle"
                  style="width: 100%">

          <el-table-column type="selection" header-align="center" align="center" width="50">
          </el-table-column>

          <el-table-column v-for="(item, index) in columnList" :key="index" :prop="item.columnName"
                           header-align="center"
                           align="center" :label="item.columnComment">
          </el-table-column>

          <el-table-column fixed="right" header-align="center" align="center" width="100" label="操作">
            <template slot-scope="scope">
              <!--            <el-button type="text" size="small"-->
              <!--                       @click="addOrUpdateHandle(scope.row.id)">修改-->
              <!--            </el-button>-->
              <el-button type="text" size="small"
                         @click="deleteHandle(scope.row.id)">删除
              </el-button>
            </template>
          </el-table-column>


        </el-table>
        <el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
                       :page-sizes="[10, 20, 50, 100, 200, 500]" :page-size="pageSize" :total="totalCount"
                       layout="total, sizes, prev, pager, next, jumper">
        </el-pagination>

        <!-- 弹窗, 新增 / 修改 -->
        <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>

        <import-box v-if="importVisible" :action="$http.adornUrl('/aq/table/import')" ref="importBox"
                    @refreshDataList="getDataList"></import-box>
      </div>
    </div>
  </div>

</template>

<script>
import AddOrUpdate from './written-add-or-update'
import ImportBox from './import-box'
import GenTableBox from './gen-table-box'
import {downloadByFrame} from "@/utils/downloadByFrame";

export default {
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  data() {
    return {
      filterText: '',
      defaultProps: {
        children: 'children',
        label: 'tableComment'
      },
      //
      // :key="item.tableName"
      // :label="item.tableComment"
      // :value="item.tableName">

      // 选择框
      tableOptions: [],
      selectedTableName: void 0,
      // 字段
      columnList: [
        {
          columnComment: "招生类型",
          columnName: "subject",
          columnType: "varchar(255)"
        }
      ],
      genTableVisible: false,
      // 数据
      dataForm: {
        fieldName: '',
        type: 'like',
        searchValue: ''
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalCount: 0,
      dataListLoading: false,
      dataListSelections: [],
      addOrUpdateVisible: false,
      importVisible: true
    }
  },
  activated() {
    this.getDbTableList()
    // this.getDataList()
  },
  components: {
    AddOrUpdate,
    ImportBox,
    GenTableBox
  },
  watch: {
    selectedTableName(newValue) {

    }
  },
  methods: {
    filterNode(value, data) {
      if (!value) return true;
      return data.tableComment.indexOf(value) !== -1;
    },
    selectedTree(data) {
      this.selectedTableName = data.tableName
      this.getColumnList()
    },
    // 获取数据库表名列表
    getDbTableList() {
      this.$http({
        url: this.$http.adornUrl('/aq/table/list'),
        method: 'get',
      }).then(({data}) => {
        this.tableOptions = ((data && data.code === 200) ? data.list : [])
      })
    },
    // 获取数据集字段名列表
    getColumnList() {
      this.$http({
        url: this.$http.adornUrl('/aq/column/list'),
        params: this.$http.adornParams({'tableName': this.selectedTableName}),
        method: 'get',
      }).then(({data}) => {
        this.columnList = ((data && data.code === 200) ? data.list : [])
        this.getDataList(true)
      })
    },
    // 生成数据库表导入
    genTableHandle() {
      this.genTableVisible = true
      this.$nextTick(() => {
        this.$refs.genTableBox.init()
      })
    },
    // 获取数据列表
    getDataList(isRestPage) {
      this.pageIndex = isRestPage ? 1 : this.pageIndex
      this.$http({
        url: this.$http.adornUrl('/aq/data/list'),
        method: 'post',
        params: this.$http.adornParams(Object.assign({
          'page': this.pageIndex,
          'limit': this.pageSize,
        })),
        data: this.$http.adornData(Object.assign({
          'tableName': this.selectedTableName,
          'search': [this.dataForm]
        }))
      }).then(({data}) => {
        if (data && data.code === 200) {
          this.dataList = data.page.list
          this.totalCount = data.page.totalCount
        } else {
          this.dataList = []
          this.totalCount = 0
        }
        this.dataListLoading = false
      })
    },
    // 删除
    deleteTableHandle(tableName) {
      this.$confirm(`确定对[${tableName}]进行[删除]操作?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: this.$http.adornUrl('/aq/table/delete'),
          method: 'post',
          params: this.$http.adornParams({
            "tableName": tableName
          })
        }).then(({data}) => {
          if (data && data.code === 200) {
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1500,
              onClose: () => {
                this.getDbTableList()
                this.selectedTableName = void 0
              }
            })
          } else {
            this.$message.error(data.msg)
          }
        })
      }).catch(() => {
      })
    },

    // 每页数
    sizeChangeHandle(val) {
      this.pageSize = val
      this.pageIndex = 1
      this.getDataList()
    },
    // 当前页
    currentChangeHandle(val) {
      this.pageIndex = val
      this.getDataList()
    },
    // 多选
    selectionChangeHandle(val) {
      this.dataListSelections = val
    },
    // 新增 / 修改
    addOrUpdateHandle(id) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id)
      })
    },
    // 导入
    importHandle() {
      this.importVisible = true
      this.$nextTick(() => {
        this.$refs.importBox.init(this.selectedTableName)
      })
    },
    // 导出
    exportExcel() {
      this.$confirm('导出表记录为Excel, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/program/written/export'),
          method: 'post',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'key': this.dataForm.key,
            'sidx': 'id',
            'order': 'desc'
          })
        }).then(({data}) => {
          if (data && data.code === 200) {
            const fileName = data.msg
            if (fileName && fileName.indexOf(".xlsx")) {
              downloadByFrame(this.$http.adornUrl(`/common/download`), "get", [{
                key: 'fileName',
                value: fileName
              }, {key: 'delete', value: true}])
              this.$message({
                type: 'success',
                message: `导出成功!${fileName}`
              });
            }
          }

        }).finally(() => {
          this.dataListLoading = false
        })
      })
    },
    // 删除
    deleteHandle(id) {
      var ids = id ? [id] : this.dataListSelections.map(item => item.id)
      this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: this.$http.adornUrl('/aq/table/removeByIds'),
          method: 'post',
          params: this.$http.adornParams({'tableName': this.selectedTableName}),
          data: this.$http.adornData(ids, false)
        }).then(({data}) => {
          if (data && data.code === 200) {
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1500,
              onClose: () => this.getDataList()
            })
          } else {
            this.$message.error(data.msg)
          }
        })
      }).catch(() => {
      })
    }
  }
}
</script>

<style scoped>
.main-left {
  float: left;
  width: 25%;
  height: 100%;
  border-right: 1px solid #EBEEF5;
  display: initial;
  padding-right: 15px
}

.main-right {
  float: right;
  width: 74%;
  margin-bottom: 10px;
}
</style>
