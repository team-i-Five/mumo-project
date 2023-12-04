package com.ifive.front.domain.Rank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class MusicalRankDTO {
    private String siteName;
    private Integer ranking;
    private String title;
    private String date;
    private String location;
    private String posterUrl;
    private String goodsUrl;
    private String updateDate;
}
