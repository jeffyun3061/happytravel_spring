package kr.happytravel.erp.sales.service;

import kr.happytravel.erp.sales.dao.HotelDao;

import kr.happytravel.erp.sales.model.sales.HotelDTO;
import kr.happytravel.erp.sales.model.sales.HotelModel;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {
    private final Logger logger = LogManager.getLogger(this.getClass());

    private final HotelDao hotelDao;

    @Override
    public List<HotelModel> getHotelList(Map<String, Object> paramMap) throws Exception {
        return hotelDao.getHotelList(paramMap);
    }

    @Override
    public int getHotelCnt(Map<String, Object> paramMap) throws Exception {
        return hotelDao.getHotelCnt(paramMap);
    }

    @Override
    public String getLastHotelCode() throws Exception {
        return hotelDao.getLastHotelCode();
    }

    @Override
    public HotelModel selectHotel(HotelDTO hotel) throws Exception {
        return hotelDao.selectHotel(hotel);
    }

    @Override
    public int insertHotel(HotelDTO hotel) throws Exception {
        logger.info("Starting for insert Hotel");
        int result = hotelDao.insertHotel(hotel);
        logger.info("Insert result: " + result);
        return result;
    }

    @Override
    public int updateHotel(Map<String, Object> paramMap) throws Exception {
        logger.info("Starting for update Hotel");
        logger.info("paramMap: {}", paramMap);

        if (!paramMap.containsKey("hotelCode") || !paramMap.containsKey("empId")) {
            logger.warn("Required parameters are missing: hotelCode or empId");
            return 0;
        }

        int result = hotelDao.updateHotel(paramMap);
        logger.info("Update result: {}", result);

        if (result == 0) {
            logger.warn("No rows were updated. Please check the conditions and parameters.");
        }

        return result;
    }


    @Override
    public int updateHotelYN(Map<String, Object> paramMap) throws Exception {
        logger.info("Starting for updateHotel IS_USED Y/N");
        int result = hotelDao.updateHotelYN(paramMap);
        logger.info("Update Y/N result: " + result);
        return result;
    }
}
