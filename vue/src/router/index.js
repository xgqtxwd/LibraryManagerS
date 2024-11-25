import {createRouter,createWebHistory} from 'vue-router'
//导入组件
import loginVue from '@/views/login.vue';
import mainPage from '@/views/mainpage.vue'
import bookStillVue from '@/views/book/BookStill.vue'
import bookCategoryVue from '@/views/book/BookCategory.vue'
import bookBorrowVue from '@/views/book/BookBorrow.vue'
import UserAvatarVue from '@/views/user/UserAvatar.vue'
import UserInfoVue from '@/views/user/UserInfo.vue'
import UserResetPasswordVue from '@/views/user/UserResetPassword.vue'
import HomePage from '@/views/HomePage.vue';
import AnnouncementVue from '@/views/admin/announceM.vue'
import UserManageVue from '@/views/admin/userM.vue'
import OverdueVue from '@/views/admin/overdue.vue'
const routes = [
    {path: '/', component: mainPage,redirect: '/home',
    children:[
        {path:'/home', component: HomePage},
        {path:'/book/category',component:bookCategoryVue},
        {path:'/book/borrow',component:bookBorrowVue},
        {path: '/user/avatar',component:UserAvatarVue},
        {path: '/user/info',component:UserInfoVue},
        {path: '/user/resetPassword',component:UserResetPasswordVue},
        {path:'/book/still',component:bookStillVue},
        {path:'/admin/announce',component:AnnouncementVue},
        {path:'/admin/userMange',component:UserManageVue},
        {path:'/admin/overdue',component:OverdueVue},
    ]
    },
    {path:'/login',component:loginVue},
]
const router=createRouter({
    history:createWebHistory(),
    routes:routes
})
export default router