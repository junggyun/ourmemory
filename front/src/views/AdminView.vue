<script setup lang="ts">

import UserListView from "@/components/admin/UserListView.vue";
import {ref} from "vue";

const dynamicComponent = ref("")

const viewUserList = function () {
    dynamicComponent.value = "UserListView"
}


</script>

<template>
<!--    헤더-->
    <div class="header">
        <div class="header-main">
            <h1>OURMEMORY 관리자 페이지</h1>
        </div>

    </div>
    <div class="body">
        <!--    내비 바-->
        <div class="nav">
            <div class="nav-main">
                <nav class="navbar bg-light">
                    <div class="container-fluid">
                        <a class="navbar-brand" href="#" @click="viewUserList">회원 목록</a>
                    </div>
                </nav>
                <nav class="navbar bg-light">
                    <div class="container-fluid">
                        <a class="navbar-brand" href="#">그룹 목록</a>
                    </div>
                </nav>
            </div>
        </div>
        <!--    내용-->
        <div class="content">
            <div v-if="dynamicComponent === 'UserListView'">
                <UserListView></UserListView>
            </div>
        </div>
    </div>





</template>

<style scoped>
.header {
    width: 100%;
    height: 150px;
    background-color: darkgray;
}
.header-main{
    padding: 50px 0 0 50px;
}
.body {
    display: flex;
}
.nav {
    width: 250px;
    height: 100%;
    flex: 0.2;
}
.nav-main {
    width: 100%;
    text-align: center;
}
.navbar-brand {
    width: 100%;
    border-width: 1px;

}
.content {
    margin: 0 150px 0 150px;
    flex: 0.6;
}
</style>
