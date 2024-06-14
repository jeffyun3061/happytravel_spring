package kr.happytravel.erp.sales.controller;


import kr.happytravel.erp.sales.dto.FlightDto;
import kr.happytravel.erp.sales.model.sales.FlightModel;
import kr.happytravel.erp.sales.service.FlightService;
import kr.happytravel.erp.util.CodeGenerator;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
public class FlightController {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private final FlightService flightService;

    // Create
    @PostMapping("/flight")
    public ResponseEntity<String> insertFlight(@RequestBody FlightDto flight, HttpServletRequest request,
                                               HttpServletResponse response, HttpSession session) throws Exception {
        try {
            logger.info("Received request to create hotel: " + flight);

            String lastFlightCode = flightService.getLastFlightCode();
            String newFlightCode = CodeGenerator.generateNewCode(lastFlightCode, "F");

            flight.setFlightCode(newFlightCode);

            flightService.insertFlight(flight);
            return ResponseEntity.ok("Hotel created successfully with code: " + newFlightCode);
        } catch (Exception e) {
            logger.error("An error occurred: " + e.getMessage(), e);
            return ResponseEntity.ok("Error creating flight");
        }
    }

    // Read (List)
    @GetMapping("/flight-list")
    public ResponseEntity<List<FlightModel>> getFlightList(@RequestParam Map<String, Object> paramMap, HttpServletRequest request,
                                                         HttpServletResponse response, HttpSession session) throws Exception {
        try {
            logger.info("Received request with parameters: " + paramMap);
            List<FlightModel> flights = flightService.getFlightList(paramMap);
            logger.info("Fetched " + flights.size() + " flights.");
            return ResponseEntity.ok(flights);
        } catch (Exception e) {
            logger.error("An error occurred: " + e.getMessage(), e);
            throw e;
        }
    }

    // Read (Single)
    @GetMapping("/flight")
    public ResponseEntity<FlightModel> getFlight(FlightDto flight, @RequestParam Map<String, Object> paramMap, HttpServletRequest request,
                                                 HttpServletResponse response, HttpSession session) throws Exception {
        try {
            logger.info("Received request to get flight with parameters: " + paramMap);
            FlightModel flightModel = flightService.selectFlight(flight);
            if (flight == null) {
                logger.warn("Flight not found with parameters: " + paramMap);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            logger.info("Fetched flight: " + flight);
            return ResponseEntity.ok(flightModel);
        } catch (Exception e) {
            logger.error("An error occurred: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Update
    @PutMapping("/flight/{flightCode}")
    public ResponseEntity<Boolean> updateFlight(@PathVariable String flightCode, @RequestBody Map<String, Object> paramMap, HttpServletRequest request,
                                               HttpServletResponse response, HttpSession session) throws Exception {
        try {

            logger.info("Received request to update flight: " + paramMap);

            // empId 확인 및 로그 출력
            String empId = (String) paramMap.get("empId");
            if (empId == null) {
                empId = "EMP30002";
                paramMap.put("empId", empId);
            }
            logger.info("empId: " + empId);

            // flightCode를 paramMap에 추가
            paramMap.put("flightCode", flightCode);
            logger.info("flightCode: " + flightCode);


            // 날짜 형식 변환
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMddHHmm");
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String departureTimeStr = (String) paramMap.get("departure_time");
            String arrivalTimeStr = (String) paramMap.get("arrival_time");

            try {
                paramMap.put("departure_time", outputFormat.format(inputFormat.parse(departureTimeStr)));
                paramMap.put("arrival_time", outputFormat.format(inputFormat.parse(arrivalTimeStr)));
            } catch (ParseException e) {
                logger.error("Date format is incorrect: " + e.getMessage(), e);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
            }

            boolean updateResult = flightService.updateFlight(paramMap) == 1;
            return ResponseEntity.ok(updateResult);

        }  catch (Exception e) {
            logger.error("An error occurred: " + e.getMessage(), e);
            return ResponseEntity.ok(false);
        }
    }

    // Y/N UPDATE
    @PutMapping("/flight-yn")
    public ResponseEntity<Boolean> updateFlightYN(@RequestParam Map<String, Object> paramMap, HttpServletRequest request,
                                              HttpServletResponse response, HttpSession session) throws Exception {
        try {
            logger.info("Received request to update package: " + paramMap);
            return ResponseEntity.ok(flightService.updateFlightYN(paramMap) == 1);
        }  catch (Exception e) {
            logger.error("An error occurred: " + e.getMessage(), e);
            return ResponseEntity.ok(false);
        }
    }

}
