<script setup lang="ts">
import axios from "axios";
import store from "@/store";

const createGroup = function () {
    axios.post('/api/userGroups/create')
}

const userId = store.state.userId

</script>

<template>
    <input id="createGroup" @click="createGroup" type="button" value="그룹 만들기">

    {{userId}}

</template>

<style scoped>

</style>
