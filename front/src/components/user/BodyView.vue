<script lang="ts" setup>
import GroupListView from "@/components/user/GroupListView.vue";
import {ref} from "vue";

const dynamicComponent = ref("")


</script>

<template>
    <div class="body-wrap">
        <div class="content">
            <div v-if="dynamicComponent === ''">
                <GroupListView></GroupListView>
            </div>
        </div>
    </div>
</template>

<style scoped>
.body-wrap {
    position: relative;
    display: flex;
    flex-direction: column;
    width: 70vw;
    height: 100%;
}

.content {

}

</style>
