import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import store from "./store";
import axios from "axios";

import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap-icons/font/bootstrap-icons.css";
import "bootstrap"


const app = createApp(App)
app.config.globalProperties.$store = store;
app.config.globalProperties.$axios = axios;
app.use(router).use(store).mount('#app')
