package realty.realty.api;

import java.util.Map;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import realty.realty.service.BrokerService;

@RestController
@RequestMapping("/api/broker")
public class BrokerAPIController {
    @Autowired BrokerService broker_service;
    //중계인 추가 기능
    @PutMapping("/add")
    public ResponseEntity<Map<String, Object>> brokerInfoAdd(@RequestBody BrokerInfoVO data) throws Exception{
        return broker_service.insertbrokerInfoAdd(data);
    }
    //중계인 아이디 중복체크 기능
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
    //중계인 계정 수정 기능
    @PatchMapping("/update")
    public ResponseEntity<Map<String, Object>> updateBrokerInfo(@RequestBody BrokerInfoVO data) throws Exception{
    return broker_service.updateBrokerInfo(data);
    }
    //중계인 상태 수정 기능
    @PatchMapping("/status/{value}")
    public ResponseEntity<Map<String, Object>> updateBrokerStatus(
        @PathVariable String value, @RequestParam Integer status,@RequestParam Integer user_no, HttpSession session
    ) {
    return broker_service.updateBrokerstatus(value,status,user_no,session);
    }
}
