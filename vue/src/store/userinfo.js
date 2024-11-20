import {defineStore} from "pinia";
import {ref} from "vue";
const useUserInfoStore= defineStore("userinfo", ()=>{
    const userinfo = ref({});
    const setUserinfo = (newUserinfo) => {
        userinfo.value = newUserinfo
    }
    const removeUserinfo = () => {
        userinfo.value = {}
    }
    return {userinfo, setUserinfo, removeUserinfo}
},
    {
        persist:true//持久化存储
    }
)
export default useUserInfoStore;
