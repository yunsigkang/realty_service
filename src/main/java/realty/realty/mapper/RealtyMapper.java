package realty.realty.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import realty.realty.data.realty.RealtyBasicInfoVO;
import realty.realty.data.realty.RealtyBuildingInfoVO;
import realty.realty.data.realty.RealtyOptionInfoVO;
import realty.realty.data.realty.RealtyPostinfoVO;

@Mapper
public interface RealtyMapper {
    public void insertrealtyBuildingInfo(RealtyBuildingInfoVO data);
    public boolean isExistBuildingInfo(String address);
    public Integer selectBuildingTotalPage(String keyword, Integer page);
    public Integer selectBuildingTotalCount(String keyword, Integer page);
    public List<RealtyBuildingInfoVO> selectBuildingList(String keyword, Integer page);

    public void insertRealtyOptionInfo(RealtyOptionInfoVO data);
    public void insertRealtyBasicInfo(RealtyBasicInfoVO data);
    public void insertRealtyPostInfo(RealtyPostinfoVO data);
}
