<template>

  <div class="position: relative;">
    <div class="container">

      <div v-for="(item, index) in queryList" :key="index">
        <query-item @click-item="clickItem" :id="item.id" :name="item.name"
                    :description="item.description"></query-item>
        <div class="y-line"></div>
      </div>

    </div>
  </div>
</template>

<script>
import QueryItem from './list-item'

export default {
  name: "query-list",
  components: {QueryItem},
  data() {
    return {
      groupId: void 0,
      queryList: [{
        id: 1,
        name: '可查询表1',
        description: '自定义分组',
        icon: 'el-icon-star-on'
      }, {
        id: 2,
        name: '可查询表2',
        description: '自定义分组',
        icon: 'el-icon-s-help'
      }, {
        id: 3,
        name: '可查询表3',
        description: '自定义分组',
        icon: 'el-icon-s-promotion'
      }, {
        id: 4,
        name: '可查询表4',
        description: '自定义分组',
        icon: 'el-icon-s-promotion'
      }, {
        id: 5,
        name: '可查询表5',
        description: '自定义分组',
        icon: 'el-icon-s-promotion'
      }]
    }
  },
  created() {
    this._checkParams()
  },
  methods: {
    _checkParams() {
      // if (!this.$route.params || this.$route.params.id === void 0) {
      //   this.$router.push({name: 'queryHome'})
      //   return
      // }
      // this.groupId = this.$route.params.id
    },
    clickItem(id) {
      this.$router.push(`/query/query/${id}`)
    }
  }
}
</script>

<style scoped>
@media screen and (min-width: 1000px) {
  .container {
    max-width: 600px !important;
  }
}


.container {
  position: absolute;
  margin: auto;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  font-size: 14px;
  font-weight: normal;
  box-sizing: border-box;
  font-style: normal;
  -webkit-tap-highlight-color: transparent;
  border-width: 0;
  list-style: none;
  text-decoration: none;
  outline: none;
  width: 100%;
  overflow: hidden;
  padding: 5px;
  height: 100vh;
  box-shadow: 0 10px 20px 2px rgb(0 0 0 / 10%);
}

.y-line {
  height: 1px;
  width: 100%;
  margin: 5px 0;
}


.y-line::after {
  content: '';
  position: absolute;
  left: 0;
  width: 100%;
  height: 1px;
  transform: scaleY(0.5);
  -webkit-transform: scaleY(0.5);
  -ms-transform: scaleY(0.5);
  background: #d7d5d5;
}

</style>
