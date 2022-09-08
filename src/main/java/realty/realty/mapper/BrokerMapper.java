package realty.realty.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import realty.realty.common.LoginSessionVO;
import realty.realty.data.borker.BrokerInfoVO;
import realty.realty.data.borker.BrokerLoginVO;
import realty.realty.data.borker.BrokerOfficeInfoVO;

@Mapper
public interface BrokerMapper {
    public void insertBrokerInfo(BrokerInfoVO data);
    public Boolean isExistBrokerId(String id);
    public Boolean loginBroker(BrokerLoginVO login);
    public LoginSessionVO getLoginBrokerInfo(BrokerLoginVO login);
    public void updateBrokerInfo(BrokerInfoVO data);

    public Integer selectBrockerStatus(Integer bork_seq);
    public void updateBrokerStatus(Integer bork_seq, Integer bork_status);

    public Boolean isExistBrokerOffice(String name, String reg_no);
    public void insertBrokerOfficeInfo(BrokerOfficeInfoVO data);

    public Integer selectBrockerOfficePageCnt(String keyword);
    public Integer selectBrockerOfficeTotalCnt(String keyword);
    public List<BrokerOfficeInfoVO> selectBrockerOfficeList(String keyword, Integer page);
    public void updateBrokerOfficeInfo(BrokerOfficeInfoVO data);
    public void deleteBrokerOffceInfo(Integer no);
    public BrokerOfficeInfoVO selectBrokerOfficeBySeq(Integer seq);
}

