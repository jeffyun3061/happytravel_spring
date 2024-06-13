package kr.happytravel.erp.attendances.service;

import kr.happytravel.erp.attendances.model.TeamAttendanceResponseModel;

import java.util.List;

public interface TeamAttendanceService {
    List<TeamAttendanceResponseModel> getTeamAttendanceList(String department);
}
