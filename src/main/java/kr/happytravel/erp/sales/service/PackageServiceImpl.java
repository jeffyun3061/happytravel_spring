package kr.happytravel.erp.sales.service;

import kr.happytravel.erp.sales.dao.PackageDao;
import kr.happytravel.erp.sales.model.sales.packages.*;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PackageServiceImpl implements PackageService {
    private final Logger logger = LogManager.getLogger(this.getClass());

    private final PackageDao packageDao;

    @Override
    public List<PackageListDTO> getPackageList(Map<String, Object> paramMap) throws Exception {
        // limit와 offset를 정수형으로 변환
        paramMap.put("limit", Integer.parseInt(paramMap.get("limit").toString()));
        paramMap.put("offset", Integer.parseInt(paramMap.get("offset").toString()));

        return packageDao.getPackageList(paramMap);
    }

    @Override
    public int getPackageCnt(Map<String, Object> paramMap) throws Exception {
        return packageDao.getPackageCnt(paramMap);
    }

    @Override
    @Transactional
    public int insertPackage(Map<String, Object> paramMap) throws Exception {
        logger.info("Starting transaction for insertPackage");
        int result = packageDao.insertPackage(paramMap);
        logger.info("Insert result: " + result);
        return result;
    }

    @Override
    @Transactional
    public int updatePackage(Map<String, Object> paramMap) throws Exception {
        logger.info("Starting transaction for updatePackage");
        int result = packageDao.updatePackage(paramMap);
        logger.info("Update result: " + result);
        return result;
    }

    @Override
    public int updatePackageYN(Map<String, Object> paramMap) throws Exception {
        logger.info("Starting for updatePackage IS_USED Y/N");
        int result = packageDao.updatePackageYN(paramMap);
        logger.info("Update Y/N result: " + result);
        return result;

    }

    @Override
    public int assignPackage(Map<String, Object> paramMap) throws Exception {
        logger.info("Starting for updatePackage ASSIGN_CODE to 1000 OR 2000 OR 3000");
        int result = packageDao.assignPackage(paramMap);
        logger.info("Update assign result: " + paramMap.values());
        return result;
    }

    @Override
    @Transactional
    public PackageDTO selectPackage(Map<String, Object> paramMap) throws Exception {
        logger.info("Selecting PackagePartners from DAO");
        return packageDao.selectPackage(paramMap);
    }

    @Override
    public List<CountryDTO> getCountries(Map<String, Object> paramMap) throws Exception {
        logger.info("Selecting Countries from DAO");
        return packageDao.getCountries(paramMap);
    }

    @Override
    public List<FlightListDTO> getFlightList(Map<String, Object> paramMap) throws Exception {
        // limit와 offset를 정수형으로 변환
        paramMap.put("limit", Integer.parseInt(paramMap.get("limit").toString()));
        paramMap.put("offset", Integer.parseInt(paramMap.get("offset").toString()));

        return packageDao.getFlightList(paramMap);
    }

    @Override
    public int getFlightCnt(Map<String, Object> paramMap) throws Exception {
        return packageDao.getFlightCnt(paramMap);
    }

    @Override
    public List<HotelListDTO> getHotelList(Map<String, Object> paramMap) throws Exception {
        // limit와 offset를 정수형으로 변환
        paramMap.put("limit", Integer.parseInt(paramMap.get("limit").toString()));
        paramMap.put("offset", Integer.parseInt(paramMap.get("offset").toString()));

        return packageDao.getHotelList(paramMap);
    }

    @Override
    public int getHotelCnt(Map<String, Object> paramMap) throws Exception {
        return packageDao.getHotelCnt(paramMap);
    }

    @Override
    public List<AgencyListDTO> getAgencyList(Map<String, Object> paramMap) throws Exception {
        // limit와 offset를 정수형으로 변환
        paramMap.put("limit", Integer.parseInt(paramMap.get("limit").toString()));
        paramMap.put("offset", Integer.parseInt(paramMap.get("offset").toString()));

        return packageDao.getAgencyList(paramMap);
    }

    @Override
    public int getAgencyCnt(Map<String, Object> paramMap) throws Exception {
        return packageDao.getAgencyCnt(paramMap);
    }
}