package realty.realty.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import realty.realty.data.user.UserInfoVO;
import realty.realty.data.user.UserRealtyLikeVO;
import realty.realty.data.user.UserRealtyLookupVO;
import realty.realty.mapper.UserMapper;

@Service
public class Userservice {
    @Autowired UserMapper user_mapper;
    // 사용자 추가 service
    public ResponseEntity<Map<String, Object>> insertUserInfo(UserInfoVO data){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }
    // 사용자 아이디 중복 체크 service
    public ResponseEntity<Map<String, Object>> isDuplicatedId(String id){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }
    // 사용자 검색 service
    public ResponseEntity<Map<String, Object>> selectUserInfo(String id, String pwd){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }
    // 사용자 수정 service
    public ResponseEntity<Map<String, Object>> updateUserInfo(UserInfoVO data){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }
    // 사용자 상태 수정 service
    public ResponseEntity<Map<String, Object>> updateUserStatus(String id, Integer status){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }
    // 사용자 좋아요 service
    public ResponseEntity<Map<String, Object>> addLikeInfo(Integer seq){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }
    // 사용자 좋아요 수정 service
    public ResponseEntity<Map<String, Object>> updateLikeInfo(UserRealtyLikeVO data){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }
    // 시간 정보 추가 service
    public ResponseEntity<Map<String, Object>> insertLookupInfo(UserRealtyLookupVO data){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }
    
}
