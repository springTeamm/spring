package com.spring.demo.host.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class HostPracticeRoomDTO {
    private Integer roomNumber;
    private String roomName;
    private Integer rentalPrice;
    private Integer sellerDiscountPrice;
    private Integer discountPrice;
    private String displayStatus;
    private LocalDateTime registeredDate;
    private LocalDateTime lastModifiedDate;

    public HostPracticeRoomDTO(Integer roomNumber, String roomName, Integer rentalPrice, Integer sellerDiscountPrice, Integer discountPrice, String displayStatus, LocalDateTime registeredDate, LocalDateTime lastModifiedDate) {
        this.roomNumber = roomNumber;
        this.roomName = roomName;
        this.rentalPrice = rentalPrice;
        this.sellerDiscountPrice = sellerDiscountPrice;
        this.discountPrice = discountPrice;
        this.displayStatus = displayStatus;
        this.registeredDate = registeredDate;
        this.lastModifiedDate = lastModifiedDate;
    }
}
