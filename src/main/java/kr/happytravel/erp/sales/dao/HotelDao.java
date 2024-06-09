package kr.happytravel.erp.sales.dao;

import kr.happytravel.erp.sales.model.sales.HotelDTO;
import kr.happytravel.erp.sales.model.sales.HotelModel;
import java.util.List;
import java.util.Map;


public interface HotelDao {

    // 호텔 전체 조회
    List<HotelModel> getHotelList(Map<String, Object> paramMap) throws Exception;

    // 호텔 전체 조회 카운트
    int getHotelCnt(Map<String, Object> paramMap) throws Exception;

    String getLastHotelCode() throws Exception;

    // 호텔 단건 조회
    HotelModel selectHotel(HotelDTO hotel) throws Exception;

    // 호텔 단건 저장
    int insertHotel(HotelDTO hotel) throws Exception;

    // 호텔 단건 수정
    int updateHotel(Map<String, Object> paramMap) throws Exception;

    // 호텔 단건 삭제
    int updateHotelYN(Map<String, Object> paramMap) throws Exception;

    void setHotelCode(String newHotelCode);
}
