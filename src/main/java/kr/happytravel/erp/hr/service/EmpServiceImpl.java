package kr.happytravel.erp.hr.service;

import kr.happytravel.erp.hr.dao.EmpDao;
import kr.happytravel.erp.hr.model.EmpModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmpServiceImpl implements EmpService {
private final EmpDao empDao;

    /** 전체사원조회 */
    @Override
    public List<EmpModel> totalEmpList() throws Exception {
        return empDao.totalEmpList();
    }

    /** 사원 단건 조회 */
    @Override
    public EmpModel getEmpInfo(String empId) throws Exception {
        return empDao.getEmpInfo(empId);
    }

    /** 사원 검색 조회 */
    @Override
    public List<EmpModel> searchEmpList(String searchType, String searchQuery){
        return empDao.searchEmpList(searchType, searchQuery);
    }

    /** 부서 리스트 조회 */
    @Override
    public List<EmpModel> getDeptName() throws Exception {
        return empDao.getDeptName();
    }

    /** 직급 리스트 조회 */
    @Override
    public List<EmpModel> getPosList() throws Exception {
        return empDao.getPosList();
    }

    /** 재직상태 리스트 조회 */
    @Override
    public List<EmpModel> getEmpStatusList() throws Exception {
        return empDao.getEmpStatusList();
    }

    /** 은행 리스트 조회 */
    @Override
    public List<EmpModel> getBankList() throws Exception {
        return empDao.getBankList();
    }

    /** 사원 정보 수정 */


    /** 신규 사원 등록 */

        // 마지막 사원번호 조회
    @Override
    public String generateNewEmpId() throws Exception {
        String lastEmpId =  empDao.generateNewEmpId();
        String currentYear = String.valueOf(Year.now().getValue());
        //마지막 사원번호가 null인경우 (초기상태)
        if(lastEmpId == null) {
            return currentYear+"0001";
        }

        String lastEmpYear = lastEmpId.substring(0,4);

        if(lastEmpYear.equals(currentYear)){
            //마지막 사원번호의 년도가 현재의 년도와 같다면
            int newEmpId = Integer.parseInt(lastEmpId.substring(4)) + 1;
            return currentYear + String.format("%04d",newEmpId);
        }else{
            //마지막 사원번호의 년도가 현재 년도와 다르다면
            return currentYear + "0001";
        }

    }

    @Override
    public void saveEmp(EmpModel saveEmpInfo) throws Exception {
        empDao.saveEmp(saveEmpInfo);
    }


}
