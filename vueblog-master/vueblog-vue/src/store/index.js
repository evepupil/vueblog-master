import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token: '',
    userInfo: JSON.parse(sessionStorage.getItem("userInfo")),
    visitUser:null,
    isOwn:false,
  },
  mutations: {
    SET_ISOWN:(state,isOwn)=>{
      state.isOwn
      sessionStorage.setItem("isOwn",isOwn)
    },
    SET_VISIT:(state,visitUser)=>{
      state.visitUser=visitUser
      sessionStorage.setItem("visitUser",visitUser)
    },
    // set
    SET_TOKEN: (state, token) => {
      state.token = token
      localStorage.setItem("token", token)
    },
    SET_USERINFO: (state, userInfo) => {
      state.userInfo = userInfo
      sessionStorage.setItem("userInfo", JSON.stringify(userInfo))
    },
    REMOVE_INFO: (state) => {
      state.token = ''
      state.userInfo = {}
      localStorage.setItem("token", '')
      sessionStorage.setItem("userInfo", JSON.stringify(''))
    }

  },
  getters: {
    // get
    getUser: state => {
      return state.userInfo
    },
    getVisit:state => {
      return state.visitUser
    },
    getIsOwn:state => {
      return this.state.isOwn
    },

  },
  actions: {
  },
  modules: {
  }
})
