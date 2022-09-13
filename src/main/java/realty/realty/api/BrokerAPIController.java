package realty.realty.api;

import java.util.Map;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import realty.realty.data.borker.BrokerInfoVO;
import realty.realty.data.borker.BrokerLoginVO;
import realty.realty.data.borker.BrokerOfficeInfoVO;
import realty.realty.service.BrokerService;

@RestController
@RequestMapping("/api/broker")
public class BrokerAPIController {
    @Autowired BrokerService broker_service;
    //중개인 추가 기능
    @PutMapping("/add")
    public ResponseEntity<Map<String, Object>> brokerInfoAdd(@RequestBody BrokerInfoVO data) throws Exception{
        return broker_service.insertbrokerInfoAdd(data);
    }
    //중개인 아이디 중복체크 기능
    @GetMapping("/idcheck")
    public ResponseEntity<Map<String, Object>> isExistBrokerId(@RequestParam String id){
        return broker_service.isExistBrokerId(id);
    }
    //로그인 기능
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginBroker(@RequestBody BrokerLoginVO login, HttpSession session) throws Exception{
    return broker_service.loginBroker(login, session);
    }
    //로그아웃 기능
    @GetMapping("/logout")
    public String logoutBroker(HttpSession session){
        session.invalidate();
        return "로그아웃 되었습니다.";
    }
    //중개인 계정 수정 기능
    @PatchMapping("/update")
    public ResponseEntity<Map<String, Object>> updateBrokerInfo(@RequestBody BrokerInfoVO data) throws Exception{
    return broker_service.updateBrokerInfo(data);
    }
    //중개인 상태 수정 기능
    @PatchMapping("/status/{value}")
    public ResponseEntity<Map<String, Object>> updateBrokerStatus(
        @PathVariable String value, @RequestParam Integer status,@RequestParam Integer user_no, HttpSession session
    ) {
    return broker_service.updateBrokerstatus(value,status,user_no,session);
    }
    //중개사 추가 기능
    @PutMapping("/office/add")
        public ResponseEntity<Map<String, Object>> insertBrokerOfficeInfo(@RequestBody BrokerOfficeInfoVO data) {
            return broker_service.insertBrokerOfficeInfo(data);
        }
    //중개사 중복 체크 기능
    @GetMapping("/office/check")
    public ResponseEntity<Map<String, Object>> getBrokerOfficeInfo(@RequestParam String name, @RequestParam String reg_no) {
        return broker_service.getBrokerOfficeInfo(name, reg_no);
    }
    //중개사 조회 기능
    @GetMapping("/office/list")
    public ResponseEntity<Map<String, Object>> getBrokerOfficelist(@RequestParam @Nullable String keyword, @RequestParam @Nullable Integer page) {
        return broker_service.getBrokerOfficelist(keyword, page);
    }
    //중개사 수정 기능
    @PatchMapping("/office/update")
    public ResponseEntity<Map<String, Object>> updateBrokerOffice(@RequestBody BrokerOfficeInfoVO data) {
        return broker_service.updateBrokerOffice(data);
    }
    //중개사 삭제 기능
    @DeleteMapping("/office/delete")
    public ResponseEntity<Map<String, Object>> deleteBrokerOfficeInfo(@RequestParam Integer no) {
        return broker_service.deleteBrokerOfficeInfo(no);
    }
    //중개사 여부 기능
    @GetMapping("/office/get")
    public ResponseEntity<Map<String, Object>> selectBrokerOfficeInfoBySeq(@RequestParam Integer no) {
        return broker_service.selectBrokerOfficeInfoBySeq(no);
    }
}
