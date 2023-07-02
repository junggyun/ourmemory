<script lang="ts" setup>
import {defineEmits, defineProps, onMounted, ref} from 'vue';
import {getUploadByPostAPI} from "@/api";
import store from "@/store";
import DeletePostModal from "@/components/user/DeletePostModal.vue";

const isDeletePostModal = ref(false)

const props = defineProps({
    postData: {
        type: Object,
        required: true
    }
});

const emit= defineEmits(['groupHome', 'editPost'])

const postId = ref("")
const title = ref("")
const content = ref("")
const createdDate = ref("")
const userNickName = ref("")
const uploads = ref([{
    id: null,
    fileName: "",
    filePath: "",
}])


const setup = async function () {
    try {
        postId.value = props.postData.postId
        title.value = props.postData.title
        content.value = props.postData.content
        createdDate.value = props.postData.createdDate
        userNickName.value = props.postData.user.nickName

        const result = await getUploadByPostAPI(postId.value);
        uploads.value = result.data;
    } catch (error) {
        console.log(error)
    }

}

const viewDeletePostModal = function () {
    isDeletePostModal.value = true
};

const closeDeletePostModal = function () {
    isDeletePostModal.value = false
    emit('groupHome')
};

const viewEditPostForm = function () {
    emit('editPost', postId.value)
}

onMounted(setup)
</script>

<template>
    <div class="post-wrap">
        <div class="post-title">
            <div class="mt-2 mb-2">
                <span style="font-size: 30px; margin-left: 20px">{{ title }}</span>
            </div>
            <div class="post-date">
                <span style="font-size: 15px;">{{ createdDate }}</span>
            </div>
        </div>
        <div class="post-writer">
            <div class="mt-2 mb-2">
                <span style="font-size: 15px; margin-left: 20px">{{ userNickName }}</span>
            </div>
            <div class="post-edit-delete">
                <button v-show="userNickName === store.state.userData.nickName" type="button" class="btn btn-outline-danger- " @click="viewEditPostForm" style="text-decoration: underline; color: darkgray; padding: 0">수정</button>
                <button v-show="userNickName === store.state.userData.nickName || store.state.userGroupRole === 'HOST'" type="button" class="btn btn-outline-danger- " @click="viewDeletePostModal" style="text-decoration: underline; color: darkgray; ">삭제</button>
            </div>
        </div>
        <div class="post-content">

            <div v-for="upload in uploads" :key="upload.id" class="post-img">
                <img :src="upload.filePath">
            </div>

            <div class="delete-post" v-if="isDeletePostModal">
                <DeletePostModal :postData="postData" @closeModal="closeDeletePostModal"></DeletePostModal>
            </div>

            <pre>{{ content }}</pre>

        </div>
    </div>
</template>

<style scoped>
.post-wrap {
    display: flex;
    flex-direction: column;
}
.post-title {
    flex: 1;
    border-top: 1px solid gainsboro;
    border-bottom: 1px solid gainsboro;
    background: rgba(0,0,0,0.05);
    display: flex;
    justify-content: space-between;
}
.post-writer {
    display: flex;
    flex: 1;
    border-bottom: 1px solid gainsboro;
    justify-content: space-between;
}
.post-edit-delete {

}
.post-date{
    margin-right: 10px;
    display: flex;
    align-items: end;
}
.post-content {
    flex: 9;
    margin-left: 20px;
    display: flex;
    flex-direction: column;
}
.post-img {
    width: 70%;
    margin: 20px 10px 0 0;
}

img {
    max-width: 100%;
    height: auto;
}

.delete-post {
    width: 100%;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    position: absolute;
}

pre {
    margin: 20px 10px 0 0;
    font-size: 15px;
}
</style>
