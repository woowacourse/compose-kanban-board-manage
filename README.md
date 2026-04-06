# 칸반 보드 생성(수정)

## 기능 구현 사항

### 비즈니스 로직
- [x] Review 상태를 추가한다.
- [x] 수정 모달을 통해 태스크를 삭제하면 태스크 리스트에서 삭제된다.
  - To Do, In Progress -> 태스크 삭제 가능
  - Review, Done -> 태스크 삭제 불가능
- [x] 태스크 상태 전이 규칙을 적용한다.
  - To Do → In Progress
  - In Progress → To Do, Review
  - Review → In Progress, Done
  - Done → To Do
- [x] 담당자 미지정 상태에서 To do -> In Progress 전이 불가
- [x] TaskCardData를 수정할 수 있다.

### UI 로직
- [x] Board에 Review를 추가한다.
- [x] 수정, 삭제 모달을 추가한다.
- [x] 태스크 카드를 클릭하면 수정, 삭제 모달이 출력된다.
- [x] 모달에서 To do 상태를 선택하면 담당자 없음 버튼이 출력된다.
- [x] 태스크 삭제가 가능, 불가능할 때 각각 스낵바를 출력한다.
- [x] 태스크 수정 시 스낵바를 출력한다.
- [x] 태스크 상태 전이가 불가능할 때 스낵바를 출력한다.
- [x] 담당자를 지정하지 않고 To do → In Progress 전이를 시도할 때 스낵바를 출력한다.