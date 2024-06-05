package kr.happytravel.erp.attendances.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyVacationResponseModel {
    private String startDate;
    private String endDate;
}
