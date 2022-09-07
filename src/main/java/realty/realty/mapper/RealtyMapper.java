package realty.realty.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import realty.realty.data.realty.RealtyBasicInfoVO;
import realty.realty.data.realty.RealtyBuildingInfoVO;
import realty.realty.data.realty.RealtyMaintainInfoVO;
import realty.realty.data.realty.RealtyOptionInfoVO;
import realty.realty.data.realty.RealtyPostinfoVO;
import realty.realty.data.realty.update.RealtyBuildingUpdateVO;

@Mapper
public interface RealtyMapper {
    //건물
    public void insertrealtyBuildingInfo(RealtyBuildingInfoVO data);
    public boolean isExistBuildingInfo(String address);
    public Integer selectBuildingTotalPage(String keyword, Integer page);
    public Integer selectBuildingTotalCount(String keyword, Integer page);
    public List<RealtyBuildingInfoVO> selectBuildingList(String keyword, Integer page);
    public void updateBulidingInfo(RealtyBuildingUpdateVO data);
    public boolean isExistBuildingBySeq(Integer building_seq);
    public void deleteBuildingInfo(Integer building_seq);
    //옵션
    public void insertRealtyOptionInfo(RealtyOptionInfoVO data);
    public void insertRealtyBasicInfo(RealtyBasicInfoVO data);
    public void insertRealtyPostInfo(RealtyPostinfoVO data);
    //게시글
    public void updateRealtyPostBasicInfo(RealtyBasicInfoVO data);
    public void updateRealtyPostOptioncInfo(RealtyOptionInfoVO data);
    public void updateRealtyPostInfo(RealtyPostinfoVO data);
    //관리항목
    public boolean isExistBuildingItem(String name);
    public void insertMaintainItem(String name);
    public Integer selectMaintainItemCount();
    public List<RealtyMaintainInfoVO> selectMaintainItemList();
    public void deleteMaintainItemList(String name);
}
