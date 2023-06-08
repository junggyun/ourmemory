<script setup lang="ts">
import router from "@/router";
import {ref} from "vue"
import jwtDecode from "jwt-decode";
import store from "@/store";
import {loginUser} from "@/api";

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
        const res = await loginUser(loginRequest);

        const token = res.data.accessToken
        const userId = jwtDecode<MyPayload>(token).sub
        const role = jwtDecode<MyPayload>(token).auth

        store.commit('setUserId', userId)
        store.commit('setRole', role)
        store.commit('setToken', token)

        const getUserId = store.getters.getUserId
        if (role === "ROLE_ADMIN") {
            await router.push('/admin')
        } else if (role === "ROLE_USER") {
            await router.push(`/home/${getUserId}`)
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
    position: absolute; top:0; left:0;
    width: 100%;
    height: 100%;
}

video {
    width: 100%;
}

.form {
    position: absolute;
    left: 50%;
    top: 50%;
    width: 400px;
    margin: -300px 0px 0px -250px;
    text-align: center;
    opacity: 1;

}

.title {
    margin: 50px 0px 50px 0px;
}

#emailInput, #passwordInput, #loginButton, #signupButton {
    margin-bottom: 20px;
    width: 100%;
    opacity: 0.8;
}
</style>
