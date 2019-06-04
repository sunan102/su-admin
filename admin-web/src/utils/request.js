import axios from 'axios'
import { Message, MessageBox } from 'element-ui'
import store from '../store'
import { getToken } from '@/utils/auth'
import { isNotEmpty } from '@/utils'
import qs from "qs";
import errorCode from '@/const/errorCode'
import router from '@/router'

// 创建axios实例


// var BASE_API = '';
// if (process.env.NODE_ENV == 'production') {
//     BASE_API = 'http://39.98.78.46/admin-api';
// }else{
//     BASE_API = 'http://127.0.0.1:8080/api';
// }
const service = axios.create({
    baseURL: process.env.VUE_APP_BASE_API, // api的base_url
    timeout: 60 * 1000 // 请求超时时间
})

// request拦截器
service.interceptors.request.use(config => {
    //   if (store.getters.token) {
    //     config.headers['X-Token'] = getToken() // 让每个请求携带自定义token 请根据实际情况自行修改
    //   };
    if (store.getters.token) {
        config.headers.Authorization = `Bearer ${getToken()}`;
    }
    return config
}, error => {
    // Do something with request error
    console.log(error) // for debug
    Promise.reject(error)
})

// respone拦截器
service.interceptors.response.use(
    response => {
        const res = response.data
        if (response.headers['content-type'] == 'application/json;charset=UTF-8') {
            if (isNotEmpty(res) && 'flag' in res) {
                /**
                 * 自定义返回flag,msg
                 */
                if (res.msg != null && res.msg != null) {
                    let message_type = res.flag === 0 ? 'success' : 'error';
                    Message({
                        message: res.msg,
                        type: message_type
                    })
                }
            }
            return response.data
        }else{
            return response;
        }
    },
    error => {
        const errMsg = error.toString()
        const code = error.response.status
        console.log(error.response)
        let msg = errorCode[code] || errorCode['default'];
        if (error.response.data && error.response.data.flag) {
            msg = error.response.data.msg;
        }
        Message({
            message: msg,
            type: 'error'
        })

        if (parseInt(code) === 401) {
            store.dispatch('FedLogOut').then(() => {
                router.push({ path: '/login' })
            })
        }
        return Promise.reject(new Error(error))
    }
)

export default service
