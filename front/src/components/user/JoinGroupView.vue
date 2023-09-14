<script lang="ts" setup>
import {onMounted, ref} from "vue";
import {joinGroupAPI} from "@/api";
import store from "@/store";

const joinGroupRequest = ref({
    userId: store.state.userId,
    key: ""
})

const joinGroup = async function () {
    try {
        const groupKeyInput = window.prompt("그룹 코드를 입력해주세요.");
        if (groupKeyInput) {
            joinGroupRequest.value.key = groupKeyInput
            await joinGroupAPI(joinGroupRequest.value);
            window.location.reload()
        }
    } catch (error) {
        console.log(error)
    }


}

onMounted(joinGroup)
</script>


<template>
  <div>

  </div>
</template>

<style scoped>

</style>
