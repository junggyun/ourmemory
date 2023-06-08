import { createStore} from 'vuex'
import createPersistedState from 'vuex-persistedstate'

const store= createStore({
    state: {
        userId : "",
        role : "",
        token : ""
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
            state.userId = userId
        },
        setRole(state, role) {
            state.role = role
        },
        setToken(state, token) {
            state.token =token
        },
        clearStore(state) {
            state.userId = ""
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
