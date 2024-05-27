package kr.happytravel.erp.sales.service;

import kr.happytravel.erp.sales.dao.AgencyDao;
import kr.happytravel.erp.sales.dao.FlightDao;
import kr.happytravel.erp.sales.dao.HotelDao;
import kr.happytravel.erp.sales.dao.PackageDao;
import kr.happytravel.erp.sales.model.AgencyModel;
import kr.happytravel.erp.sales.model.FlightModel;
import kr.happytravel.erp.sales.model.HotelModel;
import kr.happytravel.erp.sales.model.PackageModel;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PackageServiceImpl implements PackageService {
    private final Logger logger = LogManager.getLogger(this.getClass());

    private final PackageDao packageDao;


    private final HotelDao hotelDao;


    private final FlightDao flightDao;


    private final AgencyDao agencyDao;

    @Override
    public List<PackageModel> getPackageList(Map<String, Object> paramMap) throws Exception {
        return packageDao.getPackageList(paramMap);
    }

    @Override
    public int getPackageCnt(Map<String, Object> paramMap) throws Exception {
        return packageDao.getPackageCnt(paramMap);
    }

    @Override
    public PackageModel selectPackage(Map<String, Object> paramMap) throws Exception {
        return packageDao.selectPackage(paramMap);
    }

    @Override
    public int insertPackage(PackageModel packageModel) throws Exception {
        // 호텔, 항공편, 에이전시 정보를 가져와서 총 가격을 계산
        logger.info("Fetching hotel, flight, and agency information for package insertion.");
        Map<String, Object> hotelParam = new HashMap<>();
        hotelParam.put("hotel_code", packageModel.getHotelCode());
        HotelModel hotel = hotelDao.selectHotel(hotelParam);

        Map<String, Object> flightParam = new HashMap<>();
        flightParam.put("flight_code", packageModel.getFlightCode());
        FlightModel flight = flightDao.selectFlight(flightParam);

        Map<String, Object> agencyParam = new HashMap<>();
        agencyParam.put("agency_code", packageModel.getAgencyCode());
        AgencyModel agency = agencyDao.selectAgency(agencyParam);

        if (hotel == null || flight == null || agency == null) {
            logger.error("Invalid hotel, flight, or agency code.");
            throw new IllegalArgumentException("Invalid hotel, flight, or agency code.");
        }

        // 총 가격 계산
        int totalPrice = hotel.getPrice() + flight.getPrice() + agency.getPrice();
        packageModel.setTotalPrice(totalPrice);
        logger.info("Total price calculated: {}", totalPrice);

        // 판매 가격은 총 가격의 120%
        int salePrice = (int) (totalPrice * 1.2);
        packageModel.setSalePrice(salePrice);
        logger.info("Sale price set to: {}", salePrice);

        try {
            int result = packageDao.insertPackage(packageModel);
            if (result == 0) {
                logger.warn("Sale price must be at least 20% higher than the total price.");
                throw new IllegalArgumentException("Sale price must be at least 20% higher than the total price.");
            }
            logger.info("Package inserted successfully.");
            return result;
        } catch (DataAccessException e) {
            logger.error("Data access error during package insertion.", e);
            throw e;
        }
    }

    @Override
    public int updatePackage(PackageModel packageModel) throws Exception {
        // 호텔, 항공편, 에이전시 정보를 가져와서 총 가격을 계산
        logger.info("Fetching hotel, flight, and agency information for package update.");
        Map<String, Object> hotelParam = new HashMap<>();
        hotelParam.put("hotel_code", packageModel.getHotelCode());
        HotelModel hotel = hotelDao.selectHotel(hotelParam);

        Map<String, Object> flightParam = new HashMap<>();
        flightParam.put("flight_code", packageModel.getFlightCode());
        FlightModel flight = flightDao.selectFlight(flightParam);

        Map<String, Object> agencyParam = new HashMap<>();
        agencyParam.put("agency_code", packageModel.getAgencyCode());
        AgencyModel agency = agencyDao.selectAgency(agencyParam);

        if (hotel == null || flight == null || agency == null) {
            logger.error("Invalid hotel, flight, or agency code.");
            throw new IllegalArgumentException("Invalid hotel, flight, or agency code.");
        }

        // 총 가격 계산
        int totalPrice = hotel.getPrice() + flight.getPrice() + agency.getPrice();
        packageModel.setTotalPrice(totalPrice);
        logger.info("Total price calculated: {}", totalPrice);

        // 판매 가격은 총 가격의 120%
        int salePrice = (int) (totalPrice * 1.2);
        packageModel.setSalePrice(salePrice);
        logger.info("Sale price set to: {}", salePrice);

        try {
            int result = packageDao.updatePackage(packageModel);
            if (result == 0) {
                logger.warn("Sale price must be at least 20% higher than the total price.");
                throw new IllegalArgumentException("Sale price must be at least 20% higher than the total price.");
            }
            logger.info("Package updated successfully.");
            return result;
        } catch (DataAccessException e) {
            logger.error("Data access error during package update.", e);
            throw e;
        }
    }

    @Override
    public int deletePackage(Map<String, Object> paramMap) throws Exception {
        return packageDao.deletePackage(paramMap);
    }
}
