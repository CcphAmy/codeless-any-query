<template>

  <div @click="clickItem" class="text-center">

    <div class="icon-box">
      <i class="icon" :class="icon"></i>
    </div>

    <div class="text-center group-name">
      {{ name }}
    </div>
    <div class="text-center group-remark">
      {{ description }}
    </div>
  </div>


</template>

<script>
export default {
  name: "home-item",
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
  methods: {
    clickItem() {
      this.$emit("click-item", this.id)
    }
  }
}
</script>

<style scoped>
.icon-box {
  background-color: #F1F8FF;
  width: 52px;
  height: 52px;
  border-radius: 50%;
  text-align: center;
  margin: 0 auto;
}

.icon {
  color: #2196FB;
  margin: 10px 0;
  font-size: 2.5em;
  vertical-align: middle;
}

.group-name {
  margin-top: 9px;
  font-weight: bold;
  font-size: 18px;
}

.group-remark {
  margin-top: 5px;
  color: #888888;
  font-size: 16px;
}

.text-center {
  text-align: center;
}
</style>
