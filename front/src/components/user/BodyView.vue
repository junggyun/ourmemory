<script lang="ts" setup>
import GroupListView from "@/components/user/GroupListView.vue";
import CreateGroupView from "@/components/user/CreateGroupView.vue";
import {ref} from "vue";

const dynamicComponent = ref("")


const ViewCreateGroupModal = function () {
    dynamicComponent.value = "CreateGroupModal"
}



</script>

<template>
  <div class="body-wrap">
      <div class="nav">
          <button @click="ViewCreateGroupModal">그룹 생성</button>
          <GroupListView></GroupListView>
      </div>
      <div class="content">
          <div v-if="dynamicComponent === 'CreateGroupModal'">
              <CreateGroupView></CreateGroupView>
          </div>
      </div>
  </div>
</template>

<style scoped>
.body-wrap {
    display: flex;

    width: 70vw;
    height: 100%;
}
.nav {
    flex: 1;
}
.content {
    flex: 9;
}
</style>
