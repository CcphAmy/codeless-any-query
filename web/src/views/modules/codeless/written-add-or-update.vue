<template>
    <el-dialog width="500px" :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false"
               :visible.sync="visible">
        <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
                 label-width="140px">
            <el-form-item v-for="(item, index) in columns" :key="index" :label="item.label" :prop="item.prop">
                <el-input v-model="dataForm[item.prop]" :placeholder="item.label"></el-input>
            </el-form-item>

        </el-form>
        <span slot="footer" class="dialog-footer">
            <el-button @click="visible = false">取消</el-button>
            <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
        </span>
    </el-dialog>
</template>

<script>

export default {
    data() {
        return {
            visible: false,
            menuList: [],
            menuListTreeProps: {
                label: 'name',
                children: 'children'
            },
            columns: [],
            dataForm: {
                "id": 0,
                "candidateId": "",
                "name": "",
                "professional": "",
                "score": "",
                "subjectScore": "",
                "rank": null,
                "skillEvaluation": null,
                "remark": null
            },
            dataRule: {
                candidateId: [{required: true, message: '考生号不能为空', trigger: 'blur'}],
                name: [{required: true, message: '名称不能为空', trigger: 'blur'}],
                professional: [{required: true, message: '专业不能为空', trigger: 'blur'}]
            }
        }
    },
    methods: {
        init(id) {
            this.dataForm.id = id || 0
            this.columns = [
                {label: "考生号", prop: "candidateId"},
                {label: "名称", prop: "name"},
                {label: "报考专业", prop: "professional"},
                {label: "综合文化知识成绩", prop: "score"},
                {label: "专业综合理论成绩", prop: "subjectScore"},
                {label: "排名", prop: "rank"},
                {label: "技能考核", prop: "skillEvaluation"},
                {label: "备注", prop: "remark"}
            ]
            this.visible = true
            this.$nextTick(() => {
                this.$refs['dataForm'].resetFields()
                if (this.dataForm.id) {
                    this.$http({
                        url: this.$http.adornUrl(`/program/written/info/${this.dataForm.id}`),
                        method: 'get',
                        params: this.$http.adornParams()
                    }).then(({data}) => {
                        if (data && data.code === 200) {
                            Object.assign(this.dataForm, data.data)
                        }
                    })
                }
            })
        },
        // 表单提交
        dataFormSubmit() {
            this.$refs['dataForm'].validate((valid) => {
                if (valid) {
                    this.visible = false
                    this.$http({
                        url: this.$http.adornUrl(`/program/written/${!this.dataForm.id ? 'save' : 'update'}`),
                        method: 'post',
                        data: this.$http.adornData(this.dataForm)
                    }).then(({data}) => {
                        if (data && data.code === 200) {
                            this.$message({
                                message: '操作成功',
                                type: 'success',
                                duration: 1500,
                                onClose: () => {
                                    this.$emit('refreshDataList')
                                }
                            })
                        } else {
                            this.$message.error(data.msg)
                        }
                    })
                }
            })
        }
    }
}
</script>