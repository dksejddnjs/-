**20240516 docker 세팅, 스프링 부트 실행**    
오류: 스프링 부트 실행했을 때 whitelabel Error Page 뜨지만 새로고침 했을 때 사진처럼 오류 발생
<https://github.com/dksejddnjs/CM/blob/main/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202024-05-16%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%202.43.30.png>
사진과 상관없는 오류 => 실행했을 때 url 찾기 불가, db 연결 관련 문제

------------------------------------------------------------------------------------------
**20240517 스프링 부트 실행 시 오류 및 DB 설정 관련 오류**
1. Application.propertise에서 spring.jpa.database-platform=…/ spring.jpa.properties.hibernate.dialect=…
   -> 이 명령어는 기본적으로 잡아주는 거라 필요없다는 오류
2. constructor parameter 0: Error creating bean with name 'userRepository' defined in com.example.demo.repository.UserRepository defined in @EnableJpaRepositories declared on JpaRepositoriesRegistrar.EnableJpaRepositoriesConfiguration: Not a managed type: class com.example.demo.entity.UserEntity
   -> entity나 repository의 코드 오류
3. org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'entityManagerFactory' defined in class path resource [org/springframework/boot/autoconfigure/orm/jpa/HibernateJpaConfiguration.class]: Class org.springframework.orm.jpa.vendor.SpringHibernateJpaPersistenceProvider does not implement the requested interface jakarta.persistence.spi.PersistenceProvider
   -> Spring Boot와 Jakarta Persistence API 간의 버전 충돌로 인한 오류/ 애플리케이션이 시작될 때 **`BeanCreationException`**이 발생했음을 알 수 있고 이 문제는 **`entityManagerFactory`** 빈을 생성하는 중에 발생함. 에러 메시지를 보면 **`SpringHibernateJpaPersistenceProvider`** 클래스가 **`jakarta.persistence.spi.PersistenceProvider`** 인터페이스를 구현하지 않는다는 것... 

entityManagerFactory: entity나 hibernate, jakarta.persistence 등 DB 설정 관련
Dependency 디펜던시: 의존성

------------------------------------------------------------------------------------------
**개발 도구**
intellij
mysql
