<template>

  <div class="row" @click="clickItem">
    <div class="name">
      {{ name }}
    </div>

    <div class="description">
      {{ description }}
    </div>
  </div>

</template>

<script>
export default {
  name: "query-item",
  props: {
    id: {
      required: true,
      default: () => -1
    },
    name: {
      type: String,
      required: false,
      default: () => "Default Name"
    },
    description: {
      type: String,
      required: false,
      default: () => "Description"
    },
    icon: {
      type: String,
      required: false,
      default: () => "el-icon-user-solid"
    }
  },
  data() {
    return {}
  },
  methods: {
    clickItem() {
      this.$emit("click-item", this.id)
    }
  }
}
</script>

<style scoped>

.row {
  margin-bottom: 10px;
}

.name {
  font-size: 17px;
  /* font-weight: 400; */
  line-height: 35px;

}

.description{
  font-weight: 300;
  font-size: 14px;
}

</style>
