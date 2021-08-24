<template>

  <div>
    <UserCenterCom :isOwn="isOwn" :userid="userid" :userinfo="userinfo"></UserCenterCom>
    <div class="main">
      <div class="setting-container">
        <div class="large-box">
            <div>
              <p class="title">基础资料</p>
              <el-link icon="el-icon-edit" @click="SettingContainer">编辑</el-link>
            </div>
            <div class="little-box">
              <span class="title-two">昵称</span>
              <el-input v-model="username" placeholder="请输入内容" :disabled=basicsdisabled class="input"></el-input>
            </div>
            <div class="little-box">
              <span class="title-two">签名</span>
              <el-input v-model="signature" placeholder="请输入内容" :disabled=basicsdisabled class="input"></el-input>
            </div>
        </div>
        
        <div class="large-box">
            <div>
              <p class="title">绑定邮箱地址</p>
              <el-link icon="el-icon-edit" @click="SettingContainer2">编辑</el-link>
            </div>
            <div class="little-box">
              <span class="title-two">绑定邮箱地址</span>
              <el-input v-model="email" placeholder="请绑定邮箱" :disabled=emaildisabled class="input"></el-input>
            </div>
        </div>
            

        <div class="large-box">
          <div>
              <p class="title">修改头像</p>
            <el-upload
              class="avatar-uploader"
              action="https://jsonplaceholder.typicode.com/posts/"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload">
              <img v-if="imageUrl" :src="imageUrl" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon el-upload"></i>
              <p>上传图片</p>
            </el-upload>
            
            </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import UserCenterCom from "./UserCenterCom";
export default {
  name: "UserCenter",
  components: {UserCenterCom},
  data(){
    return{
      userinfo:null,
      isOwn:false,
      userid:null,
      username:'',
      signature:'',
      imageUrl: '',
      basicsdisabled:true,

      email:'',
      emaildisabled:true,

    }
  },
  props:{

  },
  methods:{

    SettingContainer(){
      this.basicsdisabled = !this.basicsdisabled;
    },
    SettingContainer2(){
      this.emaildisabled = !this.emaildisabled;
    },
    handleAvatarSuccess(res, file) {
        this.imageUrl = URL.createObjectURL(file.raw);
      },
      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg';
        const isLt2M = file.size / 1024 / 1024 < 2;

        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!');
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!');
        }
        return isJPG && isLt2M;
      },
    refresh() {
      const _this=this
      const userid=this.$route.params.userid
      _this.userid=userid
      console.log('userid--'+userid)
      const  visitor=_this.$store.getters.getVisit
      _this.isOwn=(visitor==userid)
      _this.$store.commit("SET_ISOWN",this.isOwn)
      _this.$axios.get('/usercenter?id='+userid).then(res=>{
        _this.userinfo=res.data.data
        _this.username=_this.userinfo.nickname
        _this.signature=_this.userinfo.sign
        _this.email=_this.userinfo.email
        console.log('isown'+_this.isOwn)
        console.log('--'+_this.userinfo)
      })
    }
  },
   created() {
     this.$nextTick(() => {
       // 在此处执行你要执行的函数
       this.refresh()
     });
   }


}
</script>

<style scoped>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }

  .title{
    margin-right: 5px;
    margin-top: 20px;
    font-size: 20px;
    font-weight: bold;
    display: inline-block;
  }
  .title-two{
    float: left;
    font-size: 18px;
    line-height: 40px;
    display: block;
    margin-right: 10px;
  }
  .input{
    width: 300px;
  }
  .little-box{
    margin-bottom: 20px;
  } 
</style>