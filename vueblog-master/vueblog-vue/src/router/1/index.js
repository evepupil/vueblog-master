import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import Blogs from '../views/Blogs.vue'
import BlogEdit from '../views/BlogEdit.vue'
import BlogDetail from '../views/BlogDetail.vue'
import SearchResult from "@/views/SearchResult";
import Register from "@/views/Register";
import UserCenter from "@/views/UserCenter";
import MyPost from "@/views/MyPost";
import Collect from "@/views/Collect"
import Like from "@/views/Like"
import Reply from "@/views/Reply"
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
  {
    path: '/blog/add',
    name: 'BlogAdd',
    component: BlogEdit,
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
    path:'/UserCenter',
    name:'UserCenter',
    component: UserCenter,
  },
  {
    path:'/MyPost',
    name:'MyPost',
    component:MyPost,
  },
  {
    path:'/Collect',
    name:'Collect',
    component:Collect
  },
  {
    path:'/Like',
    component:Like
  },
  {
    path:'/Reply',
    component:Reply
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
