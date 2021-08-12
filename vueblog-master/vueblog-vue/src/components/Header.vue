<template>
  <div class="m-content">
	  <div class="title"    >
      <ul class="top">
        <li  >
          <span><el-link href="/blogs" >论坛广场</el-link></span>
        </li>
        <li  >
          <el-input v-model="search" maxlength="20" placeholder="请输入内容" ></el-input>
        </li>
        <li  >
          <el-button type="" icon="el-icon-search">搜索</el-button>
        </li>
        <li  >
          <div class="user" >
            <el-avatar :size="50"  shape="square" :src="user.avatar"></el-avatar>
            <p class="username">{{user.nickname}}</p>
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
  export default {
    name: "Header",
	
    data() {
      return {
		  search:'',
        user: {
		      nickname:'',
		      sign:'',
          username: '请先登录',
          avatar: 'https://blog-1257029975.cos.ap-beijing.myqcloud.com/head/QQ%E5%9B%BE%E7%89%8720210530121836.jpg'
        },
        hasLogin: false
      }
    },
    methods: {
		// Tosearch(){
		// 	const _this=this
		// 	_this.$axios.get("/blog/search"),{
		// 		headers:{
		// 			"Authorization": localStorage.getItem("token")
		// 		},
		// 		}).then(res=>{
					
		// 		})
		// 	},
		
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
  .m-content {
    max-width: 960px;
    margin: 0 auto;
    text-align: center;
  }
  .maction {
    margin: 10px 0;
  }

</style>