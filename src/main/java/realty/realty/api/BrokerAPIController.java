package realty.realty.api;

import java.util.Map;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import realty.realty.data.BrokerInfoVO;
import realty.realty.data.BrokerLoginVO;
import realty.realty.service.BrokerService;

@RestController
@RequestMapping("/api/broker")
public class BrokerAPIController {
    @Autowired BrokerService broker_service;
    @PutMapping("/add")
    public ResponseEntity<Map<String, Object>> brokerInfoAdd(@RequestBody BrokerInfoVO data) throws Exception{
        return broker_service.insertbrokerInfoAdd(data);
    }
    @GetMapping("/idcheck")
    public ResponseEntity<Map<String, Object>> isExistBrokerId(@RequestParam String id){
        return broker_service.isExistBrokerId(id);
    }
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginBroker(@RequestBody BrokerLoginVO login, HttpSession session) throws Exception{
    return broker_service.loginBroker(login, session);
    }
    @GetMapping("/logout")
    public String logoutBroker(HttpSession session){
        session.invalidate();
        return "로그아웃 되었습니다.";
    }@PatchMapping("/update")
    public ResponseEntity<Map<String, Object>> updateBrokerInfo(@RequestBody BrokerInfoVO data) throws Exception{
    return broker_service.updateBrokerInfo(data);
    }
}
