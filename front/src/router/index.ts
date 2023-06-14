import {createRouter, createWebHistory, RouteRecordRaw} from 'vue-router'
import store from "@/store";


const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'Login',
    component: () => import('@/views/LoginView.vue'),
    meta: {
      role: ''
    },
    beforeEnter: (to, from, next) => {
      localStorage.removeItem('vuex')
      next()
    },
  },
  {
    path: '/signup',
    name: 'SignUp',
    component: () => import('@/views/SignUpView.vue'),
    meta: {
      role: ''
    }
  },
  {
    path: '/home/:nickName',
    name: 'User',
    component: () => import('@/views/UserView.vue'),
    meta: {
      role: 'ROLE_USER'
    }
  },
  {
    path: '/admin',
    name: 'Admin',
    component: () => import('@/views/AdminView.vue'),
    meta: {
      role: 'ROLE_ADMIN'
    }
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

router.beforeEach((to, from, next) => {
  if (to.path === "/" || to.path === "/signup") {
    localStorage.removeItem('vuex')
    next()
  } else {
    if (store.state.role === 'ROLE_ADMIN' && to.meta.role === store.state.role) {
      next()
    } else if (store.state.role === 'ROLE_USER' && to.meta.role === store.state.role) {
      if (to.params?.nickName === store.state.userData.nickName) {
        next()
      } else {
        next(`/home/${store.state.userData.nickName}`)
      }

    } else {
      next('/')
    }
  }
})


export default router
