package com.ifive.front.domain.Present;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicalPresentRepository extends JpaRepository<MusicalPresent, Integer>{

    // 전달받은 뮤지컬 ID 리스트로 각 데이터들 조회
    //findByMusicalIdIn -> SELECT * FROM ~ WHERE id IN ~ 와 같음.
    List<MusicalPresent> findByMusicalIdIn(List<Integer> musicalIds);
    
}
