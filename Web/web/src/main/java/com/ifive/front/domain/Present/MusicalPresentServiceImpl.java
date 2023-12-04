package com.ifive.front.domain.Present;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MusicalPresentServiceImpl implements MusicalPresentService {

    // 사용하려면 application-aws에 ml_url={ml url} 추가
    @Value("${ml_url}"+"/recommend/present/")
    private String mlUrl;

    private final RestTemplate restTemplate;

    private MusicalPresentRepository musicalPresentRepository;

    @Autowired
    public MusicalPresentServiceImpl(MusicalPresentRepository musicalPresentRepository, RestTemplate restTemplate) {
        this.musicalPresentRepository = musicalPresentRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Integer> getIDsFromJsonResponse(String jsonResponse) {
        List<Integer> musicalIds = new ArrayList<>();

        JSONArray recommendArray = new JSONArray(jsonResponse);

        // log.info("추천 배열 : "+recommendArray);

        for (int i = 0; i < recommendArray.length(); i++) {
            JSONObject musicalObject = recommendArray.getJSONObject(i);
            int musicalId = musicalObject.getInt("musical_id");
            musicalIds.add(musicalId);
        }

        // log.info("뮤지컬 ID 리스트 : "+musicalIds);
        return musicalIds;
    }

    @Override
    public List<MusicalPresentDTO> getPresentDTOsbyIdFromML(String id) {
        // application-aws.properties
        String apiUrl = mlUrl + id;
        // ml에서 받은 json string 파싱할 곳
        List<Integer> ids;

        // GET 요청 보내고 JSON 응답 받기
        String jsonResponse = restTemplate.getForObject(apiUrl, String.class);

        // log.info("제이슨 : "+jsonResponse);

        // JSON String 파싱해서 id리스트로 가져옴
        ids =  getIDsFromJsonResponse(jsonResponse);

        // log.info("아이디스 : "+ids);

        return getMusicalsByIds(ids);
    }
    
    @Override
    public List<MusicalPresentDTO> getMusicalsByIds(List<Integer> musicalIds) {
        List<MusicalPresent> mpl = musicalPresentRepository.findByMusicalIdIn(musicalIds);
        List<MusicalPresentDTO> mplDto = new ArrayList<>();
        
        for(MusicalPresent mp : mpl){
            // log.info("****************add mp : {}",mp.toDTO());
            mplDto.add(mp.toDTO());
            // log.info("****************added mplDto : {}",mplDto.toString());
        }
        
        return mplDto;
    }


}
