<script setup lang="ts">

import HeaderView from "@/components/user/HeaderView.vue";
import FooterView from "@/components/user/FooterView.vue";
</script>
<template>
    <div class="app">
        <div class="header" v-show="$route.name != 'Login' && $route.name != 'SignUp'">
            <HeaderView></HeaderView>
        </div>
        <div class="body">
            <RouterView/>
        </div>
        <div class="footer" v-show="$route.name != 'Login' && $route.name != 'SignUp'">
            <FooterView></FooterView>
        </div>
    </div>

</template>

<style scoped>
.app {
    display: flex;
    flex-direction: column;
    position: relative;
    height: 100vh;
    align-items: center;

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
    .app {
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
