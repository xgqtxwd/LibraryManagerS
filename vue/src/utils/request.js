//定制请求的实例

//导入axios  npm install axios
import axios from 'axios';
//定义一个变量,记录公共的前缀  ,  baseURL
const baseURL = '/api';
const instance = axios.create({baseURL})
import {useRouter} from 'vue-router'
import {ElMessage} from "element-plus"
import {useTokenStore} from "@/store/token.js";
//添加请求拦截器
instance.interceptors.request.use((config) => {


//请求前的回调
    const tokenStore = useTokenStore();
    if (tokenStore.token) {
        config.headers.Authorization = tokenStore.token;
    }
    return config;
},

        (err) => {
            //请求错误回调
           Promise.reject(err);
        }
)
//添加响应拦截器
import router from '@/router'
instance.interceptors.response.use(
    result => {
        //统一处理返回的结果// 成功的状态
        if (result.data.code === 0) {
            return result.data;
        }
        //失败的状态
        // 1. 提示错误信息

        ElMessage.error(result.data.message ? result.data.message : '服务异常');
        //异步的状态转化成失败的状态
        return Promise.reject(result.data);
    },
    err => {
        if (err.status === 401) {
            ElMessage.error('还未登录，请先登录')
            router.push('/login')
        }else { alert('服务异常')}

        return Promise.reject(err);//异步的状态转化成失败的状态
    }
)

export default instance;