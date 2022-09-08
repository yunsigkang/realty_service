package realty.realty.data.realty;

import java.util.Date;

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
    private Integer[] floor;                    // 층 {2,3,4,5}
    private Double min_supply_area;             // 공급면적(제곱미터)
    private Double max_supply_area;             // 공급면적(제곱미터)
    private Integer room_count;                 // 방 개수 
    private Integer[] dirction;                 // 방향 (동서남북/1234){0,2}
    private Integer kitchen_type;               // 발코니/배란다 유무
    private Date max_available_dt;              // 최대 사용승인일
    private Date min_available_dt;              // 최소 사용승인일
    private Integer ro_induction;               // 옵션 : 인덕션
    private Integer ro_elec_range;              // 옵션 : 전자레인지
    private Integer ro_ariconditioner;          // 옵션 : 에어컨
    private Integer ro_washing_machine;         // 옵션 : 세탁기
    private Integer ro_television;              // 옵션 : TV
    private Integer ro_closet;                  // 옵션 : 옷장
    private Integer ro_bed;                     // 옵션 : 침대
    private Integer ro_desk;                    // 옵션 : 책상
    private Integer ro_shoe_closet;             // 옵션 : 신발장
    private Integer ro_bidet;                   // 옵션 : 비데
    private Integer ro_stove;                   // 옵션 : 스토브
    private Integer ro_refrigerator;            // 옵션 : 냉장고
    private Integer ro_elec_doorlock;           // 옵션 : 전자도어락
    private Integer bi_elevator;                // 옵션 : 엘리베이터
    private String bi_addresse;                 // 주소검색
}
