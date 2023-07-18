<script lang="ts" setup>
import {defineEmits} from 'vue'
import store from "@/store";
import {deleteUserAPI} from "@/api";
import router from "@/router";

const emit = defineEmits(['closeModal']);

const closeModal = function () {
    emit('closeModal')
}

const deleteUser = async function () {
    try {
        await deleteUserAPI(store.state.userId)
        closeModal()
        await router.push('/')
    } catch (error) {
        console.log(error)
    }

}

</script>

<template>
    <div class="delete-user-modal">
        <div class="delete-user-modal-content">
            <div class="delete-user-modal-content-header">
                <span>회원 탈퇴</span>
            </div>
            <div class="delete-user-modal-content-body">
                <b>정말로 탈퇴하시겠습니까?</b>
                <b style="color: red">(회원님의 모든 정보가 삭제됩니다!)</b>

            </div>
            <div class="delete-user-modal-content-footer">
                <button class="btn btn-secondary me-3" @click="deleteUser">확인</button>
                <button class="btn btn-secondary" @click="closeModal">닫기</button>
            </div>
        </div>
    </div>
</template>

<style scoped>
span {
    font-size: 20px;
}
.delete-user-modal {
    width: 300px; height: 200px;
    border: 1px solid darkgray;
    border-radius: 8px;
    z-index: 999;
    position: absolute;
    top: 10px;
    background: white;
}
.delete-user-modal-content {
    width: 100%; height: 100%;
    display: flex;
    flex-direction: column;

}
.delete-user-modal-content-header {
    flex: 1;
    display: flex;
    justify-content: center;
    align-items: center;
    border-top-left-radius: 8px;
    border-top-right-radius: 8px;
}
.delete-user-modal-content-body {
    flex: 2;
    border-top: 1px solid darkgray;
    border-bottom: 1px solid darkgray;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}
.delete-user-modal-content-footer {
    flex: 1;
    display: flex;
    justify-content: center;
    align-items: center;
    border-bottom-left-radius: 8px;
    border-bottom-right-radius: 8px;
}

@media screen and (max-width: 768px) {
    span {
        font-size: 15px;
    }
    b {
        font-size: 12px;
    }
    button {
        font-size: 11px;
        padding: 5px 10px 5px 10px
    }
    .delete-user-modal {
        width: 200px; height: 150px;
        border: 1px solid darkgray;
        border-radius: 8px;
        z-index: 999;
        position: absolute;
        top: 10px;
        background: white;
    }
    .delete-user-modal-content {
        width: 100%; height: 100%;
        display: flex;
        flex-direction: column;

    }
    .delete-user-modal-content-header {
        flex: 1;
        display: flex;
        justify-content: center;
        align-items: center;
        border-top-left-radius: 8px;
        border-top-right-radius: 8px;
    }
    .delete-user-modal-content-body {
        flex: 2;
        border-top: 1px solid darkgray;
        border-bottom: 1px solid darkgray;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
    }
    .delete-user-modal-content-footer {
        flex: 1;
        display: flex;
        justify-content: center;
        align-items: center;
        border-bottom-left-radius: 8px;
        border-bottom-right-radius: 8px;
    }
}
</style>
