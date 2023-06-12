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
        clearStore(state) {
            state.userId = 0
            state.role = ""
            state.token = ""
        },

    },
    actions: {
    },
    plugins: [ createPersistedState({
        paths: ['userId', 'role', 'token']
    }) ]
})
export default store
