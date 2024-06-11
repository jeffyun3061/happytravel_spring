package kr.happytravel.erp.hr.controller;
import kr.happytravel.erp.hr.model.EmpModel;
import kr.happytravel.erp.hr.service.EmpService;

import kr.happytravel.erp.salary.service.SalaryDataService;
import kr.happytravel.erp.salary.service.SalaryDataServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.rmi.server.ExportException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/hr")
@RequiredArgsConstructor
public class EmpController {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private final EmpService empService;
    private final SalaryDataService salaryDataService;

    /** 사원 전체 조회 */
    @GetMapping("/emp-list")
    public ResponseEntity<List<EmpModel>> getEmpList(@RequestParam Map<String, Object> paramMap,
                                                                HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        try {
            logger.info("Received request read emp list");
            logger.info("Received request with parameters: " + paramMap);
            List<EmpModel> totalEmpList = empService.totalEmpList();
            logger.info("Detched " + totalEmpList + " totalEmpList.");
            return ResponseEntity.ok(totalEmpList);
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid argument: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("An error occurred: " + e.getMessage(), e);
            throw e;
        }
    }

    /** 사원 검색 조회 */
    @GetMapping("emp-list/search")
    public ResponseEntity<List<EmpModel>> searchEmpList(@RequestParam String searchType, @RequestParam String searchQuery) {
        try {
            logger.info("Received search request with type: " + searchType + " and query: " + searchQuery);
            List<EmpModel> searchResults = empService.searchEmpList(searchType, searchQuery);
            return ResponseEntity.ok(searchResults);
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid argument: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("An error occurred during search: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /** 사원 단건 조회 */
    @GetMapping("/emp-info")
    public ResponseEntity<EmpModel> getEmpInfo(@RequestParam String empId, HttpServletRequest request,
                                               HttpServletResponse response, HttpSession session) throws Exception {
        try {
            logger.info("Received request to get emp with parameters: " + empId);
            EmpModel emp = empService.getEmpInfo(empId);
            if(emp == null) {
                logger.warn("Emp not found with parameters: " + empId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            logger.info("Fetched emp: " + emp);
            return ResponseEntity.ok(emp);
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid argument: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("An error occurred: " + e.getMessage(), e);
            throw e;
        }

    }

    /** 부서 목록 조회 */
    @GetMapping("/dept-list")
    public ResponseEntity<List<EmpModel>> getDeptName(@RequestParam Map<String, Object> paramMap,
                                                     HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        List<EmpModel> getDeptName = empService.getDeptName();
        getDeptName.forEach(dept -> logger.info("Department: " + dept.getDeptName()));
        return ResponseEntity.ok(getDeptName);

    }

    /** 직급 목록 조회 */
    @GetMapping("/pos-list")
    public ResponseEntity<List<EmpModel>> getPosList(@RequestParam Map<String, Object> paramMap,
                                                     HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        List<EmpModel> getPosList = empService.getPosList();
        getPosList.forEach(pos -> logger.info("Position: " + pos.getPosName()));
        return ResponseEntity.ok(getPosList);
    }

    /** 재직 상태 목록 조회 */
    @GetMapping("/empstat-list")
    public ResponseEntity<List<EmpModel>> getEmpStatusList(@RequestParam Map<String, Object> paramMap,
                                                     HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        List<EmpModel> getEmpStatusList = empService.getEmpStatusList();
        getEmpStatusList.forEach(stat -> logger.info("Position: " + stat.getStatName()));
        return ResponseEntity.ok(getEmpStatusList);
    }

    /** 은행 목록 조회 */
    @GetMapping("/bank-list")
    public ResponseEntity<List<EmpModel>> getBankList(@RequestParam Map<String, Object> paramMap,
                                                     HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        List<EmpModel> getBankList = empService.getBankList();
        getBankList.forEach(bnk -> logger.info("bank: " + bnk.getBankName()));
        return ResponseEntity.ok(getBankList);
    }

    /** 신규 사원 등록 */

    // 새로운 사원번호 생성
    @GetMapping("/emp/generate-id")
    public ResponseEntity<String> getNewEmpId() throws Exception {
        try{
            String empId = empService.generateNewEmpId();
            return ResponseEntity.ok(empId);
        }catch (Exception e){
            logger.error("An error occurred: " + e.getMessage(), e);
            throw e;
        }
    }

    // 사원 정보 중복 체크
    @GetMapping("/emp/check-duplicate")
    public ResponseEntity<Boolean> checkDuplicate(@RequestParam String field, @RequestParam String value) {
        try {
            logger.info("Checking for duplicate field: " + field + ", value: " + value);
            boolean isDuplicate = empService.checkDuplicate(field, value);
            return ResponseEntity.ok(isDuplicate);
        } catch (Exception e) {
            logger.error("An error occurred while checking duplicate: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }

    /** 신규 사원 등록 */
    @PostMapping("/emp/save")
    public ResponseEntity<String> saveEmp(@RequestBody EmpModel saveEmpInfo) {
        try {
            logger.info("Received request to save emp with parameters: " + saveEmpInfo);
            if (empService.checkDuplicate("ssn", saveEmpInfo.getSsn())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Duplicate SSN");
            }
            if (empService.checkDuplicate("mobile", saveEmpInfo.getMobile())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Duplicate Mobile");
            }
            if (empService.checkDuplicate("bank_code", saveEmpInfo.getBankCode() + "-" + saveEmpInfo.getAccountNo())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Duplicate Bank Account");
            }
            empService.saveEmp(saveEmpInfo);
            salaryDataService.initSalaryData(saveEmpInfo.getEmpId(), Integer.parseInt(saveEmpInfo.getSalary()));
            return ResponseEntity.ok("Employee saved successfully");
        } catch (Exception e) {
            logger.error("An error occurred: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    /** 비밀번호 수정 유효성 검사 */
    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\d\\s]).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(password).matches();
    }

    /** 사원 정보 수정 */
    @PutMapping("/emp/update")
    public ResponseEntity<String> updateEmp(@RequestBody EmpModel updateEmpInfo) {
        try {
            logger.info("Received request to update emp with parameters: " + updateEmpInfo);

            if (!isValidPassword(updateEmpInfo.getPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Password");
            }

            if (empService.checkDuplicate("ssn", updateEmpInfo.getSsn())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Duplicate SSN");
            }
            if (empService.checkDuplicate("mobile", updateEmpInfo.getMobile())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Duplicate Mobile");
            }
            if (empService.checkDuplicate("bank_code", updateEmpInfo.getBankCode() + "-" + updateEmpInfo.getAccountNo())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Duplicate Bank Account");
            }
            empService.updateEmp(updateEmpInfo);
            salaryDataService.initSalaryData(updateEmpInfo.getEmpId(), Integer.parseInt(updateEmpInfo.getSalary()));
            return ResponseEntity.ok("Employee updated successfully");
        } catch (Exception e) {
            logger.error("An error occurred: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }


}
