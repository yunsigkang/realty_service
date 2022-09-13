package realty.realty.data.realty;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class RealtySearchVO {
    private Integer min_price;                  // 금액 최소 (보증금/매매가)
    private Integer max_price;                  // 금액 최대 (보증금/매매가)
    private Integer min_monthly_price;          // 월세 최소
    private Integer max_monthly_price;          // 월세 최대
    private Integer min_maintion_price;         // 관리비 최소
    private Integer max_maintion_price;         // 관리비 최대
    private Integer sale_type;                  // 매물유형 (월세:1/전세:2/매매:3)
    private Double parking_count;               // 주차 가능 대수 
    private Integer shortter_lease;             // 단기임대 가능여부
    private Integer room_type;                  // 방 타입(원룸:0/투룸:1/쓰리룸이상:2/빌라:3/아파트:4/오피스텔:5)
    private List<Integer> floor;                // 층 {2,3,4,5}
    private Double min_supply_area;             // 공급면적(제곱미터)
    private Double max_supply_area;             // 공급면적(제곱미터)
    private Integer room_count;                 // 방 개수 
    private List<Integer> dirction;             // 방향 (동서남북/1234){0,2}
    private Integer kitchen_type;               // 주방 유무
    private Integer balcony_type;               // 발코니/배란다 유무
    private Integer ktchen_structure;           // 주방 빌트인 유무
    private Date min_available_dt;              // 최소 사용승인일
    private Date max_available_dt;              // 최대 사용승인일
    private Integer induction;                  // 옵션 : 인덕션
    private Integer elec_range;                // 옵션 : 전자레인지
    private Integer ariconditioner;             // 옵션 : 에어컨
    private Integer washing_machine;            // 옵션 : 세탁기
    private Integer television;                 // 옵션 : TV
    private Integer closet;                     // 옵션 : 옷장
    private Integer bed;                        // 옵션 : 침대
    private Integer desk;                       // 옵션 : 책상
    private Integer shoe_closet;                // 옵션 : 신발장
    private Integer bidet;                      // 옵션 : 비데
    private Integer stove;                      // 옵션 : 스토브
    private Integer refrigerator;               // 옵션 : 냉장고
    private Integer elec_doorlock;              // 옵션 : 전자도어락
    private Integer elevator;                   // 옵션 : 엘리베이터
    private String address;                     // 주소검색

    private Integer page;                       // 조회 페이지 번호

    private Double lat;                         // 중심좌표 위도(ex:35.869585)
    private Double lon;                         // 중심좌표 경도(ex:128.691933)
    private Double rad;                         // 변경(radius:단위km)
}
