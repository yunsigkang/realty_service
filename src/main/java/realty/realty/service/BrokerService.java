package realty.realty.service;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import realty.realty.common.LoginSessionVO;
import realty.realty.data.borker.BrokerInfoVO;
import realty.realty.data.borker.BrokerLoginVO;
import realty.realty.mapper.BrokerMapper;
import realty.realty.utils.AESAlgorithm;

@Service
public class BrokerService {
    @Autowired BrokerMapper broker_mapper;
    //중개사 등록 service
    public ResponseEntity<Map<String, Object>> insertbrokerInfoAdd(BrokerInfoVO data) throws Exception{
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        HttpStatus stat = null;
        // System.out.println(data);
        if(broker_mapper.isExistBrokerId(data.getBork_id())){
            resultMap.put("message",data.getBork_id()+"이미 가입된 아이디입니다.");
            stat = HttpStatus.BAD_REQUEST;
        }
        else if(data.getBork_pwd() == null || data.getBork_pwd().length() < 6){
            resultMap.put("message","비밀번호는 6자 이상 입력하세요");
            stat = HttpStatus.BAD_REQUEST;
        }
        else{
            try{
            data.setBork_pwd(AESAlgorithm.Encrypt(data.getBork_pwd()));
        }
        catch(Exception e) {
            resultMap.put("message", "비밀번호 입력을 확인해주세요");
            stat = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<Map<String, Object>>(resultMap, stat);
        }
        broker_mapper.insertBrokerInfo(data);
        resultMap.put("message","중개사 회원 정보 추가가 완료되었습니다.");
            stat = HttpStatus.CREATED;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, stat);
    }
    //중개사 중복 아이디 체크 service
    public ResponseEntity<Map<String, Object>> isExistBrokerId(String id){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        if(broker_mapper.isExistBrokerId(id)) {
            resultMap.put("status", false);
            resultMap.put("message", id+"은/는 이미 등록된 중개사 아이디입니다.");
            return new ResponseEntity<Map<String, Object>>(resultMap,HttpStatus.ACCEPTED);
        }
        else{
            resultMap.put("status", true);
            resultMap.put("message", "사용가능한 아이디입니다.");
            return new ResponseEntity<Map<String, Object>>(resultMap,HttpStatus.ACCEPTED);
        }
    }
    //중개사 로그인 및 로그인 유호검사 service
    public ResponseEntity<Map<String, Object>> loginBroker(BrokerLoginVO login, HttpSession session) throws Exception{
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        HttpStatus stat = null;
        if(login.getId()==null || login.getId().length() <= 0){
            resultMap.put("status",false);
            resultMap.put("message","아이디를 입력해주세요");
            stat = HttpStatus.BAD_REQUEST;
        }
        else if(login.getPwd()==null || login.getPwd().length() < 6){
            resultMap.put("status",false);
            resultMap.put("message","비밀번호를 입력해주세요");
            stat = HttpStatus.BAD_REQUEST;
        }
        else {
            try{
                login.setPwd(AESAlgorithm.Encrypt(login.getPwd()));
            }
            catch(Exception e){
                resultMap.put("status",false);
                resultMap.put("message","AES 암호화에 실패했습니다.");
                stat = HttpStatus.BAD_REQUEST;
                return new ResponseEntity<Map<String, Object>>(resultMap, stat);
            }
            if(broker_mapper.loginBroker(login)){
                resultMap.put("status", true);
                resultMap.put("message", "로그인 성공");
                stat = HttpStatus.ACCEPTED;
                LoginSessionVO loginUser = broker_mapper.getLoginBrokerInfo(login);
                resultMap.put("sessionInfo", loginUser);
                session.setAttribute("sessionInfo", loginUser);
            }
            else if(broker_mapper.loginBroker(login)){
                resultMap.put("status", false);
                resultMap.put("message", "아이디 혹은 비밀번호를 확인해주세요.");
                stat = HttpStatus.FORBIDDEN;
            }
        }
        return new ResponseEntity<Map<String, Object>>(resultMap,stat);
    }
    //중계사 계정 수정 service
    public ResponseEntity<Map<String, Object>> updateBrokerInfo(BrokerInfoVO data){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        HttpStatus stat = null;
        if(!broker_mapper.isExistBrokerId(data.getBork_id())){
            resultMap.put("status",false);
            resultMap.put("message","인증 실패");
            stat = HttpStatus.BAD_REQUEST;
        }
        else if(data.getBork_pwd()==null || data.getBork_pwd().length() < 6){
            resultMap.put("status",false);
            resultMap.put("message","비밀번호를 입력해주세요");
            stat = HttpStatus.BAD_REQUEST;
        }
        else{
            try{
                data.setBork_pwd(AESAlgorithm.Encrypt(data.getBork_pwd()));
            }
            catch(Exception e){
                resultMap.put("status",false);
                resultMap.put("message","AES 암호화에 실패했습니다.");
                stat = HttpStatus.INTERNAL_SERVER_ERROR;
                return new ResponseEntity<Map<String, Object>>(resultMap,stat);
            }
            broker_mapper.updateBrokerInfo(data);
            resultMap.put("status",true);
            resultMap.put("message", "중개사 정보 변경 완료");
            stat = HttpStatus.ACCEPTED;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap,stat);
    }
    //중계인 계정 상태 수정 service
    public ResponseEntity<Map<String, Object>> updateBrokerstatus(String value, Integer status, Integer user_no, HttpSession session){
    Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
    HttpStatus stat = null;
    String[] arr_msg = {
        "탈퇴 철회가 완료되었습니다.",
        "탈퇴 신청가 완료합니다.",
        "중계인 정지 처리가 완료되었습니다.",
        "중계인 즉시 탈퇴 처리가 안료되었습니다."
    };
    if(value.equals("broker")){
        // 1,3
        if(status == 1 | status == 3){
            resultMap.put("status",true);
            stat = HttpStatus.ACCEPTED;
            Integer current = broker_mapper.selectBrockerStatus(user_no);
            if(broker_mapper.selectBrockerStatus(user_no) == status){
                resultMap.put("message", "동일한 상태로 변경 불가합니다.(current : "+current+", incoming : "+status+")");
            }
            else{
                broker_mapper.updateBrokerStatus(user_no, status);
                resultMap.put("massage",arr_msg[status-1]);
            }
        }
        else{
            resultMap.put("status",false);
            resultMap.put("message","중계사 계정은 탈퇴 및 탈퇴 철회만 가능합니다.");
            stat = HttpStatus.BAD_REQUEST;
        }
    }
    else if(value.equals("admin")){
        // 1,2,3,4
        if(status <= 4 && status > 0){
            resultMap.put("status",true);
            Integer current = broker_mapper.selectBrockerStatus(user_no);
            if(broker_mapper.selectBrockerStatus(user_no) == status){
                resultMap.put("message", "동일한 상태로 변경 불가합니다.(current : "+current+", incoming : "+status+")");
            }
            else{
                broker_mapper.updateBrokerStatus(user_no, status);
                resultMap.put("massage",arr_msg[status-1]);
            }
            stat = HttpStatus.ACCEPTED;
        }
        else{
            resultMap.put("status",false);
            resultMap.put("message","잘못된 요청 입니다.(usage : status : [1,2,3,4])");
            stat = HttpStatus.BAD_REQUEST;
        }
    }
    else{
        resultMap.put("status",false);
        resultMap.put("message","잘못된 요청 입니다.(usage : /api/borker/status/[broker, admin])");
        stat = HttpStatus.BAD_REQUEST;
    }
    return new ResponseEntity<Map<String, Object>>(resultMap,stat);
    }
}
