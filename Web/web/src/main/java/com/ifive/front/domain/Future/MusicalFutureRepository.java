package com.ifive.front.domain.Future;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicalFutureRepository extends JpaRepository<MusicalFuture, Integer>{

    // 전달받은 뮤지컬 ID 리스트로 각 데이터들 조회
    //findByMusicalIdIn -> SELECT * FROM ~ WHERE id IN ~ 와 같음.
    List<MusicalFuture> findByMusicalIdIn(List<Integer> musicalIds);
    
}
