# 파일 확장자 차단

## 사용 기술

 - Back : Java11, SpringBoot, Spring Data Jpa
 - DB : MySQL
 - Front : Vue.js

##  요구사항 정의

<img width="950" alt="image" src="https://github.com/Gonue/block-file-extensions/assets/109960034/ea100139-8135-4887-9b5a-5469f63962dd">


## ERD

<img width="847" alt="image" src="https://github.com/Gonue/block-file-extensions/assets/109960034/a09a0d01-d3e6-463b-b16f-73817344d822">

## 고려 사항들


### ERD 고려사항

<img width="680" alt="image" src="https://github.com/Gonue/block-file-extensions/assets/109960034/30bcd939-02f3-495e-8dde-9063540ab67b">


처음의 경우 위와 같은 단일테이블로 type 필드는 fixed 또는 custom을 가지게끔 생각하였습니다.

하지만 위 테이블의 경우 is_active 필드는 고정확장자에만 적용되는 필드이지만 고정확장자가 아닌 다른 확장자에 대해 이 필드를 관리해야 하며 나중에 확장자에 대해 다른 속성이나 룰을 추가해야 할 경우 이 테이블 구조는 확장성면에서 조금 아쉽다 생각하여 최종 형태는 아래와 같은 테이블을 사용하였습니다.

<img width="847" alt="image" src="https://github.com/Gonue/block-file-extensions/assets/109960034/a09a0d01-d3e6-463b-b16f-73817344d822">

각 테이블이 각각의 역할만 가지게 되므로 코드의 가독성과 유지 보수성, 그리고 나중에 확장자에 대한 다른 속성이나 룰을 추가해야 할 경우 이 형태가 더 적합하며 영향을 주는 부분의 경우 데이터베이스 레벨에서 규칙을 강제하기 보다 애플리케이션 코드 레벨에서 규칙을 처리하는 것이 더 유연하다 판단하였습니다.

### 기본 요건 이외 고려사항

 - 고정 확장자도 추가, 제거 ,조회(총 등록된 고정확장자수 포함) 가능
 - 확장자는 영문자만 가능
 - 확장자는 최소 1자에서 20자리
 - 커스텀 확장자의 경우 고정 및 커스텀 확장자에 등록된 확장자인 경우 제한
 - 고정 확장자의 경우 고정 확장자에 등록된 확장자인 경우는 제한하지만, 커스텀 확장자에 등록된 확장자일 경우 커스텀 확장자 삭제후 고정확장자로 등록
 - 확장자 테스트를 위한 파일 업로드, 조회, 다운로드, 삭제 가능
 - Client 동일 예외처리 및 예외에 따른 모달

## API 명세

<img width="1340" alt="image" src="https://github.com/Gonue/block-file-extensions/assets/109960034/70e32f90-afee-4bd0-97c8-5a1b41216ae4">