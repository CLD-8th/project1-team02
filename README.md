# 2팀 - WorkBoard

### 서비스 소개

사내 공지 사항을 공유하는 임직원 전용 게시판 서비스

### 핵심 기능

1. 사용자는 자신이 속한 부서의 공지글을 등록
2. 사용자는 전체 공지글 목록을 조회
3. 사용자는 부서별 공지글 목록을 조회

### 팀원 정보

| 이름 | 역할 |
| --- | --- |
| 노승현 | 상세 조회 및 파일 첨부 구현 |
| 이재준 | 공지글 등록, 목록 조회, 부서별 조회 구현 |
| 최명수 | Git 초기 설정, 인증 구현, 목록 조회 화면 구현 |
| 허승준 | 공지글 등록 화면 구현 |

### Git 협업 규칙

| 규칙 | 내용 |
| --- | --- |
| 브랜치 | `main` + `feature/<기능명>` (develop 브랜치는 생략 가능) |
| 커밋 메시지 | `feat:` `fix:` `docs:` `refactor:` `chore:` 접두어 + 한 줄 요약 |
| PR | `main` 직접 push 금지 |
| 비밀 정보 | `.env`는 `.gitignore` 처리 |

## 기동 및 확인 절차
### 기동 절차
```bash
# 저장소 복제 및 디렉터리 이동
git clone https://github.com/CLD-8th/project1-team02.git && cd project1-team02

# .env.example을 복사하여 .env 작성
cp .env.example .env
vi .env

# docker login
docker login

# docker compose를 이용한 기동
docker compose up -d --build

# 컨테이너 기동 확인
docker compose ps
```
### 상태 확인
```bash
curl -s http://localhost:8080/actuator/health
```
### 회원 가입
```bash
curl -i -X POST "http://localhost:8080/members" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "demo@example.com",
    "password": "1234",
    "nickname": "demo"
  }'
```
### 로그인
```bash
# JSON 응답 확인 편의를 위한 jq 설치
sudo apt install jq

# 로그인 요청
LOGIN_RESPONSE=$(curl -s -X POST "http://localhost:8080/auth/login" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "demo@example.com",
    "password": "1234"
  }')

# 로그인 응답 확인
echo "$LOGIN_RESPONSE" | jq

# 변수에 저장
ACCESS_TOKEN=$(echo "$LOGIN_RESPONSE" | jq -r '.accessToken')
REFRESH_TOKEN=$(echo "$LOGIN_RESPONSE" | jq -r '.refreshToken')
MEMBER_ID=$(echo "$LOGIN_RESPONSE" | jq -r '.memberId')

# 저장된 변수 확인
echo "MEMBER_ID=$MEMBER_ID"
echo "ACCESS_TOKEN=$ACCESS_TOKEN"
```
### 공지 등록 / 자료 첨부
```bash
# 첨부 파일 생성
echo "curl attachment test" > sample.txt

# 공지글 등록
NOTICE_RESPONSE=$(curl -s -X POST "http://localhost:8080/notices?memberId=$MEMBER_ID" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $ACCESS_TOKEN" \
  -d '{
    "title": "curl 테스트 공지글",
    "content": "Ubuntu curl을 이용한 공지글 등록 테스트입니다.",
    "department": "개발팀"
  }')

# 공지글 등록 응답 확인
echo "$NOTICE_RESPONSE" | jq

# 공지글 ID 저장
NOTICE_ID=$(echo "$NOTICE_RESPONSE" | jq -r '.id')

# 저장된 변수 확인
echo "NOTICE_ID=$NOTICE_ID"

# 자료 첨부 요청
curl -i -X POST "http://localhost:8080/notices/$NOTICE_ID/attachments" \
  -H "Authorization: Bearer $ACCESS_TOKEN" \
  -F "file=@./sample.txt"
```
### 공지글 목록 조회 / 부서별 조회
```bash
# 공지글 목록 조회 요청
curl -s -X GET "http://localhost:8080/notices" | jq

# 공지글 부서별 조회 요청
curl -s -G "http://localhost:8080/notices" \
  --data-urlencode "department=개발팀" | jq
```
### 공지글 상세 조회
```bash
curl -s -X GET "http://localhost:8080/notices/$NOTICE_ID" | jq
```
### 로그아웃
```bash
curl -i -X POST "http://localhost:8080/auth/logout" \
  -H "Authorization: Bearer $ACCESS_TOKEN"
```
### Redis 키 확인
```bash
# redis-cli 진입
docker compose exec -it cache redis-cli

# 비밀번호 인증
AUTH <Redis 비밀번호>

# 전체 키 확인
KEYS *

# 공지글 목록 조회 데이터 확인
GET notices::all
```
