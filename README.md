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
 - [Mappers](https://github.com/Elizaveta-shapovalova/java-explore-with-me/blob/main/stats_server/src/main/java/ru/practicum/ewm_stats/repository/HitRepository.java) have been moved to separate static classes.
 - 












