## Explore With Me :performing_arts:

### _Introduction:_
The application allows users to share interesting events, view them, and leave requests to participate in them. For ease of use, events are divided into categories and displayed in the specified sorting. Added the ability to pay for events.

The application is divided into three types of access: administrator, user, public. Verification takes place at all stages of the application.

As for the technical part, the application is a microservice architecture, where the main blog, statistics blog and database are different services that have their own clients to communicate with each other and connect using Docker.

![Image alt](https://github.com/Elizaveta-Shapovalova/java-explore-with-me/raw/main/scheme-MA.png)

---

### _Development:_
 
 -  Java 11
 -  To work with the database used: Hibernate framework , [Query](https://github.com/Elizaveta-shapovalova/java-explore-with-me/blob/main/stats_server/src/main/java/ru/practicum/ewm_stats/repository/HitRepository.java) for more complex requests, [QuerydslPredicateExecutor](https://github.com/Elizaveta-shapovalova/java-explore-with-me/blob/main/main_server/src/main/java/ru/practicum/ewm_main/event/repository/EventRepository.java) for dynamic fields in the request.
 - [Mappers](https://github.com/Elizaveta-shapovalova/java-explore-with-me/blob/main/main_server/src/main/java/ru/practicum/ewm_main/event/mapper/EventMapper.java) have been moved to separate static classes.
 - For validation was used: [Interfaces](https://github.com/Elizaveta-shapovalova/java-explore-with-me/blob/main/main_server/src/main/java/ru/practicum/ewm_main/request/dto/RequestShortDto.java) for separation of field validation and custom [Exceptions](https://github.com/Elizaveta-shapovalova/java-explore-with-me/tree/main/main_server/src/main/java/ru/practicum/ewm_main/exception) with ErrorHandler.
 - For testing app was used: autotests on JS in [Postman](https://github.com/Elizaveta-shapovalova/java-explore-with-me/blob/main/postman/feature.json) for the feature, JUnit, [Mockito](https://github.com/Elizaveta-shapovalova/java-explore-with-me/blob/main/stats_server/src/test/java/ru/practicum/ewm_stats/service/HitServiceImplTest.java) and [Jackson](https://github.com/Elizaveta-shapovalova/java-explore-with-me/blob/main/stats_server/src/test/java/ru/practicum/ewm_stats/dto/EndpointHitTest.java).
 - Also, n-request to the database and to other modules were excluded.
 - Payment system integration [PayPal](https://github.com/Elizaveta-shapovalova/java-explore-with-me/blob/main/main_server/src/main/java/ru/practicum/ewm_main/paypal/client/PaymentClient.java).

---

### _Steak:_

<div>
  <img src="https://github.com/devicons/devicon/blob/master/icons/java/java-original-wordmark.svg" title="Java" alt="Java" width="40" height="40"/>&nbsp;
  <img src="https://github.com/devicons/devicon/blob/master/icons/spring/spring-original-wordmark.svg" title="Spring" alt="Spring" width="40" height="40"/>&nbsp;
  <img src="https://github.com/devicons/devicon/blob/master/icons/postgresql/postgresql-original.svg" title="PostgreSQL" alt="PostgreSQL" width="40" height="40"/>&nbsp;
  <img src="https://github.com/devicons/devicon/blob/master/icons/apache/apache-original.svg" title="Maven" alt="Maven" width="40" height="40"/>&nbsp;
  <img src="https://github.com/devicons/devicon/blob/master/icons/docker/docker-original.svg?short_path=bbeaed2" title="Docker" alt="Docker" width="40" height="40"/>&nbsp;
  <img src="https://github.com/devicons/devicon/blob/master/icons/javascript/javascript-original.svg" title="JS" **alt="JS" width="40" height="40"/>
</div>







