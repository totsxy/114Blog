import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    auth: localStorage.getItem('auth'),
    user: localStorage.getItem('user')
      ? JSON.parse(localStorage.getItem('user'))
      : null,
  },
  // Setter functions
  mutations: {
    setAuth: (state, auth) => {
      state.auth = auth;
      localStorage.setItem('auth', auth);
    },
    setUser: (state, user) => {
      state.user = user;
      localStorage.setItem('user', JSON.stringify(user));
    },
    xxxx: (state) => {
      state.auth = '';
      localStorage.setItem('auth', '');
      state.user = {};
      localStorage.setItem('user', '');
    },
  },
  // Getter functions
  getters: {
    getAuth: state => {
      return state.auth
    },
    getUser: state => {
      return state.user
    },
  }
})
