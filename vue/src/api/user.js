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
    return  request.patch('/user/avatar',params)
}
export const getUsers = () => {
  return request.get('/home/admin/users');
};

export const addUser = (user) => {
  return request.post('/home/admin/users', user);
};

export const updateUser = (user) => {
  return request.put(`/home/admin/users/${user.id}`, user);
};

export const deleteUser = (id) => {
  return request.delete(`/home/admin/users/${id}`);
};

export const changeUserPassword = (id, newPassword) => {
  const params = new URLSearchParams();
  params.append('newPassword', newPassword);
  return request.put(`/home/admin/users/${id}/password`, params);
};