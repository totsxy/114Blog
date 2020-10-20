import Vue from 'vue'
import Router from 'vue-router'
import Login from '../views/user/Login'
import Register from '../views/user/Register'
import Blogs from '../views/blog/Blogs'
import Modify from '../views/blog/Modify'
import Detail from '../views/blog/Detail'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Index',
      redirect: {name: 'Blogs'}
    },
    {
      path: '/blogs',
      name: 'Blogs',
      component: Blogs,
      meta: {
        hasHeader: true,
        keepAlive: true
      }
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/register',
      name: 'Register',
      component: Register
    },
    {
      path: '/blog/add',
      name: 'Create',
      component: Modify,
      meta: {
        hasHeader: true,
        keepAlive: true,
        requireAuth: true
      },
    },
    {
      path: '/blog/:blogId',
      name: 'Detail',
      component: Detail,
      meta: {
        hasHeader: true,
        keepAlive: true
      }
    },
    {
      path: '/blog/:blogId/update',
      name: 'Modify',
      component: Modify,
      meta: {
        hasHeader: true,
        keepAlive: true,
        requireAuth: true
      },
    }
  ]
})
