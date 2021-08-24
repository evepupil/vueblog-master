<template>
    <div>
        <div class="post" v-if="comment">
            <div class="my-post">
                <div class="uesr">
                    <div class="user-img">
                        <img :src=comment.avatar alt="">
                    </div>
                    <span class="username" v-text="comment.nickname">{{UserName}}</span>
                    <p class="time" v-text="comment.time">{{time}}</p>
                    <span class="like">回复您的帖子</span>
                    <span class="post-name"v-text="blogs[index].title">{{PostName}}</span>
                </div>
                <div class="main" v-text="comment.content">
                    <p>{{ReplyContent}}</p>
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
            PostName:'七夕快到了。 给情侣们推荐一些好听的歌',
            ReplyContent:'不会有人没有女朋友吧，不会吧不会吧'
        }
    },
  props:['comment','blogs','index']
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
    .post-name{
        display: inline-block;
        margin-left: 5px;
        font-size: 12px;
        font-weight: bold;
    }
    .main{
        margin-left: 5rem;
    }
    .main p {
        margin-left: 20px;
    }
</style>