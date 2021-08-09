## 개인 프로젝트
# ARS (Algorithm Review Service)
알고리즘 복습을 편하게 할 수 있는 "저"만의 웹서비스입니다. <br>
태그별 문제 리스트를 조회할 수 있고, 검색을 통해 리뷰 리스트를 조회할 수 있습니다. <br>
등록된 문제에 계속해서 복습 내용을 추가할 수 있고, 문제 상세 보기에 복습 목록이 함께 보여집니다. <br>
복습 알림 서비스 기능으로 알림 날짜를 설정하면 해당 날짜에 Slack을 통해 복습 알림을 받을 수 있습니다. <br>

## 🔗 Link
- [ARS 웹사이트](https://ars.vercel.app/)
- [프로젝트 정리 및 후기](https://velog.io/@ayoung0073/Project-ARS-%EA%B0%9C%EC%9D%B8-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%ED%9B%84%EA%B8%B0)
- [API 명세서](https://github.com/ayoung0073/ARS-backend/wiki)
- [프론트엔드 레포지토리](https://github.com/ayoung0073/ARS-frontend)


## 🛠 Architecture 
![Architecture](https://images.velog.io/images/ayoung0073/post/cadb8c1a-7029-45c5-a0aa-d41da203bc28/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202021-08-08%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%205.07.20.png)


## 📝 페이지
### 메인 페이지 
![](https://images.velog.io/images/ayoung0073/post/06e01bcb-a7b9-4fa6-8df0-14ca8d8a7c4b/image.png)
### 상세 페이지 
"저"임이 인증이 되었다면 알림 날짜와 난이도를 즉시 수정할 수 있습니다.<br><br>
![](https://images.velog.io/images/ayoung0073/post/007d9a58-d5a7-4d57-908d-12cee1a376e5/image.png)
<br><br>
오른쪽 영역은 고정되어 있습니다. <br><br>
![상세 페이지 스크롤](https://user-images.githubusercontent.com/69340410/128628683-82af6401-53a0-4181-bed9-33b04f365265.gif) 

### 태그별 문제 리스트 조회
![태그별 문제 리스트 조회](https://user-images.githubusercontent.com/69340410/128629434-18159c9a-6468-48f0-a158-881a25921ff6.gif) 

### 슬랙 알림
![](https://images.velog.io/images/ayoung0073/post/bf9c1032-6f6e-475f-9e66-adc2e4826e76/image.png)
