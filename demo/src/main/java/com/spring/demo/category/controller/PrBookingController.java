package com.spring.demo.category.controller;

import com.spring.demo.category.service.PrBookingService;
import com.spring.demo.entity.PrBooking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reserve")
public class PrBookingController {

    private final PrBookingService prBookingService;

    @Autowired
    public PrBookingController(PrBookingService prBookingService) {
        this.prBookingService = prBookingService;
    }

    @GetMapping("/room/{prNum}")
    public List<PrBooking> getBookingsByRoomAndDate(
            @PathVariable Integer prNum,
            @RequestParam String date) {
        return prBookingService.getBookingsByRoomAndDate(prNum, date);
    }

    @PostMapping
    public PrBooking createReservation(@RequestBody PrBooking booking) {
        return prBookingService.saveBooking(booking);
    }

    @DeleteMapping("/{bookingNum}")
    public void cancelReservation(@PathVariable Integer bookingNum) {
        prBookingService.cancelBooking(bookingNum);
    }


    @GetMapping("/room/all/{prNum}")
    public List<PrBooking> getAllReservationsByRoom(@PathVariable Integer prNum) {
        return prBookingService.getBookingsByRoom(prNum);
    }
}
