package kr.happytravel.erp.attendances.dao;

import kr.happytravel.erp.attendances.model.TeamAttendanceResponseModel;

import java.util.List;

public interface TeamAttendanceDao {
    List<TeamAttendanceResponseModel> getTeamAttendanceList(String department);
}
