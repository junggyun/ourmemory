<script lang="ts" setup>
import {deleteCommentAPI, getCommentsByPostAPI, getPostAPI} from "@/api";
import {defineProps, onMounted, ref} from "vue";
import CreateCommentForm from "@/components/user/CreateCommentForm.vue";
import store from "@/store";
import DeleteCommentModal from "@/components/user/DeleteCommentModal.vue";

const postNickName = ref(0)
const myNickName = store.state.userData.nickName
const sizeNum = ref(10)
const pageNum = ref(1)
const totalPages = ref(0)
const totalCount = ref(0)

const dynamicComponent = ref("")

const props = defineProps({
    postData: {
        type: Object,
        required: true
    }
});

const delCommentId = ref(0)

const comments = ref([{
    commentId: 0,
    postId: 0,
    userNickName: "",
    content: "",
    createdDate: ""
}])

const nextPage = async function () {
    pageNum.value += 1
    await getComments()
}

const prevPage = async function () {
    pageNum.value -= 1
    await getComments()
}

const getComments = async function () {
    try {
        const getCommentsRequest = {
            postId: props.postData.postId,
            size: sizeNum.value,
            page: pageNum.value

        }

        const result = await getCommentsByPostAPI(getCommentsRequest);
        totalCount.value = result.data.totalCount
        totalPages.value = result.data.totalPages
        comments.value = result.data.comments
    } catch (error) {
        console.log(error)
    }
};

const viewDeleteCommentModal = function (commentId: any) {
    dynamicComponent.value = "deleteCommentModal"
    delCommentId.value = commentId
}

const closeDeleteCommentModal = function () {
    dynamicComponent.value = ""
}

const deleteComment = async function () {
    try {
        const commentId = delCommentId.value
        await deleteCommentAPI(commentId)
        await getComments()
        dynamicComponent.value = ""
    } catch (error) {
        console.log(error)
    }
};

const getPost = async function () {
    try {
        const postId = props.postData.postId
        const result = await getPostAPI(postId);
        postNickName.value = result.data.user.nickName
        await getComments()
    } catch (error) {
        console.log(error)
    }
};

onMounted(getPost)
</script>

<template>
    <div class="comment-wrap">
        <div class="comment-title">
                <span style="margin-left: 10px">댓글 : {{ totalCount }}개</span>
        </div>
        <div class="comment-item" v-for="comment in comments" :key="comment.commentId">
            <div class="comment-item-header">
                <span style="margin-right: 15px;"><b>{{ comment.userNickName }}</b></span>
                <span style="color: darkgray">{{ comment.createdDate }}</span>
                <button class="btn btn-outline-danger-" @click="viewDeleteCommentModal(comment.commentId)" v-show="comment.userNickName === myNickName || postNickName === myNickName"
                        style="text-decoration: underline; color: darkgray; padding: 0; position: absolute; right: 0; margin-right: 10px;font-size: 12px" >
                    삭제
                </button>
            </div>
            <div class="comment-item-footer">
                <pre style="margin-top: 5px">{{ comment.content }}</pre>
            </div>
        </div>
        <div class="btn-cover">
            <button :disabled="pageNum === 1" @click="prevPage" class="page-btn">이전</button>
            <span class="page-count">{{ pageNum  }} / {{ totalPages }} 페이지</span>
            <button :disabled="pageNum >= totalPages" @click="nextPage" class="page-btn">다음</button>
        </div>
        <div class="comment-create">
            <CreateCommentForm :postData="postData" @refresh="getComments"></CreateCommentForm>
        </div>
        <div class="comment-modal">
            <DeleteCommentModal v-show="dynamicComponent === 'deleteCommentModal'" @deleteComment="deleteComment" @closeModal="closeDeleteCommentModal"></DeleteCommentModal>
        </div>
    </div>
</template>

<style scoped>
.comment-wrap {
    margin-top: 50px;
    border-top: 1px solid gainsboro;
    position: relative;
}
.comment-title {
    display: flex;
    align-items: center;
    margin-top: 10px;
    border: 1px solid gainsboro;
    border-radius: 4px;
    background-color: rgba(0,0,0,0.05);
    height: 40px;
}
.comment-item {
    display: flex;
    flex-direction: column;
    justify-content: center;
    margin-top: 5px;
    height: 60px;
    border-bottom: 1px solid gainsboro;

}
.comment-item span {
    font-size: 13px;
}
.comment-item-header {

}
.comment-item-header, .comment-item-footer {
    margin-left: 10px;
    position: relative;
}
.btn-cover {
    margin-top: 40px;
    text-align: center;
}
.btn-cover .page-btn {
    width: 4rem;
    height: 2rem;
    letter-spacing: 0.5px;
}
.btn-cover .page-count {
    font-size: 15px;
    padding: 0 1rem;
}
.comment-modal {
    position: absolute;
    top: 50%;
    left: 50%;
    margin: -150px 0 0 -100px;
}
</style>
