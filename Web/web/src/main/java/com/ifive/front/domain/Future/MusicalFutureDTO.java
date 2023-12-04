package com.ifive.front.domain.Future;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.ToString;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // DTO에는 numb가없어서 무시하게함
@ToString
public class MusicalFutureDTO {

    private Integer musicalId;
    private String title;
    private String posterUrl;
    private String genre;
    private String date;
    private String startDate;
    private String endDate;
    private String location;
    private String actors;
    private String ageRating;
    private String runningTime;
    private String info;
    private String synopsis;
    private String tag1;
    private String tag2;
    private String tag3;
}
