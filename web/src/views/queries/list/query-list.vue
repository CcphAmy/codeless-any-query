<template>
  <div style="margin: 5px">

    <div v-for="(item, index) in queryList" :key="index">
      <query-item @click-item="clickItem" :id="item.id" :name="item.name" :description="item.description"></query-item>
      <div class="y-line"></div>
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
      if (!this.$route.params || this.$route.params.id === void 0) {
        this.$router.push({name: 'queryHome'})
        return
      }
      this.groupId = this.$route.params.id
    },
    clickItem(id) {
      this.$router.push({ name: 'queryBox', params: { id: id }})}
  }
}
</script>

<style scoped>

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
