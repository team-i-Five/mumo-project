package com.ifive.front.domain.Past;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class MusicalPastServiceImpl implements MusicalPastService{
    
    private MusicalPastRepository musicalPastRepository;

    @Autowired
    public MusicalPastServiceImpl(MusicalPastRepository musicalPastRepository){
        this.musicalPastRepository = musicalPastRepository;
    }

    // 검색
    @Override
    public List<MusicalPastDTO> getMusicalPastListBySearch(String searchKeyword){
        Pageable pageable = PageRequest.of(0,50);
        List<MusicalPast> mpl = musicalPastRepository.queryBySearchKeyword(pageable, searchKeyword);
        List<MusicalPastDTO> mplDto = new ArrayList<>(); // mplDto 변수 선언 및 초기화
    
        for(MusicalPast mp : mpl){
            mplDto.add(mp.toDTO());
        }
    
        return mplDto;
    }
    
    //
    @Override
    public List<MusicalPastDTO> getMusicalPastListOrderByEndDate(int count){
        // Pageable을 통해 0부터 count까지의 쿼리 데이터만 가져옴.
        Pageable pageable = PageRequest.of(0,count);

        List<MusicalPast> mpl = musicalPastRepository.findAllByOrderByEndDateDesc(pageable);
        // log.info("mpl 보여줘 : "+mpl);
        List<MusicalPastDTO> mplDto = new ArrayList<>();

        for(MusicalPast mp : mpl){
            mplDto.add(mp.toDTO());
        }

        return mplDto;
    }

    // 태그 관련
    @Override
    public List<MusicalPastDTO> getMusicalPastListByTag1(String tag1){
        Pageable pageable = PageRequest.of(0,50);

        List<MusicalPast> mpl = musicalPastRepository.queryByTag1(pageable, tag1);

        List<MusicalPastDTO> mplDto = new ArrayList<>();
        
        for(MusicalPast mp : mpl){
            mplDto.add(mp.toDTO());
        }

        return mplDto;
    }

    @Override
    public List<MusicalPastDTO> getMusicalPastListByTag1AndTag2(String tag1, String tag2){
        Pageable pageable = PageRequest.of(0,50);

        List<MusicalPast> mpl = musicalPastRepository.queryByTag1AndTag2(pageable, tag1, tag2);

        List<MusicalPastDTO> mplDto = new ArrayList<>();
        
        for(MusicalPast mp : mpl){
            mplDto.add(mp.toDTO());
        }

        return mplDto;
    }

    @Override
    public List<MusicalPastDTO> getMusicalPastListByAllTags(String tag1, String tag2, String tag3){
        Pageable pageable = PageRequest.of(0,50);

        List<MusicalPast> mpl = musicalPastRepository.queryByAllTags(pageable, tag1, tag2, tag3);

        List<MusicalPastDTO> mplDto = new ArrayList<>();
        
        for(MusicalPast mp : mpl){
            mplDto.add(mp.toDTO());
        }

        return mplDto;
    }
  
}
