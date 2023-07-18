<script lang="ts" setup>
import {onMounted, ref} from 'vue';
import {getPostAPI, getUploadByPostAPI} from "@/api";
import store from "@/store";
import DeletePostModal from "@/components/user/DeletePostModal.vue";
import CommentListView from "@/components/user/CommentListView.vue";
import router from "@/router";

const isDeletePostModal = ref(false)

const userId = ref(store.state.userId)
const groupId = ref(store.state.groupData.id)
const postId = ref(store.state.postData.id)
const title = ref("")
const content = ref("")
const createdDate = ref("")
const viewCount = ref(0)
const userNickName = ref("")
const uploads = ref([{
    id: null,
    fileName: "",
    filePath: "",
}])

const postData = ref({
    postId: postId.value
})

const goGroup = function () {
    store.commit('clearPost')
    router.push(`/${userId.value}/${groupId.value}`)
}

const setup = async function () {
    try {
        const postResult = await getPostAPI(postId.value);
        store.commit('setPostId', postResult.data.postId)
        store.commit('setPostTitle', postResult.data.title)
        store.commit('setPostContent', postResult.data.content)
        store.commit('setPostCreatedDate', postResult.data.createdDate)
        store.commit('setPostUserNickName', postResult.data.user.nickName)
        store.commit('setPostViewCount', postResult.data.viewCount)
        title.value = postResult.data.title
        content.value = postResult.data.content
        createdDate.value = postResult.data.createdDate
        viewCount.value = postResult.data.viewCount
        userNickName.value = postResult.data.user.nickName
        const result = await getUploadByPostAPI(postId.value);
        uploads.value = result.data;
    } catch (error) {
        console.log(error)
    }

}

const goEditPost = function () {
    router.push(`/${userId.value}/${groupId.value}/${postId.value}/edit`)
}

const viewDeletePostModal = function () {
    isDeletePostModal.value = true
};

const closeDeletePostModal = function () {
    isDeletePostModal.value = false

};


onMounted(setup)
</script>

<template>
    <div class="post-wrap">
        <div>
            <h4 @click="goGroup">{{ store.state.groupData.name }}</h4>
        </div>
        <div class="post-title">
            <div class="mt-2 mb-2">
                <span style="margin-left: 20px">{{ title }}</span>
            </div>
            <div class="post-date">
                <span>{{ createdDate }}</span>
                <span>조회 {{ viewCount }}</span>
            </div>
        </div>
        <div class="post-writer">
            <div class="mt-2 mb-2">
                <span style="margin-left: 20px">{{ userNickName }}</span>
            </div>
            <div class="post-edit-delete">
                <button v-show="userNickName === store.state.userData.nickName" type="button" class="btn btn-outline-danger- " @click="goEditPost" style="text-decoration: underline; color: darkgray; padding: 0">수정</button>
                <button v-show="userNickName === store.state.userData.nickName || store.state.userGroupRole === 'HOST'" type="button" class="btn btn-outline-danger- " @click="viewDeletePostModal" style="text-decoration: underline; color: darkgray; ">삭제</button>
            </div>
        </div>
        <div class="post-content">

            <div v-for="upload in uploads" :key="upload.id" class="post-img">
                <img :src="upload.filePath">
            </div>

            <pre>{{ content }}</pre>

        </div>
        <div class="post-comment">
            <CommentListView :postData="postData"></CommentListView>
        </div>
        <div class="post-modal">
            <div class="delete-post" v-if="isDeletePostModal">
                <DeletePostModal :postData="postData" @closeModal="closeDeletePostModal"></DeletePostModal>
            </div>
        </div>
    </div>
</template>

<style scoped>
.post-wrap {
    display: flex;
    flex-direction: column;
    width: 50vw;
    position: relative;

}
span {
    font-size: 20px;
}
h4 {
    pointer-events: auto;
    cursor : pointer;
    margin-bottom: 20px;
    color: darkgray;
    display: inline-block;
}
h4:hover {
    text-decoration: underline;
}
.post-title {
    border-top: 1px solid gainsboro;
    border-bottom: 1px solid gainsboro;
    background: rgba(0,0,0,0.05);
    display: flex;
    justify-content: space-between;
}
.post-writer {
    display: flex;
    border-bottom: 1px solid gainsboro;
    justify-content: space-between;
}
.post-writer span {
    font-size: 15px;
}
.post-date{
    margin-right: 10px;
    display: flex;
    flex-direction: column;
    align-items: end;
    justify-content: space-between;
}
.post-date span {
    font-size: 13px
}
.post-content {
    margin-left: 20px;
    display: flex;
    flex-direction: column;
    min-height: 100px;
}
.post-img {
    width: 70%;
    margin: 20px 10px 0 0;
}

img {
    max-width: 100%;
    height: auto;
}
pre {
    margin: 20px 10px 0 0;
    font-size: 15px;
}
.post-modal {
    position: absolute;
    left: 50%;
    margin: 200px 0 0 -150px;
}
@media screen and (max-width: 768px){
    .post-wrap {
        width: 90vw;
    }
    .post-modal {
        position: absolute;
        left: 50%;
        margin: 200px 0 0 -100px;
    }
    span {
        font-size: 15px;
    }
    .post-writer span {
        font-size: 13px;
    }
    pre {
        font-size: 13px;
    }
}
</style>
