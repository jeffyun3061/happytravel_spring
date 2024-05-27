package kr.happytravel.erp.sales.controller;

import kr.happytravel.erp.sales.model.PackageModel;
import kr.happytravel.erp.sales.service.PackageService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
public class PackageController {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private final PackageService packageService;

    // Create
    @PostMapping("/package")
    public ResponseEntity<String> createPackage(@RequestBody PackageModel packageModel, HttpServletRequest request,
                                                HttpServletResponse response, HttpSession session) throws Exception {
        try {
            logger.info("Received request to create package: " + packageModel);
            int result = packageService.insertPackage(packageModel);
            logger.info("Created package, result: " + result);
            return ResponseEntity.ok("Package created successfully");
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid argument: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("An error occurred: " + e.getMessage(), e);
            throw e;
        }
    }

    // Read (List)
    @GetMapping("/package-list")
    public ResponseEntity<List<PackageModel>> getPackageList(@RequestParam Map<String, Object> paramMap, HttpServletRequest request,
                                                             HttpServletResponse response, HttpSession session) throws Exception {
        try {
            logger.info("Received request with parameters: " + paramMap);
            List<PackageModel> packages = packageService.getPackageList(paramMap);
            logger.info("Fetched " + packages.size() + " packages.");
            return ResponseEntity.ok(packages);
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid argument: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("An error occurred: " + e.getMessage(), e);
            throw e;
        }
    }

    // Read (Single)
    @GetMapping("/package")
    public ResponseEntity<PackageModel> getPackage(@RequestParam Map<String, Object> paramMap, HttpServletRequest request,
                                                   HttpServletResponse response, HttpSession session) throws Exception {
        try {
            logger.info("Received request to get package with parameters: " + paramMap);
            PackageModel packageModel = packageService.selectPackage(paramMap);
            if (packageModel == null) {
                logger.warn("Package not found with parameters: " + paramMap);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            logger.info("Fetched package: " + packageModel);
            return ResponseEntity.ok(packageModel);
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid argument: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("An error occurred: " + e.getMessage(), e);
            throw e;
        }
    }

    // Update
    @PutMapping("/package")
    public ResponseEntity<String> updatePackage(@RequestBody PackageModel packageModel, HttpServletRequest request,
                                                HttpServletResponse response, HttpSession session) throws Exception {
        try {
            logger.info("Received request to update package: " + packageModel);
            int result = packageService.updatePackage(packageModel);
            if (result == 0) {
                logger.warn("No package updated: " + packageModel);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Package not found for update");
            }
            logger.info("Updated package, result: " + result);
            return ResponseEntity.ok("Package updated successfully");
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid argument: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("An error occurred: " + e.getMessage(), e);
            throw e;
        }
    }

    // Delete
    @DeleteMapping("/package")
    public ResponseEntity<String> deletePackage(@RequestParam Map<String, Object> paramMap, HttpServletRequest request,
                                                HttpServletResponse response, HttpSession session) throws Exception {
        try {
            logger.info("Received request to delete package with parameters: " + paramMap);
            int result = packageService.deletePackage(paramMap);
            if (result == 0) {
                logger.warn("No package deleted with parameters: " + paramMap);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Package not found for deletion");
            }
            logger.info("Deleted package, result: " + result);
            return ResponseEntity.ok("Package deleted successfully");
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid argument: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("An error occurred: " + e.getMessage(), e);
            throw e;
        }
    }
}
