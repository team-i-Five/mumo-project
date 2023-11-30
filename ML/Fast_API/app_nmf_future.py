import json
import pymysql
import pandas as pd
import numpy as np
from sklearn.preprocessing import StandardScaler
from sklearn.decomposition import NMF
from sklearn.metrics.pairwise import cosine_similarity
from fastapi import FastAPI, HTTPException
from fastapi.responses import JSONResponse
import joblib

app_nmf_future = FastAPI()

# MySQL 연결 설정 (애플리케이션 시작 시에 연결을 열고 종료 시에 닫음)
db = pymysql.connect(
    host='ifive-db.ckteh9hwnkjf.ap-northeast-2.rds.amazonaws.com',
    port=3306,
    user='admin',
    passwd='ifive1234',
    db='ifive',
    charset='utf8'
)

# MySQL 데이터베이스에서 과거 및 미래 데이터를 로드
past_sql = "SELECT * FROM musical_past"
future_sql = "SELECT * FROM musical_future"

# 데이터 로드
past_data = pd.read_sql(past_sql, db)
future_data = pd.read_sql(future_sql, db)

# MySQL 연결 닫기 (애플리케이션이 종료될 때)
db.close()

# 뮤지컬 추천을 위한 musical_id에 기반한 엔드포인트 정의
@app_nmf_future.get("/{musical_id}")
def recommend_future(musical_id: int):
    try:
        # 모델 불러오기
        loaded_nmf = joblib.load("npm_future_weights.joblib")
        
        # 선택한 작품의 인덱스 찾기
        selected_work_index_past = past_data[past_data['musical_id'] == musical_id].index[0]

        # 과거 데이터 파싱 및 스케일링
        past_data['synopsis_numpy_scale'] = past_data['synopsis_numpy_scale'].apply(lambda x: np.array(json.loads(x.decode('utf-8')) if isinstance(x, bytes) else x)) # bytes 형태인 경우에만 decode를 적용하도록 수정
        scaler_past = StandardScaler()
        past_data_scaled = scaler_past.fit_transform(np.vstack(past_data['synopsis_numpy_scale']))
        past_data_scaled = past_data_scaled - np.min(past_data_scaled) + 1e-10

        # 미래 작품 선택
        future_data['synopsis_numpy_scale'] = future_data['synopsis_numpy_scale'].apply(lambda x: np.array(json.loads(x.decode('utf-8')) if isinstance(x, bytes) else x))
        scaler_future = StandardScaler()
        future_data_scaled = scaler_future.fit_transform(np.vstack(future_data['synopsis_numpy_scale']))
        future_data_scaled = future_data_scaled - np.min(future_data_scaled) + 1e-10

        # 'list' 객체에 'tolist' 속성이 없는 경우를 고려하여 tolist 호출
        future_data['synopsis_numpy_scale'] = future_data['synopsis_numpy_scale'].apply(lambda x: x.tolist() if hasattr(x, 'tolist') else x if x is not None else [])

        # NMF 모델 초기화
        # nmf = NMF(n_components=10, init='random', random_state=42, max_iter=500)
        nmf = loaded_nmf  # 저장된 모델 불러오기

        # 특성 행렬 생성
        V = np.vstack(past_data_scaled)

        # NMF 모델 훈련
        W = nmf.transform(V)
        
        # NMF 모델 가중치 저장
        # joblib.dump(nmf, "npm_future_weights.joblib")

        # 미래 상영중인 데이터에 대한 특성 행렬 생성
        V_future = np.vstack(future_data_scaled)

        # NMF 모델을 사용하여 미래 상영중인 데이터의 특성 행렬 분해
        W_future = nmf.transform(V_future)

        # 선택한 작품과 다른 작품 간의 코사인 유사도 계산
        selected_work = W[selected_work_index_past].reshape(1, -1)
        similarities = cosine_similarity(W_future, selected_work)

        # 유사도가 높은 순서대로 정렬하여 유사한 작품 인덱스를 찾기
        similar_work_indices = similarities.argsort(axis=0)[::-1].flatten()
        top_n = min(5, len(similar_work_indices))

        # 상위 N개의 유사한 작품에 대한 정보를 추출하고 결과 리스트에 추가
        result = []
        for i in range(top_n):
            index = similar_work_indices[i]
            similarity = float(similarities[index])
            # 작품 정보 추출
            title = future_data.loc[index, 'title']
            musical_id = int(future_data.loc[index, 'musical_id'])
            # 결과 리스트에 작품 정보를 추가
            result.append({"title": title, "musical_id": musical_id, "similarity": similarity})

        return result
    except Exception as e:
        # 예외가 발생한 경우, 에러 응답을 반환
        return HTTPException(status_code=500, detail=f"An error occurred: {str(e)}")