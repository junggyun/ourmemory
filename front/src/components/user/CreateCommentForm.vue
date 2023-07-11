<script lang="ts" setup>

import {defineProps, defineEmits, ref} from "vue";
import store from "@/store";
import {createCommentAPI} from "@/api";

const content = ref("")

const emit = defineEmits(['refresh'])

const props = defineProps({
    postData: {
        type: Object,
        required: true
    }
});

const createComment = async function () {
    try {
        const createCommentRequest = {
            userId: store.state.userId,
            postId: props.postData.postId,
            content: content.value
        }
        await createCommentAPI(createCommentRequest);
        emit('refresh')
        content.value = ""

    } catch (error) {
        console.log(error)
    }
};
</script>

<template>
    <div class="comment-create-wrap">
        <div class="comment-create-form">
            <b style="font-size: 13px; margin-bottom: 10px; margin-left: 5px">댓글 쓰기</b>
          <textarea  v-model="content" style="resize: none; border-radius: 4px; padding: 5px;" rows="3"></textarea>
        </div>
        <div class="comment-create-btn">
            <button class="btn btn-secondary" @click="createComment" style="height: 50px">등록</button>
        </div>
    </div>
</template>

<style scoped>
.comment-create-wrap {
    margin-top: 30px;
    width: 100%;
    height: 140px;
    background-color: rgba(0,0,0,0.05);
    border-radius: 8px;
    display: flex;
    justify-content: space-between;
    align-items: center;
}
.comment-create-form {
    flex: 9;
    width: 80%;
    margin-left: 20px;
    display: flex;
    flex-direction: column;
}
.comment-create-btn {
    flex: 1;
    display: flex;
    justify-content: center;
    align-items: center;
}
</style>
