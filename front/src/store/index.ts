import { createStore} from 'vuex'
import createPersistedState from 'vuex-persistedstate'
import {ref} from "vue";

const store= createStore({
    state: {
        userId : 0,
        role : "",
        token : "",
        userData: {
            email: "",
            name: "",
            nickName: "",
        },
        groupData: {
            id: 0,
            name: "",
            key: "",
        },
        dynamicComponent: ""
    },
    getters: {
        getUserId: function (state) {
            return state.userId
        },
        getRole: function (state) {
            return state.role
        }
    },
    mutations: {
        setUserId(state, userId) {
            state.userId = parseInt(userId)
        },
        setRole(state, role) {
            state.role = role
        },
        setToken(state, token) {
            state.token =token
        },
        setEmail(state, email) {
            state.userData.email = email
        },
        setUserNickName(state, nickName) {
            state.userData.nickName = nickName
        },
        setUserName(state, name) {
            state.userData.name = name
        },
        setGroupId(state, id) {
            state.groupData.id = id
        },
        setGroupName(state, name) {
            state.groupData.name = name
        },
        setGroupKey(state, key) {
            state.groupData.key = key
        },
        setDynamicComponent(state, dynamicComponent) {
            state.dynamicComponent = dynamicComponent
        },
        clearGroup(state) {
            state.groupData.id = 0
            state.groupData.name = ""
            state.groupData.key = ""
            state.dynamicComponent = ""
        },
        clearStore(state) {
            state.userId = 0
            state.role = ""
            state.token = ""
            state.userData.name = ""
            state.userData.nickName = ""
            state.userData.email = ""
            state.groupData.id = 0
            state.groupData.name = ""
            state.groupData.key = ""
            state.dynamicComponent = ""
        },

    },
    actions: {
    },
    plugins: [ createPersistedState({
        paths: ['userId', 'role', 'token', 'userData', 'groupData', 'dynamicComponent']
    }) ]
})
export default store
