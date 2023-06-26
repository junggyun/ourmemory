 <script lang="ts" setup>
 import store from "@/store";
 import {onMounted, ref, defineEmits} from "vue";
 import {getPostByGroupAPI, getUploadByPostAPI} from "@/api";
 import moment from "moment";

 const emit = defineEmits(['viewPost'])

 const sizeNum = ref(10)
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

         for (const post of posts.value) {
             post.createdDate = moment(post.createdDate, 'YYYY-MM-DD hh:mm:ss').format('YYYY.MM.DD hh:mm')
             post.createdDateSimple = moment(post.createdDate, 'YYYY-MM-DD hh:mm:ss').format('YYYY.MM.DD')
             const result = await getUploadByPostAPI(post.postId);
             if (result.data.length != 0) {
                 post.thumbnailPath = result.data[0].filePath
             }
         }
     } catch (error) {
         console.log(error)
     }
 }

 const viewPost = function (post: any) {
     emit('viewPost', post)
 }

 onMounted(getPostByGroup)

 </script>

 <template>
     <div class="post-list-wrap">
         <div class="d-flex position-relative mb-2" v-for="post in posts" :key="post.postId">
             <img v-if="!post.thumbnailPath" src="@/image/no-image.jpg" class="flex-shrink-0 me-3" alt="..." @click="viewPost(post)">
             <img v-if="post.thumbnailPath" :src="post.thumbnailPath" class="flex-shrink-0 me-3" alt="..." @click="viewPost(post)">
             <div class="d-flex flex-column justify-content-center position-relative">

                 <h3 class="mt-0" @click="viewPost(post)">{{post.title}}</h3>
                 <span style="font-size: 15px">{{post.createdDateSimple}} / {{post.user.nickName}}</span>
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
    width: 100px;
    height: 100px;
}
h3, img {
    pointer-events: auto;
    cursor : pointer;
}
h3:hover {
    text-decoration: underline;
}
.btn-cover {
    margin-top: 40px;
    text-align: center;
}
.btn-cover .page-btn {
    width: 5rem;
    height: 2rem;
    letter-spacing: 0.5px;
}
.btn-cover .page-count {
    padding: 0 1rem;
}
 </style>
