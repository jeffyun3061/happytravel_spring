package kr.happytravel.erp.sales.dao;

import kr.happytravel.erp.sales.dto.CountryDto;
import java.util.List;

public interface CountryDao {
    List<CountryDto> findAllCountries();
}
