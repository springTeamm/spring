package com.spring.demo.setlist.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class SetListDTO {

    private Integer listNum;
    private String listTitle;
    private Integer teamInterval;
    private Integer teamBeatNum;
    private Integer teamPerformanceNum;
}
