<script setup lang="ts">
import {onMounted, ref} from "vue";
import {getUserAPI} from "@/api";
import store from "@/store";
import HeaderView from "@/components/user/HeaderView.vue";
import FooterView from "@/components/user/FooterView.vue";
import BodyView from "@/components/user/BodyView.vue";


const userData = ref({
    userName: "",
    userNickName: "",
})


const getUser = async function () {
    try {
        const user = await getUserAPI(store.state.userId);
        userData.value.userName = user.data.name
        userData.value.userNickName = user.data.nickName
    } catch (error) {
        console.log(error)
    }

}


onMounted(getUser)

</script>

<template>
    <div class="wrap">

        <!--    헤더-->
        <div class="header">
            <HeaderView></HeaderView>
        </div>

        <!--    바디-->
        <div class="body">
            <BodyView></BodyView>
        </div>

        <!--    푸터-->
        <div class="footer">
            <FooterView></FooterView>
        </div>

    </div>
</template>

<style scoped>

.wrap {
    display: flex;
    flex-direction: column;
    position: relative;
    height: 100vh;

}

.header {
    display: flex;
    justify-content: center;
    width: 100vw;
    height: 100px;
    border-bottom: 1px solid darkgray;
    margin-bottom: 30px;
    flex-shrink: 0;
}


.footer {
    display: flex;
    justify-content: center;
    width: 100vw;
    height: 60px;
    border-top: 1px solid rgba(0,0,0,0.2);
    margin-top: 70px;
    flex-shrink: 0;
}

.body {
    flex: 1;
    width: 100vw;
    height: auto;
    display: flex;
    justify-content: center;
}

@media screen and (max-width: 768px) {
    .wrap {
        display: flex;
        flex-direction: column;
        position: relative;
        height: 100vh;

    }

    .header {
        display: flex;
        justify-content: center;
        width: 100vw;
        height: 70px;
        border-bottom: 1px solid darkgray;
        margin-bottom: 30px;
        flex-shrink: 0;
    }


    .footer {
        display: flex;
        justify-content: center;
        width: 100vw;
        height: 50px;
        border-top: 1px solid rgba(0,0,0,0.2);
        margin-top: 70px;
        flex-shrink: 0;
    }

    .body {
        flex: 1;
        width: 100vw;
        height: auto;
        display: flex;
        justify-content: center;
    }
}

</style>
