# ✅ 개발 시작 전 체크할 사항

개발을 시작하기 전에 반드시 아래의 사항을 확인하고 설정해주세요.

---
# **개발 환경 분리 설정**

## 1. **`application.yml` 파일에서 본인의 야믈 파일로 프로필 설정하기**
- 자신의 프로필에 해당하는 `.yml` 파일을 사용해야 합니다.
- 만약 본인의 `.yml` 파일이 없다면, 다음 경로에 새 파일을 생성합니다:

UserService/src/main/resources/application-{본인이 사용할 프로필명}.yml

---

## 2. **프로필 활성화 설정 추가하기**
생성한 `.yml` 파일에 아래 코드를 추가합니다.

> ❗ **주의사항:** `on-profile` 속성에 본인이 설정한 프로필명을 정확히 입력해야 합니다.

```yaml
spring:
config:
  activate:
    on-profile: {사용할 프로필명}
```

## 3. **application.yml에서 활성화 프로필 설정하기**
application.yml 파일로 이동한 뒤, 아래와 같이 active 속성에 본인이 사용할 프로필명을 입력합니다.

```yaml
spring:
  profiles:
    active: {사용할 프로필명}
```
