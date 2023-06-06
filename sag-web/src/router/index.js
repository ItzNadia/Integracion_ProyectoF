import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import Principal from '../views/Principal.vue'
import Hatos from '../views/Hatos.vue'

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
  {
    path: '/hatos',
    name: 'Hatos',
    component: Hatos
  },
]

const router = new VueRouter({
  routes
})

export default router
