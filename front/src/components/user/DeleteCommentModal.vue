<script lang="ts" setup>
import {defineEmits} from 'vue'


const emit = defineEmits(['deleteComment', 'closeModal']);

const closeModal = function () {
    emit('closeModal')
}

const deletePost = function () {
    emit('deleteComment')
}

</script>

<template>
    <div class="delete-group-modal">
        <div class="delete-group-modal-content">
            <div class="delete-group-modal-content-header">
                <span>게시글 삭제</span>
            </div>
            <div class="delete-group-modal-content-body">
                <b>정말로 삭제하시겠습니까?</b>
            </div>
            <div class="delete-group-modal-content-footer">
                <button class="btn btn-secondary me-3" @click="deletePost">확인</button>
                <button class="btn btn-secondary" @click="closeModal">닫기</button>
            </div>
        </div>
    </div>
</template>

<style scoped>
span {
    font-size: 20px;
}
.delete-group-modal {
    width: 300px; height: 200px;
    border: 1px solid darkgray;
    border-radius: 8px;
    z-index: 999;
    position: absolute;
    top: 10px;
    background: white;
}
.delete-group-modal-content {
    width: 100%; height: 100%;
    display: flex;
    flex-direction: column;

}
.delete-group-modal-content-header {
    flex: 1;
    display: flex;
    justify-content: center;
    align-items: center;
    border-top-left-radius: 8px;
    border-top-right-radius: 8px;
}
.delete-group-modal-content-body {
    flex: 2;
    border-top: 1px solid darkgray;
    border-bottom: 1px solid darkgray;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}
.delete-group-modal-content-footer {
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
    .delete-group-modal {
        width: 200px; height: 150px;
        border: 1px solid darkgray;
        border-radius: 8px;
        z-index: 999;
        position: absolute;
        top: 10px;
        background: white;
    }
    .delete-group-modal-content {
        width: 100%; height: 100%;
        display: flex;
        flex-direction: column;

    }
    .delete-group-modal-content-header {
        flex: 1;
        display: flex;
        justify-content: center;
        align-items: center;
        border-top-left-radius: 8px;
        border-top-right-radius: 8px;
    }
    .delete-group-modal-content-body {
        flex: 2;
        border-top: 1px solid darkgray;
        border-bottom: 1px solid darkgray;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
    }
    .delete-group-modal-content-footer {
        flex: 1;
        display: flex;
        justify-content: center;
        align-items: center;
        border-bottom-left-radius: 8px;
        border-bottom-right-radius: 8px;
    }
}
</style>
