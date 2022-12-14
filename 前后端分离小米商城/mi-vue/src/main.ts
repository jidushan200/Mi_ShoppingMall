import { createApp } from 'vue'
import App from './App.vue'
import router from "./router";
import store from "./store/store";
import axios from "axios";
import 'element-plus/es/components/message/style/css'

axios.defaults.baseURL='api'
axios.defaults.withCredentials = true
const app=createApp(App)
app.use(router)
app.use(store)
app.mount('#app')
