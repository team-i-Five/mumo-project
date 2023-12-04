package com.ifive.front.domain.Present;

import java.util.List;

public interface MusicalPresentService {
    
    // ID값 파라미터로 넣어서 호출하면, ML서버에 접속해서 ID값으로 유사한 뮤지컬 5개의 ID값을 가져오고
    // 해당 ID값으로 DB를 조회해서 MusicalPresent 정보를 가져와서 DTO에 담습니다.
    List<MusicalPresentDTO> getPresentDTOsbyIdFromML(String id);

    //getPresentDTOsbyIdFromML 내부에서 동작하는 함수로 ML에서 응답한 jsonResponse에서 ID값을 가져옵니다.
    List<Integer> getIDsFromJsonResponse(String jsonResponse);

    
    // List<Integer>를 이용해서 DB에서 id값에 일치하는 것들 가져옴
    List<MusicalPresentDTO> getMusicalsByIds(List<Integer> musicalIds);
}
