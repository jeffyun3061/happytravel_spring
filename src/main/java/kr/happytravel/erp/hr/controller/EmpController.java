package kr.happytravel.erp.hr.controller;

import kr.happytravel.erp.hr.model.EmpModel;
import kr.happytravel.erp.hr.service.EmpService;
import kr.happytravel.erp.salary.service.SalaryDataService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/hr")
@RequiredArgsConstructor
public class EmpController {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private final EmpService empService;
    private final SalaryDataService salaryDataService;

    @Value("${IDPhoto.rootPath}")
    private String rootPath;
    @Value("${IDPhoto.mainPath}")
    private String mainPath;
    @Value("${IDPhoto.rootPath_mac}")
    private String rootPathMac;
    @Value("${IDPhoto.subPath}")
    private String subPath;

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
    public ResponseEntity<String> saveEmp(@RequestPart("employee") EmpModel saveEmpInfo, @RequestPart(value = "file", required = false) MultipartFile file){
        try {
            logger.info("Received request to save emp with parameters: {}", saveEmpInfo);
            logger.info("Received request to save emp with file: {}", file);

//         if (empService.checkDuplicate("ssn", saveEmpInfo.getSsn())) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Duplicate SSN");
//         }
//         if (empService.checkDuplicate("mobile", saveEmpInfo.getMobile())) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Duplicate Mobile");
//         }
//         if (empService.checkDuplicate("bank_code", saveEmpInfo.getBankCode() + "-" + saveEmpInfo.getAccountNo())) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Duplicate Bank Account");
//         }
            // 파일 처리
            if (file != null && !file.isEmpty()) { // 파일이 null이 아니고 비어 있지 않은지 확인
                String fileName = StringUtils.cleanPath(file.getOriginalFilename()); // 파일 이름을 가져와서 클린 패스를 적용
                Path uploadPath = File.separator.equals("/") ? Paths.get(rootPath, mainPath, subPath) : Paths.get(rootPathMac, subPath);// 업로드 경로를 설정 (예: "uploads/12345")

                if (!Files.exists(uploadPath)) { // 업로드 경로가 존재하지 않으면
                    Files.createDirectories(uploadPath); // 업로드 경로 디렉토리를 생성
                }
                try (InputStream inputStream = file.getInputStream()) { // 파일의 InputStream을 얻어 try-with-resources 구문을 사용하여 자동으로 닫힘 처리
                    Path filePath = uploadPath.resolve(fileName); // 파일 경로를 생성 (예: "uploads/12345/filename.png")
                    Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING); // InputStream을 파일 경로에 복사, 기존 파일이 있을 경우 덮어쓰기

                    // 파일 URL 설정
                    saveEmpInfo.setPhotoUrl(filePath.toString()); // 저장된 파일의 URL을 설정
                } catch (IOException e) { // 파일 저장 중 오류가 발생한 경우
                    throw new RuntimeException("파일 저장 중 오류 발생: " + fileName, e); // 예외를 던져 호출자가 인지할 수 있도록 처리
                }
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
    public ResponseEntity<String> updateEmp(@RequestPart("employee") EmpModel updateEmpInfo, @RequestPart(value = "file", required = false) MultipartFile file) {
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
