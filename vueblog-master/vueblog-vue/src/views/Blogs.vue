<template>
  <div class="mcontaner">
    <Header></Header>

    <div class="block">

      <el-card  class="article"  placement="top" v-for="blog in blogs" >

        <router-link :to="{name: 'BlogDetail', params: {blogId: blog.id}}">
          {{blog.title}}
        </router-link>
        <p class="restore" value-format="yyyy-MM-dd HH:mm" format="yyyy-MM-dd HH:mm"> 最近回复于 {{blog.recent}}</p>
        <p>{{blog.description}}</p>

        <p class="time" value-format="yyyy-MM-dd HH:mm" format="yyyy-MM-dd HH:mm" >发布于{{blog.created}}</p>
        <div class="interactive">
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

      <el-pagination class="mpage"
                     background
                     layout="prev, pager, next"
                     :current-page="currentPage"
                     :page-size="pageSize"
                     :total="total"
                     @current-change=page>
      </el-pagination>

    </div>

  </div>
</template>

<script>
import Header from "../components/Header";

export default {
  name: "Blogs.vue",
  components: {Header},
  data() {
    return {
      blogs: {},
      currentPage: 1,
      total: 0,
      pageSize: 5
    }
  },
  methods: {
    page(currentPage) {
      const _this = this
      _this.$axios.get("/blogs?currentPage=" + currentPage).then(res => {
        console.log(res)
        _this.blogs = res.data.data.records
        console.log(" time " + _this.blogs[0].recent)
        _this.currentPage = res.data.data.current
        _this.total = res.data.data.total
        _this.pageSize = res.data.data.size
      })
    }
  },
  created() {
    this.page(1);
    //console.log(blogs[1])
  }
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