package realty.realty.service;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

import realty.realty.data.user.UserInfoVO;
import realty.realty.data.user.UserRealtyLikeVO;
import realty.realty.data.user.UserRealtyLookupVO;
import realty.realty.mapper.UserMapper;
import realty.realty.utils.AESAlgorithm;

@Service
public class Userservice {
    @Autowired UserMapper user_mapper;
    // 사용자 추가 service
    public ResponseEntity<Map<String, Object>> insertUserInfo(UserInfoVO data){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        HttpStatus stat = null;
        Boolean status = false;
        String message = "";
        System.out.println(data);
        System.out.println(data.getUi_id());
        if(data.getUi_id() == null || data.getUi_id().equals("") || data.getUi_id().length() > 16 || data.getUi_id().length() < 6){
            message = "올바른 아이디를 입력하세요(6자 이상 16자 미만)";
            stat = HttpStatus.BAD_REQUEST;
        }
        else if(user_mapper.isduplicatedId(data.getUi_id())){
            message = data.getUi_id()+"은/는 이미 사용중인 ID 입니다.";
            stat = HttpStatus.OK;    
        }
        else if(data.getUi_pwd() == null || data.getUi_pwd().equals("") || data.getUi_pwd().length() < 8){
            message = "올바른 비밀 번호를 입력하세요(8자 이상)";
            stat = HttpStatus.BAD_REQUEST;
        }
        else{
            try{
                data.setUi_pwd(AESAlgorithm.Encrypt(data.getUi_pwd()));
            }
            catch(Exception e){
                status = false;
                message = "내부 암호화 알고리즘 오류입니다.";
                return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            user_mapper.insertUserInfo(data);
            status = true;
            message = "회원가입이 완료되었습니다.";
            stat = HttpStatus.CREATED;
        }
        resultMap.put("status",status);
        resultMap.put("message", message);
        return new ResponseEntity<Map<String, Object>>(resultMap, stat);
    }
    // 사용자 아이디 중복 체크 service
    public ResponseEntity<Map<String, Object>> isDuplicatedId(String id){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

        Boolean result = user_mapper.isduplicatedId(id);
        resultMap.put("status", result);
        resultMap.put("message", result? id+" 은/는 이미 사용중입니다" : id+" 은/는 사용하실수 있습니다.");

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
        
        if(data.getUi_pwd() != null) {
            if(data.getUi_pwd().length() < 8) {
            resultMap.put("status",false);
            resultMap.put("message", "비밀번호는 8자 이상 입력해주세요.");
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        }
        try{
            data.setUi_pwd(AESAlgorithm.Encrypt(data.getUi_pwd()));
        }
        catch(Exception e){
            resultMap.put("status",false);
            resultMap.put("message", "내부 암호화 모듈 에러입니다.");
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
        user_mapper.updateUserInfo(data);
        resultMap.put("status",true);
        resultMap.put("message", "회원 정보를 변경하였습니다.");

        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }
    // 사용자 상태 수정 service
    public ResponseEntity<Map<String, Object>> updateUserStatus(String id, Integer status){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

        if(user_mapper.isduplicatedId(id)){
            if(status > 0 && status <=4){
            resultMap.put("status",true);
            resultMap.put("message","회원 상태가 수정되었습니다.");
            user_mapper.updateUserStatus(id, status);
        }
        else{
                resultMap.put("status",false);
                resultMap.put("message","잘못된 회원 상태 값입니다.(1:정상/2:정지/3:탈퇴대기/4:탈퇴/5:영구정지)");
            }
        }
        else {
            resultMap.put("status",false);
            resultMap.put("message","회원 정보가 존재하지 않습니당.");
        }
        
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }
    // 사용자 좋아요 service
    public ResponseEntity<Map<String, Object>> setUserRealtyLikeInfo(UserRealtyLikeVO data){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        if(user_mapper.isExistUserRealtyLikeInfo(data)){
            user_mapper.deleteUserRealtyLikeInfo(data);
            resultMap.put("message","매물 좋아요 취소");
        }
        else{
            user_mapper.insertUserRealtyLikeInfo(data);
            resultMap.put("message","매물 좋아요 체크");
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }
    // // 사용자 좋아요 수정 service
    // public ResponseEntity<Map<String, Object>> updateLikeInfo(UserRealtyLikeVO data){
    //     Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        
    //     return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    // }
    // 시간 정보 추가 service
    public ResponseEntity<Map<String, Object>> insertUserRealtyLookup(UserRealtyLookupVO data){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        Calendar now = Calendar.getInstance();
        Date latestDt = user_mapper.selectLatesrealtyLookupTime(data);
        Calendar latestTime = Calendar.getInstance();
        try{
            //latestDtrk null일 경우, 사용자는 해당 매물 조회가 첫번째 이기 때문에
        latestTime.setTime(latestDt);
        }
        catch(NullPointerException e){
            // 매물을 추가시켰음
            user_mapper.insertLookupInfo(data);
            resultMap.put("status", true);
            resultMap.put("message", "매물 조회 기록이 추가되었습니다.");
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        }
            Long diff = now.getTimeInMillis() - latestTime.getTimeInMillis();

        if(diff>=30000){
            user_mapper.insertLookupInfo(data);
            resultMap.put("status", true);
            resultMap.put("message", "매물 조회 기록이 추가되었습니다.");
        }
        else{
            resultMap.put("status", false);
            resultMap.put("message", "최근 조회 시간과의 차이가 30초 이내입니다.");
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }
    
}
