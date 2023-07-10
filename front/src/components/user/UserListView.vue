<script lang="ts" setup>

import {onMounted, ref, defineEmits} from "vue";
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

onMounted(getUsers)
</script>

<template>
    <div class="user-list-wrap">
        <div class="group-list-wrap">
            <div class="d-flex position-relative border mt-2 me-3 align-items-center " v-for="user in users" :key="user.user.id"
                 @click="viewUserInfo(user.user)" style="height: 50px">
                <img v-show="user.role === 'HOST'" src="@/image/host.png" class="flex-shrink-0  " alt="">
                <img v-show="user.role === 'MEMBER'" src="@/image/userImage.jpg" class="flex-shrink-0  " alt="">
                <div class="d-flex justify-content-center" style="width: 100%">
                    <span>{{ user.user.nickName }}</span>
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
</style>
