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
	public int updateSalaryData(String empId, String salaryDate, List<SalaryDataModel> updateSalaryDataModelList) throws Exception {
		// 급여 항목 조회
		List<SalaryItemModel> salaryItemModelList = salaryDao.selectAllSalaryItem();
		// 급여 항목 데이터 추출
		SalaryCalculateModel salaryCalculateModel = new SalaryCalculateModel();
		for (SalaryDataModel salaryDataModel : updateSalaryDataModelList) {
			switch (salaryDataModel.getSalaryItemCode()) {
			case "1100":
				// 기본급
				salaryCalculateModel.setBaseSalary(salaryDataModel.getAmount());
				break;
			case "1200":
				// 식대
				salaryCalculateModel.setMealAllowance(salaryDataModel.getAmount());
				break;
			case "1300":
				// 연장근로수당
				salaryCalculateModel.setOvertimeAllowance(salaryDataModel.getAmount());
				break;
			case "1400":
				// 야간근로수당
				salaryCalculateModel.setNightWorkAllowance(salaryDataModel.getAmount());
				break;
			case "1500":
				// 휴일근로수당
				salaryCalculateModel.setHolidayWorkAllowance(salaryDataModel.getAmount());
				break;
			case "1600":
				// 가족수당
				salaryCalculateModel.setFamilyAllowance(salaryDataModel.getAmount());
				break;
			case "1700":
				// 직책수당
				salaryCalculateModel.setPositionAllowance(salaryDataModel.getAmount());
			default:
				break;
			}
		}
		// 급여 계산
		List<SalaryDataModel> salaryDataModelList = new SocialInsurance().socialInsuranceCalculate(salaryItemModelList, empId, salaryDate, salaryCalculateModel);

		return salaryDao.updateSalaryData(empId, salaryDataModelList);
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
