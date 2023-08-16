# wanted-pre-onboarding-backend
2023 8월 원티드 BE 프리온보딩 사전과제


## 1. 지원자의 성명

김정현

<br><br>

## 2. 애플리케이션 실행 방법

- 레포지토리 클론
- src/main/resources 하위에 application-secret.yml파일 추가 (리드미 최하단에 첨부)
- 최상단(docker-compose와 동일) 위치에 .env추가 (리드미 최하단에 첨부)
- docker-compose 파일 위치에서 docker-compose up --build 실행
- 최상단에서 ./gradlew bootJar 실행
- localhost 8080포트로 접속 - postman으로 엔드포인트 호출

### .env

```
MYSQL_DATABASE=wanteddb
MYSQL_ROOT_PASSWORD=akql1012!
MYSQL_USER=wanted
MYSQL_USER_PASSWORD=akql1012!

```

### application-secret.yml
```

#DB설정
spring:
  datasource:
    url: jdbc:mysql://localhost:3307/wanteddb?serverTimezone=Asia/Seoul&characterEncoding=UTF-8
    username: wanted
    password: akql1012!

  redis:
    host: localhost
    port: 6379

jwt:
  secret: c2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQtc2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQK
  access-token-expire-minute: 10
  refresh-token-expire-minute: 1440

```



<br>

### 엔드포인트 호출 방법

- postman으로 http://localhost/api ~ 엔드포인트 호출
- 로그인, 회원가입을 제외하고 모두 헤더에 Authorization 값 추가
- (로그인하여 받은 accessToken을 "Bearer "뒤에 붙여 헤더의Authorization에 추가)

<br><br>

## 3. 데이터베이스 테이블 구조

1. Mysql
- 모든 엔티티에 대해 생성, 수정, 삭제 시간 저장
- User는 이메일을 중복 불가능하게 저장하도록 하여 id처럼 사용
- password는 인코딩하여 저장
- 유저의 권한 정보 Enum 저장
- Post테이블에 외래키로 유저 id를 두어 연관관계 구성

<img width="390" alt="image" src="https://github.com/kjeongh/wanted-pre-onboarding-backend/assets/88549117/f5e86120-00eb-436e-b637-d22c6960317d">

<br>

1. Redis
- 리프레시 토큰 저장
- refreshToken: {토큰} 형태로 Hash자료구조로 저장
- refreshToken생성시 설정한 expiration과 동일하게 TTL 설정

<img width="701" alt="image" src="https://github.com/kjeongh/wanted-pre-onboarding-backend/assets/88549117/852c2d64-e54d-463a-ae70-54718e4dedc3">

<br><br>

## 4. 데모 영상 링크

https://youtu.be/KwZXQiXeDWk

<br><br>

## 5. 구현 방법 및 이유에 대한 간략한 설명

- Spring Security를 사용한 자격 증명
- TokenProvider로 refresh token과 access token 발급
- 토큰 재발급을 고려해 Redis에 Refresh Token 저장
- 간단한 crud기능만 사용하기 위해 RedisRepository사용
- 이후 Redis에 저장될 값을 추가할 것을 고려해 Hash자료구조 사용
- docker-compose로 디폴트 브리지 네트워크에 Redis와 Mysql 컨테이너 구성
- 회원가입, 로그인을 제외한 모든 api는 Bearer Access Token으로 자격 증명
- 응답 형태 / 응답 코드를 커스텀하여 코드 단순화
- 토큰 발급을 위해 JwtFilter구현
- Cors 설정
- 민감한 정보는 모두 .env나 application-secret에 기록하여 노출하지 않도록 함
- 최적화를 위해 DB연결 지연, fk 지연로딩

<br>

### 1) 회원가입

- 최초 가입시 토큰 정보가 없으므로 JwtFilter에서 주요 로직 건너뜀
- 가입시 이메일과 비밀번호에 대한 유효성 검사
- 이미 가입한 유저인지 검사 후 비밀번호를 passwordEncoder로 인코딩하여 저장
- 유저의 권한 정보를 저장 (디폴트 USER)

### 2) 로그인

- 최초 로그인시 토큰 정보가 없으므로 JwtFilter에서 주요 로직 건너뜀
- 로그인 성공할 경우 refreshToken과 accessToken을 반환
- 생성된 토큰으로 자격 증명 객체를 생성,  context에 저장하여 이후 자격 증명이 필요할 때 사용
- refreshToken은 Redis에 Hash타입으로 저장, TTL로 만료 시간을 설정함

### 3) 게시글 생성

- 헤더의 Authentication에 Bearer 토큰 정보가 있는지 검사하고, 없으면 401에러 반환
- 유효한 토큰인 경우 제목과 내용으로 게시글 생성
- 현재 로그인된 유저의 정보를 불러와 게시글 저장시 사용

### 4) 게시글 목록 조회 (페이지네이션)

- 헤더의 Authentication에 Bearer 토큰 정보가 있는지 검사하고, 없으면 401에러 반환
- page와 각 페이지의 사이즈, 정렬 방식을 파라미터로 받아 페이지네이션 구현
- 유효한 토큰일 경우 게시글 목록 조회 가능
- soft delete를 구현하여 deleted_at이 null인 게시글만 조회

<img width="685" alt="image" src="https://github.com/kjeongh/wanted-pre-onboarding-backend/assets/88549117/34296050-1db7-4cb9-a3a8-29cb04441c5c">


### 5) 단일 게시글 조회

- 헤더의 Authentication에 Bearer 토큰 정보가 있는지 검사하고, 없으면 401에러 반환
- soft delete를 구현하여 deleted_at이 null인 게시글만 조회

### 6) 게시글 수정

- 게시글의 id를 path variable로, 수정사항을 body로 받아 수정
- 헤더의 Authentication에 Bearer 토큰 정보가 있는지 검사하고, 유효한 토큰일 경우 로그인된 유저 정보를 가져와 수정 권한이 있는지 확인
- 권한이 없을 경우 FORBIDDEN에러 발생
- 수정 후 updated_at 업데이트

### 7) 게시글 삭제

- 헤더의 Authentication에 Bearer 토큰 정보가 있는지 검사하고, 유효한 토큰일 경우 로그인된 유저 정보를 가져와 삭제 권한이 있는지 확인
- 권한이 없을 경우 FORBIDDEN에러 발생
- 삭제 후 deleted_at 업데이트

<br><br>

## 6. API명세

[postman]

https://www.postman.com/interstellar-trinity-842730/workspace/public/collection/21653896-3949e1eb-d2d2-4c95-87f2-09ff323d0d68?action=share&creator=21653896

### 공통

<img width="672" alt="image" src="https://github.com/kjeongh/wanted-pre-onboarding-backend/assets/88549117/6e8516c8-c956-4480-9896-f4066cc8b982">


회원가입과 로그인을 제외하고 모두 ‘Bearer {jwt토큰}’형태의 Authorization값이 헤더에 포함되어야 함

### 1) 회원가입 (POST)

http://localhost:8080/api/auth/signup

- Request Body

```sql
{
  "email": "wantedtest@1012",
  "username": "김정현",
  "password": "10121012"
}
```

- Response (status: 201)

```sql
{
  "status": 201,
  "message": "회원가입 성공",
  "data": {
    "email": "wantedtest@1012",
    "username": "김정현",
    "createdAt": "2023-08-16T20:26:43.047853"
  }
}
```

### 2) 로그인 (POST)

http://localhost:8080/api/auth/login

- Request Body

```sql
{
  "email": "testtest@1012",
  "password": "10121012"
}
```

- Response(200)

```sql
{
  "status": 200,
  "message": "로그인 성공",
  "data": {
    "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0dGVzdEAxMDEyIiwiYXV0aCI6IlJPTEVfVVNFUiIsImV4cCI6MTY5MjE4ODg2MX0.ZwEOY4wdksyQ6EsWkotNKmTglSsUzg5Be_W8IhehO_lxy4Vdgo9Apvz4OtM2wWNPUDNkrks78BTuNiuO7C59Kw",
    "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0dGVzdEAxMDEyIiwiYXV0aCI6IlJPTEVfVVNFUiIsImV4cCI6MTY5MjI3NDY2MX0.yWz24ylQSMZGz8ejrxBjfHnQDCV08GjTwagdxj18BWt_9PxH25VyRZzDllECa6hf6pLA2f-FUuRc2nDJDDmBDQ"
  }
}
```

### 3) 게시글 생성 (POST)

헤더 토큰 필요

http://localhost:8080/api/posts

- Request Body

```sql
{
  "title": "테스트",
  "content": "테스트용 게시글"
}
```

- Response(201)

```sql
{
  "status": 201,
  "message": "게시글 생성 성공",
  "data": {
    "title": "테스트",
    "content": "테스트용 게시글",
    "username": "김정현",
    "createdAt": "2023-08-16T21:21:16.995522"
  }
}
```

### 4) 게시글 수정 (PUT)

헤더 토큰 필요

http://localhost:8080/api/posts/{게시글ID}

- Path variable : postId
- Request Body

```sql
{
  "title": "테스트",
  "content": "테스트용 게시글"
}
```

- Response(200)

```sql
{
  "status": 200,
  "message": "게시글 수정 성공",
  "data": {
    "title": "테스트",
    "content": "테스트용 게시글",
    "username": "김정현",
    "updatedAt": "2023-08-16T21:33:46.466739"
  }
}
```

### 5) 게시글 삭제 (DELETE)

헤더 토큰 필요

http://localhost:8080/api/posts/{게시글ID}

- Path variable : postId
- Response(204)

### 6) 게시글 단일조회 (GET)

헤더 토큰 필요

http://localhost:8080/api/posts/{게시글ID}

- Path variable : postId

- Response(200)

```sql
{
  "status": 200,
  "message": "단일 게시글 조회 성공",
  "data": {
    "title": "테스트용 게시글 타이틀",
    "content": "테스트용 게시글입니다.",
    "username": "김정현",
    "createdAt": "2023-08-16T05:37:26.065587",
    "updatedAt": "2023-08-16T05:37:26.065587"
  }
}
```

### 7) 게시글 목록 조회 (GET)

헤더 토큰 필요

http://localhost:8080/api/posts?sort=&page=&size=

- Query parameter: page, sort, size

<img width="655" alt="image" src="https://github.com/kjeongh/wanted-pre-onboarding-backend/assets/88549117/2dff1e46-aad0-4c20-83bb-675a2b469457">


- Response(200)

```sql
{
  "status": 200,
  "message": "게시글 목록 조회 성공",
  "data": {
    "posts": [
      {
        "title": "테스트용 게시글 타이틀",
        "content": "테스트용 게시글입니다.",
        "username": "김정현",
        "createdAt": "2023-08-16T05:37:26.065587",
        "updatedAt": "2023-08-16T05:37:26.065587"
      },
      {
        "title": "수정 테스트용 게시글 타이틀",
        "content": "다시 수정 테스트용 게시글입니다.",
        "username": "김정현",
        "createdAt": "2023-08-16T20:15:58.192636",
        "updatedAt": "2023-08-16T20:17:42.775093"
      },
      {
        "title": "테스트",
        "content": "테스트용 게시글",
        "username": "김정현",
        "createdAt": "2023-08-16T21:33:46.466739",
        "updatedAt": "2023-08-16T21:33:46.466739"
      }
    ],
    "total": 3,
    "totalPages": 1,
    "totalElements": 3
  }
}
``` 
