<template>
    <div>
      <UserCenterCom :isOwn="isOwn" :userid="userid" :userinfo="userinfo"></UserCenterCom>
        <ReplyDiv></ReplyDiv>
      <div v-for="(comment,index) in comments"  >
        <ReplyDiv :comment="comment" :index="index" :blogs="blogs"></ReplyDiv>
      </div>
    </div>
</template>

<script>
import UserCenterCom from "./UserCenterCom.vue";
import ReplyDiv from "./ReplayDiv.vue"


export default {
    components: {UserCenterCom,ReplyDiv},
    name:'Like',
  data() {
    return {
      isOwn:false,
      userid:null,
      userinfo:null,
      comments: null,
      blogs:null,
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
      _this.$axios.get("/replayme?userid="+_this.userid ,{
        headers: {
          "Authorization": localStorage.getItem("token")
        }
      }).then(res => {
        _this.comments = res.data.data.comments
        _this.blogs=res.data.data.blogs
        console.log(_this.comments)
        console.log(_this.blogs)
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