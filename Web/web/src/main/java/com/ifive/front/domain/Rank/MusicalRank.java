package com.ifive.front.domain.Rank;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "musical_rank")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class MusicalRank {
    @Id
    @Column
    private Integer id;
    
    @Column
    private String siteName;

    @Column
    private Integer ranking;

    @Column
    private String title;

    @Column
    private String date;

    @Column
    private String location;

    @Column
    private String posterUrl;

    @Column
    private String goodsUrl;

    @Column
    private String updateDate;

    public MusicalRankDTO toDTO() {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(this, MusicalRankDTO.class);
    }

}
