package kr.happytravel.erp.util;

public class CodeGenerator {

    /**
     * 마지막 코드 값을 받아 새로운 코드를 생성합니다.
     * @param lastCode 마지막 코드 값 (예: "H001", "F001")
     * @param prefix 코드 접두사 (예: "H", "F")
     * @return 새로운 코드 값
     */
    public static String generateNewCode(String lastCode, String prefix) {
        if (lastCode != null) {
            int numericPart = Integer.parseInt(lastCode.replace(prefix, ""));
            int newNumericPart = numericPart + 1;
            return prefix + String.format("%03d", newNumericPart);
        } else {
            return prefix + "001";
        }
    }
}
