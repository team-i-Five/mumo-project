package com.ifive.front.domain.Past;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MusicalPastRepository extends JpaRepository<MusicalPast, Integer>{
    // Pageable 객체를 통해 SQL문 LIMIT 와 같이 가져올 쿼리 데이터 개수를 정함.
    // Service에서 해당 개수를 정함.
    List<MusicalPast> findAllByOrderByEndDateDesc(Pageable pageable);

    @Query("SELECT mp FROM MusicalPast mp WHERE mp.tag1 = :tag1 ORDER BY mp.endDate DESC")
    List<MusicalPast> queryByTag1(Pageable pageable, String tag1);

    @Query("SELECT mp FROM MusicalPast mp "
        + "WHERE mp.tag1 = :tag1 AND mp.tag2 = :tag2 ORDER BY mp.endDate DESC")
    List<MusicalPast> queryByTag1AndTag2(Pageable pageable, String tag1, String tag2);
    
    @Query("SELECT mp FROM MusicalPast mp "
        + "WHERE mp.tag1 = :tag1 AND mp.tag2 = :tag2 AND mp.tag3 = :tag3 ORDER BY mp.endDate DESC")
    List<MusicalPast> queryByAllTags(Pageable pageable, String tag1, String tag2, String tag3); 

    // 검색기능
    @Query("SELECT mp FROM MusicalPast mp "+ "WHERE mp.title LIKE %:searchKeyword%")
    List<MusicalPast> queryBySearchKeyword(Pageable pageable, @Param("searchKeyword") String searchKeyword);

}
