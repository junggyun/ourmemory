<script lang="ts" setup>
import GroupListView from "@/components/user/GroupListView.vue";
import {computed, ref, watch} from "vue";
import GroupView from "@/components/user/GroupView.vue";
import store from "@/store";
import EditUserModal from "@/components/user/EditUserForm.vue";

const dynamicComponent = ref("")

const viewGroupEnter = function () {
    dynamicComponent.value = store.state.dynamicComponent
}
const dynamicComponentComputed = computed(() => store.state.dynamicComponent);

watch(dynamicComponentComputed, (newVal) => {
    dynamicComponent.value = newVal
})




</script>

<template>
    <div class="body-wrap">
        <div class="content">
            <div v-if="dynamicComponent === ''">
                <GroupListView @groupEnter="viewGroupEnter"></GroupListView>
            </div>
            <div v-else-if="dynamicComponent === 'groupEnter'">
                <GroupView></GroupView>
            </div>
            <div class="edit-user" v-else-if="dynamicComponent === 'userEdit'">
                <EditUserModal></EditUserModal>
            </div>
        </div>

    </div>
</template>

<style scoped>
.body-wrap {
    position: relative;
    display: flex;
    flex-direction: column;
    width: 60vw;
    height: 100%;
}

.content {

}


</style>
