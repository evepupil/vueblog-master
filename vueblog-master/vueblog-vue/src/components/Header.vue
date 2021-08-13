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
            <span class="username">{{user.nickname}}</span>
            <span class="p-intro" >{{user.sign}}</span>
          </div>
        </li>
      </ul>
	  </div>
    <div class="maction">
      <span><el-link href="/blogs">主页</el-link></span>
      <el-divider direction="vertical"></el-divider>
      <span><el-link type="success" href="/blog/add">发表博客</el-link></span>

      <el-divider direction="vertical"></el-divider>
      <span v-show="!hasLogin"><el-link type="primary" href="/login">登录</el-link></span>

      <span v-show="hasLogin"><el-link type="danger" @click="logout">退出</el-link></span>
    </div>

  </div>
</template>

<script>
import router from "@/router/index.js";
  export default {
    name: "Header",

    data() {
      return {
		  keywords:'',
        user: {
		      nickname:'请先登录',
		      sign:'',
          username: '请先登录',
          avatar: ''
        },
        hasLogin: false
      }
    },
    methods: {
		Tosearch(){
			const _this=this
			_this.$router.push({name: 'Search', query: {keywords: this.keywords}})
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
      }
    },
    created() {
      if(this.$store.getters.getUser.username) {
        this.user.username = this.$store.getters.getUser.username
        this.user.nickname = this.$store.getters.getUser.nickname
        this.user.avatar = this.$store.getters.getUser.avatar
        this.user.sign = this.$store.getters.getUser.sign

        this.hasLogin = true
      }

    }
  }
</script>


<style scoped>

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
  width: 150px;
  margin-left: 50px;
  margin-right: 50px;
  height: 70px;
}
.user .user-img{
  float: left;
}
.username{
  position: relative;
  font-size: 18px;
  display: inline-block;
  margin-left: 15px;
}
.p-intro{
  position: relative;
  top: 25px;
  right: 34px;
  font-size: 14px;
}
.m-content {
  width: 100%;
  height: 70px;
}
.maction {
  margin: 10px 0;
}

</style>