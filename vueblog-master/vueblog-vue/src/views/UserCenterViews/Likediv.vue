<template>
    <div>
        <div class="post">
            <div class="my-post">
                <div class="uesr">
                    <div class="user-img">
                        <img :src=users[index].avatar alt="">
                    </div>
                    <span class="username" v-text="users[index].nickname">{{UserName}}</span>
                    <p class="time" v-text="like.time">{{time}}</p>
                    <p class="like">赞了您的{{blogs[index].type}}</p>
                </div>
                <div class="main" v-text="blogs[index].title">
                    <p>{{PostName}}</p>
                </div>
            </div>
            
            <el-divider></el-divider>
        </div>
    </div>
</template>

<script>
export default {
     data() {
        return {
            UserImgSrc:'https://blog-1257029975.cos.ap-beijing.myqcloud.com/head/QQ%E5%9B%BE%E7%89%8720210530121836.jpg',
            UserName:'叶桐',
            time:'21.08.20',
            PostName:'七夕快到了。 给情侣们推荐一些好听的歌'
        }
    },
  props:['users','like','blogs','index']
}
</script>

<style scoped>
    .my-post{
        width: 100%;
        height: 100px;
        transition: all .3s;
        position: relative;
        box-sizing: border-box;
        margin-top: 20px;
    }
    .user{
        width: 100%;
        height: 20%;
    }
    .user-img{
        width: 5rem;
        height: 5rem;
        float: left;
        margin-right: 20px;
    }
    .user-img img{
        width: 100%;
        height: 100%;
        border-radius: 50%;
        
    }
    .username{
        display: block;
        position: relative;
        top: 10px;
    }
    .time{
        margin-top: 15px;
        font-size: 12px;
        color: #99a2aa;
        margin-bottom: 0;
    }
    .like{
        font-size: 12px;
        margin-top: 5px;
    }
    .main{
        margin-left: 5rem;
    }
    .main p {
        margin-left: 20px;
    }
</style>