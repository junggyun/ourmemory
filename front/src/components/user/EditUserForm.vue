<script lang="ts" setup>
import store from "../../store";
import {ref} from "vue";
import {editUserAPI} from "@/api";
import DeleteUserModal from "@/components/user/DeleteUserModal.vue";

const nickName = ref(store.state.userData.nickName)
const isDeleteUserModal = ref(false)
const isValidError = ref(false)
const validateError = ref("")


const viewDeleteUserModal = function () {
    isDeleteUserModal.value = true
};

const closeDeleteUserModal = function () {
    isDeleteUserModal.value = false
};

const editUser = async function () {
    try {
        const editUserRequest = {
            nickName: nickName.value
        }
        await editUserAPI(store.state.userId, editUserRequest)
        store.commit('setUserNickName', editUserRequest.nickName)
        window.location.reload()
    } catch (error: any) {
        if (error.response.data.validation.nickName) {
            isValidError.value = true
            validateError.value = error.response.data.validation.nickName
        }
    }
};


</script>

<template>
    <div class="edit-user-form-wrap">
        <div class="edit-user-form">
            <div class="mb-3 d">
                <label class="key col-sm-2 col-form-label">이메일</label>
                <label class="value col-sm-2 col-form-label">{{ store.state.userData.email }}</label>
            </div>
            <div class="mb-3">
                <label class="key col-sm-2 col-form-label">이름</label>
                <label class="value col-sm-2 col-form-label">{{ store.state.userData.name }}</label>
            </div>
            <div class="mb-3" style="display: flex">
                <label for="inputNickName" class="key col-sm-2 col-form-label">닉네임</label>
                <div class="value col-sm-10 me-3">
                    <input type="text" class="form-control" id="inputNickName" :maxlength="8" v-model="nickName">
                    <label v-show="isValidError" class="valid-error">* {{ validateError }}</label>
                </div>

            </div>
            <div class="mb-3">
                <label class="key col-sm-2 col-form-label">가입 일자</label>
                <label class="value col-sm-2 col-form-label" style="width: 50%">{{ store.state.userData.createdDate }}</label>
            </div>
            <div class="edit-user-form-footer">
                <div class="edit-user-submit">
                    <button type="button" class="btn btn-secondary me-2" @click="editUser">수정</button>
                    <button type="button" class="btn btn-outline-secondary" @click="viewDeleteUserModal">회원 탈퇴</button>
                </div>
            </div>

        </div>
        <div class="delete-user" v-if="isDeleteUserModal">
            <DeleteUserModal @closeModal="closeDeleteUserModal"></DeleteUserModal>
        </div>
    </div>
</template>

<style scoped>
.key {
    width: 100px;
}
.edit-user-form {
    width: 50vw;
    background: rgba(0,0,0,0.1);
    border-radius: 8px;
    padding: 20px;
}
.edit-user-form-footer {
    display: flex;
    justify-content: right;
}
.delete-user {
    width: 100%;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    position: absolute;
}
.valid-error {
    width: auto;
    display: flex;
    align-items: center;
    color: red;
}
input {
    width: 200px;
}

@media screen and (max-width: 768px) {
    .edit-user-form {
        width: 90vw;
        background: rgba(0,0,0,0.1);
        border-radius: 8px;
        padding: 20px;
    }
    .edit-user-form-footer {
        display: flex;
        justify-content: right;
    }
    .delete-user {
        width: 100%;
        height: 100%;
        display: flex;
        align-items: center;
        justify-content: center;
        position: absolute;
    }
    .valid-error {
        width: auto;
        display: flex;
        align-items: center;
        color: red;
        font-size: 13px;
    }
}
</style>
