package com.spring.demo.setlist.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MusicListDTO {

    private Integer musicListNum;
    private String musicName;
    private String musicPath;

}
