import axios from "axios";
import {setInterceptors} from "@/api/common/interceptors";
import store from "@/store";

const createInstance = function () {
    const instance = axios.create({
        baseURL: "http://localhost:3000/",
        headers: {
            Authorization: ""
        }
    })
    return setInterceptors(instance)
}

const instance = axios.create({
    baseURL: "http://localhost:3000"
})
const authInstance = createInstance()

const loginUser = function (loginRequest : any) {
    return instance.post("/api/users/login", loginRequest, {
        headers: {
            Authorization: `Bearer ${store.state.token}`
        }
    })
}

const registerUser = function (registerRequest : any) {
    return instance.post("/api/users", registerRequest)
}

const createUserGroup = function (createUserGroupRequest : any) {
    return authInstance.post("/api/UserGroups/create", createUserGroupRequest)
}

const userList = function (getUserRequest : any) {
    return authInstance.get(`/api/users?size=${getUserRequest.size}&page=${getUserRequest.page}`, getUserRequest)
}

export { loginUser, registerUser, userList }
