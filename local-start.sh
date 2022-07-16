# local-start.sh
./gradlew build                     # spring boot build
./gradlew bootJar                   # spring boot bootJar -> spring rest docs index.html 생성을 위해
cd build/libs/                      # move build/libs/
java -Dspring.profiles.active=local -jar shopping-category-0.0.1-SNAPSHOT.jar # start local spring boot
