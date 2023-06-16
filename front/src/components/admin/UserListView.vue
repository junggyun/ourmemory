<script setup lang="ts">
import { deleteUserAPI, userListAPI} from "@/api";
import {onMounted, ref} from "vue";


const pageNum = ref(1)
const sizeNum = ref(5)
const totalPages = ref(0)
const users = ref([])

const getUserRequest = ref({
    page: pageNum.value,
    size: sizeNum.value
})

const nextPage = async function () {
    pageNum.value += 1
    await getUser()
}

const prevPage = async function () {
    pageNum.value -= 1
    await getUser()
}

const getUser = async function () {
    try {
        getUserRequest.value.page = pageNum.value

        const result = await userListAPI(getUserRequest.value);
        users.value = result.data.users
        totalPages.value = result.data.totalPages

    } catch (err) {
        console.log(err)
    }
}

const deleteUser = async function (userId: any) {
    try {
        const confirmed = window.confirm("정말로 삭제하시겠습니까?");
        if (confirmed) {
            await deleteUserAPI(userId);
            await getUser()
        }

    } catch (err) {
        alert(err.response.data.validation.role)
    }
}

onMounted(getUser)




</script>

<template>
    <div class="title">
        <h1>회원 목록</h1>
    </div>
    <table class="table">
        <thead>
        <tr>
            <th scope="col">id</th>
            <th scope="col">이름</th>
            <th scope="col">닉네임</th>
            <th scope="col">이메일</th>
            <th scope="col">권한</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="user in users" :key="user.id">
            <th scope="row" style="width: 5%">{{user.id}}</th>
            <td style="width: 10%">{{user.name}}</td>
            <td style="width: 10%">{{user.nickName}}</td>
            <td style="width: 30%">{{user.email}}</td>
            <td style="width: 15%">
                {{user.role}}
                <button class="btn-user-delete" @click="deleteUser(user.id)">삭제</button>
            </td>
        </tr>
        </tbody>
    </table>

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
table {
    width: 100%;
    border-collapse: collapse;
}
table th {
    font-size: 1.2rem;
}
table tr {
    height: 2rem;
    text-align: center;
    border-bottom: 1px solid #505050;
}
table tr:first-of-type {
    border-top: 2px solid #404040;
}
table tr td {
    padding: 1rem 0;
    font-size: 1.1rem;
}
table tr th {
    padding: 1rem 0;
    font-size: 1.1rem;
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
.btn-user-delete {
    float: right;
}
</style>
