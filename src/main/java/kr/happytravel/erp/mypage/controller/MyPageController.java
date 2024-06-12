package kr.happytravel.erp.mypage.controller;

import kr.happytravel.erp.mypage.model.AttendanceManagementModel;
import kr.happytravel.erp.mypage.model.AttendanceModel;
import kr.happytravel.erp.mypage.model.EmployModel;
import kr.happytravel.erp.mypage.model.NoticeModel;
import kr.happytravel.erp.mypage.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private final MyPageService mypageService;


    // 마이페이지 유저 정보 조회
    @GetMapping("/userInfo")
    public ResponseEntity<EmployModel> getUserInfo(@RequestParam Map<String, Object> paramMap, HttpServletRequest request,
                                                   HttpServletResponse response, HttpSession session) throws Exception {
        try {
            logger.info("Received request to get userInfo with parameters: " + paramMap);
            EmployModel userInfo = mypageService.selectEmployInfo(paramMap);
            if (userInfo == null) {
                logger.warn("UserInfo not found with parameters: " + paramMap);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            logger.info("Fetched userInfo: " + userInfo);
            return ResponseEntity.ok(userInfo);
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid argument: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("An error occurred: " + e.getMessage(), e);
            throw e;
        }
    }


    // 부서 공지사항 조회
    @GetMapping("/notices")
    public ResponseEntity<List<NoticeModel>> getNotices(@RequestParam Map<String, Object> paramMap) throws Exception {
        try {
            logger.info("Received request to get notices with parameters: " + paramMap);
            List<NoticeModel> notices = mypageService.selectNotices(paramMap);
            if (notices == null || notices.isEmpty()) {
                logger.warn("No notices found with parameters: " + paramMap);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            logger.info("Fetched notices: " + notices);
            return ResponseEntity.ok(notices);
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid argument: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("An error occurred: " + e.getMessage(), e);
            throw e;
        }
    }


    // 출퇴근 조회
    @GetMapping("/attendance")
    public ResponseEntity<AttendanceModel> getAttendance(@RequestParam Map<String, Object> paramMap, HttpServletRequest request,
                                                         HttpServletResponse response, HttpSession session) throws Exception {
        try {
            logger.info("Received request to get attendance with parameters: " + paramMap);
            AttendanceModel attendance = mypageService.selectAttendance(paramMap);
            if (attendance == null) {
                logger.warn("Attendance not found with parameters: " + paramMap);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            logger.info("Fetched attendance: " + attendance);
            return ResponseEntity.ok(attendance);
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid argument: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("An error occurred: " + e.getMessage(), e);
            throw e;
        }
    }

    // 근태 조회
    @GetMapping("/attendanceManagement")
    public ResponseEntity<List<AttendanceManagementModel>> getAttendanceManagement(@RequestParam Map<String, Object> paramMap, HttpServletRequest request,
                                                                                   HttpServletResponse response, HttpSession session) throws Exception {
        try {
            logger.info("Received request to get attendanceManagement with parameters: " + paramMap);
            List<AttendanceManagementModel> attendanceManagementList = mypageService.selectAttendanceManagement(paramMap);
            if (attendanceManagementList == null || attendanceManagementList.isEmpty()) {
                logger.warn("AttendanceManagement not found with parameters: " + paramMap);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            logger.info("Fetched attendanceManagement: " + attendanceManagementList);
            return ResponseEntity.ok(attendanceManagementList);
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid argument: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("An error occurred: " + e.getMessage(), e);
            throw e;
        }
    }
}
