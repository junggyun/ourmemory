<script lang="ts" setup>
import store from "../../store";
import { defineEmits } from 'vue'

const emit = defineEmits(['closeGroupKey']);

const closeGroupKey = function () {
    emit('closeGroupKey')
}


</script>

<template>
    <div class="group-key-modal">
        <div class="group-key-modal-content">
            <div class="group-key-modal-content-header">
                <h5>그룹코드</h5>
            </div>
            <div class="group-key-modal-content-body">
                <b>{{ store.state.groupData.key }}</b>
            </div>
            <div class="group-key-modal-content-footer">
                <button class="btn btn-secondary" @click="closeGroupKey">닫기</button>
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
