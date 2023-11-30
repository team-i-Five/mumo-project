# code_Machine-learning
프로젝트 머신러닝 코드 repo

## 추천 과정
### 1. 처음 뮤지컬을 접한 사용자
**1) 사용자가 tag 3개를 선택하면 전체 데이터(train)에서 필터링된 데이터 보여줌(50개)**\
→ 최신순으로 50개 보여줌

**2) 필터링된 50개의 데이터(train) 중에 사용자가 작품을 선택하면 해당 작품과 유사도가 높은 현재 상영중인 작품(present) 추천**\
→ 즉, train데이터와 present데이터 줄거리 간의 유사도를 측정해서 추천

### 2. 뮤지컬을 한 번 이상 본 사용자
**1) [랭킹순으로 유명한 작품 or 검색을 통한 작품]을 먼저 보여주고, 사용자가 봤던 작품 하나를 선택**

**2) 선택한 작품과 유사도가 높은 현재 상영중인 작품(present) 추천**\
→ 즉, train데이터와 present데이터 줄거리 간의 유사도를 측정해서 추천

----
## 파일명
### DATA
- **train_data**\
`Data0_train.csv` : 원본 학습 데이터\
`Data1_cleaned_data.csv` : train.csv의 특수문자 및 HTML 엔터티 코드 제거한 데이터\
`Data2_past_vector.csv` : 날짜 처리 및 시놉시스 벡터 컬럼 추가된 데이터\
`Data3_vector_tag.csv` : Data2_past_vector.csv에 tag 3개 붙인 데이터\
`Data4_train_nan.csv` :  tag가 없는 값 모두 삭제, synopsis_numpy, synopsis_numpy_scale 컬럼 추가된 데이터\
`Data5_train_final.csv` : 'poster_url', 'date', 'actors'제외 전처리 + 중복 제거 = 총 8883rows → 최종데이터!!

- **present_data**\
`Data0_present.csv` : 원본 학습 데이터\
`Data1_cleaned_present.csv` : train.csv의 특수문자 및 HTML 엔터티 코드 제거한 데이터\
`Data2_present_vector.csv` : 날짜 처리 및 시놉시스 벡터 컬럼 추가된 데이터\
`Data3_present_tag.csv` : Data2_past_vector.csv에 tag 3개 붙인 데이터\
`Data4_present_nan.csv` :  tag가 없는 값 모두 삭제, synopsis_numpy, synopsis_numpy_scale 컬럼 추가된 데이터\
`Data4.1_present_final.csv` : 'poster_url', 'date', 'actors'제외 전처리 → 최종데이터!!

### DATA_PREPROCESSING
- **train_data_preprocessing**\
`train_synopsis_vector.ipynb` : 날짜 처리 및 시놉시스 벡터화 코드\
`train_tag.ipynb` : tag 3개 붙이는 코드\
`train_nan.ipynb` : tag, 날짜처리, tag가 없는 값 모두 삭제, synopsis_numpy, synopsis_numpy_scale 컬럼 추가

- **present_data_preprocessing**\
`present_synopsis_vector.ipynb` : 날짜 처리 및 시놉시스 벡터화 코드\
`present_tag.ipynb` : tag 3개 붙이는 코드\
`present_nan.ipynb` : tag, 날짜처리, tag가 없는 값 모두 삭제, synopsis_numpy, synopsis_numpy_scale 컬럼 추가

### MODEL
- **model_test**\
`Knn_musical_v2.ipynb`\
`SVD_musical.ipynb`\
`NMF_musical.ipynb` → 최종 모델
----
## branch
`0.1.1`(민정) → train 데이터 벡터화, tag \
`0.1.2`(수빈) → present 데이터 벡터화, tag / Knn_musical_v1 / fastapi \
`0.1.3`(민정) → model_test / SVD_musical, Knn_musical_v2, NMF_musical / 데이터 전처리(중복제거) \
`0.2.0`(하현) → data_db
