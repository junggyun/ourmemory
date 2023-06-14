<script lang="ts" setup>


import {getUserByGroupAPI} from "@/api";
import store from "@/store";
import {onMounted, ref} from "vue";

const users = ref([])

const getUsers = async function () {
    try {
        const result = await getUserByGroupAPI(store.state.groupData.id);
        users.value = result.data.users
    } catch (error) {
        console.log(error)
    }
}

onMounted(getUsers)
</script>

<template>
    <div class="post-add">
        <button class="btn btn-outline-success">
            <i class="bi bi-folder-plus"></i>
            멤버 초대
        </button>
        <button class="btn btn-outline-danger" >
            <i class="bi bi-folder-plus"></i>
            글 작성
        </button>
    </div>
    <div class="user-post-wrap" >
        <div class="group-user-list">
            <div class="group-list-wrap" style="">
                <div class="d-flex position-relative border mt-2 me-3 align-items-center " v-for="user in users" :key="user.user.id" style="height: 50px">
                    <img src="@/image/userImage.jpg" class="flex-shrink-0  " alt="">
                    <div class="d-flex justify-content-center" style="width: 100%">
                        <span>{{ user.user.nickName }}</span>
                        <a href="#" class="stretched-link" ></a>
                    </div>
                </div>
            </div>
        </div>
        <div class="post-list mt-2">
            123
        </div>
    </div>

</template>

<style scoped>
.post-add {
    display: flex;
    justify-content: space-between;
}
.user-post-wrap {
    display: flex;
}
.group-user-list {
    flex: 2;
}
img {
    width: 30px;
}

.post-list {
    flex: 8;
    border: 1px solid darkgray;
}
</style>
