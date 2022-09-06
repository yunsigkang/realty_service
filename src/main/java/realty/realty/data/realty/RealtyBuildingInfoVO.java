package realty.realty.data.realty;

import java.util.Date;

import lombok.Data;

@Data
public class RealtyBuildingInfoVO {
    private Integer bi_seq;
    private String bi_name;
    private Integer bi_total_floor;
    private Integer bi_tatal_parking;
    private Integer bi_elevator;
    private String bi_use_type;
    private Date bi_use_accepted_dt;
    private String bi_address;
    private double bi_longitude;
    private double bi_latitube;
}
