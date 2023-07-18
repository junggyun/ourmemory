<script lang="ts" setup>

import {defineEmits, onBeforeMount, ref} from "vue";
import {getUserByGroupAPI} from "@/api";
import store from "@/store";

const users = ref([])
const emit = defineEmits(['viewUserInfo'])
const getUsers = async function () {
    try {
        const result = await getUserByGroupAPI(store.state.groupData.id);
        users.value = result.data.users
    } catch (error) {
        console.log(error)
    }
}

const viewUserInfo = function (user: any) {
    emit('viewUserInfo', user)
};

onBeforeMount(getUsers)
</script>

<template>
    <div class="user-list-wrap">
        <div class="user-items">
            <div class="user-item d-flex position-relative border mt-2  align-items-center " v-for="user in users" :key="user.user.id"
                 @click="viewUserInfo(user.user)">
                <img v-show="user.role === 'HOST'" src="@/image/host.png" class="flex-shrink-0 ms-1  " alt="">
                <img v-show="user.role === 'MEMBER'" src="@/image/userImage.jpg" class="flex-shrink-0 ms-1  " alt="">
                <div class="d-flex justify-content-center" style="width: 100%">
                    <span style="font-size: 14px">{{ user.user.nickName }}</span>
                    <a href="#" class="stretched-link" ></a>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
img {
    width: 30px;
}
.user-item {
    height: 50px
}
@media screen and (max-width: 768px) {
    .user-list-wrap {
        display: flex;

    }
    img {
        width: 30px;
    }
    .user-items {
        display: flex;
        flex-wrap: wrap;
        width: 100%;
        overflow: auto;
        justify-content: flex-start;
    }
    .user-item {
        display: flex;
        position: relative;
        width: 25%;
        height: 40px;
    }

}
</style>
