package kr.happytravel.erp.attendances.service;

import kr.happytravel.erp.attendances.dao.TeamAttendanceDao;
import kr.happytravel.erp.attendances.model.TeamAttendanceResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamAttendanceServiceImpl implements TeamAttendanceService{
    private final TeamAttendanceDao teamAttendanceDao;

    @Override
    public List<TeamAttendanceResponseModel> getTeamAttendanceList(String department) {
        return teamAttendanceDao.getTeamAttendanceList(department);
    }
}
