import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import Principal from '../views/Principal.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/',
    name: 'Principal',
    component: Principal
  },
]

const router = new VueRouter({
  routes
})

export default router
