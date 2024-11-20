import './assets/main.scss'
import VueRouter from '@/router'
import { createApp } from 'vue'
import {createPinia} from "pinia";
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import locale from 'element-plus/dist/locale/zh-cn.js'
import App from './App.vue'
import router from "@/router";
const pinia=createPinia();
const app = createApp(App);
app.use(ElementPlus,{locale})
app.use(router)
app.use(pinia)
app.use(ElementPlus)
app.mount('#app')
