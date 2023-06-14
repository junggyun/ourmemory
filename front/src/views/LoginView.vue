<script setup lang="ts">
import router from "@/router";
import {ref} from "vue"
import jwtDecode from "jwt-decode";
import store from "@/store";
import {getUserAPI, loginAPI} from "@/api";

const email = ref("")
const password = ref("")

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
        const userId = jwtDecode<MyPayload>(token).sub
        const role = jwtDecode<MyPayload>(token).auth

        store.commit('setUserId', userId)
        store.commit('setRole', role)
        store.commit('setToken', token)

        const user = await getUserAPI(store.state.userId)
        store.commit('setEmail', user.data.email)
        store.commit('setUserName', user.data.name)
        store.commit('setUserNickName', user.data.nickName)

        const nickName = store.state.userData.nickName
        if (role === "ROLE_ADMIN") {
            await router.push('/admin')
        } else if (role === "ROLE_USER") {
            await router.push(`/home/${nickName}`)
        }
    } catch (err) {
        alert("아이디 또는 비밀번호가 잘못되었습니다.")
        console.log(err)
    }

}

const goSignup = function () {
    router.push("/signup")
}


</script>

<template>
    <div class="main">
        <video muted autoplay loop>
            <source src="@/video/mainbackground.mp4" type="video/mp4">
        </video>
        <div class="form">
            <div class="title">
                <h1>OurMemory</h1>
            </div>

            <div id="emailInput">
                <input class="form-control form-control-lg" type="text" placeholder="이메일" v-model="email" aria-label=".form-control-lg example">
            </div>

            <div id="passwordInput">
                <input class="form-control form-control-lg" type="password" placeholder="비밀번호" v-model="password" aria-label=".form-control-lg example">
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
}

video {
    position: fixed;
    width: 100%;
}

.form {
    width: 400px;
    text-align: center;
    margin-bottom: 50px;
}

.title {
    margin-bottom: 40px;
    width: 100%;
    opacity: 0.8;
}

#emailInput, #passwordInput, #loginButton, #signupButton {
    margin-bottom: 20px;
    width: 100%;
    opacity: 0.8;
}
</style>
