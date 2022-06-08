<template>
  <div class="mod-home">
    <div v-html="compiledMarkdown"></div>
  </div>
</template>
<script>
import marked from 'marked'

const rendererMD = new marked.Renderer();
marked.setOptions({
  renderer: rendererMD,
  gfm: true,
  tables: true,
  breaks: false,
  pedantic: false,
  sanitize: false,
  smartLists: true,
  smartypants: false
})
export default {
  data () {
    return {
      input:
          `# codeless-any-query\n一款简单的无代码生成业务系统`
    }
  },
  computed: {
    compiledMarkdown: function () {
      return marked(this.input, {sanitize: true})
    }
  }
}
</script>
<style>
.mod-home {
  line-height: 2.5;
  text-align: center;
}
</style>

