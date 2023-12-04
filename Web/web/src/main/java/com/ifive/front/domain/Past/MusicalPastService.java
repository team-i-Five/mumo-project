package com.ifive.front.domain.Past;

import java.util.List;

public interface MusicalPastService {
    
    // 과거의 뮤지컬에서 end_date 최신순으로 정렬한 전체 데이터 조회
    List<MusicalPastDTO> getMusicalPastListOrderByEndDate(int count);

    // tag1에 해당하는 뮤지컬 가져오기
    List<MusicalPastDTO> getMusicalPastListByTag1(String tag1);

    // tag1과 tag2에 해당하는 뮤지컬 가져오기
    List<MusicalPastDTO> getMusicalPastListByTag1AndTag2(String tag1, String tag2);

    // 모든 tag를 통해 필터링된 뮤지컬 가져오기
    List<MusicalPastDTO> getMusicalPastListByAllTags(String tag1, String tag2, String tag3);    

    // 검색 기능
    List<MusicalPastDTO> getMusicalPastListBySearch(String searchKeyword);
}
