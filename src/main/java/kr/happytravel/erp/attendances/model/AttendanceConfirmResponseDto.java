package kr.happytravel.erp.attendances.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceConfirmResponseDto {
    List<AttendanceConfirmResponse> attendanceConfirmResponseList;
    int total;
}
