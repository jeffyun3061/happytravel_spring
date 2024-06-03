package kr.happytravel.erp.sales.controller;

import kr.happytravel.erp.sales.model.sales.HotelDTO;
import kr.happytravel.erp.sales.model.sales.HotelModel;
import kr.happytravel.erp.sales.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
public class HotelController {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private final HotelService hotelService;
    // 확인
    // Create
    @PostMapping("/hotel")
    public ResponseEntity<String> insertHotel(@RequestBody HotelDTO hotel, HttpServletRequest request,
                                              HttpServletResponse response, HttpSession session) throws Exception {
        try {
            logger.info("Received request to create hotel: " + hotel);

            String lastHotelCode = hotelService.getLastHotelCode();
            String newHotelCode;
            logger.info("lastHotelCode : " + lastHotelCode);

            if (lastHotelCode != null) {
                int numericPart = Integer.parseInt(lastHotelCode.replace("H", ""));
                int newNumericPart = numericPart + 1;
                newHotelCode = "H" + String.format("%03d", newNumericPart);
                logger.info("newNumericPart : " + newHotelCode);
            } else {
                newHotelCode = "H001";
            }
            hotel.setHotelCode(newHotelCode);

            hotelService.insertHotel(hotel);

            return ResponseEntity.ok("Hotel created successfully with code: " + newHotelCode);
        } catch (Exception e) {
            logger.error("An error occurred: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating hotel");
        }
    }

    @GetMapping("/last-hotel-code")
    public ResponseEntity<String> getLastHotelCode() {
        try {
            String lastHotelCode = hotelService.getLastHotelCode();
            return new ResponseEntity<>(lastHotelCode, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Read (List)
    @GetMapping("/hotel-list")
    public ResponseEntity<List<HotelModel>> getHotelList(@RequestParam Map<String, Object> paramMap, HttpServletRequest request,
                                                         HttpServletResponse response, HttpSession session) throws Exception {
        try {
            logger.info("Received request with parameters: " + paramMap);
            String empId = Optional.ofNullable((String) paramMap.get("empId")).orElse("EMP30002"); // 기본 empId 설정
            paramMap.put("empId", empId);
            List<HotelModel> hotels = hotelService.getHotelList(paramMap);
            logger.info("Fetched " + hotels.size() + " flights.");
            return ResponseEntity.ok(hotels);
        } catch (Exception e) {
            logger.error("An error occurred: " + e.getMessage(), e);
            throw e;
        }
    }

    // Read (Single)
    @GetMapping("/hotel")
    public ResponseEntity<HotelModel> getHotel(HotelDTO hotel, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
                                               HttpServletResponse response, HttpSession session) throws Exception {
        try {
            logger.info("Received request to get hotel with parameters: " + paramMap);
            HotelModel hotelModel = hotelService.selectHotel(hotel);
            if (hotel == null) {
                logger.warn("Hotel not found with parameters: " + paramMap);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            logger.info("Fetched hotel: " + hotel);
            return ResponseEntity.ok(hotelModel);
        } catch (Exception e) {
            logger.error("An error occurred: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Update
    @PutMapping("/hotel/{hotelCode}")
    public ResponseEntity<Boolean> updateHotel(@PathVariable String hotelCode, @RequestBody Map<String, Object> paramMap, HttpServletRequest request,
                                               HttpServletResponse response, HttpSession session) throws Exception {
        try {
            logger.info("Received request to update hotel: " + paramMap);
            paramMap.put("hotelCode", hotelCode);

            // empId 확인 및 로그 출력
            String empId = (String) paramMap.get("empId");
            if (empId == null) {
                empId = "EMP30002"; // 기본 empId 설정
                paramMap.put("empId", empId);
            }
            logger.info("empId: " + empId);

            return ResponseEntity.ok(hotelService.updateHotel(paramMap) == 1);
        }  catch (Exception e) {
            logger.error("An error occurred: " + e.getMessage(), e);
            return ResponseEntity.ok(false);
        }
    }

    // Y/N UPDATE
    @PutMapping("/hotel-yn")
    public ResponseEntity<Boolean> updateHotelYN(@RequestParam Map<String, Object> paramMap, HttpServletRequest request,
                                                 HttpServletResponse response, HttpSession session) throws Exception {
        try {
            logger.info("Received request to Y/N package with parameters: " + paramMap);
            return ResponseEntity.ok(hotelService.updateHotelYN(paramMap) == 1);
        } catch (Exception e) {
            logger.error("An error occurred: " + e.getMessage(), e);
            return ResponseEntity.ok(false);
        }
    }
}
