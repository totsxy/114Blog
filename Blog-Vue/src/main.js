import Vue from 'vue'
import App from './App'

import Element from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'

import axios from 'axios'
import router from './router'
import storer from './storer'

import './axios'
import './permission'

Vue.use(Element)
Vue.use(mavonEditor)

Vue.prototype.$axios = axios
Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store: storer,
  components: {App},
  template: '<App/>'
})
