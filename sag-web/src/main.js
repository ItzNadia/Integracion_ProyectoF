import Vue from 'vue'
import Login from './views/Login.vue'
import router from './router'
import vuetify from '@/plugins/vuetify'

Vue.config.productionTip = false

new Vue({
  router,
  vuetify,
  render: function (h) { return h(Login) }
}).$mount('#app')
