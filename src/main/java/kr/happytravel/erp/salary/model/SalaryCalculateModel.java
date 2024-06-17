package kr.happytravel.erp.salary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryCalculateModel {
	private int baseSalary; // 기본급
	private int mealAllowance; // 식대
	private int overtimeAllowance; // 연장근로수당
	private int nightWorkAllowance; // 야간근로수당
	private int holidayWorkAllowance; // 휴일근로수당
	private int familyAllowance; // 가족수당
	private int positionAllowance; // 직책수당
}
