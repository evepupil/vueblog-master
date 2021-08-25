<template>
  <div>
    <Header></Header>
    <div class="publisher">
      <div class="publisher-img">
        <img :src="blog.avatar" alt="">
      </div>
      <p class="publisher-name">{{blog.author}}</p>
      <p class="time" value-format="yyyy-MM-dd HH:mm" format="yyyy-MM-dd HH:mm" >发布于{{blog.created}}</p>
    </div>
    <div class="mblog">

      <h2 class="blog-title"> {{ blog.title }}</h2>
      <el-link icon="el-icon-edit" v-if="ownBlog">
        <router-link :to="{name: 'BlogEdit', params: {blogId: blog.id}}" >
          编辑
        </router-link>
      </el-link>
      <el-divider></el-divider>
      <div class="markdown-body" v-html="blog.content"></div>

      <el-divider content-position="right" class="splitted">{{blog.title}}</el-divider>
      <div class="star">
        <p @click=clickLike :class="{'like':isLike}">
          <svg class="icon" aria-hidden="true">
            <use xlink:href="#blog-like"></use>
          </svg>
          {{blog.favorite}}
        </p>
        <p @click=clickStar :class="{'like':isStar}">
          <svg class="icon" aria-hidden="true">
            <use xlink:href="#blog-star"></use>
          </svg>
          {{blog.star}}
        </p>
        <p>
          <i class="el-icon-chat-round"></i>
          {{blog.commentnums}}
        </p>
      </div>
    </div>



    <div>
      <h1>评论区</h1>
      <el-input  tpye="textarea" :autosize="{ minRows: 2, maxRows: 5}" placeholder="请输入内容" v-model="CommentAddDto.yourcomment"
                 maxlength="200"           show-word-limit class="input">
      </el-input>

      <el-button v-bind:icon=btnicon[succu] @click.native="addcomment" class="submit"> 提交</el-button>
      <div v-for="comment in comments">


        <el-card class="comment">
          <div class="user">
            <el-avatar :size="50" shape="circle" :src="comment.avatar" class="user-img"></el-avatar>
            <p class="nickname">{{ comment.nickname }}</p>
            <p class="time">{{comment.time}}</p>
          </div>
          <div >
            <p v-text="comment.content"></p>

            <p class="favorite">
            <span>
              <svg class="icon" aria-hidden="true">
                <use xlink:href="#blog-like"></use>
              </svg>
            </span>
              {{comment.favorite}}
            </p>
            <div>
            </div>
          </div>
        </el-card>
      </div>
    </div>

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
      ownBlog: false,
      isLike:false,
      isStar:false
    };
  },
  created() {
    this.refresh()
  },
  methods:{
    refresh(){
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
    },
    clickLike(){
      this.$axios.get('/likeblog?blogid='+this.$route.params.blogId, {
        headers: {
          "Authorization": localStorage.getItem("token")
        }
      }).then(res => {
          this.$message.success(res.data.data)
        this.refresh()
      })
      this.isLike = !this.isLike
    },
    clickStar(){
      this.$axios.get('/starblog?blogid='+this.$route.params.blogId, {
        headers: {
          "Authorization": localStorage.getItem("token")
        }
      }).then(res => {
        this.$message.success(res.data.data)
        this.refresh()
      })
      this.isStar = !this.isStar
    },
  }
}
</script>

<style scoped>
.user-img{
  float: left;
  margin-right: 10px;
}
.mblog {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  width: 100%;
  min-height: 300px;
  padding: 20px 15px;
  margin-bottom: 70px;
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
.publisher{
  overflow: hidden;
}
.publisher-name{
  margin-top: 10px;
}
.publisher-img{
  width: 3rem;
  height: 3rem;
  float: left;
  margin-right: 10px;
}
.publisher-img img{
  width: 100%;
  height: 100%;
  border-radius: 50%;
}
.input{
  width: 89%;
}
.time{
  color: #888888e3;
  font-size: 12px;
  margin-top: -6px;
}
.blog-title{
  padding-left: 10px;
  border-left: 5px solid black;
  display: block;
  color: #000000;
}
.submit{
  margin-left: 10px;
}
.splitted{
  margin-top:150px;
  font-weight: bold;
}
.star{
  width: 100%;
  height: 40px;
}
.star p{
  float: left;
  margin-right: 20px;
  cursor: pointer;
}
.like{
  color: #1296db;
}
.comment{
  margin-top: 20px;
}
.user{
  margin-bottom: 20px;
}
.user p{
  margin: 1;
  padding: 0;
}
.nickname{
  margin-bottom: 15px;
}
</style>