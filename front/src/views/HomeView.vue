<script setup lang="ts">
import axios from "axios";

const createGroup = function () {
    axios.post('/api/userGroups/create')
}
</script>

<template>
    <input id="createGroup" @click="createGroup" type="button" value="그룹 만들기">
    <ul class="list-group">
        <li class="list-group-item">An item</li>
        <li class="list-group-item">A second item</li>
        <li class="list-group-item">A third item</li>
        <li class="list-group-item">A fourth item</li>
        <li class="list-group-item">And a fifth one</li>
    </ul>
</template>

<style>

</style>
