<script lang="ts" setup>
import {defineEmits, ref} from 'vue'
import store from "@/store";
import {createGroupAPI} from "@/api";


const group = ref({
    userGroupId: 0,
    role: "",
    group: {
        id: 0,
        name: "",
        key: "",
        createdDate: "",
        postCount: "",
        newPostDate: "",
        isNewPost: false
    }
})

const createGroupRequest = ref({
    userId: store.state.userId,
    groupName: ""
})

const emit = defineEmits(['enterModal', 'closeModal']);

const closeModal = function () {
    emit('closeModal')
}

const enterModal = function () {
    emit('enterModal', group.value)
}

const createGroup = async function () {
    try {
        if (createGroupRequest.value.groupName) {

            const result = await createGroupAPI(createGroupRequest.value);
            group.value = result.data

            enterModal()
        }
    } catch (error) {
        console.log(error)
    }

}

</script>

<template>
    <div class="create-group-modal">
        <div class="create-group-modal-content">
            <div class="create-group-modal-content-header">
                <span>그룹 생성</span>
            </div>
            <div class="create-group-modal-content-body">
                <input type="text" v-model="createGroupRequest.groupName" placeholder="그룹명">

            </div>
            <div class="create-group-modal-content-footer">
                <button class="btn btn-secondary me-3" @click="createGroup">생성</button>
                <button class="btn btn-secondary" @click="closeModal">닫기</button>
            </div>
        </div>
    </div>
</template>

<style scoped>
span {
    font-size: 20px;
}
.create-group-modal {
    width: 300px; height: 200px;
    border: 1px solid darkgray;
    border-radius: 8px;
    z-index: 999;
    position: absolute;
    top: 10px;
    background: white;
}
.create-group-modal-content {
    width: 100%; height: 100%;
    display: flex;
    flex-direction: column;

}
.create-group-modal-content-header {
    flex: 1;
    display: flex;
    justify-content: center;
    align-items: center;
    border-top-left-radius: 8px;
    border-top-right-radius: 8px;
}
.create-group-modal-content-body {
    flex: 2;
    border-top: 1px solid darkgray;
    border-bottom: 1px solid darkgray;
    display: flex;
    justify-content: center;
    align-items: center;
}
.create-group-modal-content-footer {
    flex: 1;
    display: flex;
    justify-content: center;
    align-items: center;
    border-bottom-left-radius: 8px;
    border-bottom-right-radius: 8px;
}

@media screen and (max-width: 768px) {
    span {
        font-size: 15px;
    }
    .create-group-modal {
        width: 200px; height: 150px;
        border: 1px solid darkgray;
        border-radius: 8px;
        z-index: 999;
        position: absolute;
        top: 10px;
        background: white;
    }
    .create-group-modal-content {
        width: 100%; height: 100%;
        display: flex;
        flex-direction: column;

    }
    .create-group-modal-content-header {
        flex: 1;
        display: flex;
        justify-content: center;
        align-items: center;
        border-top-left-radius: 8px;
        border-top-right-radius: 8px;
    }
    .create-group-modal-content-body {
        flex: 2;
        border-top: 1px solid darkgray;
        border-bottom: 1px solid darkgray;
        display: flex;
        justify-content: center;
        align-items: center;
    }
    .create-group-modal-content-footer {
        flex: 1;
        display: flex;
        justify-content: center;
        align-items: center;
        border-bottom-left-radius: 8px;
        border-bottom-right-radius: 8px;
    }
    button {
        font-size: 11px;
        padding: 5px 10px 5px 10px;
    }
    input {
        font-size: 12px;
    }
}
</style>
