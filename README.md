KauppalehtiRSS
==============

4 RSS feeds from kauppalehti.fi merged and shown newest posts first

Technology: Java, Spring MVC

Archetype used: spring-mvc-jpa-archetype


TODO:

1. No duplicates ( from different feeds )

2. Server:
 - The feeds checked regularly by timer
 - no duplicate Feed Items added to database (is it duplicate if it is from an another RSS feed? )
 - Tests should have been written

3. Client:
 - Intelligent Ajax behavior for feed requests ( or not to client to refresh page ones a while )
 - Remove unused libraries from git (bootstrap.js)
