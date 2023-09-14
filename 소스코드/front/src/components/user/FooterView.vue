<script lang="ts" setup>

</script>

<template>

    <div class="footer-wrap">
        <div class="copyright">
            <span>Copyright 2023 OurMemory All Rights Reserved.</span>
            <span>Email : onlyplsson@naver.com</span>
        </div>
    </div>

</template>

<style scoped>

.footer-wrap {
    display: flex;
    align-items: center;
    width: 50vw;
    height: 100%;
    position: relative;

}
.copyright {
    width: 100%;
    display: flex;
    flex-direction: column;
    font-size: 14px;
}

.copyright p {
    color: #404040;

}

@media screen and (max-width: 768px) {
    .footer-wrap {
        display: flex;
        align-items: center;
        width: 90vw;
        height: 100%;
        position: relative;

    }
    .copyright {
        width: 100%;
        display: flex;
        justify-content: space-between;
        font-size: 11px;
    }

    .copyright p {
        color: #404040;

    }
}
</style>
