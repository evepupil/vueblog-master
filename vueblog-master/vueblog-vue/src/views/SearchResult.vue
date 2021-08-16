<template>
  <div>
    <Header></Header>
    <div  v-if="blogs">
      <p>共找到{{blogs.length}}个结果</p>
      <el-card  class="article"  placement="top" v-for="blog in blogs" >

        <router-link :to="{name: 'BlogDetail', params: {blogId: blog.id}}">
          {{blog.title}}
        </router-link>
        <p class="restore" value-format="yyyy-MM-dd HH:mm" format="yyyy-MM-dd HH:mm"> 最近回复于 {{blog.recent}}</p>
        <p>{{blog.description}}</p>

        <p class="time" value-format="yyyy-MM-dd HH:mm" format="yyyy-MM-dd HH:mm" >发布于{{blog.created}}</p>
        <div class="interactive">
          <p class="browse">
            <span>浏览</span>
            {{blog.browse}}
          </p>
          <p class="favorite">
            <span>回复</span>
            {{blog.commentnums}}
          </p>
          <p class="favorite">
            <span>收藏</span>
            {{blog.favorite}}
          </p>
          <p class="star">
            <span>点赞</span>
            {{blog.star}}
          </p>
        </div>

      </el-card>
    </div>
  </div>

</template>

<script>
import Header from "../components/Header";
export default {
  components: {Header},
  name: "SearchResult",
  data(){
    return{
      blogs:null
    }
  },
  methods:{
      refresh(){
        const _this=this
        const keywords = this.$route.query.keywords
        _this.$axios.get("/blog/search?keywords="+keywords+'&'+new Date()).then(res=>{
          _this.blogs=res.data.data
        })
        //console.log('             11 '+_this.blogs)
      }
  },
  created(){
    this.refresh()
  },
}
</script>

<style scoped>
.article{
  margin-top: 10px;
  margin-bottom: 10px;
}
.mpage {
  margin: 0 auto;
  text-align: center;
}
a{
  text-decoration: none;
  color: black;
  font-weight: bold;
  font-size: 25px;
}
.time{
  float: right;
  margin-top: 55px;
}
.interactive{
  height: 20px;
  float: right;
  margin-right: -185px;
}
.interactive p{
  display: inline-block;
  font-size: 14px;
  margin-right: 5px;
}
.interactive span{
  font-size: 14px;
}
.restore{
  color: #888888e3;
  font-size: 12px;
}
</style>