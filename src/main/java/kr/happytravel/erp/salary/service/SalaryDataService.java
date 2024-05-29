package kr.happytravel.erp.salary.service;

import java.util.List;

import kr.happytravel.erp.salary.model.SalaryDataModel;
import kr.happytravel.erp.salary.model.SalaryPaymentModel;

public interface SalaryDataService {
	// 급여 기본 정보
	int initSalaryData(String empId, int salary) throws Exception;

	// 월급 내역 - 일괄 처리 & 단건 처리
	int insertSalaryData(String empId, String salaryDate) throws Exception;

	// 월급 내역 - 수정
	int updateSalaryData(String empId, String salaryDate, List<SalaryDataModel> salaryDataModelList) throws Exception;
	
	// 급여 지급 내역 - 조회
	List<SalaryPaymentModel> selectAllSalaryPayment(String salaryYear) throws Exception;
}