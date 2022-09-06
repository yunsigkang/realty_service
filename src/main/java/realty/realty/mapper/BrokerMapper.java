package realty.realty.mapper;

import org.apache.ibatis.annotations.Mapper;

import realty.realty.common.LoginSessionVO;
import realty.realty.data.borker.BrokerInfoVO;
import realty.realty.data.borker.BrokerLoginVO;

@Mapper
public interface BrokerMapper {
    public void insertBrokerInfo(BrokerInfoVO data);
    public boolean isExistBrokerId(String id);
    public boolean loginBroker(BrokerLoginVO login);
    public LoginSessionVO getLoginBrokerInfo(BrokerLoginVO login);
    public void updateBrokerInfo(BrokerInfoVO data);

    public Integer selectBrockerStatus(Integer bork_seq);
    public void updateBrokerStatus(Integer bork_seq, Integer bork_status);
}

