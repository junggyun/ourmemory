<script lang="ts" setup>
import {defineProps, onMounted, ref} from 'vue';

const props = defineProps({
    postData: {
        type: Object,
        required: true
    }
});

const title = ref("")
const content = ref("")
const createdDate = ref("")
const userNickName = ref("")


const setup = function () {
    title.value = props.postData.title
    content.value = props.postData.content
    createdDate.value = props.postData.createdDate
    userNickName.value = props.postData.userNickName
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
        </div>
        <div class="post-content">
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
    flex: 1;
    border-bottom: 1px solid gainsboro;

}
.post-date{
    margin-right: 10px;
    display: flex;
    align-items: end;
}
.post-content {
    flex: 9;
}
pre {
    margin: 20px 10px 0 20px;
}
</style>
