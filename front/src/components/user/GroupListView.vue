<script setup lang="ts">
import {getGroupAPI} from "@/api";
import {onMounted, ref} from "vue";
import store from "@/store";
import CreateGroupView from "@/components/user/CreateGroupView.vue";
import JoinGroupView from "@/components/user/JoinGroupView.vue";

const groups = ref([])

const dynamicComponent = ref("")


const ViewCreateGroupModal = function () {
    dynamicComponent.value = "CreateGroupModal"
}

const ViewJoinGroupModal = function () {
    dynamicComponent.value = "JoinGroupModal"
}


const getGroup = async function () {
    try {
        const result = await getGroupAPI(store.state.userId);
        groups.value = result.data.groups
    } catch (error) {
        console.log(error)
    }

};

onMounted(getGroup)

//todo 최신 활동 순으로 조회 필요
</script>

<template>
    <div class="group-add">
        <button class="btn btn-outline-danger me-1" @click="ViewCreateGroupModal">
            <i class="bi bi-folder-plus"></i>
            그룹 생성
        </button>

        <button class="btn btn-outline-success" @click="ViewJoinGroupModal">
            <i class="bi bi-folder-plus"></i>
            그룹 참가
        </button>
    </div>

    <div class="group-list-wrap justify-content-between" style="display: flex; flex-wrap: wrap; ">
        <div class="d-flex position-relative border mt-3 align-items-center " v-for="group in groups" :key="group.group.id" style="width: 45%">
            <img src="@/image/groupImage.jpg" class="flex-shrink-0 me-3" alt="">
            <div class="d-flex justify-content-center me-5" style="width: 100%">
                <h3>{{ group.group.name }}</h3>
                <a href="#" class="stretched-link"></a>
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
