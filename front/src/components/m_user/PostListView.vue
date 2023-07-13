 <script lang="ts" setup>
 import store from "@/store";
 import {defineEmits, onMounted, ref} from "vue";
 import {addViewCountAPI, getPostByGroupAPI} from "@/api";
 import dayjs from "dayjs";

 const emit = defineEmits(['viewPost'])

 const sizeNum = ref(20)
 const pageNum = ref(1)
 const totalPages = ref(0)
 const posts = ref([{
     postId: null,
     user: {
         id: null,
         name: "",
         nickName: "",
         email: "",
         role: ""
     },
     title: "",
     content: "",
     createdDate: "",
     createdDateSimple: "",
     modifiedDate: "",
     thumbnailPath: "",
     viewCount: 0,
     commentCount: 0,
     uploads: [{
         id: null,
         fileName: "",
         filePath: "",
     }]
 }
 ])

 const nextPage = async function () {
     pageNum.value += 1
     await getPostByGroup()
 }

 const prevPage = async function () {
     pageNum.value -= 1
     await getPostByGroup()
 }

 const getPostByGroup = async function () {
     try {
         const getPostRequest = {
             groupId: store.state.groupData.id,
             size: sizeNum.value,
             page: pageNum.value,
         }

         const result = await getPostByGroupAPI(getPostRequest);
         posts.value = result.data.posts

         totalPages.value = result.data.totalPages

         const currDate = dayjs()
         for (const post of posts.value) {
             const dayDiff = currDate.diff(post.createdDate, 'day')
             post.createdDate = dayjs(post.createdDate).format('YYYY.MM.DD HH:mm');
             if (dayDiff == 0) {
                 post.createdDateSimple = dayjs(post.createdDate).format('HH:mm')
             } else {
                 post.createdDateSimple = dayjs(post.createdDate).format('YYYY.MM.DD')
             }




         }
     } catch (error) {
         console.log(error)
     }
 }

 const viewPost = async function (post: any) {
     await addViewCountAPI(post.postId)
     post.viewCount += 1
     emit('viewPost', post)
 }

 onMounted(getPostByGroup)

 </script>

 <template>
     <div class="post-list-wrap">
         <div class="post-items">
             <div class="d-flex position-relative mb-1" v-for="post in posts" :key="post.postId" style="height: 50px">
                 <img v-if="post.uploads.length == 0" src="@/image/no-image.png" class="flex-shrink-0 me-2" alt="..." @click="viewPost(post)">
                 <img v-if="post.uploads.length > 0" :src="post.uploads[0].filePath" class="flex-shrink-0 me-2" alt="..." @click="viewPost(post)">
                 <div class="post-list-item">
                     <div class="post-list-title">
                         <span class="post-title" style="color: black" @click="viewPost(post)">{{post.title}} [{{ post.commentCount }}]</span>
                         <span style="font-size: 13px; color: lightslategray">{{post.createdDateSimple}} / {{post.user.nickName}}</span>
                     </div>
                     <div class="post-list-view-count">
                         <span style="font-size: 13px; margin-right: 10px">조회 {{post.viewCount}}</span>
                     </div>
                 </div>
             </div>
         </div>

         <div class="btn-cover">
             <button :disabled="pageNum === 1" @click="prevPage" class="page-btn">이전</button>
             <span class="page-count">{{ pageNum  }} / {{ totalPages }} 페이지</span>
             <button :disabled="pageNum >= totalPages" @click="nextPage" class="page-btn">다음</button>
         </div>
     </div>
 </template>

 <style scoped>
img {
    width: 50px;
    height: 50px;
}
.post-title, img {
    pointer-events: auto;
    cursor : pointer;
}
.post-title:hover {
    text-decoration: underline;
}
.btn-cover {
    margin-top: 40px;
    text-align: center;
    font-size: 15px;
}
.btn-cover .page-btn {
    width: 4rem;
    height: 2rem;
    letter-spacing: 0.5px;

}
.post-list-wrap {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
}
.post-items {
    min-height: 540px;
}
.post-list-item{
    width: 100%;
    display: flex;
    justify-content: space-between;
}
.post-list-title{
    display: flex;
    flex-direction: column;
    justify-content: center;
}
.post-list-view-count{
    display: flex;
    align-items: center;
}
.btn-cover .page-count {
    padding: 0 1rem;
}
 </style>
