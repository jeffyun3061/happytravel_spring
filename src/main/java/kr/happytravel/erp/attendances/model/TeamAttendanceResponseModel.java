package kr.happytravel.erp.attendances.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamAttendanceResponseModel {
    private String empId;
    private String empName;
    private String attendanceType;
    private String startDate;
    private String endDate;
    private String deptCode;
    private String position;
}
