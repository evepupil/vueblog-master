<template>
  <div>
    <Header></Header>
    <div>
      <el-card  class="article"  placement="top" v-for="blog in blogs" >
        <h4>
          <router-link :to="{name: 'BlogDetail', params: {blogId: blog.id}}">
            {{blog.title}}
          </router-link>
        </h4>
        <p>{{blog.description}}</p>
        <p class="time" value-format="yyyy-MM-dd HH:mm" format="yyyy-MM-dd HH:mm" >发布于{{blog.created}}</p>
        <p class="time" value-format="yyyy-MM-dd HH:mm" format="yyyy-MM-dd HH:mm"> {{blog.recent}}</p>
        <p>{{blog.favorite}}</p>
        <p>{{blog.star}}</p>
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
      blogs:''
    }
  },
  methods:{
      refresh(){
        const _this=this
        const keywords = this.$route.query.keywords
        _this.$axios.get("/blog/search?keywords="+keywords).then(res=>{
          _this.blogs=res.data.data
        })
      }
  },
  created(){
    this.refresh()
  },
}
</script>

<style scoped>

</style>