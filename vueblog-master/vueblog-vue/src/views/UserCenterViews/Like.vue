<template>
    <div>
      <UserCenterCom :isOwn="isOwn" :userid="userid" :userinfo="userinfo"></UserCenterCom>
      <div v-for="(like,index) in likes">
          <LikeDiv :index="index" :like="like" :blogs="blogs" :users="users"></LikeDiv>
      </div>

    </div>
</template>

<script>
import UserCenterCom from "./UserCenterCom.vue";
import LikeDiv from "./Likediv.vue"

export default {
    components: {UserCenterCom,LikeDiv},
    name:'Like',
  data() {
    return {
      isOwn:false,
      userid:null,
      userinfo:null,
      blogs: null,
      likes:null,
      users:null,
    }
  },
  methods: {
    refresh() {
      const _this = this
      const userid = this.$route.params.userid
      _this.userid = userid
      const visitor = _this.$store.getters.getVisit
      _this.isOwn = (visitor == userid)
      _this.$store.commit("SET_ISOWN", this.isOwn)
      _this.userid = this.$route.params.userid
      _this.$axios.get("/likeme?userid=" + _this.userid,{
        headers: {
          "Authorization": localStorage.getItem("token")
        }
      }).then(res => {
        _this.blogs = res.data.data.blog
        _this.users = res.data.data.user
        _this.likes = res.data.data.like
      })
      _this.$axios.get('/usercenter?id='+userid).then(res=> {
        _this.userinfo = res.data.data
      })
    },
  },
  created() {
    this.$nextTick(() => {
      // 在此处执行你要执行的函数
      this.refresh()
    });
  },
}
</script>

<style scoped>
   
</style>