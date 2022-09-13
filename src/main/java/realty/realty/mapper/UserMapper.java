package realty.realty.mapper;

import org.apache.ibatis.annotations.Mapper;

import realty.realty.data.user.UserInfoVO;
import realty.realty.data.user.UserRealtyLikeVO;
import realty.realty.data.user.UserRealtyLookupVO;

@Mapper
public interface UserMapper {
    //사용자
    public void insertUserInfo(UserInfoVO data);
    public Boolean isduplicatedId(String id);
    public UserInfoVO loginUser(String id, String pwd);
    public void updateUserInfo(UserInfoVO data);
    public void updateUserStatus(String id, Integer status);
    public void deleteUserInfo(Integer seq);
    //좋아요
    public void insertLikeInfo(UserRealtyLikeVO data);
    public void updateLikeInfo(UserRealtyLikeVO data);
    //시간
    public void insertLookupInfo(UserRealtyLookupVO data);
}
