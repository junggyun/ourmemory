import { createStore} from 'vuex'
import createPersistedState from 'vuex-persistedstate'

const store= createStore({
    state: {
        userId : 0,
        role : "",
        token : "",
        userData: {
            email: "",
            name: "",
            nickName: "",
        }
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
        setNickName(state, nickName) {
            state.userData.nickName = nickName
        },
        setName(state, name) {
            state.userData.name = name
        },
        clearStore(state) {
            state.userId = 0
            state.role = ""
            state.token = ""
        },

    },
    actions: {
    },
    plugins: [ createPersistedState({
        paths: ['userId', 'role', 'token', 'userData']
    }) ]
})
export default store
