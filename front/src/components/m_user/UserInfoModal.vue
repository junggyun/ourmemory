<script lang="ts" setup>
import {defineEmits, defineProps} from 'vue'


const emit = defineEmits(['closeModal']);

const props = defineProps({
    userData: {
        type: Object,
        required: true
    }
});

const closeModal = function () {
    emit('closeModal')
}



</script>

<template>
    <div class="info-user-modal">
        <div class="info-user-modal-content">
            <div class="info-user-modal-content-header">
                <h5>회원 정보</h5>
            </div>
            <div class="info-user-modal-content-body">
                <span>이름 : {{ props.userData.name }}</span>
                <span>닉네임 : {{ props.userData.nickName }}</span>
                <span>이메일 : {{ props.userData.email }}</span>

            </div>
            <div class="info-user-modal-content-footer">
                <button class="btn btn-secondary" @click="closeModal">닫기</button>
            </div>
        </div>
    </div>
</template>

<style scoped>

.info-user-modal {
    width: 300px; height: 200px;
    border: 1px solid darkgray;
    border-radius: 8px;
    z-index: 999;
    position: absolute;
    top: 10px;
    background: white;
}
.info-user-modal-content {
    width: 100%; height: 100%;
    display: flex;
    flex-direction: column;

}
.info-user-modal-content-header {
    flex: 1;
    display: flex;
    justify-content: center;
    align-items: center;
    border-top-left-radius: 8px;
    border-top-right-radius: 8px;
}
.info-user-modal-content-body {
    flex: 2;
    border-top: 1px solid darkgray;
    border-bottom: 1px solid darkgray;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}
.info-user-modal-content-body span {
    width: 80%;
}

.info-user-modal-content-footer {
    flex: 1;
    display: flex;
    justify-content: center;
    align-items: center;
    border-bottom-left-radius: 8px;
    border-bottom-right-radius: 8px;
}

.info-user-modal-content button {
}
</style>
