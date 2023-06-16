<script lang="ts" setup>
import store from "@/store";
import {ref} from "vue";
import GroupKeyModal from "@/components/user/GroupKeyModal.vue";
import CreatePostForm from "@/components/user/CreatePostForm.vue";
import PostListView from "@/components/user/PostListView.vue";
import UserListView from "@/components/user/UserListView.vue";
import PostView from "@/components/user/PostView.vue";

const dynamicComponent = ref("")
const isGroupKeyModal = ref(false)
const findPost = ref({
    title: "",
    content: "",
    createdDate: "",
    userNickName: "",
})

const viewGroupKey = function () {
    isGroupKeyModal.value = true
}

const closeGroupKey = function () {
    isGroupKeyModal.value = false
}

const viewCreatePostForm = function () {
    dynamicComponent.value = "CreatePostForm"
}

const viewPost = function (postData: any) {
    findPost.value.title = postData.title
    findPost.value.content = postData.content
    findPost.value.createdDate = postData.createdDate
    findPost.value.userNickName = postData.user.nickName
    dynamicComponent.value = "ViewPost"
}

const groupHome = function () {
    dynamicComponent.value = ""
}

</script>

<template>
    <h4 @click="groupHome">{{ store.state.groupData.name }}</h4>
    <div class="post-add" v-if="dynamicComponent === ''">
        <button class="btn btn-outline-success" @click="viewGroupKey">
            <i class="bi bi-folder-plus"></i>
            멤버 초대
        </button>
        <button class="btn btn-outline-danger" @click="viewCreatePostForm" >
            <i class="bi bi-folder-plus"></i>
            글 작성
        </button>
    </div>
    <div class="user-post-wrap">
        <div class="group-user-list" v-if="dynamicComponent === ''">
            <UserListView></UserListView>
        </div>
        <div class="group-code" v-if="isGroupKeyModal">
            <GroupKeyModal @closeGroupKey="closeGroupKey"></GroupKeyModal>
        </div>
        <div class="post-content-wrap mt-2">
            <div class="post-list" v-if="dynamicComponent === ''">
                <PostListView @viewPost="viewPost"></PostListView>
            </div>
            <div class="post-create" v-if="dynamicComponent === 'CreatePostForm'">
                <CreatePostForm @groupHome="groupHome"></CreatePostForm>
            </div>
            <div class="post-view" v-if="dynamicComponent === 'ViewPost'">
                <PostView :postData="findPost"></PostView>
            </div>
        </div>
    </div>


</template>

<style scoped>
h4 {
    pointer-events: auto;
    cursor : pointer;
    margin-bottom: 20px;
    color: darkgray;
}
h4:hover {
    text-decoration: underline;
}
.post-add {
    display: flex;
    justify-content: space-between;
    border: 1px solid darkgray;
    border-radius: 8px;
}
.post-add button {
    margin: 0.5em;
}
.user-post-wrap {
    display: flex;
}
.group-user-list {
    flex: 2;
}
.post-content-wrap {
    flex: 8;
}
.group-code {
    width: 100%;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    position: absolute;
}
</style>
