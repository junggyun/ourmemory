<script lang="ts" setup>
import router from "@/router";
import store from "@/store";
import {ref, watch} from "vue";
import {logoutAPI} from "@/api";

const userId = ref(store.state.userId)

const logout = async function () {
    await logoutAPI(userId.value)
    await router.replace('/')
}

const home = async function () {
    store.commit('clearGroup')
    store.commit('clearPost')
    await router.push(`/${userId.value}/home`)
}

const goUserInfo = async function () {

    await router.push(`/${userId.value}/userinfo`)

};

watch(() => store.state.userId, (newValue) => {
    userId.value = newValue
})

</script>

<template>

    <div class="header-wrap">
        <div class="title">
            <h1 @click="home">OURMEMORY</h1>
        </div>
        <div class="user-info">
            <button class="btn btn-secondary-" @click="goUserInfo" style="margin-top: 5px; color: darkgray">내 정보</button>
            <button type="button" class="btn btn-light- " @click="logout" style=" color: darkgray;">로그아웃</button>
        </div>

    </div>

</template>

<style scoped>
h1 {
    pointer-events: auto;
    cursor : pointer;
}
.header-wrap {
    position: relative;
    display: flex;
    align-items: center;
    width: 50vw;
    height: 100%;

}
.user-info {
    display: flex;
    align-items: center;
    position: absolute;
    top: 0;
    right: 0;
}
.user-info button {
    font-size: 14px;
}
button {
    padding: 0;
    margin-top: 5px;
    margin-left: 5px;

}

@media screen and (max-width: 768px) {
    h1 {
        margin-top: 5px;
        pointer-events: auto;
        cursor : pointer;
    }
    .header-wrap {
        position: relative;
        display: flex;
        align-items: center;
        width: 90vw;
        height: 100%;

    }
    .user-info {
        display: flex;
        align-items: center;
        position: absolute;
        top: 0;
        right: 0;
    }
    .user-info button {
        font-size: 14px;
    }
    button {
        padding: 0;
        margin-top: 5px;
        margin-left: 5px;

    }
}

</style>
