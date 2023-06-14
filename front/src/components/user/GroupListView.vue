<script setup lang="ts">
import {getGroupAPI} from "@/api";
import {onMounted, ref} from "vue";
import store from "@/store";

const groups = ref([])



const getGroup = async function () {
    try {
        const result = await getGroupAPI(store.state.userId);
        groups.value = result.data.groups
    } catch (error) {
        console.log(error)
    }

};

onMounted(getGroup)


</script>

<template>
    <div class="group-list-wrap">
        <div class="d-flex position-relative" v-for="group in groups" :key="group.group.id">
            <img src="@/image/groupImage.jpg" class="flex-shrink-0 me-3" alt="">
            <div>
                <h5 class="mt-0">{{ group.group.name }}</h5>
                <p>test</p>
                <a href="#" class="stretched-link"></a>
            </div>
        </div>
    </div>


</template>

<style scoped>
.group-list-wrap {
    width: 400px;
}

img {
    width: 200px;
    height: 200px;
}
</style>
