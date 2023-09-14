<script lang="ts" setup>
import {defineEmits, defineProps, onMounted, ref} from 'vue';
import {editPostAPI, getPostAPI} from "@/api";

const props = defineProps({
    postData: {
        type: Object,
        required: true
    }
});

const post = ref({
    postId: null,
    user: {
        id: null,
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
        id: null,
        fileName: "",
        filePath: "",
    }]
})

const setup = async function () {
    const result = await getPostAPI(props.postData.postId);
    post.value = result.data
    title.value = post.value.title
    content.value = post.value.content
}

const emit = defineEmits(['viewPost']);
const title = ref("")
const content = ref("")

const editPost = async function () {
    try {
        const editPostRequest = {
            postId: post.value.postId,
            title: title.value,
            content: content.value
        }

        await editPostAPI(editPostRequest)
        const result = await getPostAPI(post.value.postId);
        console.log(post.value)
        emit('viewPost', result.data)

    } catch (error) {
        console.log(error)
    }

}

onMounted(setup)

</script>

<template>

    <div class="post-form-wrap">
        <div class="post-form">
            <div class="post-form-header">
                <div class="mb-3 mt-3">
                    <input type="text" class="form-control" id="exampleFormControlInput1"  v-model="title">
                </div>
            </div>
            <div class="post-form-body">
                <div class="mb-3">
                    <textarea class="form-control" id="exampleFormControlTextarea1" rows="20"  v-model="content"></textarea>
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
