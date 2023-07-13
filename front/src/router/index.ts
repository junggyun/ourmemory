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
    },
    {
        path: '/home/:nickName/:groupId',
        name: 'Group',
        component: () => import('@/views/UserView.vue'),
        meta: {
            role: 'ROLE_USER'
        }
    },
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

router.beforeEach((to, from, next) => {
    if (to.name === "Login" || to.name === "SignUp") {
        store.commit('clearStore')
        localStorage.removeItem('vuex')
        next()
    } else if (to.name === "User") {
        store.commit('clearGroup')
        store.commit('setUserGroupRole', "")
        store.commit('setUserGroupId', 0)
        next()
    } else {
        if (store.state.role === 'ROLE_ADMIN' && to.meta.role === store.state.role) {
            next()
        } else if (store.state.role === 'ROLE_USER' && to.meta.role === store.state.role) {
            if (to.params?.nickName === store.state.userData.nickName) {
                if (to.name === "Group") {
                    if (to.params?.groupId === store.state.groupData.id.toString()) {
                        next()
                    } else {
                        next(`/home/${store.state.userData.nickName}`)
                    }
                } else {
                    next()
                }

            } else {
                next(`/home/${store.state.userData.nickName}`)
            }

        } else {
            next('/')
        }
    }
})


export default router
