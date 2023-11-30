# 기본 이미지로 파이썬 3.12 사용
FROM python:3.12

# 작업 디렉토리 설정
WORKDIR /app

# 파이썬 가상환경 생성
RUN python -m venv venv

# 파이썬 가상환경 활성화
SHELL ["/bin/bash", "-c"]
RUN source venv/bin/activate

# 필요한 파이썬 패키지를 설치
RUN pip install jupyter requests beautifulsoup4 selenium 

# Jupyter 노트북을 실행
## , "--NotebookApp.token=''" -> Jupyter notebook을 토큰없이 실행
###                             => http://localhost:8888
CMD ["jupyter", "notebook", "--ip=0.0.0.0", "--port=8888", "--no-browser", "--allow-root", "--NotebookApp.token=''"]

# 포트 8888 외부로 노출
EXPOSE 8888
