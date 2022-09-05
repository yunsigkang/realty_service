package realty.realty.service;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import realty.realty.common.LoginSessionVO;
import realty.realty.data.BrokerInfoVO;
import realty.realty.data.BrokerLoginVO;
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
}
