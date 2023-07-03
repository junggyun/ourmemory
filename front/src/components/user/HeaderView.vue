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
            <a href="#" @click="viewEditUserModal">{{ store.state.userData.nickName}}</a>님
            <button type="button" class="btn btn-outline-danger- " @click="logout" style="text-decoration: underline; color: darkgray">로그아웃</button>
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
    width: 60vw;
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

</style>
