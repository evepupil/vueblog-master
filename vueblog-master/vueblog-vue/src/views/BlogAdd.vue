<template>
  <div>
    <Header></Header>

    <div class="m-content">

      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="标题" prop="title">
          <el-input v-model="ruleForm.title"></el-input>
        </el-form-item>

        <el-form-item label="摘要" prop="description">
          <el-input type="textarea" v-model="ruleForm.description"></el-input>
        </el-form-item>

        <el-form-item label="内容" prop="content">
          <mavon-editor
              :toolbars="toolbars"
              @imgAdd="handleEditorImgAdd"
              @imgDel="handleEditorImgDel"
              style="height:600px"
              v-model="ruleForm.content"
              @change="change"
              ref=md
          ></mavon-editor>
          <!--          <mavon-editor v-model="ruleForm.content">-->

        </el-form-item>

        <el-form-item>
          <el-button type="primary" :loading="loading"  @click="submitForm('ruleForm')"
                    >立即创建</el-button>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>

    </div>
  </div>
</template>

<script>
import Header from "../components/Header";
export default {
  name: "BlogAdd.vue",
  components: {Header},
  data() {
    return {
      loading:false,
      ruleForm: {

        id: '',
        title: '',
        description: '',
        content: ''
      },
      rules: {
        title: [
          { required: true, message: '请输入标题', trigger: 'blur' },
          { min: 3, max: 25, message: '长度在 3 到 25 个字符', trigger: 'blur' }
        ],
        description: [
          { required: true, message: '请输入摘要', trigger: 'blur' }
        ],
        content: [
          { trequired: true, message: '请输入内容', trigger: 'blur' }
        ]
      }
    };
  },
  methods: {
    handleEditorImgAdd(pos , $file){
      var formdata = new FormData();
      formdata.append('file' , $file);
      this.$axios
          .post("/upload/newarticleimg", formdata)
          .then(res => {
            var url = res.data.data;
            url=encodeURI(url)
            console.log(url)
            this.$refs.md.$img2Url(pos, url);
            //这里就是引用ref = md 然后调用$img2Url方法即可替换地址
          });
    },
    handleEditorImgDel(){
      console.log('handleEditorImgDel');    //我这里没做什么操作，后续我要写上接口，从七牛云CDN删除相应的图片
    },
    submitForm(formName) {
      this.loading=true
      this.$refs[formName].validate((valid) => {
        if (valid) {

          const _this = this
          this.$axios.post('/blog/add', this.ruleForm, {
            headers: {
              "Authorization": localStorage.getItem("token")
            }
          }).then(res => {
            console.log(res)
            _this.$alert('操作成功', '提示', {
              confirmButtonText: '确定',
              callback: action => {
                _this.$router.push("/blogs")
              }
            });

          })

        } else {
          console.log('error submit!!');
          return false;
        }
      });
      this.loading=false
      this.resetForm(formName)
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  },
  created() {
    const blogId = this.$route.params.blogId
    console.log(blogId)
    const _this = this
    if(blogId) {
      this.$axios.get('/blog/' + blogId).then(res => {
        const blog = res.data.data
        _this.ruleForm.id = blog.id
        _this.ruleForm.title = blog.title
        _this.ruleForm.description = blog.description
        _this.ruleForm.content = blog.content
      })
    }

  }
}
</script>

<style scoped>
.m-content {
  text-align: center;
}
</style>