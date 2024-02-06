## 요구 사항

### 도메인(할인정책)
- 현재 요일이 금요일인 경우 모든 상품 10% 할인
- 상품 카테고리가 의류인 경우 5% 할인
- 상품 카테고리가 전자기기인 경우 1만원 할인 or 5% 할인 중에서 최대 할인 적용

### 기타
- 서버를 실행할 `Dockerfile 작성 필수`
- `유닛 테스트 코드 작성 필수`
- request schema
```json
{
  "product" : {
    "id" : 1,
    "category" : 10 // 의류 = 10, 전자기기 = 20, 음식 = 30 .... 등등
  }
}
```
- response schema는 개별 판단, 구현
- IDE는 Intellij 권장