package com.spring.demo.admin.userManager.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class UserManagerDTO {

    private Integer userNum;

    private String userId;

    private String userName;

    private String userNickname;

    private String userRights;

    private LocalDateTime userMakingTime;

    private Integer usingNum;

    private String registState;

    private Integer registRoom;

    public UserManagerDTO(Integer userNum, String userId, String userName, String userNickname, String userRights, LocalDateTime userMakingTime, Integer usingNum, String registState, Integer registRoom) {
        this.userNum = userNum;
        this.userId = userId;
        this.userName = userName;
        this.userNickname = userNickname;
        this.userRights = userRights;
        this.userMakingTime = userMakingTime;
        this.usingNum = usingNum;
        this.registState = registState;
        this.registRoom = registRoom;
    }
}
