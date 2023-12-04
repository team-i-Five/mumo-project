package com.ifive.front.domain.Future;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class MusicalFutureServiceImpl implements MusicalFutureService{
// 사용하려면 application-aws에 ml_url={ml url} 추가
    @Value("${ml_url}"+"/recommend/future/")
    private String mlUrl;

    private final RestTemplate restTemplate;

    private MusicalFutureRepository musicalFutureRepository;

    @Autowired
    public MusicalFutureServiceImpl(MusicalFutureRepository musicalFutureRepository, RestTemplate restTemplate) {
        this.musicalFutureRepository = musicalFutureRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Integer> getIDsFromJsonResponse(String jsonResponse) {
        List<Integer> musicalIds = new ArrayList<>();

        JSONArray recommendArray = new JSONArray(jsonResponse);

        for (int i = 0; i < recommendArray.length(); i++) {
            JSONObject musicalObject = recommendArray.getJSONObject(i);
            int musicalId = musicalObject.getInt("musical_id");
            musicalIds.add(musicalId);
        }

        return musicalIds;
    }

    @Override
    public List<MusicalFutureDTO> getFutureDTOsbyIdFromML(String id) {
        // application-aws.properties
        String apiUrl = mlUrl + id;
        // ml에서 받은 json string 파싱할 곳
        List<Integer> ids;

        // GET 요청 보내고 JSON 응답 받기
        String jsonResponse = restTemplate.getForObject(apiUrl, String.class);

        // JSON String 파싱해서 id리스트로 가져옴
        ids =  getIDsFromJsonResponse(jsonResponse);
        return getMusicalsByIds(ids);
    }

    @Override
    public List<MusicalFutureDTO> getMusicalsByIds(List<Integer> musicalIds) {
        List<MusicalFuture> mfl = musicalFutureRepository.findByMusicalIdIn(musicalIds);
        List<MusicalFutureDTO> mflDto = new ArrayList<>();
        
        for(MusicalFuture mf : mfl){
            // log.info("****************add mf : {}",mf.toDTO());
            mflDto.add(mf.toDTO());
            // log.info("****************added mflDto : {}",mflDto.toString());
        }
        return mflDto;
    }
}
