package kr.happytravel.erp.salary.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.happytravel.erp.common.insurance.SocialInsurance;
import kr.happytravel.erp.salary.dao.SalaryDao;
import kr.happytravel.erp.salary.model.EmploymentModel;
import kr.happytravel.erp.salary.model.SalaryCalculateModel;
import kr.happytravel.erp.salary.model.SalaryDataModel;
import kr.happytravel.erp.salary.model.SalaryItemModel;
import kr.happytravel.erp.salary.model.SalaryPaymentDetailModel;
import kr.happytravel.erp.salary.model.SalaryPaymentModel;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SalaryDataServiceImpl implements SalaryDataService {
	private final SalaryDao salaryDao;

	@Override
	public int initSalaryData(String empId, int salary) throws Exception {
		// 급여 항목 조회
		List<SalaryItemModel> salaryItemModelList = salaryDao.selectAllSalaryItem();
		// 급여: 연봉 * 10000 * 0.12
		salary = (int) (salary * 10000 / 12);
		String salaryDate = "000000";
		List<SalaryDataModel> salaryDataModelList = new SocialInsurance().socialInsuranceCalculate(salaryItemModelList, empId, salaryDate, new SalaryCalculateModel(salary, 0, 0, 0, 0, 0, 0));

		if (salaryDao.selectInitSalaryData(empId)) {
			return salaryDao.updateInitSalaryData(empId, salaryDataModelList);
		} else {
			return salaryDao.insertInitSalaryData(salaryDataModelList);
		}
	}

	@Override
	public int insertSalaryData(String empId, String salaryDate) throws Exception {
		List<EmploymentModel> employmentModelList = salaryDao.selectAllEmployment(salaryDate);
		if (empId != null && !empId.isEmpty()) {
			employmentModelList.removeIf(emp -> !emp.getEmpId().equals(empId));
		}
		return salaryDao.insertSalaryData(empId, salaryDate, employmentModelList);
	}

	@Override
	public List<SalaryDataModel> selectAllSalaryData(String empId, String salaryDate) throws Exception {
		return salaryDao.selectAllSalaryData(empId, salaryDate);
	}

	@Override
	public int updateSalaryData(String empId, String salaryDate, List<SalaryDataModel> salaryDataModelList) throws Exception {
		return salaryDao.updateSalaryData(empId, salaryDate, salaryDataModelList);
	}

	@Override
	public List<SalaryPaymentModel> selectAllSalaryPayment(String salaryYear) throws Exception {
		return salaryDao.selectAllSalaryPayment(salaryYear);
	}

	@Override
	public List<SalaryPaymentDetailModel> selectAllSalaryPaymentDetail(String empId, String salaryYear) throws Exception {
		return salaryDao.selectAllSalaryPaymentDetail(empId, salaryYear);
	}

}
