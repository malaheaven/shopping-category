# local-start.sh
./gradlew bootJar                   # spring boot bootJar
cd build/libs/                      # move build/libs/
java -Dspring.profiles.active=local -jar shopping-category-0.0.1-SNAPSHOT.jar # start local spring boot
