import router from './router'
import storer from './storer'

// 路由前置拦截
router.beforeEach((to, from, next) => {
  // 定位到页面顶部
  // if (to.path !== '/blogs') {
  //   document.body.scrollTop = 0             // chrome
  //   document.documentElement.scrollTop = 0  // firefox
  //   window.pageYOffset = 0                  // safari
  // }

  // 未登录时访问需要登录才能访问的页面将被自动路由到登录页面
  if (!to.matched.some(record => record.meta.requireAuth) || storer.getters.getAuth) {
    next()
  } else {
    next({path: '/login'})
  }
})
