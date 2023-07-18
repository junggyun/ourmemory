import {createRouter, createWebHistory, RouteRecordRaw} from 'vue-router'
import store from "@/store";

// const routes: Array<RouteRecordRaw> = [
//     {
//         path: '/',
//         name: 'Login',
//         component: () => import('@/views/LoginView.vue'),
//         meta: {
//             role: ''
//         },
//         beforeEnter: (to, from, next) => {
//             localStorage.removeItem('vuex')
//             next()
//         },
//     },
//     {
//         path: '/signup',
//         name: 'SignUp',
//         component: () => import('@/views/SignUpView.vue'),
//         meta: {
//             role: ''
//         }
//     },
//     {
//         path: '/home/:nickName',
//         name: 'User',
//         component: () => import('@/views/UserView.vue'),
//         meta: {
//             role: 'ROLE_USER'
//         }
//     },
//     {
//         path: '/admin',
//         name: 'Admin',
//         component: () => import('@/views/AdminView.vue'),
//         meta: {
//             role: 'ROLE_ADMIN'
//         }
//     },
//     {
//         path: '/home/:nickName/:groupId',
//         name: 'Group',
//         component: () => import('@/views/UserView.vue'),
//         meta: {
//             role: 'ROLE_USER'
//         }
//     },
// ]

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
        },
        beforeEnter: (to, from, next) => {
            localStorage.removeItem('vuex')
            next()
        },
    },
    {
        path: '/:userId/home',
        name: 'GroupList',
        component: () => import('@/views/GroupListView.vue'),
        meta: {
            role: 'ROLE_USER'
        }
    },
    {
        path: '/:userId/userinfo',
        name: 'UserInfo',
        component: () => import('@/views/UserInfoView.vue'),
        meta: {
            role: 'ROLE_USER'
        }
    },
    {
        path: '/:userId/:groupId',
        name: 'Group',
        component: () => import('@/views/GroupView.vue'),
        meta: {
            role: 'ROLE_USER'
        }
    },
    {
        path: '/:userId/:groupId/post',
        name: 'CreatePost',
        component: () => import('@/views/CreatePostView.vue'),
        meta: {
            role: 'ROLE_USER'
        }
    },
    {
        path: '/:userId/:groupId/:postId',
        name: 'Post',
        component: () => import('@/views/PostView.vue'),
        meta: {
            role: 'ROLE_USER'
        }
    },
    {
        path: '/:userId/:groupId/:postId/edit',
        name: 'EditPost',
        component: () => import('@/views/EditPostView.vue'),
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
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes,
    scrollBehavior() {
        return {top: 0}
    }
})

router.beforeEach((to, from, next) => {
    if (to.name === "Login" || to.name === "SignUp") {
        next()
    } else if (store.state.userId == 0) {
        next("/");
    } else if (to.params?.userId !== store.state.userId.toString()) {
        next(`/${store.state.userId}/home`);
    } else {
        next();
    }

})

// router.beforeEach((to, from, next) => {
//     if (to.name === "Login" || to.name === "SignUp") {
//         store.commit('clearStore')
//         localStorage.removeItem('vuex')
//         next()
//     }
//     if (store.state.userId == 0) {
//         next("/")
//     }
//     if (to.params?.userId !== store.state.userId.toString()) {
//         next(`${store.state.userId}/home`)
//     }
//     if (to.name === "GroupList") {
//         store.commit('clearGroup');
//         store.commit('clearPost')
//         store.commit('setUserGroupId', 0)
//         store.commit('setUserGroupRole', "")
//         next()
//     }
//     if (to.name === "UserInfo") {
//         next()
//     }
// })


// router.beforeEach((to, from, next) => {
//     if (to.name === "Login" || to.name === "SignUp") {
//         store.commit('clearStore')
//         localStorage.removeItem('vuex')
//         next()
//     } else if (to.name === "GroupList") {
//         store.commit('clearGroup')
//         store.commit('setUserGroupRole', "")
//         store.commit('setUserGroupId', 0)
//         next()
//     } else {
//         if (store.state.role === 'ROLE_ADMIN' && to.meta.role === store.state.role) {
//             next()
//         } else if (store.state.role === 'ROLE_USER' && to.meta.role === store.state.role) {
//             if (to.params?.nickName === store.state.userData.nickName) {
//                 if (to.name === "Group") {
//                     if (to.params?.groupId === store.state.groupData.id.toString()) {
//                         next()
//                     } else {
//                         next(`/${store.state.userData.nickName}/home`)
//                     }
//                 } else {
//                     next()
//                 }
//
//             } else {
//                 next(`/home/${store.state.userData.nickName}`)
//             }
//
//         } else {
//             next('/')
//         }
//     }
// })

// router.beforeEach((to, from, next) => {
//     if (to.name === "Login" || to.name === "SignUp") {
//         store.commit('clearStore')
//         localStorage.removeItem('vuex')
//         next()
//     } else if (to.name === "User") {
//         store.commit('clearGroup')
//         store.commit('setUserGroupRole', "")
//         store.commit('setUserGroupId', 0)
//         next()
//     } else {
//         if (store.state.role === 'ROLE_ADMIN' && to.meta.role === store.state.role) {
//             next()
//         } else if (store.state.role === 'ROLE_USER' && to.meta.role === store.state.role) {
//             if (to.params?.nickName === store.state.userData.nickName) {
//                 if (to.name === "Group") {
//                     if (to.params?.groupId === store.state.groupData.id.toString()) {
//                         next()
//                     } else {
//                         next(`/home/${store.state.userData.nickName}`)
//                     }
//                 } else {
//                     next()
//                 }
//
//             } else {
//                 next(`/home/${store.state.userData.nickName}`)
//             }
//
//         } else {
//             next('/')
//         }
//     }
// })


export default router
