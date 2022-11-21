![Java_8](https://img.shields.io/badge/java-v11-red?logo=java)
![Spring_Boot](https://img.shields.io/badge/Spring_Boot-v2.7.5-green.svg?logo=spring)

# Cafe Service REST API


## 프로젝트 소개
커피 주문 시스템

## Feature
* JDK 11
* Sprint boot 2.7.5
* gradle 7.3
* JPA, h2
* Spring Rest Docs
* Webclient, Mono, MockWebServer
* AOP, Interceptor, Custom Annotation

### 기능
1. 유저 - 포인트 등록
2. 메뉴 - 전체 메뉴 조회
3. 메뉴 - 인기 메뉴 조회
4. 주문 - 커피주문/결제
5. 주문 시 데이터플랫폼서버로 주문내역 전송

## 실행방법
* build
```cmd
./gradlew clean build bootJar
```
* run(default port:8080)
```cmd
java -jar build/libs/cafe-service-0.0.1-SNAPSHOT.jar
```

## API Document (after server running)
```cmd
http://localhost:8080/docs/index.html
```

## 문제 해결 전략
### 1. 유저 - 포인트 등록
```
유저ID와 포인트를 입력받아 DB에 저장.
데이터 정합성을 고려해 해당 서비스만 isolation level=REPEATABLE_READ 로 변경
```
### 2. 메뉴 - 전체 메뉴 조회
```
등록된 메뉴들을 리스트로 조회되게끔 구현.
```
### 3. 메뉴 - 인기 메뉴 조회
```
주문히스토리에서 최근 7일간 주문횟수가 가장 높은 메뉴 3가지를 조회하는 API
Native query로 구현.
```
### 4. 주문 - 커피주문/결제
```
유저ID와 메뉴ID로 주문 API 호출 시 주문이 등록됨.
```
### 5. 주문 시 데이터플랫폼서버로 주문내역 전송
```
주문이 끝난 후 response 를 Interceptor 로 after catch한 후,
Webclient와 Mono를 이용하여 주문내역을 MockWebServer로 비동기 전송처리함.
```

## 기존 설계 후 추가사항
### 1. 데이터 소스 분기 로직(확장성 고려)
```
다수의 서버에서 동작할 경우, 대용량 트래픽이 유입되는 경우를 감안할 경우, 
데이터베이스 서버를 replication 처리하고 reader db와 writer db로 나눈다고 간주함.
인프라 확장성을 고려하여 AOP와 custom annotation을 통해 SAVE 메소드들은 writer db를 분기타도록 처리.
ThreadLocal 을 통해 트랜잭션을 처리하는 쓰레드가 도중에 변경되지 않도록 처리. 
(테스트용 DB인 h2 db는 datasource가 분기되지 않음.)
```

### 2. MockWebServer
```
MockWebServer를 통해 어떤값이 들어오더라도 200 status를 내려주는 Mock서버를 구현.
해당 서버를 데이터수집플랫폼이라고 간주함.
```

### 3. WebClient
```
RestTemplate 대신 비동기전송에 용이한 WebClient 구현.
```

### 3. Spring Rest Docs
```
테스트코드와 결합이 용이한 REST DOCS를 이용해 API문서 작성
```