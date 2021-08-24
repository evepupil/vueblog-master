<template>

  <div>
    <Header></Header>
    <div class="person-center">
      <div class="title">
        <div class="user" v-if=userinfo>
          <div class="user-img">
            <img  :src=userinfo.avatar alt="">
          </div>
          <div class="user-intor">
            <p class="username" v-text="userinfo.nickname">xxx</p>
            <p class="intor" v-text="userinfo.sign">xxx</p>
          </div>
        </div>
      </div>
      <div class="main">
        <ul class="list">
          <li v-show=isOwn v-for="(list,index) in lists1" :key='index' @click='num = index'>
            <router-link  :to="list.href+'/'+userid" tag='div'>{{list.name}}</router-link>
            </li>
        </ul>
        <ul class="list">
          <li v-show=!isOwn v-for="(list,index) in lists2" :key='index' @click='num = index'>
            <router-link :to="list.href+'/'+userid" tag='div'>{{list.name}}</router-link>
          </li>
        </ul>
      </div>
    </div>

  </div>
</template>

<script>
import Header from "@/components/Header";
export default {
  name: "UserCenterCom",
  components: {Header},
  methods: {
        log(index){
            console.log(index);
    },
    getUserid(){
          return this.userid
    }
  },
  props:['isOwn','userid','userinfo'],
  data(){
    return{
        lists1:[
          {
            name:'个人信息',
            href:'/UserCenter'
          },
          {
            name:'我的帖子',
            href:'/MyPost'
          },
          {
            name:'我的收藏',
            href:'/Collect'
          },
          {
            name:'收到的赞',
            href:'/Like'
          },
          {
            name:'回复我的',
            href:'/Reply'
          }
        ],
      lists2:[
        {
          name:'个人信息',
          href:'/UserCenter'
        },
        {
          name:'他的帖子',
          href:'/MyPost'
        }
      ],
        url:['/UserCenter'],
        isfoucs:true,
        num:0,
    }
  }
}
</script>

<style scoped>
.person-center{
  margin-bottom: 5px;
}
  *{
    margin: 0;
    padding: 0;
    font-family: "Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","微软雅黑",Arial,sans-serif;
  }
  .title{
    overflow: hidden;
    width: 100%;
    height: 300px;
    background-image: url(https://blog-1257029975.cos.ap-beijing.myqcloud.com/bg/89164923_p0.png);
    background-size: cover;
    box-shadow: -10px -106px 266px 0px rgb(0 0 0 / 50%) inset;
  }
  .user{
    margin-top: 150px;
    margin-left: 50px;
    width: 100%;
    height: 40%;
  }
  .user-img{
    width: 6rem;
    height: 6rem;
    float: left;
    margin-right: 50px;
  }
  .user-img img{
    width: 100%;
    height: 100%;
    border-radius: 50%;
    border: 5px solid rgba(255, 255, 255, 0.443);
  }
  .user-intor{
    width: 80%;
    height: 100%;
  }
  .username{
     color: white;
     font-size: 25px;
     font-weight: bold;
     position: relative;
     top: 20px;
  }
  .intor{
    color: white;
     position: relative;
     top: 30px;
  }
  .list{
    width: 100%;
    height: 50px;
    list-style-type: none;
    box-sizing: border-box;
  }
  .list li{
    float: left;
    width: 10%;
    text-align: center;
    height: 100%;
    line-height: 50px;
    cursor: pointer;
    transition: all .3s;
  }
  .list li:hover{
    background-color: #343a40!important;
    color: white;
  }
  .list li:hover a{
    color: #fff;
  }
  .foucs{
    background-color: #343a40!important;
    color: white;
  }
  .foucs a{
    color: white;
  }
  a{
    text-decoration: none;
    color: black;
  }
  .router-link-active{
    background-color: #343a40!important;
    color: white;
  }
</style>