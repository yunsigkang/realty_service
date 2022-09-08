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
import realty.realty.data.borker.BrokerOfficeInfoVO;
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
    //중개인 계정 상태 수정 service
    public ResponseEntity<Map<String, Object>> updateBrokerstatus(String value, Integer status, Integer user_no, HttpSession session){
    Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
    HttpStatus stat = null;
    String[] arr_msg = {
        "탈퇴 철회가 완료되었습니다.",
        "탈퇴 신청가 완료합니다.",
        "중개인 정지 처리가 완료되었습니다.",
        "중개인 즉시 탈퇴 처리가 안료되었습니다."
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
    //중개사 추가 service
    public ResponseEntity<Map<String, Object>> insertBrokerOfficeInfo(BrokerOfficeInfoVO data){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        HttpStatus stat = null;

        String name = data.getBoi_name().replace(" ", "");

        if(broker_mapper.isExistBrokerOffice(name,data.getBoi_reg_number())) {
            resultMap.put("status",false);
            resultMap.put("message", "이미 등록된 사무소 이름/등록번호 입니다.");
            stat = HttpStatus.OK;
        }
        else{
            broker_mapper.insertBrokerOfficeInfo(data);
            resultMap.put("status",true);
            resultMap.put("message", "중개사무소가 등록되었습니다.");
            resultMap.put("office_seq",data.getBoi_seq());
            stat = HttpStatus.CREATED;
        }

        return new ResponseEntity<Map<String, Object>>(resultMap,stat);
    }
    //중개사 중복 체크 service
    public ResponseEntity<Map<String, Object>> getBrokerOfficeInfo(String name,String reg_no){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        HttpStatus stat = null;
        name = name.replace(" ", "");

        if(broker_mapper.isExistBrokerOffice(name,reg_no)) {
            resultMap.put("status",false);
            resultMap.put("message", "이미 등록된 사무소 이름/등록번호 입니다.");
            stat = HttpStatus.OK;
        }
        else{
            resultMap.put("status",true);
            resultMap.put("message", "동록할 수 있는 사무소 정보입니다.");
            stat = HttpStatus.ACCEPTED;
        }

        return new ResponseEntity<Map<String, Object>>(resultMap,stat);
    }
    public ResponseEntity<Map<String, Object>> getBrokerOfficelist(String keyword,Integer page){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        if (keyword == null) keyword = "";
        if (page == null) page = 1;
        resultMap.put("status",true);
        resultMap.put("keyword", keyword);
        resultMap.put("currentPage", page);
        resultMap.put("pageCnt", broker_mapper.selectBrockerOfficePageCnt(keyword));
        resultMap.put("totalCnt", broker_mapper.selectBrockerOfficeTotalCnt(keyword));
        resultMap.put("list", broker_mapper.selectBrockerOfficeList(keyword,page));
        return new ResponseEntity<Map<String, Object>>(resultMap,HttpStatus.CREATED);
    }
    public ResponseEntity<Map<String, Object>> updateBrokerOffice(BrokerOfficeInfoVO data){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        HttpStatus stat = null;

        broker_mapper.updateBrokerOfficeInfo(data);
        resultMap.put("status",true);
        resultMap.put("message", "중개사무소 정보가 수정되었습니다.");
        stat = HttpStatus.ACCEPTED;

        return new ResponseEntity<Map<String, Object>>(resultMap,stat);
    }
    public ResponseEntity<Map<String, Object>> deleteBrokerOfficeInfo(Integer no){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        HttpStatus stat = null;
        if(broker_mapper.selectBrokerOfficeBySeq(no) == null){
            resultMap.put("status",false);
            resultMap.put("message", "중개사무소 정보가 없습니다..");
            stat = HttpStatus.OK;
        }
        else{
        broker_mapper.deleteBrokerOffceInfo(no);
        resultMap.put("status",true);
        resultMap.put("message", "중개사무소 정보가 삭제되었습니다.");
        stat = HttpStatus.ACCEPTED;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap,stat);
    }
    public ResponseEntity<Map<String, Object>> selectBrokerOfficeInfoBySeq(Integer no){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        HttpStatus stat = null;
        BrokerOfficeInfoVO data = broker_mapper.selectBrokerOfficeBySeq(no);
        if(data == null){
            resultMap.put("status",false);
            resultMap.put("message", "중개사무소 정보가 없습니다..");
            stat = HttpStatus.OK;
        }
        else{
        resultMap.put("status",true);
        resultMap.put("office", data);
        stat = HttpStatus.ACCEPTED;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap,stat);
    }
}
