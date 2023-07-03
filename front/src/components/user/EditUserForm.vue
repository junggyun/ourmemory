<script lang="ts" setup>
import store from "../../store";
import {onMounted, ref} from "vue";
import {editUserAPI, getUserAPI} from "@/api";
import DeleteUserModal from "@/components/user/DeleteUserModal.vue";

const nickName = ref(store.state.userData.nickName)
const isDeleteUserModal = ref(false)
const isValidError = ref(false)
const validateError = ref("")
const userData = ref({
    id: 0,
    name: "",
    nickName: "",
    email: "",
    createdDate: "",
})

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
    } catch (error) {
        if (error.response.data.validation.nickName) {
            isValidError.value = true
            validateError.value = error.response.data.validation.nickName
        }
    }
};

onMounted(async function () {
    try {
        const result = await getUserAPI(store.state.userId);
        userData.value = result.data;
    } catch (error) {
        console.log(error)
    }
});

</script>

<template>
    <div class="edit-user-form-wrap">
        <div class="edit-user-form">
            <div class="row mb-3">
                <label class="col-sm-2 col-form-label">이메일</label>
                <label class="col-sm-2 col-form-label">{{ userData.email }}</label>
            </div>
            <div class="row mb-3">
                <label class="col-sm-2 col-form-label">이름</label>
                <label class="col-sm-2 col-form-label">{{ userData.name }}</label>
            </div>
            <div class="row mb-3" style="display: flex">
                <label for="inputNickName" class="col-sm-2 col-form-label">닉네임</label>
                <div class="col-sm-10" style="width: 200px">
                    <input type="email" class="form-control" id="inputNickName" :maxlength="8" v-model="nickName">
                </div>
                <label v-show="isValidError" class="valid-error">* {{ validateError }}</label>
            </div>
            <div class="row mb-3">
                <label class="col-sm-2 col-form-label">가입 일자</label>
                <label class="col-sm-2 col-form-label" style="width: 50%">{{ userData.createdDate }}</label>
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
.edit-user-form-wrap {
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
</style>
