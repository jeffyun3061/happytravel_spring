package kr.happytravel.erp.sales.model.sales;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HotelModel {

    private String hotelCode; // HOTEL_CODE
    private String hotelName; // HOTEL_NAME
    private String phone; // PHONE
    private String country; //COUNTRY_CODE
    private String address; // ADDRESS
    private int price; // PRICE
    private String isUsed; // IS_USED

}
