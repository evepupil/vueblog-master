<template>
  <div class="mcontaner">
    <Header></Header>

    <div class="block">

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

}
  .mpage {
    margin: 0 auto;
    text-align: center;
  }


</style>