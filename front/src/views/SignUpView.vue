<script setup lang="ts">
import router from "@/router";
import {ref} from 'vue'
import {registerUserAPI} from "@/api";

const email = ref("")
const password = ref("")
const name = ref("")
const nickName = ref("")
const valid = ref({
    email: false,
    password: false,
    nickName: false,
})
const emailHasError = ref(false)
const passwordHasError = ref(false)

const signup = async function () {
    try {
        const registerRequest = {
            email: email.value,
            password: password.value,
            name: name.value,
            nickName: nickName.value
        }
        await checkEmail()
        await checkPassword()
        if (!valid.value.email && !valid.value.password) {
            await registerUserAPI(registerRequest);

            alert("회원가입 성공")
            await router.push("/")
        }

    } catch (err) {
        console.log(err)
        console.log(err.response.data.validation.nickName)
        if (err.response.data.validation.nickName === "이미 사용 중인 닉네임입니다.") {
            valid.value.nickName = true
        }
    }
}

const checkEmail = function () {
    const validateEmail = /^[A-Za-z0-9_\\.\\-]+@[A-Za-z0-9\\-]+\.[A-Za-z0-9\\-]+/

    if (!validateEmail.test(email.value) || !email.value) {
        valid.value.email = true
        emailHasError.value = true
    } else {
        valid.value.email = false
        emailHasError.value = false
    }
}

const checkPassword = function () {
    const validatePassword = /^(?=.*[a-zA-z])(?=.*[0-9]).{8,16}$/

    if (!validatePassword.test(password.value) || !password.value) {
        valid.value.password = true
        passwordHasError.value = true
    } else {
        valid.value.password = false
        passwordHasError.value = false
    }
}

const loginPage = function () {
    router.push('/')
}


</script>

<template>
    <div class="main">
        <div class="form">
            <div class="title">
                <h1 @click="loginPage">OurMemory</h1>
            </div>
            <div id="nameInput">
                <input class="form-control form-control-lg" type="text" placeholder="이름" v-model="name" aria-label=".form-control-lg example">
            </div>

            <div id="emailInput">
                <input class="form-control form-control-lg" type="email" placeholder="이메일" v-model="email" aria-label=".form-control-lg example">
                <span v-show="valid.email" class="input-error">이메일 주소를 정확히 입력해주세요.</span>
            </div>

            <div id="passwordInput">
                <input class="form-control form-control-lg" type="password" placeholder="비밀번호 (영문,숫자 조합 8-16자)"
                       v-model="password" aria-label=".form-control-lg example">
                <span v-show="valid.password" class="input-error">영문,숫자를 조합하여 입력해주세요.(8-16자)</span>
            </div>


            <div id="nickNameInput">
                <input class="form-control form-control-lg" type="text" placeholder="닉네임" v-model="nickName" aria-label=".form-control-lg example">
                <span v-show="valid.nickName" class="input-error">이미 사용 중인 닉네임입니다.</span>
            </div>

            <div id="signupButton">
                <div class="d-grid gap-2">
                    <button type="button" @click="signup" class="btn btn-secondary btn-lg">회원가입</button>
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

video {
    position: fixed;
    width: 100%;
}

.form {
    width: 500px;
    text-align: center;
    margin-bottom: 50px;
    background-color: white;
    padding: 50px 20px 20px 20px;
    border-radius: 8px;

}
input::placeholder {
    font-size: 20px;
}

.input-error {
    color: red;
    /*text-align: left;*/
    margin-left: 17px;
    position: absolute;
    left: 0;
}

.title {
    margin-bottom: 40px;
    width: 100%;
    opacity: 0.8;
}



#emailInput, #passwordInput, #nameInput, #nickNameInput, #signupButton {
    margin-bottom: 30px;
    width: 100%;
    opacity: 0.8;
    /*display: flex;*/
    /*flex-direction: column;*/
    position: relative;
}
</style>
