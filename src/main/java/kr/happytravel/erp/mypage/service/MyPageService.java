package kr.happytravel.erp.mypage.service;

import kr.happytravel.erp.mypage.model.AttendanceManagementModel;
import kr.happytravel.erp.mypage.model.AttendanceModel;
import kr.happytravel.erp.mypage.model.EmployModel;
import kr.happytravel.erp.mypage.model.NoticeModel;

import java.util.List;
import java.util.Map;

public interface MyPageService {

    // 마이페이지 사용자 조회
    EmployModel selectEmployInfo(Map<String, Object> paramMap) throws Exception;

    // 부서별 공지사항 조회
    List<NoticeModel> selectNotices(Map<String, Object> paramMap) throws Exception;

    // 출퇴근 조회
    AttendanceModel selectAttendance(Map<String, Object> paramMap) throws Exception;

    // 근태 조회
    List<AttendanceManagementModel> selectAttendanceManagement(Map<String, Object> paramMap) throws Exception;


}
