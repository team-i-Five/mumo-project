# 사용할 dockerhub 이미지
FROM apache/airflow:2.7.3-python3.9

# 관리자 계정 사용
USER root

# airflow 실행에 필요한 패키지 설치
RUN apt-get update \
  && apt-get install -y --no-install-recommends \
         vim \
  && apt-get autoremove -yqq --purge \
  && apt-get clean \
  && rm -rf /var/lib/apt/lists/*

# 관리자 계정 사용
User root

# 관리자 권한이 필요한 명령어
## airflow DAG 시간대를 Asia/Seoul로 설정
RUN ln -snf /usr/share/zoneinfo/Asia/Seoul /etc/localtime

# airflow 계정
USER airflow

# 로컬에 저장한 dag 파일 복사
COPY ./dags /opt/airflow/dags

# 해당 dag 파일 실행에 필요한 PYPI 모듈 설치
COPY requirements.txt ./requirements.txt
RUN pip3 install -r requirements.txt

# 초기화 스크립트 실행
RUN airflow db init

# admin 사용자 생성 및 초기 비밀번호 설정
RUN airflow users create \
    --username admin \
    --firstname ifive \
    --lastname data \
    --role Admin \
    --email admin@example.com \
    --password 1234

# 웹 서버 및 스케줄러 시작
CMD ["bash", "-c", "airflow webserver -p 8080 -D & airflow scheduler"]
