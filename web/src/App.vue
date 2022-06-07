<template>
    <div id="app">
        <transition name="fade">
            <router-view />
        </transition>
    </div>
</template>


<style>

html, body {
    height: 100%;
    width: 100%;
}

.full-hw {
    height: 100%;
    width: 100%;
}

::-webkit-scrollbar {
    width: 6px !important;
    height: 6px !important;
    background-color: #F5F5F5 !important;
}

::-webkit-scrollbar-thumb:hover {
    background-color: #409EFF !important;
}

::-webkit-scrollbar-thumb {
    border-radius: 6px !important;
    background-color: rgba(47, 47, 49, 0.2) !important;
}
::-webkit-scrollbar-track {
    background-color: #F5F5F5 !important;
}

img.image-sm {
    max-width: 80px;
    max-height: 80px;
}
.el-col .el-select,
.el-col .el-date-editor {
    width: 100%;
}
.demo-table-expand {
    font-size: 0;
}
.demo-table-expand label {
    width: 90px;
    color: #99a9bf;
}
.demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;
}
.text-warning {
    color: #e6a23c;
}
</style>
