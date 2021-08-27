<template>
    <div>
        <div class="header">
            <el-page-header @back="goBack" content="重置密码">
            </el-page-header>
        </div>
        
        <div class="title">
            <el-divider>重置密码</el-divider>
            <p>Password Reset</p>
        </div>
        <div class="main">
          <el-form ref="form" label-width="80px" :model="form" :rules="rules">
                <el-form-item type='email' label="邮箱地址" prop="email">
                    <el-input 
                        placeholder="请输入邮箱地址" 
                         v-model="form.email"
                    ></el-input>
                </el-form-item>

                <el-form-item label="验证码" prop="code">
                <el-input type='text' placeholder="请输入验证码" style="width:270px;margin-right:17px;" v-model="form.code" maxlength="4"
                    oninput="value=value.replace(/[^0-9.]/g,'')"
                ></el-input>
                <el-button @click="sendMsg" :disabled="canClick">{{ content }}</el-button>
                </el-form-item>

                 <el-form-item label="重置密码" prop="password">
                    <el-input placeholder="请输入新密码" v-model="form.password" type="password" show-password></el-input>
                </el-form-item>

                <el-form-item label="确认密码" prop="replacepassword">
                    <el-input placeholder="请输入与刚才相同的密码" v-model="form.replacepassword" type="password" show-password></el-input>
                </el-form-item>
                <el-button type="primary" style="width:60%;margin-left:120px" @click="changepw">重置密码</el-button>
        </el-form>
        </div>
    </div>
    
    
</template>

<script>

export default {
    data(){
            var validatePass2 = (rule,value, callback) => {
            if (value === '') {
            callback(new Error('请再次输入密码'));
            } else if (value !== this.form.password) {
            callback(new Error('两次输入密码不一致!'));
            } else {
            callback();
            }
        };
        return{
                content:'发送邮件',
                totalTime: 60,
                canClick: false,
            form: {
                email:'',
                code:'',
                password:'',
                replacepassword:'',
            },
            rules:{
                email:[
                    {required: true,message:'请输入您绑定的邮箱',tigger:'blur'}
                ],
                code:[
                    {required: true,message:'请输入验证码',tigger:'blur'},
                    {min:4,message:'验证码长度错误',tigger:'change'},
                ],
                password:[
                    {required: true,message:'请输入新密码',tigger:'blur'},
                    {min:8,max:16,message:'密码长度在8-16之间',tigger:'blur'}
                ],
                replacepassword:[
                    { validator: validatePass2, trigger: 'blur' }
                ],
            }
        }
    },
    methods:{
      changepw(){
        let form=new FormData();
        form.append("email",this.form.email)
        form.append("code",this.form.code)
        form.append("newPw",this.form.password)
        this.$axios.post("/changePw" ,form).then(res => {
          if(res.data.data=="Ok")
            this.$message.success("修改成功")
            this.$router.push('/login')
        })
      },
        goBack(){
            this.$router.push('/login')
        },
        sendMsg() {
        let form=new FormData
          form.append("email",this.form.email)
          form.append("state",1)
          this.$axios.post("/code" ,form).then(res => {
            if(res.data.data=="Ok")
              this.$message.success("验证码发送成功")
          })
            if (this.canClick) return
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
.title{
    margin-top: 30px;
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
.header{
    margin-top: 20px;
}
</style>