<script setup lang="ts">
import {getGroupByUserAPI} from "@/api";
import {defineEmits, onMounted, ref} from "vue";
import store from "@/store";
import router from "@/router";
import CreateGroupModal from "@/components/user/CreateGroupModal.vue";
import JoinGroupModal from "@/components/user/JoinGroupModal.vue";
import dayjs from "dayjs";

const emit = defineEmits(['groupEnter']);

const dynamicComponent = ref("")

const groups = ref([{
    userGroupId: null,
    role: "",
    group: {
        id: null,
        name: "",
        key: "",
        createdDate: "",
        postCount: "",
        newPostDate: "",
        isNewPost: false
    }
}
])


const viewCreateGroupModal = function () {
    dynamicComponent.value = "CreateGroupModal"
}

const viewJoinGroupModal = function () {
    dynamicComponent.value = "JoinGroupModal"
}

const viewGroupEnter = async function (group:any, action:any) {
    emit('groupEnter', action)
    store.commit('setGroupId', group.group.id)
    store.commit('setGroupName', group.group.name)
    store.commit('setGroupKey', group.group.key)
    store.commit('setUserGroupRole', group.role)
    store.commit('setUserGroupId', group.userGroupId)
    store.commit('setDynamicComponent', action)
    await router.push(`/home/${store.state.userData.nickName}/${group.group.id}`)

}


const getGroup = async function () {
    store.commit('setDynamicComponent', '')
    store.commit('clearGroup')
    try {
        if (store.state.userId) {
            const result = await getGroupByUserAPI(store.state.userId);
            groups.value = result.data.groups

            const currDate = dayjs()
                //todo groups가 undefined일 경우 처리
                for (const group of groups.value) {
                    const hourDiff = currDate.diff(group.group.newPostDate, 'hour');
                    if (hourDiff < 24) {
                        group.group.isNewPost = true
                    } else {
                        group.group.isNewPost = false
                    }
                }

        }
    } catch (error) {
        console.log(error)
    }
};

const closeModal = function () {
    dynamicComponent.value = ""
}

onMounted(getGroup)

//todo 최신 활동 순으로 조회 필요
</script>

<template>
    <div class="group-add">
        <button class="btn btn-outline-danger me-1" @click="viewCreateGroupModal">
            <i class="bi bi-folder-plus"></i>
            그룹 생성
        </button>

        <button class="btn btn-outline-success" @click="viewJoinGroupModal">
            <i class="bi bi-folder-plus"></i>
            그룹 참가
        </button>
    </div>

    <div class="group-list-wrap justify-content-between" style="display: flex; flex-wrap: wrap; ">
        <div class="d-flex position-relative border mt-3 align-items-center " v-for="group in groups" :key="group.group.id" style="width: 48%; min-height: 50px">
            <img src="@/image/groupImage.jpg" class="flex-shrink-0" alt="">
            <div class="d-flex justify-content-center" style="width: 100%">
                <span>{{ group.group.name }}</span>
                <img class="new-post-img" v-if="group.group.isNewPost" src="@/image/new.png">
                <a href="#" class="stretched-link" @click="viewGroupEnter(group, 'groupEnter')"></a>
            </div>
        </div>
        <div class="group-empty" v-if="groups.length == 0">
            <h1 class="mt-3">그룹이 없습니다.</h1>
        </div>
        <div class="create-group" v-if="dynamicComponent === 'CreateGroupModal'">
            <CreateGroupModal @closeModal="closeModal"></CreateGroupModal>
        </div>
        <div class="join-group" v-if="dynamicComponent === 'JoinGroupModal'">
            <JoinGroupModal @closeModal="closeModal"></JoinGroupModal>
        </div>
    </div>




</template>

<style scoped>
.group-list-wrap {
    width: 100%;
    margin-bottom: 10px;
}

img {
    width: 150px;
    height: 150px;
}
.new-post-img {
    width: 17px;
    height: 17px;
    margin-left: 5px
}
span {
    font-size: 25px;
}

.group-add {
    text-align: right;
}

.create-group, .join-group {
    width: 100%;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    position: absolute;
}

@media screen and (max-width: 768px) {
.group-list-wrap {
    width: 100%;
    margin-bottom: 10px;
}

img {
    width: 30px;
    height: 30px;
}
span {
    font-size: 14px;
}

.group-add {
    text-align: right;
}

.group-add button {

    font-size: 13px;
}

.create-group, .join-group {
    width: 100%;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    position: absolute;
}
.new-post-img {
    width: 13px;
    height: 13px;
    margin-left: 5px;
}

}

</style>
