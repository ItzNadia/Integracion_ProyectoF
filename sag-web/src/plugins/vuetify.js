import Vue from 'vue'
import Vuetify from 'vuetify';
import "vuetify/dist/vuetify.min.css"
import '@mdi/font/css/materialdesignicons.css'
import VueSession from "vue-session";

Vue.use(Vuetify)
Vue.use(VueSession)


const opts = {}

export default new Vuetify(opts)