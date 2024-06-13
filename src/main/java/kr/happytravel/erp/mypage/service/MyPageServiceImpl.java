package kr.happytravel.erp.mypage.service;

import kr.happytravel.erp.mypage.dao.MyPageDao;
import kr.happytravel.erp.mypage.model.AttendanceManagementModel;
import kr.happytravel.erp.mypage.model.AttendanceModel;
import kr.happytravel.erp.mypage.model.EmployModel;
import kr.happytravel.erp.mypage.model.NoticeModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService{


    private final MyPageDao mypageDao;


    @Override
    public EmployModel selectEmployInfo(Map<String, Object> paramMap) throws Exception {
        return mypageDao.selectEmployInfo(paramMap);
    }

    @Override
    public List<NoticeModel> selectNotices(Map<String, Object> paramMap) throws Exception {
        return mypageDao.selectNotices(paramMap);
    }


    @Override
    public AttendanceModel selectAttendance(Map<String, Object> paramMap) throws Exception {
        return mypageDao.selectAttendance(paramMap);
    }

    @Override
    public List<AttendanceManagementModel> selectAttendanceManagement(Map<String, Object> paramMap) throws Exception {
        return mypageDao.selectAttendanceManagement(paramMap);
    }

}
