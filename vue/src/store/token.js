import {defineStore} from "pinia";
import {ref} from "vue";
//使用pinia状态管理
export const  useTokenStore
    = defineStore('token', ()=> {
        const token = ref('');
        const setToken = (newToken) => {
            token.value = newToken
        }
        const removeToken = () => {
            token.value = ''
        }
        return {token, setToken, removeToken}
    }
    ,
    {
         persist:true//持久化存储
        }

        )