<template>
  <div class="mcontaner">
    <Header></Header>

    <div class="block">

      <el-card  class="article"  placement="top" v-for="blog in blogs" >

        <router-link :to="{name: 'BlogDetail', params: {blogId: blog.id}}">
          {{blog.title}}
        </router-link>
        <p class="restore"  value-format="yyyy-MM-dd HH:mm" format="yyyy-MM-dd HH:mm"> 最近回复于 {{blog.recent}}</p>
        <p>{{blog.description}}</p>



        <div class="publisher">
          <div class="publisher-img">
            <img :src=blog.avatar alt="">
          </div>
          <p class="publisher-name"v-text="blog.author"> 茶摘</p>
          <p class="time" value-format="yyyy-MM-dd HH:mm" format="yyyy-MM-dd HH:mm" >发布于{{blog.created}}</p>
        </div>

        <div class="interactive">
          <p class="browse">
            <span>
              <i class="el-icon-view"></i>
            </span>
            {{blog.browse}}
          </p>
          <p class="star">
            <span>
              <svg class="icon" aria-hidden="true">
                  <use xlink:href="#blog-like"></use>
              </svg>
            </span>
            {{blog.star}}
          </p>
          <p class="favorite">
            <span>
              <i class="el-icon-chat-round"></i>
            </span>
            {{blog.commentnums}}
          </p>
          <p class="favorite">
            <span>
              <svg class="icon" aria-hidden="true">
                  <use xlink:href="#blog-star"></use>
              </svg>
            </span>
            {{blog.favorite}}
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
  padding: 0;
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
.interactive{
  height: 20px;
  float: right;
  margin-top: -50px;
}
.interactive p{
  display: inline-block;
  font-size: 14px;
  margin-right: 10px;
}
.interactive span{
  font-size: 14px;
}
.restore{
  color: #888888e3;
  font-size: 12px;
}
.publisher{
  margin-top: 45px;
}
.publisher-name{
  font-weight: bold;
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
.time{
  color: #888888e3;
  font-size: 12px;
  margin-top: -6px;
}
</style>