package com.spring.demo.host.service;


import com.spring.demo.entity.*;
import com.spring.demo.host.DTO.SalesDTO;
import com.spring.demo.host.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class SalesService {

    private final HostAdjustmentRepository adjustmentRepository;
    private final HostPaymentRepository paymentRepository;
    private final HostPrBookingRepository prBookingRepository;
    private final HostPracticeRoomRepository practiceRoomRepository;
    private final HostPrCategoryRepository prCategoryRepository;
    @Autowired
    public SalesService(HostAdjustmentRepository adjustmentRepository,
                        HostPaymentRepository paymentRepository,
                        HostPrBookingRepository prBookingRepository,
                        HostPracticeRoomRepository practiceRoomRepository,
                        HostPrCategoryRepository prCategoryRepository) {
        this.adjustmentRepository = adjustmentRepository;
        this.paymentRepository = paymentRepository;
        this.prBookingRepository = prBookingRepository;
        this.practiceRoomRepository = practiceRoomRepository;
        this.prCategoryRepository = prCategoryRepository;
    }

    public List<SalesDTO> getMonthlySales() {
        List<Adjustment> adjustments = adjustmentRepository.findAll();

       Map<Integer, Payment> paymentMap = paymentRepository.findAll().stream()
                .collect(Collectors.toMap(Payment::getPayNum, payment -> payment));

        Map<Integer, PrBooking> bookingMap = prBookingRepository.findAll().stream()
                .collect(Collectors.toMap(PrBooking::getBookingNum, booking -> booking));

         Map<Integer, PracticeRoom> roomMap = practiceRoomRepository.findAll().stream()
                .collect(Collectors.toMap(PracticeRoom::getPrNum, room -> room));
      Map<Integer, String> categoryMap = prCategoryRepository.findAll().stream()
                .collect(Collectors.toMap(PrCategory::getCategoryNum, PrCategory::getCategoryName));

      Map<String, Map<String, Integer>> monthlySales = new HashMap<>();

        for (Adjustment adjustment : adjustments) {
            Payment payment = paymentMap.get(adjustment.getPayNum());
            if (payment == null) continue;

            PrBooking booking = bookingMap.get(payment.getBookingNum());
            if (booking == null) continue;

            PracticeRoom room = roomMap.get(booking.getPrNum());
            if (room == null) continue;

            String categoryName = categoryMap.getOrDefault(room.getCategoryNum(), "Unknown");

            // Extract year-month from adjustment date
            String month = adjustment.getAdDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
                    .withDayOfMonth(1)
                    .toString();

            // Accumulate sales
            monthlySales.computeIfAbsent(month, k -> new HashMap<>())
                    .merge(categoryName, adjustment.getAdPrice(), Integer::sum);
        }

        // Step 7: Map to DTOs
        List<SalesDTO> salesDTOList = new ArrayList<>();
        monthlySales.forEach((month, categorySales) -> {
            categorySales.forEach((categoryName, totalSales) -> {
                SalesDTO dto = new SalesDTO();
                dto.setMonth(month);
                dto.setSalesItem(categoryName);
                dto.setSalesAmount(totalSales);
                dto.setAccumulatedSales(0); // 누적 매출 계산 추가 가능
                dto.setNote("정상");
                salesDTOList.add(dto);
            });
        });

        return salesDTOList;
    }
}