**20240516 docker 세팅, 스프링 부트 실행**    
오류: 스프링 부트 실행했을 때 whitelabel Error Page 뜨지만 새로고침 했을 때 사진처럼 오류 발생
<https://github.com/dksejddnjs/CM/blob/main/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202024-05-16%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%202.43.30.png>

사진과 상관없는 오류 => 실행했을 때 url 찾기 불가, db 연결 관련 문제

------------------------------------------------------------------------------------------
**20240517 스프링 부트 실행 시 오류 및 DB 설정 관련 오류**
1. Application.propertise에서 spring.jpa.database-platform=…/ spring.jpa.properties.hibernate.dialect=…

   -> 이 명령어는 기본적으로 잡아주는 거라 필요없다는 오류
3. constructor parameter 0: Error creating bean with name 'userRepository' defined in com.example.demo.repository.UserRepository defined in @EnableJpaRepositories declared on JpaRepositoriesRegistrar.EnableJpaRepositoriesConfiguration: Not a managed type: class com.example.demo.entity.UserEntity

   -> entity나 repository의 코드 오류
4. org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'entityManagerFactory' defined in class path resource [org/springframework/boot/autoconfigure/orm/jpa/HibernateJpaConfiguration.class]: Class org.springframework.orm.jpa.vendor.SpringHibernateJpaPersistenceProvider does not implement the requested interface jakarta.persistence.spi.PersistenceProvider

   -> Spring Boot와 Jakarta Persistence API 간의 버전 충돌로 인한 오류/ 애플리케이션이 시작될 때 **`BeanCreationException`**이 발생했음을 알 수 있고 이 문제는 **`entityManagerFactory`** 빈을 생성하는 중에 발생함. 에러 메시지를 보면 **`SpringHibernateJpaPersistenceProvider`** 클래스가 **`jakarta.persistence.spi.PersistenceProvider`** 인터페이스를 구현하지 않는다는 것... 

entityManagerFactory: entity나 hibernate, jakarta.persistence 등 DB 설정 관련

Dependency 디펜던시: 의존성

------------------------------------------------------------------------------------------
**240518  의존성 문제 해결 및 스프링 부트 재실행시 연동된 db안에 테이블 정보가 사라지는 것, chat**

1. 0517의 오류 중  3번째 오류

오류났던 파일의 build.gradle 파일과 수정하여 실행된 build.gradle 파일의 dependency 부분을 비교해본 결과  implementation 'org.hibernate.validator:hibernate-validator' 이게 없었어서 오류가 나지 않았나 싶다. 사무실에 있는 환경과 맥북 환경 모두 동일하고, 오류난 프로젝트를 깃에서 받고 수정하여 ./gradlew clean build를 해본 결과 오류 문제 없이 실행이 된다.

'org.hibernate.validator:hibernate-validator'는 종속성(Dependency) 식별자라고 한다. 이 식별자는 프로젝트에서 Hibernate Validator 라이브러리를 사용하기 위해 지정된다.

1. 스프링 부트 실행 후 postman으로 post, get 테스트 한 것들이 users table 안에 저장된 것을 확인할 수 있는데 스프링 부트를 재실행했을 때 그 정보들이 사라지는 것을 확인하였다. 이 부분은 application.propertise에서 

`spring.jpa.hibernate.ddl-auto=update` 이 부분이 update가 아닌 create으로 되어 있어서 그랬던 것을 알게되었다.

`spring.jpa.hibernate.ddl-auto` 는 Spring Boot에서 Hibernate의 데이터베이스 스키마 자동 생성 및 업데이트 동작을 제어하는 설정 옵션이다. 이 설정을 통해 Hibernate가 애플리케이션의 엔티티 모델과 데이터베이스 스키마 간의 관계를 어떻게 관리할지 정의할 수 있
다.

------------------------------------------------------------------------------------------
**240519 socket 오류**

1. http://localhost:8098/chat 으로 들어가면 404오류와 함께 반환한 chater를 찾지 못 하는 오류가  뜸.

application.properties에서 ⇒

`spring.thymeleaf.prefix=classpath:/templates/`

`spring.thymeleaf.suffix=.html`

이렇게 선언한 부분을  ⇒

`spring.mvc.view.suffix=.html`

원래 templates 안에 chater.html이 있었는데 정적 리소스여서 static 폴더 안에 있어야 했다. 이동시킨 후 /templates/를 지우지 않아서 인지 계속 404 오류가 났었다.

[http://localhost:8080/chat](http://localhost:8080/chat이) 들어가서 개발자용 도구로 js 콘솔 표시로 보면 socket 연결이 됐다고 뜬다 

1. "Handshake failed due to invalid Upgrade header: null"

 brew install nginx;

 nano /usr/local/etc/nginx/nginx.conf 

location / {
proxy_pass [http://localhost:8080](http://localhost:8080/);
proxy_set_header Host $host;
proxy_set_header X-Real-IP $remote_addr;
proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
proxy_set_header X-Forwarded-Proto $scheme;
proxy_set_header Upgrade $http_upgrade;
proxy_set_header Connection "upgrade";
proxy_http_version 1.1;
} <= 이걸로 수정해주기

컨트롤 O, 엔터, 컨트롤 X

nginx -t   

nginx -s reload

------------------------------------------------------------------------------------------
**240520 소켓 로컬 통신**

1. 다른 컴퓨터에서 보낸 메시지가 안보였던 이유
`websocket = new WebSocket("ws://localhost:8080/ws/chat")`  이 부분을 
`websocket = new WebSocket("ws://192.168.101.81:8080/ws/chat")`  ← 이걸로 바꾸니 상대방이 보낸 메시지들도 `payload` log로 잘 뜨는걸 확인함

2. 405 오류, 클라이언트가 해당 경로에 POST 요청을 보내면 서버는 해당 요청을 처리할 수 없으며 "Method Not Allowed" 오류가 발생
   
  "status": 405,
  
  "error": "Method Not Allowed"
  
  "message": "Method 'POST' is not supported.",
  
  "path": "/chat"


 

------------------------------------------------------------------------------------------
**개발 도구**

intellij

mysql
