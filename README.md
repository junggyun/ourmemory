
## 👉 프로젝트 소개
다양한 주제의 그룹을 생성할 수 있으며 그룹원의 참여 코드 공유를 통해 그룹에 참여하여 <br>
게시글, 댓글기능을 이용할 수 있는 프라이빗 커뮤니티입니다. <br>
공유 대상이 오로지 그룹원이고 추억, 정보 등의 기록들을 이미지와 함께 나누고 소통할 수 있는 점이 특징입니다.<br>
OURMEMORY의 유래 또한 '우리들만의 기억'에서 나왔습니다. <br>

## 🔗 링크
https://www.ourmemory.shop <br>
테스트 계정 EMAIL / PW : test@test.com / test1234

## 🗓 프로젝트 기간 (개인 프로젝트)
<b>2023-05-12 ~ 2023-07-04</b> (기본 구현) <br>
<b>2023-07-04 ~</b> (기능 추가, 오류 수정)

## 🛠 기술 스택, 개발 환경
✔ front-end <br>
- vue3 + typescript <br>

✔ back-end <br>
- java17 + springboot+ mysql + aws ec2 <br>

✔ dev tool <br>
- Intellij <br>

✔ dev env <br>
- windows10 + linux(nginx) <br>


## 📜 ERD
![image](https://github.com/terranking1/ourmemory/assets/92567159/89a54296-b835-40f8-9b7f-3b200cb3b550)

## ✍ REST API
https://www.ourmemory.shop/docs/index.html

## ✅ 핵심 기능

### 1. 회원
#### 1-1. 회원 가입
![image](https://github.com/terranking1/ourmemory/assets/92567159/467dfc2c-baca-4a39-9d3e-31f4fe85d666)

#### 1-2. 로그인
![image](https://github.com/terranking1/ourmemory/assets/92567159/290f86a4-c13f-4b3e-98d1-c47e8877d2ac)

#### 1-3. 회원 정보 수정
![image](https://github.com/terranking1/ourmemory/assets/92567159/6f4b43dd-dd28-4b7b-9abb-e6d342f35b58)

- 닉네임 변경, 회원탈퇴 기능 <br>

### 2. 그룹

#### 2-1. 그룹 목록
![image](https://github.com/terranking1/ourmemory/assets/92567159/77f650ad-d0db-4d6c-a600-3618d016d3cd)

#### 2-2. 그룹 메인
- 게시글은 페이징 조회

![image](https://github.com/terranking1/ourmemory/assets/92567159/2217bae0-620b-4949-aaee-7864f44f901b)

### 3. 게시글, 댓글
- 이미지 파일 업로드 가능 <br>

![image](https://github.com/terranking1/ourmemory/assets/92567159/6dcd6691-d374-4293-a734-508cdc8e46ca) <br>
![image](https://github.com/terranking1/ourmemory/assets/92567159/56bc725f-0dcd-4884-807c-6a2c31a9ff0b) <br>
![image](https://github.com/terranking1/ourmemory/assets/92567159/683b7dc8-08a4-440d-b3b0-e2523aafcf60)

## 🖊 핵심 구현(기술)
### JPA

- sql문 없이 객체를 테이블과 매핑(엔티티 개발) <br>

![image](https://github.com/terranking1/ourmemory/assets/92567159/a0ab819f-4010-4fd5-884e-02e1b9dba4a7)

- 엔티티 테이블에 대한 CRUD쿼리문 실행(리포지토리, 서비스 개발) <br>

![image](https://github.com/terranking1/ourmemory/assets/92567159/769c8e5a-33c1-4601-878b-d642a93d3d7d) <br>
![image](https://github.com/terranking1/ourmemory/assets/92567159/aee6ee3a-3dbf-4611-8007-c9110a287084)

### QUERYDSL

- 자바 코드로 SQL 작성 <br>

![image](https://github.com/terranking1/ourmemory/assets/92567159/557da7d0-d85d-4c0c-b4af-dacc3672f6ea) <br>
![image](https://github.com/terranking1/ourmemory/assets/92567159/e54bba49-f049-4d8b-a743-5a37e1bf2b7a) <br>
N:1 혹은 1:1 관계매핑에서는 fetchType을 LAZY로 설정하고, fetchjoin으로 조회하는 것이 최적화에 중요!

### SPRING SECURITY + JWT

- security를 통한 권한에 따른 컨트롤러 접근 제어 <br>

![image](https://github.com/terranking1/ourmemory/assets/92567159/283d5e05-1dc9-49b8-a5b1-ba05e02e3d11)

- jwt토큰을 통한 api요청 권한 관리 => 로그인 관리 <br>

![image](https://github.com/terranking1/ourmemory/assets/92567159/d6fa0464-2f0e-4583-95d9-43970deb2d63)

### VUE

- 클라이언트 화면 개발 및 axios를 통한 백엔드 api와의 http 통신 <br>

![image](https://github.com/terranking1/ourmemory/assets/92567159/10cc27cd-369d-42bd-b75c-f17be8235335) <br>
![image](https://github.com/terranking1/ourmemory/assets/92567159/f9f7df80-435f-4eac-b99a-a8110d4eb002) <br>
![image](https://github.com/terranking1/ourmemory/assets/92567159/c8673a8b-55be-4172-b37a-02f7487b4821) <br>

## 🎞 프로젝트 후기
- 개인 프로젝트여서 다소 많은 시간이 소요되었지만 백엔드와 프론트엔드를 동시에 작업해보니 큰 경험이 되었던 것 같습니다. <br>
우선, 백엔드에서의 기능 구현은 크게 어려움이 없었지만 클라이언트에서 요구되는 데이터가 명확히 제시되지 않은 상태라면 원활한 작업이 되지 않음을 겪었습니다.
따라서, 팀 프로젝트라면 프론트단과 백단의 소통이 정말 중요하다는 것을 알게되었습니다.

- 개발 후에 오류 수정이나 새로운 기능을 추가 또는 삭제 할 경우, 프로젝트를 어떻게 설계했냐에 따라서 추가 코드량이 달라짐을 겼었습니다. <br>
객체 지향 언어를 사용하는 만큼 설계도 최대한 객체 지향의 특징을 살려서 하는 것이 중요하다는 것을 알게되었습니다.

- 소규모 프로젝트라 큰 영향은 없었지만 복잡한 엔티티 관계 매핑에서의 데이터 조회나 많은 양의 데이터를 조회할 때 쿼리 최적화에 신경썼습니다.

- 처음에는 깃 관리를 하나의 브랜치로 관리하였는데 이 경우 여러 작업을 동시에 하기에 어려움을 겪어 기능, 핫픽스, 배포 등의 브랜치를 추가하여 작업하였습니다.


















