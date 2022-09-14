package realty.realty.api;

import java.util.Date;
import java.util.Map;

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

import realty.realty.data.user.UserInfoVO;
import realty.realty.data.user.UserRealtyLikeVO;
import realty.realty.data.user.UserRealtyLookupVO;
import realty.realty.mapper.UserMapper;
import realty.realty.service.Userservice;

@RestController
@RequestMapping("/api/user")
public class UserAPICountroller {
    @Autowired Userservice user_service;
    // 사용자 추가 기능
    @PutMapping("/add")
    public ResponseEntity<Map<String, Object>> insertUserInfo(@RequestBody UserInfoVO data){
        return user_service.insertUserInfo(data);
    } 
    // 사용자 아이디 중복 체크 기능
    @GetMapping("/idcheck")
    public ResponseEntity<Map<String, Object>> isDuplicatedId(@RequestParam String id){
        return user_service.isDuplicatedId(id);
    } 
    // 사용자 검색 기능
    @GetMapping("/get")
    public ResponseEntity<Map<String, Object>> selectUserInfo(){
        return null;
    } 
    // 사용자 수정 기능
    @PatchMapping("/update/info")
    public ResponseEntity<Map<String, Object>> updateUserInfo(@RequestBody UserInfoVO data){
        return user_service.updateUserInfo(data);
    } 
    // 사용자 상태 수정 기능
    @PatchMapping("/update/status")
    public ResponseEntity<Map<String, Object>> updateUserStatus(@RequestParam String id, @RequestParam Integer status){
        return user_service.updateUserStatus(id,status);
    } 
    // 좋아요 추가 기능
    @PostMapping("/like")
    public ResponseEntity<Map<String, Object>> postLikeInfo(@RequestBody UserRealtyLikeVO data){
        return user_service.setUserRealtyLikeInfo(data);
    } 
    // //좋아요 업데이트 기능
    // @PatchMapping("/like")
    // public ResponseEntity<Map<String, Object>> updateLikeInfo(){
    //     return null;
    // } 
    // 시간 기록 기능
    @PutMapping("/lookup")
    public ResponseEntity<Map<String, Object>> insertLookupInfo(@RequestBody UserRealtyLookupVO data){
        return user_service.insertUserRealtyLookup(data);
    } 
}
