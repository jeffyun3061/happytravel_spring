package kr.happytravel.erp.hr.dao;

import kr.happytravel.erp.hr.model.EmpModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EmpDao {
    /** 사원 전체 조회 */
    List<EmpModel> totalEmpList() throws Exception;

    /** 사원 단건 조회 */
    EmpModel getEmpInfo(@Param("empId") String empId) throws Exception;

    /** 사원 검색 조회 */
    List<EmpModel> searchEmpList(@Param("searchType") String searchType, @Param("searchQuery") String searchQuery);

    /** 부서 리스트 조회 */
    List<EmpModel> getDeptName() throws Exception;

    /** 직급 리스트 조회 */
    List<EmpModel> getPosList() throws Exception;

    /** 재직상태 리스트 조회 */
    List<EmpModel> getEmpStatusList() throws Exception;

    /** 은행 리스트 조회 */
    List<EmpModel> getBankList() throws Exception;

    /** 현재 연도에 해당하는 마지막 사원번호 조회 */
    String findLastEmpIdByYear(@Param("year") String year) throws Exception;

    /** 사원 정보 중복 체크 */
    int checkDuplicate(@Param("field") String field, @Param("value") String value) throws Exception;

    /** 신규 사원 등록 */
    void saveEmp(EmpModel saveEmpInfo) throws Exception;

    /** 신규 사원 등록 */
    void updateEmp(EmpModel updateEmpInfo) throws Exception;
}
