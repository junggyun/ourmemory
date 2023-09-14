import {createStore} from 'vuex'
import createPersistedState from 'vuex-persistedstate'

const store= createStore({
    state: {
        userId : 0,
        role : "",
        token : "",
        tokenExp : 0,
        refreshToken: "",
        refreshTokenExp: 0,
        userData: {
            email: "",
            name: "",
            nickName: "",
            createdDate: "",
        },
        groupData: {
            id: 0,
            name: "",
            key: "",
        },
        postData: {
            id: 0,
            title: "",
            content: "",
            createdDate: "",
            userNickName: "",
            viewCount: 0,
        },
        userGroupRole: "",
        userGroupId: 0,
    },
    getters: {
        getUserId: function (state) {
            return state.userId
        },
        getRole: function (state) {
            return state.role
        },
        getRefreshToken: function (state) {
            return state.refreshToken
        },
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
        setTokenExp(state, tokenExp) {
            state.tokenExp =tokenExp
        },
        setRefreshToken(state, refreshToken) {
            state.refreshToken =refreshToken
        },
        setRefreshTokenExp(state, refreshTokenExp) {
            state.refreshTokenExp =refreshTokenExp
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
        setUserCreatedDate(state, createdDate) {
            state.userData.createdDate = createdDate
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
        setPostId(state, id) {
            state.postData.id = id
        },
        setPostTitle(state, title) {
            state.postData.title = title
        },
        setPostContent(state, content) {
            state.postData.content = content
        },
        setPostCreatedDate(state, createdDate) {
            state.postData.createdDate = createdDate
        },
        setPostUserNickName(state, userNickName) {
            state.postData.userNickName = userNickName
        },
        setPostViewCount(state, viewCount) {
            state.postData.viewCount = viewCount
        },
        setUserGroupRole(state, userGroupRole) {
            state.userGroupRole = userGroupRole
        },
        setUserGroupId(state, userGroupId) {
            state.userGroupId = userGroupId
        },
        clearGroup(state) {
            state.groupData.id = 0
            state.groupData.name = ""
            state.groupData.key = ""
        },
        clearPost(state) {
            state.postData.id = 0
            state.postData.title = ""
            state.postData.content = ""
            state.postData.createdDate = ""
            state.postData.userNickName = ""
            state.postData.viewCount = 0
        },
        clearStore(state) {
            state.userId = 0
            state.role = ""
            state.token = ""
            state.tokenExp = 0
            state.refreshToken = ""
            state.refreshTokenExp = 0
            state.userData.name = ""
            state.userData.nickName = ""
            state.userData.email = ""
            state.userData.createdDate = ""
            state.groupData.id = 0
            state.groupData.name = ""
            state.groupData.key = ""
            state.postData.id = 0
            state.postData.title = ""
            state.postData.content = ""
            state.postData.createdDate = ""
            state.postData.userNickName = ""
            state.postData.viewCount = 0
            state.userGroupRole = ""
            state.userGroupId = 0
        },

    },
    actions: {
    },
    plugins: [ createPersistedState({
        paths: ['userId', 'role', 'token', 'tokenExp', 'refreshToken', 'refreshTokenExp', 'userData', 'groupData', 'postData', 'userGroupRole', 'userGroupId']
    }) ]
})
export default store
