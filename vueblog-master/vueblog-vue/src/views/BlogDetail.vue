<template>
  <div>
    <Header></Header>

    <div class="mblog">
      <h2> {{ blog.title }}</h2>
      <el-link icon="el-icon-edit" v-if="ownBlog">
        <router-link :to="{name: 'BlogEdit', params: {blogId: blog.id}}" >
        编辑
        </router-link>
      </el-link>
      <el-divider></el-divider>
      <div class="markdown-body" v-html="blog.content"></div>
    </div>
    <el-button type="info" icon="el-icon-check" circle></el-button>
    <el-button type="succss" icon="el-icon-star-off" circle></el-button>

    <h4>---------------------</h4>
    <div v-for="comment in comments">

      <div class="user">
        <el-avatar :size="50" shape="square" :src="comment.avatar"></el-avatar>
        <p class="nickname">{{ comment.nickname }}
        </p>
        <span class="p-intro">さあ、始めようか</span>
      </div>
      <el-card>
        <div>
          <p>
            {{ comment.content }}
          </p>
          <p>{{comment.favorite}}</p>
          <div>

          </div>
        </div>
      </el-card>
    </div>
    <el-input tpye="textarea" :autosize="{ minRows: 2, maxRows: 5}" placeholder="请输入内容" v-model="yourcomment">
    </el-input>

  </div>
</template>

<script>
  import 'github-markdown-css'
  import Header from "../components/Header";
  export default {
    name: "BlogDetail.vue",
    components: {Header},
    data() {
      return {
        comments: {},
        yourcomment:"",
        blog: {
          id: "",
          title: "",
          content: ""
        },
        ownBlog: false
      }
    },
    created() {
      const blogId = this.$route.params.blogId
      console.log(blogId)
      const _this = this
      this.$axios.get('/blog/' + blogId).then(res => {
        const blog = res.data.data
        _this.blog.id = blog.id
        _this.blog.title = blog.title

        var MardownIt = require("markdown-it")
        var md = new MardownIt()

        var result = md.render(blog.content)
        _this.blog.content = result
        _this.ownBlog = (blog.userId === _this.$store.getters.getUser.id)

      })
      _this.$axios.get("/comments?blogid=" + blogId ).then(res => {
        console.log(res)
        this.comments = res.data.data
      })

    }
    ,
    addcomment() {
      const blogId = this.$route.params.blogId
      this.$axios.get("/commentadd?blogid=" + blogId + "&content=" + yourcomment).then(res => {
        console.log(res)

      })
    }
  }
</script>

<style scoped>
  .mblog {
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    width: 100%;
    min-height: 700px;
    padding: 20px 15px;
  }

</style>