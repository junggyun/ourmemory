<script lang="ts" setup>
import {defineEmits, ref} from 'vue'
import store from "@/store";
import {joinGroupAPI} from "@/api";

const joinGroupRequest = ref({
    userId: store.state.userId,
    key: ""
})

const errorMessage = ref("")

const emit = defineEmits(['closeModal']);

const closeModal = function () {
    emit('closeModal')
}

const joinGroup = async function () {
    if (joinGroupRequest.value.key) {
        try {
            await joinGroupAPI(joinGroupRequest.value);
            closeModal()
            window.location.reload()
        } catch (error) {
            if (error.response.data.validation.groupId) {
                errorMessage.value = error.response.data.validation.groupId
            } else {
                errorMessage.value = error.response.data.message
            }
        }

    }


}

</script>

<template>
    <div class="join-group-modal">
        <div class="join-group-modal-content">
            <div class="join-group-modal-content-header">
                <h5>그룹 참가</h5>
            </div>
            <div class="join-group-modal-content-body">
<!--                <label class="me-3">그룹 코드 : </label>-->
                <input type="text" v-model="joinGroupRequest.key" placeholder="그룹 코드">
                <span v-show="errorMessage" class="input-error">{{ errorMessage }}</span>

            </div>
            <div class="join-group-modal-content-footer">
                <button class="btn btn-secondary me-3" @click="joinGroup">참가</button>
                <button class="btn btn-secondary" @click="closeModal">닫기</button>
            </div>
        </div>
    </div>
</template>

<style scoped>

.join-group-modal {
    width: 300px; height: 200px;
    border: 1px solid darkgray;
    border-radius: 8px;
    z-index: 999;
    position: absolute;
    top: 10px;
    background: white;
}
.join-group-modal-content {
    width: 100%; height: 100%;
    display: flex;
    flex-direction: column;

}
.join-group-modal-content-header {
    flex: 1;
    display: flex;
    justify-content: center;
    align-items: center;
    border-top-left-radius: 8px;
    border-top-right-radius: 8px;
}
.join-group-modal-content-body {
    flex: 2;
    border-top: 1px solid darkgray;
    border-bottom: 1px solid darkgray;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}
.join-group-modal-content-footer {
    flex: 1;
    display: flex;
    justify-content: center;
    align-items: center;
    border-bottom-left-radius: 8px;
    border-bottom-right-radius: 8px;
}

.join-group-modal-content button {
}

.input-error {
    color: red;
}
</style>
