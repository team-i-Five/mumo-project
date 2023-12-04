#클라우드 환경
FROM ubuntu:22.04

# jar 저장할 디렉토리
WORKDIR /ifive_boot


# 작업파일 복사하고 ifive_boot에 복사
COPY  .  .

# 실행환경 설정
RUN apt update

RUN apt install openjdk-17-jre-headless -y

RUN apt install maven -y

RUN mvn clean package

# 사용포트 
EXPOSE 8081

# nohub 실행 명령
ENTRYPOINT [ "java", "-jar", "/ifive_boot/target/front-0.0.1-SNAPSHOT.jar" ]