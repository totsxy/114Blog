import axios from 'axios'
import Element from 'element-ui'
import router from './router'
import storer from './storer'

// 默认域名
axios.defaults.baseURL = 'http://47.93.249.227:8888'

// 前置拦截
axios.interceptors.request.use(config => {
  return config
})

// 错误处理
function handler(exception) {
  Element.Message({message: exception, type: 'error'})
  return Promise.reject(exception)
}

// 后置拦截
axios.interceptors.response.use(result => {
  let res = result.data
  return res.code === 200 ? result : handler(res.desc)
}, error => {
  let resp = error.response
  if (!error || !resp) {
    return handler('未知的错误')
  }
  if (resp.status === 500) {
    return handler('服务器异常')
  }
  if (resp.status === 401) {
    storer.commit('xxxx')
    router.push('/login')
  }
  return handler(resp.data.desc)
})
