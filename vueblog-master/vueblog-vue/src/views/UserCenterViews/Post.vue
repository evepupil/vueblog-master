<template>
  <div>
    <div class="post">
      <div class="my-post">
        <div class="uesr">
          <div class="user-img">
            <img :src=blog.avatar >
          </div>
          <span class="username" v-text="blog.author">{{UserName}}</span>
          <p class="time" v-text="blog.created">21.08.20</p>
        </div>
        <div class="post-main">
          <router-link :to="{name: 'BlogDetail', params: {blogId: blog.id}}">
            {{blog.title}}
          </router-link>
          <div class="content">
            <p v-text="blog.description"></p>
          </div>
        </div>

        <div class="star">
                    <span div="large" >
                        <i class="el-icon-view"></i>
                        <span>{{blog.browse}}</span>
                    </span>
          <span div="large" >
                        <span>
                            <svg class="icon" aria-hidden="true">
                                <use xlink:href="#blog-star"></use>
                            </svg>
                        </span>

                        <span>{{blog.favorite}}</span>
                    </span>

          <span div="large" >
                            <span>
                            <svg class="icon" aria-hidden="true">
                                <use xlink:href="#blog-like"></use>
                            </svg>
                        </span>
                        <span>{{blog.star}}</span>
                    </span>
          <span div="large" >
                            <i class="el-icon-chat-round"></i>
                        <span>{{blog.commentnums}}</span>
                    </span>
        </div>
      </div>
      <el-divider></el-divider>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      UserImgSrc:'https://blog-1257029975.cos.ap-beijing.myqcloud.com/head/QQ%E5%9B%BE%E7%89%8720210530121836.jpg',
      UserName:'叶桐'
    }
  },
  props:['blog']
}
</script>

<style scoped>
.my-post{
  width: 100%;
  height: 180px;
  transition: all .3s;
  position: relative;
  box-sizing: border-box;
  margin-top: 20px;
}
.user{
  width: 100%;
  height: 20%;
}
.user-img{
  width: 5rem;
  height: 5rem;
  float: left;
  margin-right: 20px;
}
.user-img img{
  width: 100%;
  height: 100%;
  border-radius: 50%;

}
.username{
  display: block;
  position: relative;
  top: 10px;
}
.time{
  margin-top: 15px;
  font-size: 12px;
  color: #99a2aa;
}
.post-main{
  margin-top: 20px;
  width: 100%;
  height: 100px;
  margin-left: 6rem;
}
h3{
  margin: 0;
  padding: 0;
}
.post-main p{
  margin: 0;
  margin-top: 5px;
  margin-left: 4px;
}
.star{
  float: right;
}
.content{
  text-overflow:ellipsis;
}
a{
  text-decoration: none;
  display: block;
  color: black;
  font-size: 20px;
}
.star span{
  display: inline-block;
  margin-right: 5px;
}
.star i{
  margin-right: 5px;
}
</style>