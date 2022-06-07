<template>
  <div class="container">

    <div class="box">
      <div style="height: 40px">
        <i class="el-icon-star-on"></i>
        <span style="font-size: 18px;">2022年喵喵哒查询表单</span>
      </div>

      <div class="item-input">
        <label>真实姓名</label>
        <input type="text" name="name" autocomplete="off"  placeholder="请输入真实姓名" />
      </div>

      <div class="item-input">
        <label>真实姓名</label>
        <input type="text" name="name" autocomplete="off"  placeholder="请输入真实姓名" />
      </div>

      <div class="item-input">
        <label>真实姓名</label>
        <input type="text" name="name" autocomplete="off"  placeholder="请输入真实姓名" />
      </div>


      <div style="padding: 30px 0 20px 0">
        <span class="circle-left"></span>
        <span class="y-line"></span>
        <span class="circle-right"></span>
      </div>

      <div>
        <span class="tip"><i class="el-icon-warning"></i>注意事项</span>
        <p style="font-size: 13px">1.查询时请注意避免输入首尾空格, 特殊符号.</p>
      </div>

<!--      注意事项-->
      <div class="submit">
        <el-button class="submit-button" type="primary" round>查询</el-button>
      </div>
    </div>

    <div class="copyright">
      自动生成查询页面
    </div>
  </div>
</template>

<script>
export default {
  name: "query-box",
  data() {
    return {}
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
    }
  }
}
</script>

<style scoped>


.container {
  position: absolute;
  top: 0;
  left: 0;
  font-size: 14px;
  font-weight: normal;
  box-sizing: border-box;
  font-style: normal;
  -webkit-tap-highlight-color: transparent;
  margin: 0;
  border-width: 0;
  list-style: none;
  text-decoration: none;
  outline: none;
  width: 100%;
  overflow: hidden;
  padding: 0;
  background: #1e96fb;
  height: 100vh;
}

.box {
  font-size: 24px;
  font-weight: normal;
  box-sizing: border-box;
  font-style: normal;
  -webkit-tap-highlight-color: transparent;
  margin: 15px;
  border-width: 0;
  list-style: none;
  text-decoration: none;
  outline: none;
  background: #FFFFFF;
  background-size: 100% auto;
  /*border-radius: 12px;*/
  padding: 18px 15px 15px 15px;
}

.item-input {
  color: inherit;
  font-weight: normal;
  box-sizing: border-box;
  font-style: normal;
  -webkit-tap-highlight-color: transparent;
  border-width: 0;
  list-style: none;
  text-decoration: none;
  outline: none;
  width: 100%;
  height: 44px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: #f7f7f7;
  font-size: 16px;
  padding: 0 3px 0 0;
  margin: 0 0 2px;
}
.item-input label {
  color: #222222;
  flex-shrink: 0;
  text-align: center;
  padding: 0 15px 0 15px;
}

.item-input input {
  text-indent: 2px;
  width: 100%;
  height: 90%;
  background: transparent;
  color: #333;
  font-size: 16px;
  border: 0;
}


.y-line {
  height: 1px;
  width: 100%;
  margin: 5px 0;
}


.y-line::after {
  content: '';
  position: absolute;
  left: 15px;
  width: calc(100% - 60px);
  margin-left: 15px;
  margin-right: 15px;
  height: 1px;
  transform: scaleY(0.5);
  -webkit-transform: scaleY(0.5);
  -ms-transform: scaleY(0.5);
  background: #d7d5d5;
}

.circle-left {
  position: absolute;
  left: -2px;
  margin-top: -12px;
  border-radius: 50%;
  display: inline-block;
  box-sizing: border-box;
  text-align: center;
  overflow: hidden;
  color: #fff;
  background: #1e96fb;
  width: 25px;
  height: 25px;
  line-height: 25px;
  font-size: 14px;
}

.circle-right {
  position: absolute;
  left: 100%;
  margin-top: -12px;
  margin-left: -23px;
  border-radius: 50%;
  display: inline-block;
  box-sizing: border-box;
  text-align: center;
  overflow: hidden;
  color: #fff;
  background: #1e96fb;
  width: 25px;
  height: 25px;
  line-height: 25px;
  font-size: 14px;
}

.tip {
  color: #eca419;
  font-size: 13px;
  display: flex;
  align-items: center;
  font-weight: bold;
}

.submit {
  text-align: center;
  margin-bottom: 10px;
  padding-top: 10px;
}

.submit-button {
  width: 80%;height: 44px;font-size: 18px;letter-spacing:2px;font-weight: 400;border-radius: 12px
}

.copyright {
  text-align: center;
  font-size: .24rem;
  color: #97b9fe;
  padding: 0.35rem 0;
}
</style>
