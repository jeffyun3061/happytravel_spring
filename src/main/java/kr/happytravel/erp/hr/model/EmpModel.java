package kr.happytravel.erp.hr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmpModel {
    // Getters and Setters
    @Getter
    @Setter
    private String empId;
    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    private String photoUrl;
    @Getter
    @Setter
    private String empName;
    @Getter
    @Setter
    private String deptCode;
    @Getter
    @Setter
    private String posCode;
    @Getter
    @Setter
    private String joinDate;
    @Getter
    @Setter
    private String ssn;
    @Getter
    @Setter
    private String zipCode;
    @Getter
    @Setter
    private String address;
    @Getter
    @Setter
    private String addressDetail;
    @Getter
    @Setter
    private String phone;
    @Getter
    @Setter
    private String mobile;
    @Getter
    @Setter
    private String statusCode;
    @Getter
    @Setter
    private String leaveDate;
    @Getter
    @Setter
    private String bankCode;
    @Getter
    @Setter
    private String accountNo;
    @Setter
    @Getter
    private String salary;
    @Setter
    @Getter
    private String remarks;

    @Setter
    @Getter
    private String deptName;
    @Setter
    @Getter
    private String posName;
    private String isUsed;

    @Setter
    @Getter
    private String statCode;
    @Setter
    @Getter
    private String statName;

    @Setter
    @Getter
    private String bankName;



}
