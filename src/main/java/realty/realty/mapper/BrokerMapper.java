package realty.realty.mapper;

import org.apache.ibatis.annotations.Mapper;

import realty.realty.common.LoginSessionVO;
import realty.realty.data.BrokerInfoVO;
import realty.realty.data.BrokerLoginVO;

@Mapper
public interface BrokerMapper {
    public void insertBrokerInfo(BrokerInfoVO data);
    public boolean isExistBrokerId(String id);
    public boolean loginBroker(BrokerLoginVO login);
    public LoginSessionVO getLoginBrokerInfo(BrokerLoginVO login);
    public void updateBrokerInfo(BrokerInfoVO data);
}

