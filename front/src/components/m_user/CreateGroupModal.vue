<script lang="ts" setup>
import {defineEmits, ref} from 'vue'
import store from "@/store";
import {createGroupAPI} from "@/api";

const createGroupRequest = ref({
    userId: store.state.userId,
    groupName: ""
})

const emit = defineEmits(['closeModal']);

const closeModal = function () {
    emit('closeModal')
}

const createGroup = async function () {
    try {
        if (createGroupRequest.value.groupName) {

            await createGroupAPI(createGroupRequest.value);
            closeModal()
            window.location.reload()
        }
    } catch (error) {
        console.log(error)
    }

}

</script>

<template>
    <div class="group-key-modal">
        <div class="group-key-modal-content">
            <div class="group-key-modal-content-header">
                <h5>그룹 생성</h5>
            </div>
            <div class="group-key-modal-content-body">
                <input type="text" v-model="createGroupRequest.groupName" placeholder="그룹명">

            </div>
            <div class="group-key-modal-content-footer">
                <button class="btn btn-secondary me-3" @click="createGroup">생성</button>
                <button class="btn btn-secondary" @click="closeModal">닫기</button>
            </div>
        </div>
    </div>
</template>

<style scoped>

.group-key-modal {
    width: 300px; height: 200px;
    border: 1px solid darkgray;
    border-radius: 8px;
    z-index: 999;
    position: absolute;
    top: 10px;
    background: white;
}
.group-key-modal-content {
    width: 100%; height: 100%;
    display: flex;
    flex-direction: column;

}
.group-key-modal-content-header {
    flex: 1;
    display: flex;
    justify-content: center;
    align-items: center;
    border-top-left-radius: 8px;
    border-top-right-radius: 8px;
}
.group-key-modal-content-body {
    flex: 2;
    border-top: 1px solid darkgray;
    border-bottom: 1px solid darkgray;
    display: flex;
    justify-content: center;
    align-items: center;
}
.group-key-modal-content-footer {
    flex: 1;
    display: flex;
    justify-content: center;
    align-items: center;
    border-bottom-left-radius: 8px;
    border-bottom-right-radius: 8px;
}

.group-key-modal-content button {
}
</style>
