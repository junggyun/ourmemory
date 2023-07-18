<script lang="ts" setup>
import store from "@/store";
import {onMounted, ref} from "vue";
import GroupKeyModal from "@/components/user/GroupKeyModal.vue";
import DeleteGroupModal from "@/components/user/DeleteGroupModal.vue";
import LeaveGroupModal from "@/components/user/LeaveGroupModal.vue";
import UserInfoModal from "@/components/user/UserInfoModal.vue";
import router from "@/router";
import EditGroupModal from "@/components/user/EditGroupModal.vue";
import {addViewCountAPI, getPostByGroupAPI, getUserByGroupAPI} from "@/api";
import dayjs from "dayjs";

const userId = ref(store.state.userId)
const groupId = ref(store.state.groupData.id)
const isGroupKeyModal = ref(false)
const isDeleteGroupModal = ref(false)
const isLeaveGroupModal = ref(false)
const isUserInfoModal = ref(false)
const isEditGroupModal = ref(false)

const infoUser = ref({
    id: 0,
    name: "",
    nickName: "",
    email: "",
})

const sizeNum = ref(20)
const pageNum = ref(1)
const totalPages = ref(0)
const posts = ref([{
    postId: 0,
    user: {
        id: 0,
        name: "",
        nickName: "",
        email: "",
        role: ""
    },
    title: "",
    content: "",
    createdDate: "",
    createdDateSimple: "",
    modifiedDate: "",
    thumbnailPath: "",
    viewCount: 0,
    commentCount: 0,
    newCommentDate: "",
    isNewComment: false,
    uploads: [{
        id: 0,
        fileName: "",
        filePath: "",
    }]
}
])

const viewGroupKey = function () {
    isGroupKeyModal.value = true
}

const closeGroupKey = function () {
    isGroupKeyModal.value = false
}

const viewDeleteGroupModal = function () {
    isDeleteGroupModal.value = true
}

const closeDeleteGroupModal = function () {
    isDeleteGroupModal.value = false
}

const viewLeaveGroupModal = function () {
    isLeaveGroupModal.value = true
}

const closeLeaveGroupModal = function () {
    isLeaveGroupModal.value = false
}

const viewUserInfoModal = function (user: any) {
    isUserInfoModal.value = true
    infoUser.value.id = user.id
    infoUser.value.name = user.name
    infoUser.value.nickName = user.nickName
    infoUser.value.email = user.email
};

const closeUserInfoModal = function () {
    isUserInfoModal.value = false
};

const viewEditGroupModal = function () {
    isEditGroupModal.value = true
}

const closeEditGroupModal = function () {
    isEditGroupModal.value = false
};

const goCreatePost = function () {
    router.push(`/${userId.value}/${groupId.value}/post`)
}

const goGroup = function () {
    router.push(`/${userId.value}/${groupId.value}`)
    window.location.reload()
}


//UserList
const users = ref([])
const getUsers = async function () {
    try {
        const result = await getUserByGroupAPI(store.state.groupData.id);
        users.value = result.data.users
        await getPostByGroup()
    } catch (error) {
        console.log(error)
    }
}

//UserList End

//PostList


const nextPage = async function () {
    pageNum.value += 1
    await getPostByGroup()
}

const prevPage = async function () {
    pageNum.value -= 1
    await getPostByGroup()
}

const getPostByGroup = async function () {
    try {
        const getPostRequest = {
            groupId: store.state.groupData.id,
            size: sizeNum.value,
            page: pageNum.value,
        }

        const result = await getPostByGroupAPI(getPostRequest);
        posts.value = result.data.posts

        totalPages.value = result.data.totalPages

        const currDate = dayjs()
        for (const post of posts.value) {
            const daySame = currDate.isSame(post.createdDate, 'day')
            if (daySame) {
                post.createdDateSimple = dayjs(post.createdDate).format('HH:mm')
            } else {
                post.createdDateSimple = dayjs(post.createdDate).format('YYYY.MM.DD')
            }
            const hourDiff = currDate.diff(post.newCommentDate, 'hour')
            if (hourDiff < 24) {
                post.isNewComment = true
            } else {
                post.isNewComment = false
            }
        }
    } catch (error) {
        console.log(error)
    }
}

const goPost = async function (post: any) {

    await store.commit('setPostId', post.postId)
    await addViewCountAPI(post.postId)

    await router.push(`/${userId.value}/${groupId.value}/${post.postId}`)
}
//PostList End

onMounted(getUsers)
</script>

<template>
    <div class="group-wrap">
        <div>
            <h4 @click="goGroup">{{ store.state.groupData.name }}</h4>
            <i v-show="store.state.userGroupRole === 'HOST'" @click="viewEditGroupModal" class="edit-group-btn bi-pencil-square"></i>
        </div>

        <div class="post-add">
            <button class="btn btn-outline-success" @click="viewGroupKey">
                <i class="bi bi-folder-plus"></i>
                멤버 초대
            </button>
            <button class="btn btn-outline-danger" @click="goCreatePost" >
                <i class="bi bi-folder-plus"></i>
                글 작성
            </button>
        </div>
        <div class="user-post-wrap">
            <div class="post-del">
<!--                UserList-->
                <div class="user-list-wrap">
                    <div class="user-items">
                        <div class="user-item d-flex position-relative border mt-2  align-items-center " v-for="user in users" :key="user.user.id"
                             @click="viewUserInfoModal(user.user)">
                            <img v-show="user.role === 'HOST'" src="@/image/host.png" class="flex-shrink-0 ms-1  " alt="">
                            <img v-show="user.role === 'MEMBER'" src="@/image/userImage.jpg" class="flex-shrink-0 ms-1  " alt="">
                            <div class="d-flex justify-content-center" style="width: 100%">
                                <span style="font-size: 14px">{{ user.user.nickName }}</span>
                                <a href="#" class="stretched-link" ></a>
                            </div>
                        </div>
                    </div>
                </div>
<!--                UserList End-->
                <button v-show="store.state.userGroupRole === 'HOST'" class="btn btn-outline-danger mt-2" @click="viewDeleteGroupModal" >
                    <i class="bi bi-box-arrow-left"></i>
                    그룹 삭제
                </button>
                <button v-show="store.state.userGroupRole === 'MEMBER'" class="btn btn-outline-danger mt-2" @click="viewLeaveGroupModal" >
                    <i class="bi bi-box-arrow-left"></i>
                    그룹 탈퇴
                </button>
            </div>
            <div class="post-content-wrap mt-2">
                <div class="post-list">
<!--                    PostList-->
                    <div class="post-list-wrap">
                        <div class="post-items">
                            <div class="post-item d-flex position-relative mb-1" v-for="post in posts" :key="post.postId" style="height: 50px">
                                <img v-if="post.uploads.length == 0" src="@/image/no-image.png" class="flex-shrink-0 me-2" alt="..." @click="goPost(post)">
                                <img v-if="post.uploads.length > 0" :src="post.uploads[0].filePath" class="flex-shrink-0 me-2" alt="..." @click="goPost(post)">
                                <div class="post-list-item">
                                    <div class="post-list-title">
                                        <div>
                                            <span class="post-title" style="color: black" @click="goPost(post)">{{post.title}} [{{ post.commentCount }}]</span>
                                            <img v-if="post.isNewComment" src="@/image/new.png" style="width: 17px; height: 17px; margin-left: 5px">
                                        </div>
                                        <span style="font-size: 13px; color: lightslategray">{{post.createdDateSimple}} / {{post.user.nickName}}</span>
                                    </div>
                                    <div class="post-list-view-count">
                                        <span style="font-size: 13px; margin-right: 10px">조회 {{post.viewCount}}</span>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="btn-cover">
                            <button :disabled="pageNum === 1" @click="prevPage" class="page-btn">이전</button>
                            <span class="page-count">{{ pageNum  }} / {{ totalPages }} 페이지</span>
                            <button :disabled="pageNum >= totalPages" @click="nextPage" class="page-btn">다음</button>
                        </div>
                    </div>
<!--                    PostList End-->
                </div>
            </div>
        </div>
        <div class="group-modal">
            <div class="group-code" v-if="isGroupKeyModal">
                <GroupKeyModal @closeGroupKey="closeGroupKey"></GroupKeyModal>
            </div>
            <div class="leave-group" v-if="isLeaveGroupModal">
                <LeaveGroupModal @closeModal="closeLeaveGroupModal"></LeaveGroupModal>
            </div>
            <div class="delete-group" v-if="isDeleteGroupModal">
                <DeleteGroupModal @closeModal="closeDeleteGroupModal"></DeleteGroupModal>
            </div>
            <div class="info-user" v-if="isUserInfoModal">
                <UserInfoModal :userData="infoUser" @closeModal="closeUserInfoModal"></UserInfoModal>
            </div>
            <div class="edit-group" v-if="isEditGroupModal">
                <EditGroupModal @closeModal="closeEditGroupModal"></EditGroupModal>
            </div>
        </div>
    </div>




</template>

<style scoped>
.group-wrap {
    width: 50vw;
    position: relative;
}
h4 {
    pointer-events: auto;
    cursor : pointer;
    margin-bottom: 20px;
    color: darkgray;
    display: inline-block;
}
.edit-group-btn {
    pointer-events: auto;
    cursor : pointer;
    margin-left: 30px;
    color: rgba(0,0,0,0.2);
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
.post-del {
    flex: 2;
    margin-right: 10px;
}
.post-content-wrap {
    flex: 8;
}
.group-modal {
    position: absolute;
    top: 50%;
    left: 50%;
    margin: -300px 0 0 -150px;
}

.user-item img {
    width: 30px;
}
.user-item {
    height: 50px
}

.post-item img {
    width: 50px;
    height: 50px;
}
.post-title, .post-item img {
    pointer-events: auto;
    cursor : pointer;
}
.post-title:hover {
    text-decoration: underline;
}
.btn-cover {
    margin-top: 40px;
    text-align: center;
    font-size: 15px;
}
.btn-cover .page-btn {
    width: 4rem;
    height: 2rem;
    letter-spacing: 0.5px;

}
.post-list-wrap {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
}
.post-items {
}
.post-list-item{
    width: 100%;
    display: flex;
    justify-content: space-between;
}
.post-list-title{
    display: flex;
    flex-direction: column;
    justify-content: center;
}
.post-list-view-count{
    display: flex;
    align-items: center;
}
.btn-cover .page-count {
    padding: 0 1rem;
}

@media screen and (max-width: 768px) {
    .group-wrap {
        width: 90vw;
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
    .edit-group-btn {
        pointer-events: auto;
        cursor : pointer;
        margin-left: 30px;
        color: rgba(0,0,0,0.2);
        display: inline-block;
    }
    .post-add {
        display: flex;
        justify-content: space-between;
        border: 1px solid darkgray;
        border-radius: 8px;
    }
    .post-add button {
        margin: 0.5em;
        padding: 5px 10px 5px 10px;
        font-size: 13px;
    }
    .user-post-wrap {
        display: flex;
        flex-direction: column-reverse;
    }
    .post-del {
        flex: 2;
    }
    .post-del button {
        padding: 5px 10px 5px 10px;
        font-size: 13px;
    }
    .post-content-wrap {
        flex: 8;
    }
    .group-modal {
        position: absolute;
        top: 50%;
        left: 50%;
        margin: -150px 0 0 -100px;
    }

    .user-list-wrap {
        display: flex;

    }
    .user-item img {
        width: 30px;
    }
    .user-items {
        display: flex;
        flex-wrap: wrap;
        width: 100%;
        overflow: auto;
        justify-content: flex-start;
    }
    .user-item {
        display: flex;
        position: relative;
        width: 25%;
        height: 40px;
    }
    .user-post-wrap {
        position: relative;
    }
    .group-code, .delete-group, .leave-group, .info-user, .edit-group {
        width: 100%;
        height: 100%;

        position: absolute;
        top: 0;
        left: 0;
    }
    .post-del {
        margin-right: 0;
    }
    .edit-group-btn {
        pointer-events: auto;
        cursor : pointer;
        margin-left: 20px;
        color: rgba(0,0,0,0.2);
        display: inline-block;
    }
}



</style>
