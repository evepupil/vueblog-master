<template>
  <div>
    <el-container>
      
      <el-main>
         <div class="header">
            <el-page-header @back="goBack" content="登陆账号">
            </el-page-header>
        </div>
         <div class="title">
            <el-divider>登陆账号</el-divider>
            <p>Login</p>
        </div>
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm center">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="ruleForm.username"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input placeholder="请输入密码" type="password" v-model="ruleForm.password" show-password class="password"></el-input>
            <span class="forge" @click="forgePassword">忘记密码?</span>
          </el-form-item>
          
          <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')">登录</el-button>
            <el-button @click="resetForm()">注册</el-button>
            
          </el-form-item>
        </el-form>

      </el-main>
    </el-container>

  </div>
</template>

<script>
  export default {
    name: "Login",
    data() {
      return {
        ruleForm: {
          username: '',
          password: ''
        },
        rules: {
          username: [
            { required: true, message: '请输入用户名', trigger: 'blur' },
            { min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '请输入密码', trigger: 'change' },
			  { min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur' }
          ]
        }
      };
    },
    methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            const _this = this
            this.$axios.post('/login', this.ruleForm).then(res => {

              console.log(res.data)
              const jwt = res.headers['authorization']
              const userInfo = res.data.data

              // 把数据共享出去
              _this.$store.commit("SET_TOKEN", jwt)
              _this.$store.commit("SET_USERINFO", userInfo)

              // 获取
              console.log(_this.$store.getters.getUser)

              _this.$router.push("/blogs")
            })

          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      resetForm() {
        this.$router.push("/register")
      },
      forgePassword(){
        this.$router.push("/ChangePassword")
      },
      goBack(){
        this.$router.push('/blogs')
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
  .forge{
    font-size: 12px;
    color: #B3C0D1;
    cursor: pointer;
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
.center{
  margin-right: 28%;
}
</style>