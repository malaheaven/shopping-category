rm -rf ./docker/db/  # remove docker volume directory
./gradlew clean      # spring boot build clean
./gradlew build      # spring boot build
./gradlew bootJar    # spring boot bootJar -> spring rest docs index.html 생성을 위해

cd docker/           # move docker directory

#sh docker-clear.sh  # docker 현재 실행하는 컨테이너 제거 및 docker-compose.yml 정의 파일에서 지정한 컨테이너를 정지시키고 모든 이미지를 삭제 (필요시 주석 해제 후 사용)
sh docker-start.sh   # docker run
