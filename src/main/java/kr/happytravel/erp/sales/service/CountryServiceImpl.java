package kr.happytravel.erp.sales.service;

import kr.happytravel.erp.sales.dao.CountryDao;
import kr.happytravel.erp.sales.dto.CountryDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private final CountryDao countryDao;

    public CountryServiceImpl(CountryDao countryDao) {
        this.countryDao = countryDao;
    }

    @Override
    public List<CountryDto> findAllCountries() {
        try {
            return countryDao.findAllCountries();
        } catch (Exception e) {
            logger.error("Error retrieving countries", e);
            return Collections.emptyList();
        }
    }
}

