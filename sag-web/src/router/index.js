import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import Principal from '../views/Principal.vue'
import Hatos from '../views/Hatos.vue'
import Lotes from '../views/Lotes.vue'
import Traspasos from '../views/Traspasos.vue'

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
  {
    path: '/lotes',
    name: 'Lotes',
    component: Lotes
  },
  {
    path: '/traspasos',
    name: 'Traspasos',
    component: Traspasos
  },
]

const router = new VueRouter({
  routes
})

export default router
