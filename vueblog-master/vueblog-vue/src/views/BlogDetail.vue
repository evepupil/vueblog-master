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

    <el-input  tpye="textarea" :autosize="{ minRows: 2, maxRows: 5}" placeholder="请输入内容" v-model="CommentAddDto.yourcomment"
               maxlength="200"           show-word-limit>
    </el-input>
    <el-button v-bind:icon=btnicon[succu] @click.native="addcomment"> 提交</el-button>
    <div v-for="comment in comments">

      <div class="user">
        <el-avatar :size="50" shape="square" :src="comment.avatar"></el-avatar>
        <span class="nickname">{{ comment.nickname }}
        </span>

      </div>
      <el-card>
        <div >
          <p v-text="comment.content"></p>
          <p>{{comment.time}}</p>
          <p class="favorite">
            <span>点赞</span>
            {{comment.favorite}}
          </p>
          <div>
          </div>
        </div>
      </el-card>
    </div>

    <el-alert
        title="成功提示的文案"
        type="success"
        center
        show-icon>
    </el-alert>
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
        succu: 0,  //0 ,1提交中,2提交成功
        btnicon:['el-icon-caret-top','el-icon-loading','el-icon-check'],
        //btnicon:"el-icon-loading",
        comments: {},
        CommentAddDto:{
          blogId: 0,
          yourcomment:"",
          user:""
        },
        blog: '',
        ownBlog: false
      };
    },
    created() {
      const blogId = this.$route.params.blogId
      console.log(blogId)
      const _this = this
      this.$axios.get('/blog/' + blogId).then(res => {
        this.blog = res.data.data
        let MardownIt = require("markdown-it")
        let md = new MardownIt()
        let result = md.render(this.blog.content)
        _this.blog.content = result
        _this.ownBlog = (this.blog.userId === _this.$store.getters.getUser.id)

      })
      _this.$axios.get("/comments?blogid=" + blogId ).then(res => {
        console.log(res)
        this.comments = res.data.data
      })
    },
    methods:{
      addcomment() {
        this.succu=1
        const _this = this
        const blogId = this.$route.params.blogId
        //console.log('blogid:'+blogId)
        this.CommentAddDto.blogId=blogId
        this.CommentAddDto.user=_this.$store.getters.getUser
        this.$axios.post("/commentadd", this.CommentAddDto).then(res => {
          this.succu=2
          this.CommentAddDto.yourcomment=''
          //console.log(res)
          _this.$axios.get("/comments?blogid=" + blogId).then(res => {
            this.succu=0
            //console.log(res)
            this.comments = res.data.data
          })
        })
        this.succu=0
      }
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
  .nickname{
    margin-left: 10px;
    position: relative;
    bottom: 20px;
  }
  .card{
    margin-bottom: 30px;
  }
  .user-img{
    border-radius: 50%;
  }
  .favorite{
    float: right;
  }
</style>