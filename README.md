### 프로젝트 선정이유

- 반응형 웹페이지와 예약 시스템에 도전하고자 Airbnb를 주제로 선정

### 개발 기간 및 인원

- **개발 기간**
    - 2023.05.12(금) ~ 2023.05.18(목)
- **개발 인원**
    - FE: 전정훈(React), 남민영(React)
    - BE: 김선민(Spring), 강준수(Spring), 이태경(Spring), 전재우(Spring)
- **구현 항목**
    - 예약하기 기능
    - 예약 데이터 및 숙소 등록 만료 기한 기반 예약 가능 날짜 계산 기능
    - 로그인, 회원가입 기능
    - Spring Security, Jwt 기능
    - WishList CRUD 기능
    - 페이지네이션(infinite scrolling)
    - 필터링(querydsl)
    - XSS Filter
    - 숙소 등록, 수정, 삭제(S3 활용한 이미지 업로드, 삭제 포함)


- **기술 스택**
    - Front-end : JavaScript, HTML, CSS, React
    - Back-end : Spring boot, Java, QueryDSL, Spring Security, MySQL, Jwt, XSS Filter
    - Tools : Github, Git Action, AWS, S3, Swagger, Post Man
- 영상 시연:https://youtu.be/UOTzn970yQ0
- AWS 배포:
- Github Repo: https://github.com/CloneCoding-5/Backend
- Team Notion :[5조 클론 프로젝트](https://www.notion.so/5-e92b9ab3a66b47bb9c4a699cb7fd15e3)
- TroubleShooting : [트러블 슈팅](https://www.notion.so/72fc47dd439c4540b26f210551d4e132)

## 와이어프레임
https://www.figma.com/file/1qN4b5HRhBagh3I1rVtDJ3/%ED%95%AD%ED%95%B4-5%EC%A1%B0-%ED%81%B4%EB%A1%A0%EC%BD%94%EB%94%A9-AirBnb?type=design&node-id=0-1&t=b7V9nSAqkLtnCEKX-0

## ERD
<img width="840" alt="스크린샷 2023-05-17 오후 8 00 02" src="https://github.com/CloneCoding-5/Backend/assets/111736036/9cbac583-1b48-4887-b84f-1d3d13d78147">



## API 명세서 
https://www.notion.so/08c4718dd935446098cbee5d76cd5fc4?v=2e86df1546d847ecb474fa7b73ad8616

<br>

# KPT 회고

---

## Keep

- 매일 오전 9시 정규 회의 시간 갖기
    - 각자의 진행사항 리마인드 및 계획과 수정
- 각자가 맡은 기능들을 책임감 있게 구현
- 코드 리뷰를 통해 다른 팀원이 구현한 코드 및 기능에 대해 학습
- 팀원 모두가 프로젝트의 설계에 참여하여 API 설계를 모두가 이해하고 시작할 수 있었던 것 같다.

## Problem

- 정규 회의 시간 선정을 아침에 잡으니, 밤 늦게까지 작업하신 분들의 출석률이 저조
- 문서 기반 소통이 잘 안되었던 것 같다.
    - 작업 단계에서 기획 내용을 변경해야 할 필요가 느껴질 경우 변경 사항을 노션에 정리하고 다른 팀원들에게 알려주면서 공유가 되어야 하는데 그 부분이 부족했다.
- 다른 분의 코드 리뷰를 처음 들었을때 실시간으로 이해가 힘든 부분이 있었다.
- PR을 날린 후 각자가 merge하여서 변경된 코드를 쉽게 이해하지 못했다.
- 프론트분들과 소통이 잘 이루어지지 않아 진행사항의 파악이 되지 않았다.

## Try

- 점심시간 이후에 정규 회의 진행
- 새벽 작업으로 회의 참석이 어려울때 사전에 공유
- 명세서를 기반으로 협업이 이루어지기 때문에 명세서 내용에 변경이 있을 경우 바로바로 명세서를 수정하고 수정 내용을 알리기
- 코드 리뷰 시간 전에 각자의 코드 리뷰 범위를 공유하여 보는 시간을 가진다.
- PR후 각각의 팀원들이 comment를 남겨서 충분히 검토 후 merge 하는 시간을 갖기.
- 프론트와 백 모두 계획에 차질이 있거나 이해하기 어려운 부분들, 서로의 도움이 필요할 때 적극적으로 소통하기.


