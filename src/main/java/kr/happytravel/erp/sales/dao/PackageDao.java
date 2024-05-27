package kr.happytravel.erp.sales.dao;

import kr.happytravel.erp.sales.model.PackageModel;
import java.util.List;
import java.util.Map;

public interface PackageDao {
    // 전체 조회
    List<PackageModel> getPackageList(Map<String, Object> paramMap) throws Exception;

    // 전체 조회 카운트
    int getPackageCnt(Map<String, Object> paramMap) throws Exception;

    // 단건 조회
    PackageModel selectPackage(Map<String, Object> paramMap) throws Exception;

    // 단건 등록
    int insertPackage(PackageModel packageModel) throws Exception;

    // 단건 수정
    int updatePackage(PackageModel packageModel) throws Exception;

    // 단건 삭제
    int deletePackage(Map<String, Object> paramMap) throws Exception;
}
