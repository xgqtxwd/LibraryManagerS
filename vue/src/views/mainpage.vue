<script setup>
import {
  Management,
  Promotion,
  UserFilled,
  View,
  Setting,
  Refresh,
  User,
  Crop,
  EditPen,
  SwitchButton,
  CaretBottom,
  House
} from '@element-plus/icons-vue'
import avatar from '@/assets/default.png'
import {getUserInfoService} from "@/api/user.js";
import useUserInfoStore from '@/store/userinfo.js';
const userInfoStore= useUserInfoStore();
//调用函数，获取用户信息
const getUserInfo = async () => {
  let res = await getUserInfoService();
  userInfoStore.setUserinfo(res.data);
}
getUserInfo()

//dropDown条目被点击后，回调的函数

import {ElMessage,ElMessageBox} from 'element-plus'
import { useTokenStore } from '@/store/token.js'
import {useRouter} from 'vue-router'
const router = useRouter()
const tokenStore = useTokenStore()
const handleCommand = (command) => {
  if (command === 'logout') {
    //退出登录//实现退出登录
    ElMessageBox.confirm(
        '你确认退出登录码？',
        '温馨提示',
        {
          confirmButtonText: '确认',
          cancelButtonText: '取消',
          type: 'warning',
        }
    )
        .then(async () => {
          //用户点击了确认
          //清空pinia中的token和个人信息
          userInfoStore.info={}
          tokenStore.token=''
          //跳转到登录页
          router.push('/login')
        })
        .catch(() => {
          //用户点击了取消
          ElMessage({
            type: 'info',
            message: '取消退出',
          })
        })
  } else {
    //路由
    router.push('/user/' + command)
  }
}
</script>

<template>
  <el-container class="layout-container">
    <!-- 左侧菜单 -->
    <el-aside width="200px">
      <div class="el-aside__logo"></div>
      <el-menu active-text-color="#ffd04b" background-color="#232323"  text-color="#fff"
               router>
        <el-menu-item index="/home">
          <el-icon>
            <House />
          </el-icon>
          <span>首页</span>
        </el-menu-item>
        <el-menu-item index="/book/category">
          <el-icon>
            <Management />
          </el-icon>
          <span>图书分类</span>
        </el-menu-item>
        <el-sub-menu index="1">
          <template #title >
              <el-icon>
                <Setting />
              </el-icon>
            <span>图书管理</span>
          </template>
          <el-menu-item index="/book/borrow" >
            <el-icon>
              <View />
            </el-icon>
            <span>借阅图书</span>
          </el-menu-item>
          <el-menu-item index="/book/still">
            <el-icon><Refresh /></el-icon>
            <span>归还图书</span>
          </el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="2">
          <template #title>
            <el-icon>
              <UserFilled />
            </el-icon>
            <span>个人中心</span>
          </template>
          <el-menu-item index="/user/info" >
            <el-icon>
              <User />
            </el-icon>
            <span>基本资料</span>
          </el-menu-item>
          <el-menu-item index="/user/avatar">
            <el-icon>
              <Crop />
            </el-icon>
            <span>更换头像</span>
          </el-menu-item>
          <el-menu-item index="/user/resetPassword">
            <el-icon>
              <EditPen />
            </el-icon>
            <span>重置密码</span>
          </el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>
    <!-- 右侧主区域 -->
    <el-container>
      <!-- 头部区域 -->
      <el-header>
        <div>用户昵称：<strong>{{userInfoStore.userinfo.nickname}}</strong></div>
        <el-dropdown placement="bottom-end" @command="handleCommand">
                    <span class="el-dropdown__box">
                        <el-avatar :src="userInfoStore.userinfo.userPic?userInfoStore.userinfo.userPic:avatar" />
                        <el-icon>
                            <CaretBottom />
                        </el-icon>
                    </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="info" :icon="User" >基本资料</el-dropdown-item>
              <el-dropdown-item command="avatar" :icon="Crop" >更换头像</el-dropdown-item>
              <el-dropdown-item command="resetPassword" :icon="EditPen">重置密码</el-dropdown-item>
              <el-dropdown-item command="logout" :icon="SwitchButton">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-header>
      <!-- 中间区域 -->
      <el-main>
          <router-view></router-view>
      </el-main>
      <!-- 底部区域 -->
      <el-footer>图书管理系统 ©2024 Created by 徐贵强</el-footer>
    </el-container>
  </el-container>
</template>

<style lang="scss" scoped>
.layout-container {
  height: 100vh;

  .el-aside {
    background-color: #232323; // 统一侧边栏背景颜色
    color: #fff; // 统一侧边栏文本颜色

    &__logo {
      height: 120px;
      background: url('@/assets/libr.png') no-repeat center / 60px auto;
    }

    .el-menu {
      border-right: none; // 移除边框
      background-color: transparent; // 移除菜单背景色

      .el-menu-item,
      .el-sub-menu__title {
        color: #fff; // 统一菜单项文本颜色
      }

      .el-menu-item:hover,
      .el-sub-menu__title:hover {
        background-color: #303030; // 鼠标悬停时的背景颜色
      }

      .el-menu-item.is-active,
      .el-sub-menu__title.is-active {
        background-color: #ffd04b; // 激活项的背景颜色
        color: #232323; // 激活项的文本颜色
      }
    }
  }

  .el-header {
    background-color: #fff;
    display: flex;
    align-items: center;
    justify-content: space-between;

    .el-dropdown__box {
      display: flex;
      align-items: center;

      .el-icon {
        color: #999;
        margin-left: 10px;
      }

      &:active,
      &:focus {
        outline: none;
      }
    }
  }

  .el-footer {
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 14px;
    color: #666;
    background-color: #fff; // 统一底部背景颜色
  }
}
</style>