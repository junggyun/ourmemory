<script lang="ts" setup>
import {defineEmits, ref} from 'vue';
import store from "@/store";
import {createPostAPI} from "@/api";

const emit = defineEmits(['groupHome']);
const title = ref("")
const content = ref("")


const createPost = async function () {
    try {
        const createPostRequest = {
            userId: store.state.userId,
            groupId: store.state.groupData.id,
            title: title.value,
            content: content.value
        }

        await createPostAPI(createPostRequest)
        emit('groupHome')
    } catch (error) {
        console.log(error)
    }

}

const fileUpload = function () {

}

</script>

<template>

    <div class="post-form-wrap">
        <div class="post-form">
            <div class="post-form-header">
                <div class="mb-3 mt-3">
                    <input type="text" class="form-control" id="exampleFormControlInput1" placeholder="제목" v-model="title">
                </div>
            </div>
            <div class="post-form-body">
                <div class="mb-3">
                    <textarea class="form-control" id="exampleFormControlTextarea1" rows="20" placeholder="내용" v-model="content"></textarea>
                </div>
                <input multiple type="file" accept="image/*" @change="fileUpload">
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
</style>
