package kr.happytravel.erp.attendances.dao;

import kr.happytravel.erp.attendances.model.MyAttendanceResponseModel;
import kr.happytravel.erp.attendances.model.MyVacationResponseModel;

import java.util.List;

public interface MyAttendanceDao {
    List<MyAttendanceResponseModel> getMyAttendanceList(String empId);
    List<MyVacationResponseModel> getMyVacationList(String empId);
}
