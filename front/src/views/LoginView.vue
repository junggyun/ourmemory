<script setup lang="ts">
import router from "@/router";
import {ref} from "vue"
import jwtDecode from "jwt-decode";
import store from "@/store";
import {getUserAPI, loginAPI} from "@/api";

const email = ref("")
const password = ref("")
const valid = ref(false)

interface MyPayload {
    sub: String
    auth: String
    exp: number
}

const login = async function () {
    try {
        const loginRequest ={
            email: email.value,
            password: password.value
        }
        const res = await loginAPI(loginRequest);
        const token = res.data.accessToken
        const refreshToken = res.data.refreshToken
        const tokenExp = jwtDecode<MyPayload>(token).exp
        const userId = jwtDecode<MyPayload>(token).sub
        const role = jwtDecode<MyPayload>(token).auth
        const refreshTokenExp = jwtDecode<MyPayload>(refreshToken).exp

        store.commit('setUserId', userId)
        store.commit('setRole', role)
        store.commit('setToken', token)
        store.commit('setTokenExp', tokenExp)
        store.commit('setRefreshToken', refreshToken)
        store.commit('setRefreshTokenExp', refreshTokenExp)

        const user = await getUserAPI(store.state.userId)
        store.commit('setEmail', user.data.email)
        store.commit('setUserName', user.data.name)
        store.commit('setUserNickName', user.data.nickName)

        const nickName = store.state.userData.nickName
        if (role === "ROLE_ADMIN") {
            await router.replace('/admin')
        } else if (role === "ROLE_USER") {
            await router.replace(`/home/${nickName}`)
        }
    } catch (err) {
        valid.value = true
        console.log(err)
    }

}

const loginPage = function () {
    router.push("/")
    console.log(process.env)
}

const goSignup = function () {
    router.push("/signup")
}

const handleEmailInput = function (e) {
    e.target.value = e.target.value.replace(/[^A-Za-z0-9@.]/ig, '')
};


</script>

<template>
    <div class="main">
        <div class="form">
            <div class="title">
                <h1 @click="loginPage">OurMemory</h1>
            </div>

            <div id="emailInput">
                <input class="form-control form-control-lg" type="email" placeholder="이메일" @keyup.enter="login" v-model="email" @input="handleEmailInput" aria-label=".form-control-lg example">
                <span v-show="valid" class="input-error" style="color: red">이메일 또는 비밀번호를 다시 확인해주세요.</span>
            </div>

            <div id="passwordInput">
                <input class="form-control form-control-lg" type="password" placeholder="비밀번호"
                       v-model="password" :minlength="8" :maxlength="16" @keyup.enter="login" aria-label=".form-control-lg example">
            </div>

            <div id="loginButton">
                <div class="d-grid gap-2">
                    <button type="button" @click="login" class="btn btn-secondary btn-lg">로그인</button>
                </div>
            </div>

            <div id="signupButton">
                <div class="d-grid gap-2">
                    <button type="button" @click="goSignup" class="btn btn-secondary btn-lg">계정 만들기</button>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.main {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    width: 100vw;
    background-color: darkgray;
}

h1 {
    pointer-events: auto;
    cursor : pointer;
}

.form {
    width: 500px;
    text-align: center;
    margin-bottom: 50px;
    background-color: white;
    padding: 50px 20px 50px 20px;
    border-radius: 8px;
}

.title {
    margin-bottom: 40px;
    width: 100%;
    opacity: 0.8;
}

#loginButton, #signupButton {
    margin-bottom: 20px;
    width: 100%;
    opacity: 0.8;
}
#emailInput, #passwordInput {
    margin-bottom: 30px;
    width: 100%;
    opacity: 0.8;
    position: relative;
}

.input-error {
    color: red;
    /*text-align: left;*/
    margin-left: 17px;
    position: absolute;
    left: 0;
}
</style>
