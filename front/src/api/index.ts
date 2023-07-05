import axios from "axios";
import {setInterceptors} from "@/api/common/interceptors";
import store from "@/store";

const createAuthInstance = function () {
    const instance = axios.create({
        baseURL: "http://localhost:3000/",
    })
    return setInterceptors(instance)
}

const createFormDataInstance = function () {
    const instance = axios.create({
        baseURL: "http://localhost:3000/",
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    })
    return setInterceptors(instance)
}

const instance = axios.create({
    baseURL: "http://localhost:3000"
})
const authInstance = createAuthInstance()
const formDataInstance = createFormDataInstance()



/**
 * USER API
 */
// 회원 가입
const registerUserAPI = function (registerRequest : any) {
    return instance.post(
        "/api/users", registerRequest)
}
// 로그인
const loginAPI = function (loginRequest : any) {
    return instance.post("/api/users/login", loginRequest, {
        // headers: {
        //     Authorization: `Bearer ${store.state.token}`
        // }
    })
}
// 회원 단건 조회
const getUserAPI = function (userId: any) {
    return authInstance.get(
        `/api/users/${userId}`)
}
// 회원 수정
const editUserAPI = function (userId: any, editUserRequest: any) {
    return authInstance.post(
        `/api/users/${userId}`, editUserRequest)
};
// 회원 삭제
const deleteUserAPI = function (userId: any) {
    return authInstance.delete(
        `/api/users/${userId}`)
}

// admin
const userListAPI = function (getUserRequest : any) {
    return authInstance.get(
        `/api/users?size=${getUserRequest.size}&page=${getUserRequest.page}`, getUserRequest)
}
/**
 * GROUP API
 */
const getGroupAPI = function (groupId: any) {
    return authInstance.get(
        `/api/groups/${groupId}`)
}

// const getGroupAPI = function (getGroupRequest : any) {
//     return authInstance.get(
//         `/api/userGroups/byUser?userId=${getGroupRequest.userId}&size=${getGroupRequest.size}&page=${getGroupRequest.page}`, getGroupRequest)
// }
/**
 * USERGROUP API
 */
// 그룹 생성
const createGroupAPI = function (createGroupRequest : any) {
    return authInstance.post(
        "/api/userGroups/create", createGroupRequest)
}
// 그룹 참가
const joinGroupAPI = function (joinGroupRequest : any) {
    return authInstance.post(
        "/api/userGroups/join", joinGroupRequest)
}
// 회원별 그룹 조회
const getGroupByUserAPI = function (userId : any) {
    return authInstance.get(
        `/api/userGroups/byUser/${userId}`)
}
// 그룹별 회원 조회
const getUserByGroupAPI = function (groupId : any) {
    return authInstance.get(
        `/api/userGroups/byGroup/${groupId}`)
}
// 그룹 탈퇴, 삭제
const deleteUserGroupAPI = function (userGroupId: any) {
    return authInstance.delete(
        `/api/userGroups/${userGroupId}`)
};
/**
 * POST API
 */
// 게시글 작성
const createPostAPI = function (formData: any) {
    return formDataInstance.post(
        "/api/posts", formData)
}
// 게시글 단건 조회
const getPostAPI = function (postId: any) {
    return authInstance.get(
        `/api/posts/${postId}`)
};
// 그룹별 게시글 조회
const getPostByGroupAPI = function (getPostRequest: any) {
    return authInstance.get(
        `/api/posts/byGroup?groupId=${getPostRequest.groupId}&size=${getPostRequest.size}&page=${getPostRequest.page}`)
}
// 게시글 수정
const editPostAPI = function (editPostRequest: any) {
    return authInstance.post(
        `/api/posts/${editPostRequest.postId}`, editPostRequest)
};
// 게시글 삭제
const deletePostAPI = function (postId: any) {
    return authInstance.delete(
        `/api/posts/${postId}`)
}
/**
 * UPLOAD API
 */
// 게시글별 업로드 조회
const getUploadByPostAPI = function (postId: any) {
    return authInstance.get(
        `/api/uploads/${postId}`)
};

export { loginAPI, registerUserAPI, userListAPI, deleteUserAPI, getUserAPI, createGroupAPI, getGroupByUserAPI, joinGroupAPI, getUserByGroupAPI, getGroupAPI, createPostAPI,
    getPostByGroupAPI, getUploadByPostAPI, deletePostAPI, getPostAPI, editPostAPI, deleteUserGroupAPI, editUserAPI }
