<script setup>
import { User, Lock } from '@element-plus/icons-vue'
import { ref } from 'vue'
import {reactive} from "vue";
//控制注册与登录表单的显示， 默认显示注册
const isRegister = ref(false)
//用于注册的数据模型
const registerData = ref({
  username: '',
  password: '',
  rePassword: ''
})
const rePasswordValid = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请再次确认密码'))
  }
 else if (value!== registerData.value.password) {
    callback(new Error('两次输入密码不一致'))
  }
  else {
    callback()
  }
}
//用于注册的表单校验模型
const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 5, max: 16, message: '用户名的长度必须为5~16位', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 5, max: 16, message: '密码长度必须为5~16位', trigger: 'blur' }
  ],
  rePassword: [
    { validator: rePasswordValid, trigger: 'blur' }
  ]
}
//调用后台接口完成注册
import {ElMessage} from "element-plus"
import {userRegister} from "@/api/user.js";
const register = async () => {
 let res =await userRegister(registerData.value);
ElMessage.success('注册成功')
}
//登录函数
import {useRouter} from "vue-router";
import {userLogin} from "@/api/user.js";
const router = useRouter();
import {useTokenStore} from "@/store/token.js";
const tokenStore = useTokenStore();
const login = async () => {
  let res =await userLogin(registerData.value);
  ElMessage.success('登录成功')
  //登录成功后储存token
  tokenStore.setToken(res.data);
//跳转到主页
  router.push('/');
}




// import {userRegister} from "@/api/user.js";
//
// const register = async () => {
//   let res =await userRegister(registerData.value);
//   if(res.code===1){
//     alert(res.message?res.message:'注册成功')
//   }else{
//     alert('注册失败，用户名已经存在')
//   }
// }

//定义函数，防止注册表单返回登录带有数据
const logout= () => {
  registerData.value = {
    username: '',
    password: '',
    rePassword: ''
  }
}
</script>

<template>
  <el-row class="login-page">·
    <el-col :span="12" class="bg"></el-col>
    <el-col :span="6" :offset="3" class="form" >
      <!-- 注册表单 -->
      <el-form ref="form" size="large" autocomplete="off" v-if="isRegister" :model="registerData" :rules="rules" >
        <el-form-item>
          <h1>注册</h1>
        </el-form-item>
        <el-form-item prop="username" >
          <el-input :prefix-icon="User" placeholder="请输入用户名" v-model="registerData.username"></el-input>
        </el-form-item>
        <el-form-item prop="password" >
          <el-input :prefix-icon="Lock" type="password" placeholder="请输入密码" v-model="registerData.password"></el-input>
        </el-form-item>
        <el-form-item prop="rePassword" >
          <el-input :prefix-icon="Lock" type="password" placeholder="请输入再次密码" v-model="registerData.rePassword"></el-input>
        </el-form-item>
        <!-- 注册按钮 -->
        <el-form-item>
          <el-button class="button" type="primary" auto-insert-space @click="register">
            注册
          </el-button>
        </el-form-item>
        <el-form-item class="flex">
          <el-link type="info" :underline="false" @click="isRegister = false;logout()">
            ← 返回
          </el-link>
        </el-form-item>
      </el-form>
      <!-- 登录表单 -->
      <el-form ref="form" size="large" autocomplete="off" v-else :model="registerData" :rules="rules" >
        <el-form-item >
          <h1>图书管理系统登录</h1>
        </el-form-item>
        <el-form-item prop="username">
          <el-input :prefix-icon="User" placeholder="请输入用户名" v-model="registerData.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input name="password" :prefix-icon="Lock" type="password" placeholder="请输入密码" v-model="registerData.password"></el-input>
        </el-form-item>
        <el-form-item class="flex">
          <div class="flex">
            <el-checkbox>记住我</el-checkbox>
            <el-link type="primary" :underline="false">忘记密码？</el-link>
          </div>
        </el-form-item>
        <!-- 登录按钮 -->
        <el-form-item>
          <el-button class="button" type="primary" auto-insert-space @click="login">登录</el-button>
        </el-form-item>
        <el-form-item class="flex">
          <el-link type="info" :underline="false" @click="isRegister = true;logout()">
            注册 →
          </el-link>
        </el-form-item>
      </el-form>
    </el-col>
  </el-row>
</template>

<style lang="scss" scoped>
/* 样式 */
.login-page {
  width: 100vw;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;
  background-color: #f0f2f5;

  /* 设置背景图片 */
  background-image: url('@/assets/login.jpg'); /* 确保路径正确 */
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
}

.form-container {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
}

.form {
  display: flex;
  flex-direction: column;
  background-color: rgba(255, 255, 255, 0.9); /* 半透明背景 */
  padding: 30px;
  border-radius: 8px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 400px;

  .button {
    width: 100%;
  }

  .flex {
    display: flex;
    justify-content: space-between;
    width: 100%;
  }
}
</style>
