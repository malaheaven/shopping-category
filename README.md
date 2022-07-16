# shopping-category

### 실행 방법

#### Common
*Repository Clone*

```
git clone https://github.com/malaheaven/shopping-category.git
```

*JDK 설치*

JDK 11(이)가 설치되어 있는 경우 사용가능. JDK11을 설치해주세요.

#### Run Local SpringBoot
*아래 쉘 파일 RUN*
```
sh local-start.sh
```

```
# local-start.sh description

./gradlew build                     # spring boot build
./gradlew bootJar                   # spring boot bootJar -> spring rest docs index.html 생성을 위해
cd build/libs/                      # move build/libs/
java -Dspring.profiles.active=local -jar shopping-category-0.0.1-SNAPSHOT.jar # start local spring boot
```

##### Health Check
*request*
```
curl --location --request GET 'http://localhost:8080/health'
```

*response*
```
OK
```

##### H2 console 접속
```
http://localhost:8080/h2-console
```
```
Driver Class: org.h2.Driver
JDBC URL: jdbc:h2:mem:test;MODE=MySQL;
User Name: SA
Password: 
```

#### Run Docker SpringBoot

docker가 설치되어 있는 경우 사용 가능. docker를 설치해 주세요.

docker 설치: [Download Docker](https://www.docker.com/products/docker-desktop/)

*아래 쉘 파일 RUN*
```
sh docker-start.sh
```
```
# docker-start.sh description

rm -rf ./docker/db/  # remove docker volume directory
./gradlew clean      # spring boot build clean
./gradlew build      # spring boot build
./gradlew bootJar    # spring boot bootJar -> spring rest docs index.html 생성을 위해

cd docker/           # move docker directory

#sh docker-clear.sh  # docker 현재 실행하는 컨테이너 제거 및 docker-compose.yml 정의 파일에서 지정한 컨테이너를 정지시키고 모든 이미지를 삭제 (필요시 주석 해제 후 사용)
sh docker-start.sh   # docker run
```

##### Health Check
*request*
```
curl --location --request GET 'http://localhost/health'
```

*response*
```
OK
```
--- 

### 개발 문서
- TimeZone : UTC
```
  @PostConstruct
  public void construct() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
  }
```
#### ERD
[ERD wiki 바로가기](https://github.com/malaheaven/shopping-category/wiki/ERD)

#### Docs
Spring REST Docs 적용

- [Docs wiki 바로가기](https://github.com/malaheaven/shopping-category/wiki/Docs)

|환경|url|ETC|
|---|----|---|
|Docker|http://localhost/docs/index.html| Docker로 실행하면 해당 URL|
|Local|http://localhost:8080/docs/index.html||

#### Version

|kind|name|version|etc |
|-----|----|-------|----|
|DBMS|H2|2.1.214| Local 에서 사용|
|DBMS|MySQL|8.0| Docker 에서 사용|
|Language|JAVA|11||
|Framework|Spring Boot |2.7.1||

#### DDL
- resources/schema.sql
```
-- -----------------------------------------------------
-- DROP Table
-- -----------------------------------------------------
DROP TABLE IF EXISTS musinsa.category;

-- -----------------------------------------------------
-- Table `category`
-- -----------------------------------------------------
create table if not exists musinsa.category
(
    id          bigint auto_increment primary key,
    created_at  datetime(6)  null,
    modified_at datetime(6)  null,
    depth       bigint       null,
    name        varchar(255) not null,
    parent_id   bigint       null,
    constraint unique_category_name_depth
        unique (name, depth),
    constraint fk_parent_id
        foreign key (parent_id) references musinsa.category (id)
);

```
--- 

### 개발 방식

##### Branch 규칙

- feature : 새로운 기능 개발이나 버그 수정을 위한 브랜치
- develop : 새로운 기능(feature)에 대한 검토(PR)가 완료되면 소스코드를 합치기 위한 브랜치
- main : 정식 배포되는 안정적인 소스코드를 이곳에서 관리

##### 간단한 테스트 코드도 작성했습니다.

##### Commit 규칙

|Name     |Description|
|---------|-----------|
|ADD      |코드 추가|
|MOD      |코드 수정, 버그 수정 (Modify)|
|DOCS     |문서 작성, 수정|
|STY      |코드 줄 맞춤, 오탈자 수정 (Style)|
|TEST     |테스트 코드 작성, 수정| 
