<script setup lang="ts">
import {getGroupByUserAPI} from "@/api";
import {defineEmits, onMounted, ref} from "vue";
import store from "@/store";
import CreateGroupView from "@/components/user/CreateGroupView.vue";
import JoinGroupView from "@/components/user/JoinGroupView.vue";
import router from "@/router";

const groups = ref([])
const emit = defineEmits(['groupEnter']);

const dynamicComponent = ref("")


const viewCreateGroupModal = function () {
    dynamicComponent.value = "CreateGroupModal"
}

const viewJoinGroupModal = function () {
    dynamicComponent.value = "JoinGroupModal"
}

const viewGroupEnter = async function (group:any, action:any) {
    emit('groupEnter', action)
    store.commit('setGroupId', group.id)
    store.commit('setGroupName', group.name)
    store.commit('setGroupKey', group.key)
    store.commit('setDynamicComponent', action)
    await router.push(`/home/${store.state.userData.nickName}/${group.id}`)

}


const getGroup = async function () {
    try {
        if (store.state.userId) {
            const result = await getGroupByUserAPI(store.state.userId);
            groups.value = result.data.groups
        }
    } catch (error) {
        console.log(error)
    }
};

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
        <div class="d-flex position-relative border mt-3 align-items-center " v-for="group in groups" :key="group.group.id" style="width: 45%">
            <img src="@/image/groupImage.jpg" class="flex-shrink-0 me-3" alt="">
            <div class="d-flex justify-content-center me-5" style="width: 100%">
                <h3>{{ group.group.name }}</h3>
                <a href="#" class="stretched-link" @click="viewGroupEnter(group.group, 'groupEnter')"></a>
            </div>
        </div>
        <div class="group-empty" v-if="groups.length == 0">
            <h1>그룹이 없습니다.</h1>
        </div>
    </div>

    <div v-if="dynamicComponent === 'CreateGroupModal'">
        <CreateGroupView></CreateGroupView>
    </div>
    <div v-if="dynamicComponent === 'JoinGroupModal'">
        <JoinGroupView></JoinGroupView>
    </div>

</template>

<style scoped>
.group-list-wrap {
    width: 100%;
    margin-bottom: 10px;
}

img {
    width: 200px;
    height: 200px;
}

.group-add {
    text-align: right;
}

</style>
