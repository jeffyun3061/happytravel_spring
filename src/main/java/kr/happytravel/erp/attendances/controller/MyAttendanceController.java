package kr.happytravel.erp.attendances.controller;

import kr.happytravel.erp.attendances.model.MyAttendanceResponseModel;
import kr.happytravel.erp.attendances.model.MyVacationResponseModel;
import kr.happytravel.erp.attendances.service.MyAttendanceService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/attendance")
@RestController
@RequiredArgsConstructor
public class MyAttendanceController {

    private final Logger logger = LogManager.getLogger(this.getClass());
    private final MyAttendanceService myAttendanceService;


    @GetMapping("/api/attendance")
    public ResponseEntity<List<MyAttendanceResponseModel>> myAttendanceList(@RequestParam String empId) {
        try {
            logger.info("Received request read myAttendanceList");
            logger.info("Received request with parameters: " + empId);
            List<MyAttendanceResponseModel> myAttendanceList = myAttendanceService.getMyAttendanceList(empId);
            logger.info("Detched " + myAttendanceList + " empList.");
            return ResponseEntity.ok(myAttendanceList);
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid argument: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("An error occurred: " + e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping("/api/vacation")
    public ResponseEntity<List<MyVacationResponseModel>> myVacationList(@RequestParam String empId) {
        try {
            logger.info("Received request read myVacationList");
            logger.info("Received request with parameters: " + empId);
            List<MyVacationResponseModel> myVacationList = myAttendanceService.getMyVacationList(empId);
            logger.info("Detched " + myVacationList + " empList.");
            return ResponseEntity.ok(myVacationList);
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid argument: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("An error occurred: " + e.getMessage(), e);
            throw e;
        }
    }
}
