<template>
  <div>
    <el-dialog width="500px" title="生成数据库" :close-on-click-modal="false" :visible.sync="visible">
      <div v-loading="load">


        <el-upload
            ref="upload"
            :action="tmpAction"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :on-success="handleSuccess"
            :on-error="handleError"
            :file-list="fileList"
            :accept="accept"
            :headers="config"
            :auto-upload="false">

          <div slot="trigger">

            <el-input ref="inputBox" :disabled="load" v-model="tableComment"
                      style="width:200px;margin-right: 10px;font-size: 12px"></el-input>

            <el-button size="small" type="primary">选取文件</el-button>

          </div>

          <div slot="tip" class="el-upload__tip">
            创建数据库表请上传只有表头的Excel，同时不允许空行和右侧填充。
          </div>
        </el-upload>

        <div v-if="uploadBox">
          <p style="font-weight: bold">上传结果回显</p>
          <el-input
              type="textarea"
              :autosize="{ minRows:4, maxRows: 6}"
              placeholder=""
              v-model="textarea2">
          </el-input>
        </div>

      </div>

      <span slot="footer" class="dialog-footer">

                <el-button :disabled="load" size="small" type="success" @click="submitUpload">上传到服务器</el-button>
                <el-button :disabled="load" @click="closeWindow">关闭</el-button>
            </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "gen-table-box",
  props: {
    action: {
      type: String,
      required: true
    },
    accept: {
      type: String,
      required: false,
      default: '.xlsx, .xls'
    }
  },
  data() {
    return {
      visible: false,
      tableComment: '最新2022年统计数据',
      tmpAction: '',
      fileList: [],
      load: false,
      textarea2: '',
      uploadBox: false,
      config: {}
    }
  },
  methods: {
    init() {
      this.visible = true
      this.$nextTick(() => {
        if (this._inputClickEventFlag) return
        this.$refs.inputBox.getInput().addEventListener("click", (e) => {
          e.stopPropagation()
        })
      })
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },
    handleSuccess(response, file, fileList) {
      this.uploadBox = true
      this.load = false
      this.textarea2 = response.msg
    },
    handleError(err, file, fileList) {
      this.uploadBox = true
      this.load = false
      this.textarea2 = err
    },
    submitUpload() {
      this.tmpAction = `${this.action}?tableComment=` + this.tableComment
      this.config = this.$http.getToken()
      // load
      this.load = true
      this.$nextTick(() => {
        this.$refs.upload.submit();
      })
    },
    closeWindow() {
      this.visible = false
      this.$emit('close-event')
    }
  }
}
</script>

<style scoped>
>>> .el-checkbox__label {
  font-size: 12px !important;
}
</style>
