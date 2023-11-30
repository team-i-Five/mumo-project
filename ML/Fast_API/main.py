from fastapi import FastAPI
from app_nmf import app_nmf
from app_nmf_future import app_nmf_future

app = FastAPI(favicon=None)

app.include_router(app_nmf.router, prefix="/recommend/present")
app.include_router(app_nmf_future.router, prefix="/recommend/future")


# 예시로 간단한 루트 엔드포인트 추가
@app.get("/")
def read_root():
    return {"message": "경로를 올바르게 입력하세요!"}

# app_nmf의 엔드포인트를 사용하는 예시
@app.get("/recommend/present")
def use_app_nmf():
    # app_nmf를 사용하여 무언가를 수행
    return {"message": "present"}

# app_nmf_future 엔드포인트를 사용하는 예시
@app.get("/recommend/future")
def use_app_nmf_future():
    # app_nmf_future를 사용하여 무언가를 수행
    return {"message": "future"}

# uvicorn main:app --reload --host 0.0.0.0 --port 8080