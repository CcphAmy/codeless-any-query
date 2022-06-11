<template>
  <div>
    <el-dialog width="500px" title="导入Excel" :close-on-click-modal="false" :visible.sync="visible">
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
          <el-button slot="trigger" size="small" type="primary">选取文件</el-button>

          <div slot="tip" class="el-upload__tip">
            只能上传“xls”或“xlsx”格式文件，建议单次只上传一个!
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
                <el-button :disabled="load" @click="visible = false">关闭</el-button>
            </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "import-box",
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
      tableName: '',
      tmpAction: '',
      fileList: [],
      load: false,
      textarea2: '',
      uploadBox: false,
      config: {}
    }
  },
  methods: {
    init(tableName) {
      this.visible = true
      this.tableName = tableName
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
      this.tmpAction = `${this.action}?tableName=` + this.tableName
      this.config = this.$http.getToken()
      console.log(this.config)
      // load
      this.load = true
      this.$nextTick(() => {
        this.$refs.upload.submit();
      })
    }
  }
}
</script>

<style scoped>
>>> .el-checkbox__label {
  font-size: 12px !important;
}
</style>
