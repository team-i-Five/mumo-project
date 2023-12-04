package com.ifive.front.domain.Rank;

import java.util.List;

public interface MusicalRankService {
    // 오늘 날짜 기준으로 일간 뮤지컬 랭킹 리스트 가져오기
    List<MusicalRankDTO> getMusicalRankListByUpdateDateSiteName(String updateDate, String siteName);

} 