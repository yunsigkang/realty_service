package realty.realty.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import realty.realty.service.Userservice;

@RestController
@RequestMapping("/api/user")
public class UserAPICountroller {
    @Autowired Userservice user_service;
    // 사용자 추가 기능
    @PutMapping("/add")
    public ResponseEntity<Map<String, Object>> insertUserInfo(){
        return null;
    } 
    // 사용자 아이디 중복 체크 기능
    @GetMapping("/idcheck")
    public ResponseEntity<Map<String, Object>> isDuplicatedId(){
        return null;
    } 
    // 사용자 검색 기능
    @GetMapping("/get")
    public ResponseEntity<Map<String, Object>> selectUserInfo(){
        return null;
    } 
    // 사용자 수정 기능
    @PatchMapping("/update/info")
    public ResponseEntity<Map<String, Object>> updateUserInfo(){
        return null;
    } 
    // 사용자 상태 수정 기능
    @PatchMapping("/update/Status")
    public ResponseEntity<Map<String, Object>> updateUserStatus(){
        return null;
    } 
    // 좋아요 추가 기능
    @PutMapping("/like")
    public ResponseEntity<Map<String, Object>> addLikeInfo(){
        return null;
    } 
    //좋아요 업데이트 기능
    @PatchMapping("/like")
    public ResponseEntity<Map<String, Object>> updateLikeInfo(){
        return null;
    } 
    // 시간 기록 기능
    @PutMapping("/lookup")
    public ResponseEntity<Map<String, Object>> insertLookupInfo(){
        return null;
    } 
}
