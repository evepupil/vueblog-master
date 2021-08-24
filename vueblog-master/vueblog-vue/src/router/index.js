import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import Blogs from '../views/Blogs.vue'
import BlogEdit from '../views/BlogEdit.vue'
import BlogDetail from '../views/BlogDetail.vue'
import SearchResult from "@/views/SearchResult";
import Register from "@/views/Register";
import BlogAdd from "@/views/BlogAdd";
import UserCenter from "@/views/UserCenterViews/UserCenter";
import MyPost from "@/views/UserCenterViews/MyPost";
import Collect from "@/views/UserCenterViews/Collect"
import Like from "@/views/UserCenterViews/Like"
import Reply from "@/views/UserCenterViews/Reply"

Vue.use(VueRouter)
mode:'hash'
const routes = [
  {
    path: '/register',
    name:'Register',
    component: Register
  },
  {
    path: '/searchResult/:keywords',
    name:'Search',
    component: SearchResult
  },
  {
    path: '/',
    name: 'Index',
    redirect: {name: "Blogs"}
  },
  {
    path: '/blogs',
    name: 'Blogs',
    component: Blogs,

  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  // {
  //   path: '/blog/edit',
  //   name: 'BlogEdit',
  //   component: BlogEdit,
  //   meta: {
  //     requireAuth: true
  //   }
  // },
  {
    path: '/blog/add',
    name: 'BlogAdd',
    component: BlogAdd,
    meta: {
      requireAuth: true
    }
  },
  {
    path: '/blog/:blogId',
    name: 'BlogDetail',
    components: {
      blogDetail: BlogDetail
    }
  },
  {
    path: '/blog/:blogId/edit',
    name: 'BlogEdit',
    component: BlogEdit,
    meta: {
      requireAuth: true
    }
  },
  {
    path:'/UserCenter/:userid',
    name:'UserCenter',
    component: UserCenter,
    //props:true
  },
  {
    path:'/MyPost/:userid',
    name:'MyPost',
    component:MyPost,
  },
  {
    path:'/Collect/:userid',
    name:'Collect',
    component:Collect
  },
  {
    path:'/Like/:userid',
    component:Like
  },
  {
    path:'/Reply/:userid',
    component:Reply
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
