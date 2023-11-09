<script lang="ts" setup>
import {ref} from 'vue';
import store from "@/store";
import {createPostAPI} from "@/api";
import router from "@/router";

const userId = ref(store.state.userId)
const groupId = ref(store.state.groupData.id)

const title = ref("")
const content = ref("")
const fileInput = ref("")
const formData = ref(new FormData())

const goGroup = function () {
    store.commit('clearPost')
    router.push(`/${userId.value}/${groupId.value}`)
}

const createPost = async function () {
    try {
        const createPostRequest = {
            userId: store.state.userId,
            groupId: store.state.groupData.id,
            title: title.value,
            content: content.value
        }

        formData.value.append('request', new Blob([JSON.stringify(createPostRequest)], {type: 'application/json'}))

        const result = await createPostAPI(formData.value);
        store.commit('setPostId', result.data.id)
        await router.replace(`/${userId.value}/${groupId.value}/${result.data.id}`)
    } catch (error) {
        console.log(error)
    }

}

const uploadFile = function (event: any) {
    formData.value = new FormData()
    fileInput.value = event.target.files
    const files = fileInput.value
    for (let i = 0; i < files.length; i++) {
        formData.value.append('files', files[i])
    }
}


</script>

<template>

    <div class="post-form-wrap">
        <div>
            <h4 @click="goGroup">{{ store.state.groupData.name }}</h4>
        </div>
        <div class="post-form">
            <div class="post-form-header">
                <div class="mb-3 mt-3">
                    <input type="text" class="form-control" id="exampleFormControlInput1" placeholder="제목" v-model="title">
                </div>
            </div>
            <div class="post-form-body">
                <div class="mb-3">
                    <textarea class="form-control" id="exampleFormControlTextarea1" style="resize: none;" rows="20" placeholder="내용" v-model="content"></textarea>
                </div>
                <input multiple type="file" accept="image/*" ref="fileInput" @change="uploadFile">
            </div>
            <div class="post-form-footer">
                <div class="post-submit">
                    <button type="button" class="btn btn-secondary" @click="createPost">등록</button>
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
