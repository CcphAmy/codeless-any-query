<template>

  <div class="home-layout">

    <div class="header">
    </div>

    <div class="main">

      <div class="row" v-for="(item, index) in groupList ? Math.ceil(groupList.length / 2) : 0" :key="index">
        <el-row>
          <el-col :span="1">
            &nbsp;
          </el-col>
          <el-col :span="10">
            <div class="grid-content">
              <home-item
                  @click-item="out"
                  :id="groupList[index * 2].id"
                  :name="groupList[index * 2].name"
                  :description="groupList[index * 2].description"
                  :icon="groupList[index * 2].icon"></home-item>
            </div>
          </el-col>
          <el-col :span="2">
            <div class="grid-content">
              <span class="x-line"></span>
            </div>
          </el-col>
          <el-col :span="10">
            <div v-if="(index + 1) * 2 <= groupList.length" class="grid-content">
              <home-item
                  @click-item="out"
                  :id="groupList[index * 2 + 1].id"
                  :name="groupList[index * 2 + 1].name"
                  :description="groupList[index * 2 + 1].description"
                  :icon="groupList[index * 2 + 1].icon"></home-item>
            </div>
          </el-col>
          <el-col :span="1">
            &nbsp;
          </el-col>
        </el-row>

        <div v-if="(index + 1) * 2 <= groupList.length" style="margin-top: -10px;">
          <div class="y-line"></div>
        </div>

      </div>

    </div>

  </div>

</template>

<script>
import HomeItem from './home-item'

export default {
  name: "index",
  components: {HomeItem},
  data() {
    return {
      groupList: [{
        id: 1,
        name: '分组1',
        description: '自定义分组',
        icon: 'el-icon-star-on'
      }, {
        id: 2,
        name: '分组2',
        description: '自定义分组',
        icon: 'el-icon-s-help'
      }, {
        id: 3,
        name: '分组3',
        description: '自定义分组',
        icon: 'el-icon-s-promotion'
      }, {
        id: 3,
        name: '分组3',
        description: '自定义分组',
        icon: 'el-icon-s-promotion'
      }, {
        id: 3,
        name: '分组3',
        description: '自定义分组',
        icon: 'el-icon-s-promotion'
      }, {
        id: 3,
        name: '分组3',
        description: '自定义分组',
        icon: 'el-icon-s-promotion'
      }, {
        id: 3,
        name: '分组3',
        description: '自定义分组',
        icon: 'el-icon-s-promotion'
      }]
    }

  },
  created() {
  },
  methods: {
    out(id) {
      this.$router.push({ name: 'queryList', params: { id: id }})
    }
  }
}
</script>

<style lang="scss" scoped>

.home-layout {
  background: #ffffff;


  .header {
    width: 100%;
    height: 297px;
    background-color: #1e96fb;
    position: fixed;
    top: 0;
    left: 0;
    z-index: 1;
  }

  .header::after {
    background-color: #1e96fb;
    content: '';
    width: 220%;
    height: 314px;
    position: absolute;
    left: -60%;
    top: 0;
    /*z-index: -1;*/
    border-radius: 0 0 50% 50%;
  }

  .main {
    position: absolute;
    width: 100%;
    top: 314px;
    overflow-y: auto;
    padding-top: 55px;
    height: calc(100% - 315px);
    overflow-x: hidden;

    .row {
      margin-top: -20px;
    }

    .grid-content {
      text-align: center;
      height: 130px;
      //background-color: #1e96fb;
      display: flex;
      align-items: center;
      justify-content: center;
    }

  }


  .x-line {
    width: 1px;
    background-color: #FFFFFF;
    height: 90%;
    position: absolute;
  }

  .x-line::after {
    content: '';
    position: absolute;
    bottom: 0;
    height: 90%;
    width: 1px;
    transform: scaleX(0.5);
    -webkit-transform: scaleX(0.5);
    -ms-transform: scaleX(0.5);
    background: #EDEDED;
  }

  .y-line {
    height: 1px;
    width: 90%;
    margin: 20px 0;
  }


  .y-line::after {
    content: '';
    position: absolute;
    left: 0;
    width: 90%;
    height: 1px;
    transform: scaleY(0.5);
    -webkit-transform: scaleY(0.5);
    -ms-transform: scaleY(0.5);
    background: #EDEDED;
    margin-left: 5%;
  }



}
</style>
