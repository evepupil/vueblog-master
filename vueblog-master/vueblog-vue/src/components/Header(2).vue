<template>
  <div class="m-content">
    <div class="title"    >
      <ul class="top">
        <li  >
          <span><el-link href="/blogs" class="nav-title">论坛广场</el-link></span>
        </li>
        <li  >
          <el-input v-model="keywords" maxlength="20" placeholder="请输入内容" ></el-input>
        </li>
        <li  >
          <el-button type=""  icon="el-icon-search" @click="Tosearch">搜索</el-button>
        </li>
        <li  >
          <div class="user" >
            <el-avatar :size="50"  shape="square" :src="user.avatar" class="user-img"></el-avatar>
            <p class="username">{{user.nickname}}</p>
            <p class="p-intro" >{{user.sign}}</p>
          </div>
        </li>
      </ul>
    </div>
    <div class="maction">
      <span><el-link href="/blogs">主页</el-link></span>
      <el-divider direction="vertical"></el-divider>
      <span><el-link type="success" href="/blog/add">发表博客</el-link></span>

      <span v-show="hasLogin">
        <el-divider direction="vertical" ></el-divider>
        <el-link type="warning" @click="toUserCenter" >个人中心</el-link>
      </span>
      <el-divider direction="vertical"></el-divider>
      <span v-show="!hasLogin"><el-link type="primary" href="/login">登录</el-link></span>

      <span v-show="hasLogin"><el-link type="danger" @click="logout">退出</el-link></span>
    </div>

  </div>
</template>

<script>
import router from "@/router/index.js";
import SearchResult from "@/views/SearchResult";
export default {
  name: "Header",

  data() {
    return {
      keywords:'',
      user: {
        userid:null,
        nickname:'请先登录',
        sign:'',
        username: '请先登录',
        avatar: ''
      },
      hasLogin: false
    }
  },
  methods: {
    toUserCenter(){
      const  _this=this
      this.user.userid = this.$store.getters.getUser.id
      _this.$router.push({name: 'UserCenter' , params: {userid:this.user.userid}})
      _this.$store.commit("SET_VISIT",this.user.userid)

    },
    Tosearch(){
      const _this=this
      _this.$router.push({name: 'Search', query: {keywords: this.keywords}})
      if(_this.$parent==SearchResult)
        _this.$parent.refresh()
    },
    logout() {
      const _this = this
      _this.$axios.get("/logout", {
        headers: {
          "Authorization": localStorage.getItem("token")
        }
      }).then(res => {
        _this.$store.commit("REMOVE_INFO")
        _this.$router.push("/login")

      })
    },
    refresh(){
      this.$axios.get('/usercenter?id='+this.$store.getters.getUser.id).then(res=> {
        this.$store.commit("SET_USERINFO", res.data.data)
      })
      if(this.$store.getters.getUser.username) {
        this.user.username = this.$store.getters.getUser.username
        this.user.nickname = this.$store.getters.getUser.nickname
        this.user.avatar = this.$store.getters.getUser.avatar
        this.user.sign = this.$store.getters.getUser.sign
        this.hasLogin = true
      }
    }
  },

  created() {
    this.refresh()
  }
}
</script>


<style scoped>
.el-divider--vertical{
  margin: 0 5px;
}
.el-button{
  width: 80px;
}
.user p{
  margin: 0;
  padding: 0;
  margin-bottom: 5px;
}
.m-content{
  margin-bottom: 25px;
}
.top{
  list-style-type:none
}
li{
  float:left;
}
.nav-title{
  font-size: 20px;
  margin-right: 50px;
  margin-top: 10px;
}
.user{
  width: 200px;
  margin-left: 50px;
  margin-right: 50px;
  height: 50px;
}
.user .user-img{
  float: left;
  margin-right: 10px;

}
.username{
  margin-bottom: 10px;
  font-weight: bold;
}
.p-intro{
  margin-top: 20px;
  font-size: 14px;
  text-overflow:ellipsis;
  -webkit-line-clamp:1;
  display: -webkit-box;
  -webkit-box-orient:block-axis;
  white-space:overflow;
  overflow:hidden;

}
.m-content {
  width: 100%;
  height: 70px;
}
.maction {
  margin: 10px 0;
}

</style>