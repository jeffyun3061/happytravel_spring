package kr.happytravel.erp.hr.service;


import kr.happytravel.erp.hr.model.EmpModel;

import java.util.List;

public interface EmpService {
    List<EmpModel> totalEmpList() throws Exception;

    List<EmpModel> getDeptName() throws Exception;

    List<EmpModel> getPosList() throws Exception;

    List<EmpModel> getEmpStatusList() throws Exception;

    List<EmpModel> getBankList() throws Exception;

    List<EmpModel> updateemp() throws Exception;

    EmpModel getEmpInfo(String empId) throws Exception;

    List<EmpModel> searchEmpList(String searchType,String searchQuery) throws Exception;

    void createEmp(EmpModel emp);

    String generateEmpId();
}
