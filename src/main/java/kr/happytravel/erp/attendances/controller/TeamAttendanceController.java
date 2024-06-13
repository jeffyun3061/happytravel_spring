package kr.happytravel.erp.attendances.controller;

import kr.happytravel.erp.attendances.model.TeamAttendanceResponseModel;
import kr.happytravel.erp.attendances.service.TeamAttendanceService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TeamAttendanceController {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private final TeamAttendanceService teamAttendanceService;

    @GetMapping("/attendance/teamAttendance")
    public ResponseEntity<List<TeamAttendanceResponseModel>> teamAttendanceList(@RequestParam String department) {
        try {
            logger.info("Received request read teamAttendanceList");
            logger.info("Received request with parameters: " + department);
            List<TeamAttendanceResponseModel> teamAttendanceList = teamAttendanceService.getTeamAttendanceList(department);
            logger.info("Detched " + teamAttendanceList + " empList.");
            return ResponseEntity.ok(teamAttendanceList);
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid argument: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("An error occurred: " + e.getMessage(), e);
            throw e;
        }
    }


}
