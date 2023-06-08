<script setup lang="ts">
import router from "@/router";
import {ref} from 'vue'
import {registerUser} from "@/api";

const email = ref("")
const password = ref("")
const name = ref("")
const nickName = ref("")

const signup = async function () {
    try {
        const registerRequest = {
            email: email.value,
            password: password.value,
            name: name.value,
            nickName: nickName.value
        }
        await registerUser(registerRequest);

        alert("회원가입 성공")
        await router.push("/")
    } catch (err) {
        console.log(err)
    }
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

            <div id="nameInput">
                <input class="form-control form-control-lg" type="text" placeholder="이름" v-model="name" aria-label=".form-control-lg example">
            </div>

            <div id="nickNameInput">
                <input class="form-control form-control-lg" type="text" placeholder="닉네임" v-model="nickName" aria-label=".form-control-lg example">
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
    margin: 50px 0 50px 0;
}

#emailInput, #passwordInput, #nameInput, #nickNameInput, #signupButton {
    margin-bottom: 20px;
    width: 100%;
    opacity: 0.8;
}
</style>
