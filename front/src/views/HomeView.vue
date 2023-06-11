<script setup lang="ts">
import {onMounted, ref} from "vue";
import {getUserAPI} from "@/api";
import store from "@/store";
import GroupListView from "@/components/user/GroupListView.vue";
import CreateGroupModal from "@/components/user/CreateGroupModal.vue";

const dynamicComponent = ref("")

const ViewGroupList = function () {
    dynamicComponent.value = "GroupListView"
}

const ViewCreateGroupModal = function () {
    dynamicComponent.value = "CreateGroupModal"
}

const userData = ref({
    userName: "",
    userNickName: "",
})



const getUser = async function () {
    try {
        const user = await getUserAPI(store.state.userId);
        userData.value.userName = user.data.name
        userData.value.userNickName = user.data.nickName
    } catch (error) {
        console.log(error)
    }

}

onMounted(getUser)

</script>

<template>


    <!--    헤더-->
    <div class="header">
        <div class="header-main">
            <h1>OURMEMORY</h1>

        </div>

    </div>
    <div class="body">
        <!--    내비 바-->
        <div class="nav">
            <div class="nav-main">
                <nav class="navbar bg-light">
                    <div class="container-fluid">
                        <a class="navbar-brand" href="#" @click="ViewCreateGroupModal">그룹 만들기</a>
                    </div>
                </nav>
            </div>
            <div class="nav-main">
                <nav class="navbar bg-light">
                    <div class="container-fluid">
                        <a class="navbar-brand" href="#" @click="ViewGroupList">그룹 목록</a>
                    </div>
                </nav>
            </div>
        </div>
        <!--    내용-->
        <div class="content">
            <div v-if="dynamicComponent === 'GroupListView'">
                <GroupListView></GroupListView>
            </div>
            <div v-if="dynamicComponent === 'CreateGroupModal'">
                <CreateGroupModal></CreateGroupModal>
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
