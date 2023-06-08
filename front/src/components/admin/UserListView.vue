<script setup lang="ts">
import {userList} from "@/api";
import {ref, onMounted} from "vue";

const pageNum = ref('')

const getUser = function () {
    try {
        const users = ref('')

        const getUserRequest = {
            page: 1,
            size: 5
        }
        onMounted(async () => {
            const result = await userList(getUserRequest);
            users.value = result.data
        })

        return users
    } catch (err) {
        console.log(err)
    }
    //todo 회원 목록 페이지네이션
}
const users = getUser()



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
            <th scope="row">{{user.id}}</th>
            <td>{{user.name}}</td>
            <td>{{user.nickName}}</td>
            <td>{{user.email}}</td>
            <td>{{user.role}}</td>
        </tr>
        </tbody>
    </table>

    <div class="btn-cover">
        <button :disabled="pageNum === 0" @click="prevPage" class="page-btn">이전</button>
        <span class="page-count">{{ pageNum + 1 }} / {{ pageCount }} 페이지</span>
        <button :disabled="pageNum >= pageCount - 1" @click="nextPage" class="page-btn">다음</button>
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
</style>
