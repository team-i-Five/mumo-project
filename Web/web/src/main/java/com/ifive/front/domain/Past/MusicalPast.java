package com.ifive.front.domain.Past;

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
@Table(name = "musical_past")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class MusicalPast {
    @Id
    @Column
    private Integer idNum;

    @Column
    private Integer musicalId;

    @Column
    private String title;

    @Column
    private String posterUrl;

    @Column
    private String genre;

    @Column
    private String date;

    @Column
    private String startDate;

    @Column
    private String endDate;

    @Column
    private String location;

    @Column
    private String actors;

    @Column
    private String ageRating;

    @Column
    private String runningTime;

    @Column
    private String info;

    @Column
    private String synopsis;

    @Column
    private String synopsisClear;

    @Column
    private String tokenizedData;

    @Column
    private byte[] synopsisVector;

    @Column
    private byte[] synopsisNumpy;

    @Column
    private byte[] synopsisNumpyScale;

    @Column
    private String tag1;

    @Column
    private String tag2;
    
    @Column
    private String tag3;

    public MusicalPastDTO toDTO() {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(this, MusicalPastDTO.class);
    }
}
