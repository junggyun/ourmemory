import { createStore} from 'vuex'

const store= createStore({
    state: {
        userId : ""
    },
    getters: {
        getUserId: function (state) {
            return state.userId
        }
    },
    mutations: {
        setUserId(state, userId) {
            state.userId = userId
        }
    },
    actions: {
    }
})
export default store
