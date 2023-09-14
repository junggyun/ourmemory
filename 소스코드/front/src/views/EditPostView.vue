<script lang="ts" setup>
import {onMounted, ref} from 'vue';
import {editPostAPI, getPostAPI} from "@/api";
import store from "@/store";
import router from "@/router";

const userId = ref(store.state.userId)
const groupId = ref(store.state.groupData.id)
const postId = ref(store.state.postData.id)
const title = ref("")
const content = ref("")

const post = ref({
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
    uploads: [{
        id: 0,
        fileName: "",
        filePath: "",
    }]
})

const setup = async function () {
    const result = await getPostAPI(postId.value);
    post.value = result.data
    title.value = post.value.title
    content.value = post.value.content
}

const goGroup = function () {
    store.commit('clearPost')
    router.push(`/${userId.value}/${groupId.value}`)
}



const editPost = async function () {
    try {
        const editPostRequest = {
            postId: post.value.postId,
            title: title.value,
            content: content.value
        }

        await editPostAPI(editPostRequest)

        await router.replace(`/${userId.value}/${groupId.value}/${postId.value}`)

    } catch (error) {
        console.log(error)
    }

}

onMounted(setup)

</script>

<template>

    <div class="post-form-wrap">
        <div>
            <h4 @click="goGroup">{{ store.state.groupData.name }}</h4>
        </div>
        <div class="post-form">
            <div class="post-form-header">
                <div class="mb-3 mt-3">
                    <input type="text" class="form-control" id="exampleFormControlInput1"  v-model="title">
                </div>
            </div>
            <div class="post-form-body">
                <div class="mb-3">
                    <textarea class="form-control" id="exampleFormControlTextarea1" rows="20" style="resize: none" v-model="content"></textarea>
                </div>
            </div>
            <div class="post-form-footer">
                <div class="post-submit">
                    <button type="button" class="btn btn-secondary" @click="editPost">수정</button>
                </div>
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
    display: inline-block;
}
h4:hover {
    text-decoration: underline;
}
.post-form {
    width: 50vw;
    background: rgba(0,0,0,0.1);
    border-radius: 8px;
    padding: 20px;
}
.post-form-footer {
    display: flex;
    justify-content: right;
}

@media screen and (max-width: 768px) {
    .post-form {
        width: 90vw;
        background: rgba(0,0,0,0.1);
        border-radius: 8px;
        padding: 20px;
    }
    .post-form-footer {
        display: flex;
        justify-content: right;
    }
    textarea {
        height: 300px;
    }
}
</style>
