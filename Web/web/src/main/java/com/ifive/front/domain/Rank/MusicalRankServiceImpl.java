package com.ifive.front.domain.Rank;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MusicalRankServiceImpl implements MusicalRankService {
    
    private MusicalRankRepository musicalRankRepository;

    @Autowired
    public MusicalRankServiceImpl(MusicalRankRepository musicalRankRepository){
        this.musicalRankRepository = musicalRankRepository;
    }

    @Override
    public List<MusicalRankDTO> getMusicalRankListByUpdateDateSiteName(String updateDate, String siteName){
        List<MusicalRank> mrList = musicalRankRepository.queryByUpdateDateSiteName(updateDate, siteName);
        List<MusicalRankDTO> mrd = new ArrayList<>();
        
        for(MusicalRank mr : mrList){
            mrd.add(mr.toDTO());
        }

        return mrd;        

    }

}
