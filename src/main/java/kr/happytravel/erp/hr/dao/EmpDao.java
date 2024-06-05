package kr.happytravel.erp.hr.dao;

import kr.happytravel.erp.hr.model.EmpModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmpDao {

    List<EmpModel> totalEmpList() throws Exception;

    List<EmpModel> getDeptName() throws Exception;

    List<EmpModel> getPosList() throws Exception;

    List<EmpModel> getEmpStatusList() throws Exception;

    List<EmpModel> getBankList() throws Exception;

    List<EmpModel> updateemp() throws Exception;

    EmpModel getEmpInfo(@Param("empId") String empId) throws Exception;

    List<EmpModel> searchEmpList(@Param("searchType") String searchType, @Param("searchQuery") String searchQuery);

    void insertEmp(EmpModel emp);

    String getLastEmpId();
}
