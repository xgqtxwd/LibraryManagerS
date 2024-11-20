import request from '../utils/request.js'
export const userRegister = (registerData) => {
    const params=new  URLSearchParams()
    for (let key in registerData){
        params.append(key,registerData[key])
    }
   return  request.post('/user/register',params)
}
//登录接口函数
export const userLogin = (loginData) => {
    const params=new  URLSearchParams()
    for (let key in loginData){
        params.append(key,loginData[key])
    }
    return  request.post('/user/login',params)
}
//获取用户详细信息
export const getUserInfoService = () => {
    return  request.get('/user/userInfo')
}
//修改个人信息
export const updateUserInfoService = (userInfo) => {
    return  request.put('/user/update',userInfo)
}
//修改头像
export const userAvatarService = (avatarData) => {
    const params=new  URLSearchParams()
    params.append('avatarData',avatarData);
    return  request.patch('/user/avatar?avatar=',+avatarData)
}