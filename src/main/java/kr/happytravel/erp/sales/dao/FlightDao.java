package kr.happytravel.erp.sales.dao;

import kr.happytravel.erp.sales.dto.CountryDto;
import kr.happytravel.erp.sales.dto.FlightDto;

import java.util.List;
import java.util.Map;

public interface FlightDao {

    // 항공권 전체조회
    List<FlightDto> getFlightList(Map<String, Object> paramMap) throws Exception;

    // 항공권 전체 조회 카운트
    int getFlightCnt(Map<String, Object> paramMap) throws Exception;

    // 항공권 리스트 단건 조회
    FlightDto selectFlight(Map<String, Object> paramMap) throws Exception;

    // 항공권 단건 저장
    int insertFlight(Map<String, Object> paramMap) throws Exception;

    // 항공권 단건 수정
    int updateFlight(Map<String, Object> paramMap) throws Exception;

    // 항공권 단건 삭제
    int updateFlightYN(Map<String, Object> paramMap) throws Exception;

    // 국가별 조회
    List<CountryDto> getCountries(Map<String, Object> paramMap) throws Exception;

}
