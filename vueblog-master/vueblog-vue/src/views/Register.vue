<template>
  <div>
    <el-container>
      
      <el-main>
        <div class="header">
            <el-page-header @back="goBack" content="注册账号">
            </el-page-header>
        </div>
         <div class="title">
            <el-divider>注册账号</el-divider>
            <p>Register</p>
        </div>
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm center">
          <el-form-item label="昵称" prop="nickname">
            <el-input placeholder="请输入昵称" v-model="ruleForm.nickname"></el-input>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input placeholder="请绑定邮箱" v-model="ruleForm.email"></el-input>
          </el-form-item>
           <el-form-item label="验证码" prop="code">
                <el-input type='text' placeholder="请输入验证码" style="width:270px;margin-right:17px;" v-model="ruleForm.code" maxlength="4"
                    oninput="value=value.replace(/[^0-9.]/g,'')"
                ></el-input>
                <el-button @click="sendMsg" :disabled="canClick">{{ content }}</el-button>
                </el-form-item>
          <el-form-item label="用户名" prop="username">
            <el-input v-model="ruleForm.username"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input placeholder="请输入密码" type="password" v-model="ruleForm.password" show-password></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="password1">
            <el-input placeholder="请输入密码" type="password" v-model="ruleForm.password1" show-password></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')">注册</el-button>
<!--            <el-button @click="resetForm('ruleForm')">注册</el-button>-->
          </el-form-item>
        </el-form>

      </el-main>
    </el-container>

  </div>
</template>

<script>
export default {
  name: "Register",
  data() {
    var validatePass2 = (rule, value, callback) => {
      console.log(value)
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.ruleForm.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      content:'发送邮件',
      totalTime: 60,
      canClick: false,
      ruleForm: {
        email:'',
        nickname:'',
        username: '',
        password: '',
        password1:'',
        code:'',
      },
      res:{
        username:'',
        password:''
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur' }
        ],
        password1: [
          { validator: validatePass2, trigger: 'blur' },
          {required: true,message:'请再次输入密码',trigger:'blur'}
        ],
        nickname:[
          {required: true,message:'请输入昵称',trigger:'blur'},
          {min:2,max:16,message:"昵称长度在2-16之间",trigger:'change'}
        ],
        email:[
          {
          required: true,message:'请输入邮箱',trigger:'blur'
          }
        ],
        code:[
                    {required: true,message:'请输入验证码',tigger:'blur'},
              ],

      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const _this = this
          let res=new FormData
          res.append("nickname",this.ruleForm.nickname)
          res.append("email",this.ruleForm.email)
          res.append("code",this.ruleForm.code)
          res.append("username",this.ruleForm.username)
          res.append("password",this.ruleForm.password)
          this.$axios.post('/register',res).then(res => {
            console.log(res.data)
            const jwt = res.headers['authorization']
            const userInfo = res.data.data
            this.$message.success("注册成功")
            // 把数据共享出去
            _this.$store.commit("SET_TOKEN", jwt)
            _this.$store.commit("SET_USERINFO", userInfo)

            // 获取
            console.log(_this.$store.getters.getUser)

            _this.$router.push("/login")
          })

        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    goBack(){
            this.$router.push('/login')
    },
    sendMsg() {
      let form=new FormData
      form.append("email",this.ruleForm.email)
      form.append("state",0)
      this.$axios.post("/code" ,form).then(res => {
        if(res.data.data=="Ok")
          this.$message.success("验证码发送成功")
      })
    if (this.canClick) 
    return
    this.canClick = true
    this.content = this.totalTime + 's后重新'
    let clock = window.setInterval(() => {
      this.totalTime--
      this.content = this.totalTime + 's后重新'
      if (this.totalTime < 0) {
        window.clearInterval(clock)
        this.content = '发送邮件'
        this.totalTime = 60
        this.canClick = false
    }
  }, 1000)
}

  }
}
</script>

<style scoped>
.el-header, .el-footer {
  background-color: #B3C0D1;
  color: #333;
  text-align: center;
  line-height: 60px;
}

.el-aside {
  background-color: #D3DCE6;
  color: #333;
  text-align: center;
  line-height: 200px;
}

.el-main {
  /*background-color: #E9EEF3;*/
  color: #333;
  text-align: center;
}

body > .el-container {
  margin-bottom: 40px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}

.mlogo {
  height: 60%;
  margin-top: 10px;
}

.demo-ruleForm {
  max-width: 500px;
  margin: 0 auto;
}
.title{
    margin-top: 30px;
    margin-bottom: 30px;
}
.title p{
    font-size: 20px;
    text-align: center;
    letter-spacing: .5rem;
}
.el-divider__text{
    font-size: 24px;
    letter-spacing: 1.5rem;
}
.main{
    width: 50%;
    margin: 50px auto;
}
.center{
  margin-right: 28%;
}
</style>