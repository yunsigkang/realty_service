package realty.realty.data.realty.update;

import lombok.Data;
import realty.realty.data.realty.RealtyBuildingInfoVO;

@Data
public class RealtyBuildingUpdateVO {
    private Boolean update_name;
    private Boolean update_total_floor;
    private Boolean update_total_parking;
    private Boolean update_elevator;
    private Boolean update_use_type;
    private Boolean update_use_acepted_dt;
    private Boolean update_address;
    private Boolean update_longitude;
    private Boolean update_latitube;
    private RealtyBuildingInfoVO building_info;
    

}
