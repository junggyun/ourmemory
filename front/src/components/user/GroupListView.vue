<script setup lang="ts">
import {getGroupAPI} from "@/api";
import {onMounted, ref} from "vue";
import store from "@/store";


const pageNum = ref(1)
const sizeNum = ref(5)
const totalPages = ref('')
const groups = ref([])

const getGroupRequest = ref({
    userId: store.state.userId,
    page: pageNum.value,
    size: sizeNum.value,
})

const nextPage = async function () {
    pageNum.value += 1
    await getGroup()
}

const prevPage = async function () {
    pageNum.value -= 1
    await getGroup()
}

const getGroup = async function () {
    try {
        getGroupRequest.value.page = pageNum.value;

        const result = await getGroupAPI(getGroupRequest.value);
        groups.value = result.data.groups
        totalPages.value = result.data.totalPages
        console.log(totalPages.value)
    } catch (error) {
        console.log(error)
    }

};

onMounted(getGroup)
//todo 그룹만들기, 그룹 목록 다듬기


</script>

<template>
    <div class="title">
        <h1>그룹 목록</h1>
    </div>
    <div class="group-list" v-for="group in groups" :key="group.group.id">
        <div class="list-group">
            <a href="#" class="list-group-item list-group-item-action">
                {{ group.group.name }}
            </a>
        </div>

    </div>

    <div class="btn-cover">
        <button :disabled="pageNum === 1" @click="prevPage" class="page-btn">이전</button>
        <span class="page-count">{{ pageNum  }} / {{ totalPages }} 페이지</span>
        <button :disabled="pageNum == totalPages" @click="nextPage" class="page-btn">다음</button>
    </div>

</template>

<style scoped>
.title {
    margin: 30px 0 30px 0;
    text-align: center;
}

.btn-cover {
    margin-top: 40px;
    text-align: center;
}
.btn-cover .page-btn {
    width: 5rem;
    height: 2rem;
    letter-spacing: 0.5px;
}
.btn-cover .page-count {
    padding: 0 1rem;
}

</style>
