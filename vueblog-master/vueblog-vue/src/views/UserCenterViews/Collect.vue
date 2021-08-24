<template>
    <div>
      <UserCenterCom :isOwn="isOwn" :userid="userid" :userinfo="userinfo"></UserCenterCom>
      <div v-for="blog in blogs">
        <Post  :blog="blog"></Post>
      </div>
    </div>
</template>

<script>
import UserCenterCom from "./UserCenterCom.vue";
import Post from "./Post.vue";
export default {
    components: {UserCenterCom,Post},
    name:'Collect',
  data() {
    return {
      isOwn:false,
      userid:null,
      userinfo:null,
      blogs: null,
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
      _this.$axios.get("/mystar?userid=" + _this.userid,{
        headers: {
          "Authorization": localStorage.getItem("token")
        }
      }).then(res => {
        _this.blogs = res.data.data
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