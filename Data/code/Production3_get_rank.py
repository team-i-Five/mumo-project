from airflow import DAG
import pendulum
import datetime
from airflow.operators.python import PythonOperator
import random

with DAG(
    # DAG 아이디 설정 
    dag_id="get_rank",
    # 하루마다 실행
    schedule_interval="@daily",
    # DAG 시작 시간 11월 15일에서 16로 넘어가는 새벽 1시에 시작.
    start_date=pendulum.datetime(2023, 11, 16, 1, 0, 0, tz="Asia/Seoul"),
    catchup=False
) as dag:
    
    # 첫번째 함수 - 인터파크
    def get_interpark():
        
        import requests
        from bs4 import BeautifulSoup
        import json
        import pandas as pd
        import time
        import datetime
        from datetime import datetime
        import pymysql
        
        tt = datetime.today()
        today = tt.strftime("%Y-%m-%d")
                
        interpark_url = "https://tickets.interpark.com/contents/ranking?genre=musical"
        r_i = requests.get(url=interpark_url)
        tmp_list = [] # 합칠 리스트 생성

        if r_i.status_code ==200:
            print("Connect Success!")
            html_i = r_i.content

        soup_i = BeautifulSoup(html_i, 'html.parser')

        # TOP3는 가장 위에 가로로 배열 -> tag_v
        # TOP4~10은 그 아래라 세로로 배열 -> tag_h
        tag_v = soup_i.findAll('div', class_="ranking-vertical-item_rankingItem__llUL_")
        tag_h = soup_i.findAll('ul', class_='ranking-horizontal-item_rankingContents__z3wFG')

        try:
            # 가로 TOP 3 리스트
            for i in range(len(tag_v)):
                name_v = tag_v[i].find('li', class_='ranking-vertical-item_rankingGoodsName__m0gOz').text
                date_v =tag_v[i].find('div', class_='ranking-vertical-item_dateWrap__uZGMU').text
                location_v = tag_v[i].find('li', class_='ranking-vertical-item_placeName__4sHRS').text
                img_v = tag_v[i].find('div', class_='ranking-vertical-item_imageWrap__R6lkF').find('img')["src"]
                id_v = img_v.split('%')[8].split('_')[0].split('F')[1]
                goods_v = f"https://tickets.interpark.com/goods/{id_v}"
                img_url_v = f"https://tickets.interpark.com{img_v}"
                
                v_dict = {
                    'title': name_v,
                    'date' : date_v,
                    'location' : location_v,
                    'poster_url' : img_url_v,
                    'goods_url' : goods_v
                }
                tmp_list.append(v_dict)
            
            # 세로 TOP 4~10 리스트
            for j in range(7):
                name_h = tag_h[j].find('li', class_='ranking-horizontal-item_rankingTicketTitle__omJYh').text
                date_h = date_h = tag_h[j].find('div', class_='ranking-horizontal-item_dateWrap__tRsWh').text
                location_h = tag_h[j].find('li', class_='ranking-horizontal-item_placeName__zb9kN').text
                img_h = tag_h[j].find('div', class_='ranking-horizontal-item_imageWrap__owTl6').find('img')['src']
                id_h = img_h.split('%')[8].split('_')[0].split('F')[1]
                goods_h = f"https://tickets.interpark.com/goods/{id_h}"
                img_url_h = f"https://tickets.interpark.com{img_h}"
                
                h_dict = {
                    'title': name_h,
                    'date' : date_h,
                    'location' : location_h,
                    'poster_url' : img_url_h,
                    'goods_url' : goods_h
                }
                tmp_list.append(h_dict)

        except Exception as  e:
            print(f"에러 발생 : {e}")

        interpark_df = pd.DataFrame(tmp_list)

        # rank, site_name 컬럼 추가 및 컬럼 순서 변경
        interpark_df['rank'] = range(1, len(interpark_df) + 1)
        interpark_df['site_name'] = '인터파크'
        interpark_df = interpark_df[['site_name', 'rank', 'title', 'date', 'location', 'poster_url', 'goods_url']]

        # 데이터 업데이트 날짜 기입
        interpark_df['update_date']=today
        
        # db 설정
        host = 'ifive-db.ckteh9hwnkjf.ap-northeast-2.rds.amazonaws.com'
        pwd = 'ifive1234'
        db = 'ifive'
        
        try:
            con = pymysql.connect(host=host, user='admin', password=pwd,  db=db, charset='utf8', port=3306)
            cur = con.cursor()
        except Exception as e:
                print (e)(e)
                
        sql = "INSERT INTO musical_rank(site_name, ranking, title, date, location, poster_url, goods_url, update_date) VALUES(%s, %s, %s, %s, %s, %s, %s, %s)"

        # 인터파크 데이터 삽입
        for idx, row in interpark_df.iterrows():
            try:
                print(tuple(row.values))
                cur.execute(sql,tuple(row.values))
            except Exception as e:
                print(e)

        con.commit()
                      
    # 첫번째 task
    py_t1 = PythonOperator(
            task_id='t1_interpark', #taskid
            python_callable=get_interpark #task에서 실행할 파이썬 함수 설정
        )
    
    
    
    # 두번째 함수 - 티켓링크
    def get_tickeklink():
        import requests
        from bs4 import BeautifulSoup
        import json
        import pandas as pd
        import time
        import datetime
        from datetime import datetime
        import pymysql
        
        tt = datetime.today()
        today = tt.strftime("%Y-%m-%d")

        url_t = "https://mapi.ticketlink.co.kr/mapi/ranking/genre/daily?categoryId=10&categoryId2=16&categoryId3=0&menu=RANKING"

        r_t = requests.get(url=url_t)

        if r_t.status_code == 200:
            print("Connect Success!")
            
            # json 데이터 df 변경
            data = json.loads(r_t.text)['data']['rankingList']
            ticketlink_df = pd.DataFrame(data)

        # 필요한 컬럼 추출 및 전처리
        columns = ['productId', 'productName', 'startDate', 'endDate', 'hallName', 'imgUrl']
        ticketlink_df = ticketlink_df[columns]

        ticketlink_df['imgUrl'] = 'https:' + ticketlink_df['imgUrl']
        ticketlink_df['productId'] = 'https://www.ticketlink.co.kr/product/' + ticketlink_df['productId'].astype(str)
        ticketlink_df['startDate'] = ticketlink_df['startDate'].apply(lambda x: datetime.fromtimestamp(x / 1000).strftime('%Y.%m.%d.'))
        ticketlink_df['endDate'] = ticketlink_df['endDate'].apply(lambda x: datetime.fromtimestamp(x / 1000).strftime('%Y.%m.%d.'))
        ticketlink_df['date'] = ticketlink_df['startDate'] + ' ~ ' + ticketlink_df['endDate']
        ticketlink_df.drop(columns=['startDate'], inplace=True)
        ticketlink_df.drop(columns=['endDate'], inplace=True)

        # 'rank', 'siteName' 컬럼 추가
        ticketlink_df['rank'] = range(1, len(ticketlink_df) + 1)
        ticketlink_df['site_name'] = '티켓링크'

        # 컬럼 순서 변경
        columns = ['site_name', 'rank', 'productName', 'date', 'hallName', 'imgUrl', 'productId']
        ticketlink_df = ticketlink_df[columns]

        # 컬럼명 변경
        ticketlink_df.rename(columns={'productName': 'title', 'hallName': 'location', 'productId': 'goods_url', 'imgUrl':'poster_url'}, inplace=True)

        ticketlink_df['update_date'] = today
        
        # db 설정
        host = 'ifive-db.ckteh9hwnkjf.ap-northeast-2.rds.amazonaws.com'
        pwd = 'ifive1234'
        db = 'ifive'
        
        try:
            con = pymysql.connect(host=host, user='admin', password=pwd,  db=db, charset='utf8', port=3306)
            cur = con.cursor()
        except Exception as e:
                print (e)(e)
                
        sql = "INSERT INTO musical_rank(site_name, ranking, title, date, location, poster_url, goods_url, update_date) VALUES(%s, %s, %s, %s, %s, %s, %s, %s)"
        
        # 티켓링크 데이터 삽입
        for idx, row in ticketlink_df.iterrows():
            try:
                print(tuple(row.values))
                cur.execute(sql,tuple(row.values))
            except Exception as e:
                print(e)

        con.commit()
            
    # 두번째 task
    py_t2 = PythonOperator(
        task_id='t2_ticketlink', #taskid
        python_callable=get_tickeklink #task에서 실행할 파이썬 함수 설정
    )
    
    
    
    # 세번쨰 함수 - yes24
    def get_yes24():
        import requests
        from bs4 import BeautifulSoup
        import json
        import pandas as pd
        import time
        import datetime
        from datetime import datetime
        import pymysql
        
        tt = datetime.today()
        today = tt.strftime("%Y-%m-%d")
        
        # yes_url = "http://ticket.yes24.com/New/Rank/Ranking.aspx?genre=15457"

        yes_url = "http://ticket.yes24.com/New/Rank/Ajax/RankList.aspx"

        payload = {
            "pt": "1"
            ,"ci": "16"
            ,"et": "2023-11-{}".format(today[-2:])
        }

        header = {
            "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36"
        }

        r_y = requests.post(yes_url, payload ,headers= header)

        if r_y.status_code == 200:
            print("Connect Success!")
            soup1 = BeautifulSoup(r_y.text, "html.parser")
            # 랭킹 1~3위 데이터
            best123 = soup1.find("div", class_="rank-best")
            # 랭킹 4~10위 데이터
            list410 = soup1.find("div", class_="rank-list")

        data_list = []

        # try:
        # 랭킹 1~3위 추출
        for i in range(3):
            y_dict = {
                'site_name' : '예스24티켓',
                'rank' : None,
                'title': None,
                'date' : None,
                'location' : None,
                'poster_url' : None,
                'goods_url' : None,
                'update_date' : today
            }
            
            title_y = best123.find_all("p", class_="rlb-tit")[i].get_text()
            date_y = best123.find_all('p', class_="rlb-sub-tit")[i].get_text(separator='|', strip=True).split('|')[0]
            location_y = best123.find_all('p', class_="rlb-sub-tit")[i].get_text(separator='|', strip=True).split('|')[1]
            img_url_y = best123.find_all("span", class_="rank-best-img")[i].find("img",class_="rank-best-img").get("src")
            goods_y = best123.find("a", title = title_y).get("href").split("=")[1]
            rank_y = best123.find_all("p", class_ = "rank-best-number")[i].find("span").get_text()[:-1]

            y_dict = {
                'site_name' : '예스24티켓',
                'rank' : rank_y,
                'title': title_y,
                'date' : date_y,
                'location' : location_y,
                'poster_url' : img_url_y,
                'goods_url' : "http://ticket.yes24.com/Perf/{}".format(goods_y),
                'update_date' : today
            }

            data_list.append(y_dict)


        for i in range(0,31,5):
            y_dict = {
                'site_name' : '예스24티켓',
                'rank' : None,
                'title': None,
                'date' : None,
                'location' : None,
                'poster_url' : None,
                'goods_url' : None,
                'update_date' : today
            }
            
            title_y = list410.find_all("div",class_=False)[i].find("p", class_="rank-list-tit").get_text()
            date_y = list410.find_all("div",class_=False)[i].find_all("p")[-1].get_text(separator='|', strip=True).split('|')[0]
            location_y = list410.find_all("div",class_=False)[i].find_all("p")[-1].get_text(separator='|', strip=True).split('|')[1]
            img_url_y = list410.find("img", alt=title_y).get("src")
            goods_y = list410.find_all("div",class_=False)[i].find("p", class_="rank-list-tit").find("a").get("href").split("=")[1]
            rank_y = list410.find_all("div",class_=False)[i].find_all("p")[0].get_text()[:-1]
            
            y_dict = {
                'site_name' : '예스24티켓',
                'rank' : rank_y,
                'title': title_y,
                'date' : date_y,
                'location' : location_y,
                'poster_url' : img_url_y,
                'goods_url' : "http://ticket.yes24.com/Perf/{}".format(goods_y),
                'update_date' : today
            }

            data_list.append(y_dict)

        # except Exception as e:
        #     print(e)

        yes24_df= pd.DataFrame(data_list)

        # db 설정
        host = 'ifive-db.ckteh9hwnkjf.ap-northeast-2.rds.amazonaws.com'
        pwd = 'ifive1234'
        db = 'ifive'
        
        try:
            con = pymysql.connect(host=host, user='admin', password=pwd,  db=db, charset='utf8', port=3306)
            cur = con.cursor()
        except Exception as e:
                print (e)(e)
                
        sql = "INSERT INTO musical_rank(site_name, ranking, title, date, location, poster_url, goods_url, update_date) VALUES(%s, %s, %s, %s, %s, %s, %s, %s)"
        
        # yes24티켓 데이터 삽입
        for idx, row in yes24_df.iterrows():
            try:
                print(tuple(row.values))
                cur.execute(sql,tuple(row.values))
            except Exception as e:
                print(e)

        con.commit()
        
    # 세번째 task
    py_t3 = PythonOperator(
        task_id='t3_yes24', #taskid
        python_callable=get_yes24 #task에서 실행할 파이썬 함수 설정
    )
    

    # 테스크 실행
    py_t1 >> py_t2 >> py_t3