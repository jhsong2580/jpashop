# 자바ORM표준 chapter 11

---
### 아래 내용들을 지켜서 개발해보자
- 한 클래스는 하나의 기능을 하게
- indent는 1을 넘어가지 말자
- TDD로 구현해보자 

---
### 도메인 설계 
1. 도메인 모델
![img.png](readmeImg/picture_11_1.png)
![img.png](readmeImg/picture_11_3.png)


2. 키 생성 전략
- Delivery : Sequence 전략으로 설정
- Order : Table 전략으로 설정
- OrderItem : 복합키로 설정(@Idclass)
- CategoryItem : 복합키로 설정(@EmbeddidId)
- 그외 : Identity전략으로 설정

3. 중복되는 값들은 값타입 객체로 추가하자
- City, Street, zipCode -> Address.class (@Embedded, @Embeddable)

4. @OneToMany의 컬렉션 연관관계는 일급컬렉션으로 감싸 처리하자 

5. 상속관계 매핑은 Join전략을 사용하자.

6. 모든 테이블에 속해야할 생성시간/수정시간 클래스는 분리하자 

