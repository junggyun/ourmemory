<script lang="ts" setup>
import {defineEmits, defineProps} from 'vue'
import {deletePostAPI} from "@/api";

const props = defineProps({
    postData: {
        type: Object,
        required: true
    }
});

const emit = defineEmits(['closeModal']);

const closeModal = function () {
    emit('closeModal')
}

const deletePost = async function () {
    try {
        await deletePostAPI(props.postData.postId)
        closeModal()
    } catch (error) {
        console.log(error)
    }

}

</script>

<template>
    <div class="delete-group-modal">
        <div class="delete-group-modal-content">
            <div class="delete-group-modal-content-header">
                <h5>게시글 삭제</h5>
            </div>
            <div class="delete-group-modal-content-body">
                <span>정말로 삭제하시겠습니까?</span>
            </div>
            <div class="delete-group-modal-content-footer">
                <button class="btn btn-secondary me-3" @click="deletePost">확인</button>
                <button class="btn btn-secondary" @click="closeModal">닫기</button>
            </div>
        </div>
    </div>
</template>

<style scoped>

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

.delete-group-modal-content button {
}
</style>
