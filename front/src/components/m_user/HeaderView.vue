<script lang="ts" setup>
import router from "@/router";
import store from "@/store";

const logout = async function () {
    await router.push('/')
}

const home = async function () {
    store.commit('setDynamicComponent', '')
    await router.push(`/home/${store.state.userData.nickName}`)
}

const viewEditUserModal = async function () {
    store.commit('setDynamicComponent', 'userEdit')
    await router.push(`/home/${store.state.userData.nickName}`)

};


</script>

<template>

    <div class="header-wrap">
        <div class="title">
            <h1 @click="home">OURMEMORY</h1>
        </div>
        <div class="user-info">
            <button class="btn btn-secondary-" @click="viewEditUserModal" style="margin-top: 5px; font-size: 14px; color: darkgray">내 정보</button>
            <button type="button" class="btn btn-light- " @click="logout" style=" color: darkgray;  font-size: 14px">로그아웃</button>
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
.user-info span {

}
button {
    padding: 0;
    margin-top: 5px;
    margin-left: 5px;

}

</style>
