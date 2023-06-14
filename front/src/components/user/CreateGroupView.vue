<script lang="ts" setup>
import {onMounted, ref} from "vue";
import {createGroupAPI} from "@/api";
import store from "@/store";

const groupName = ref("")

const createGroupRequest = ref({
    userId: store.state.userId,
    groupName: groupName.value
})

const createGroup = async function () {
    try {
        const groupNameInput = window.prompt("생성할 그룹 이름을 입력해주세요.");
        if (groupNameInput) {
            groupName.value = groupNameInput
            createGroupRequest.value.groupName = groupName.value
            console.log(createGroupRequest.value)

            await createGroupAPI(createGroupRequest.value);
            window.location.reload()
        }
    } catch (error) {
        console.log(createGroupRequest.value.groupName)
        console.log(createGroupRequest.value.userId)
        console.log(error)
    }


}

onMounted(createGroup)
</script>


<template>
  <div>

  </div>
</template>

<style scoped>

</style>
