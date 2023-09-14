<script setup lang="ts">
import {getGroupByUserAPI} from "@/api";
import {onMounted, ref} from "vue";
import store from "@/store";
import router from "@/router";
import CreateGroupModal from "@/components/user/CreateGroupModal.vue";
import JoinGroupModal from "@/components/user/JoinGroupModal.vue";
import dayjs from "dayjs";


const userId = ref(store.state.userId)
const isCreateGroupModal = ref(false)
const isJoinGroupModal = ref(false)

const groups = ref([{
    userGroupId: 0,
    role: "",
    group: {
        id: 0,
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
    isCreateGroupModal.value = true
}

const closeCreateGroupModal = function () {
    isCreateGroupModal.value = false
};

const viewJoinGroupModal = function () {
    isJoinGroupModal.value = true
}

const closeJoinGroupModal = function () {
    isJoinGroupModal.value = false
};

const createGroup = function (group: any) {
    isCreateGroupModal.value = false
    goGroup(group)
};

const joinGroup = function (group: any) {
    isJoinGroupModal.value = false
    goGroup(group)
};

const goGroup = async function (group:any) {

    await store.commit('setGroupId', group.group.id)
    await store.commit('setGroupName', group.group.name)
    await store.commit('setGroupKey', group.group.key)
    await store.commit('setUserGroupRole', group.role)
    await store.commit('setUserGroupId', group.userGroupId)

    await router.push(`/${userId.value}/${group.group.id}`);
}


const getGroup = async function () {
    store.commit('clearGroup')
    try {
        if (store.state.userId) {
            const result = await getGroupByUserAPI(store.state.userId);
            groups.value = result.data.groups

            const currDate = dayjs()
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



onMounted(getGroup)

//todo 최신 활동 순으로 조회 필요
</script>

<template>
    <div class="group-list-wrap">
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

        <div class="group-list-items justify-content-between" style="display: flex; flex-wrap: wrap; ">
            <div class="d-flex position-relative border mt-3 align-items-center " v-for="group in groups" :key="group.group.id" style="width: 48%; min-height: 50px">
                <img src="@/image/groupImage.jpg" class="flex-shrink-0" alt="">
                <div class="d-flex justify-content-center" style="width: 100%">
                    <span>{{ group.group.name }}</span>
                    <img class="new-post-img" v-if="group.group.isNewPost" src="@/image/new.png">
                    <a href="#" class="stretched-link" @click="goGroup(group)"></a>
                </div>
            </div>
            <div class="group-empty" v-if="groups.length == 0">
                <h1 class="mt-3">그룹이 없습니다.</h1>
            </div>
        </div>
        <div class="group-list-modal">
            <div class="create-group" v-if="isCreateGroupModal">
                <CreateGroupModal @enterModal="createGroup" @closeModal="closeCreateGroupModal"></CreateGroupModal>
            </div>
            <div class="join-group" v-if="isJoinGroupModal">
                <JoinGroupModal @enterModal="joinGroup" @closeModal="closeJoinGroupModal"></JoinGroupModal>
            </div>
        </div>
    </div>





</template>

<style scoped>
.group-list-wrap {
    width: 50vw;
}
.group-list-items {
    width: 100%;
    margin-bottom: 10px;
}
.group-list-modal {
    position: absolute;
    top: 50%;
    left: 50%;
    margin: -300px 0 0 -150px;
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


@media screen and (max-width: 768px) {
    .group-list-wrap {
        width: 90vw;
    }
    .group-list-items {
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

    .group-list-modal {
        position: absolute;
        top: 50%;
        left: 50%;
        margin: -150px 0 0 -100px;
    }
    .new-post-img {
        width: 13px;
        height: 13px;
        margin-left: 5px;
    }

}

</style>
