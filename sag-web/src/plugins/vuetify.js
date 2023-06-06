import Vue from 'vue'
import Vuetify from 'vuetify';
import VueSession from "vue-session";
import VueToast from 'vue-toast-notification';
import "vuetify/dist/vuetify.min.css"
import '@mdi/font/css/materialdesignicons.css'
import 'vue-toast-notification/dist/theme-bootstrap.css';

Vue.use(Vuetify)
Vue.use(VueToast)
Vue.use(VueSession)


const opts = {}

export default new Vuetify(opts)