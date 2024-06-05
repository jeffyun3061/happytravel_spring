package kr.happytravel.erp.hr.service;

import kr.happytravel.erp.hr.dao.EmpDao;
import kr.happytravel.erp.hr.model.EmpModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpServiceImpl implements EmpService {
private final EmpDao empDao;

    /** 전체사원조회 */
    @Override
    public List<EmpModel> totalEmpList() throws Exception {
        return empDao.totalEmpList();
    }

    /** 사원 조회 */
    @Override
    public EmpModel getEmpInfo(String empId) throws Exception {
        return empDao.getEmpInfo(empId);
    }


    @Override
    public List<EmpModel> searchEmpList(String searchType, String searchQuery){
        return empDao.searchEmpList(searchType, searchQuery);
    }

    @Override
    public void createEmp(EmpModel emp) {
        empDao.insertEmp(emp);
    }

    @Override
    public String generateEmpId() {
        String year = new SimpleDateFormat("yyyy").format(new Date());
        String lastEmpId = empDao.getLastEmpId();

        String newEmpId;
        if (lastEmpId == null || !lastEmpId.startsWith(year)) {
            newEmpId = year + "0001";
        } else {
            int lastSequence = Integer.parseInt(lastEmpId.substring(4));
            newEmpId = year + String.format("%04d", lastSequence+1);
        }
        return newEmpId;
    }

    @Override
    public List<EmpModel> getDeptName() throws Exception {
        return empDao.getDeptName();
    }
    @Override
    public List<EmpModel> getPosList() throws Exception {
        return empDao.getPosList();
    }
    @Override
    public List<EmpModel> getEmpStatusList() throws Exception {
        return empDao.getEmpStatusList();
    }
    @Override
    public List<EmpModel> getBankList() throws Exception {
        return empDao.getBankList();
    }
    @Override
    public List<EmpModel> updateemp() throws Exception {
        return empDao.updateemp();
    }

}
