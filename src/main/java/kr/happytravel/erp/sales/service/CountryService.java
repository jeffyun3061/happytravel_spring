package kr.happytravel.erp.sales.service;

import kr.happytravel.erp.sales.dto.CountryDto;
import java.util.List;

public interface CountryService {
    List<CountryDto> findAllCountries();
}
