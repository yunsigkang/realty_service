package realty.realty.api;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import realty.realty.data.realty.RealtyBuildingInfoVO;
import realty.realty.data.realty.RealtyTotalInfoVO;
import realty.realty.data.realty.update.RealtyBuildingUpdateVO;
import realty.realty.service.RealtyService;

@RestController
@RequestMapping("/api/realty")
public class RealtyAPIController {
    @Autowired RealtyService realty_service;
    //건물 추가 기능
    @PutMapping("/building/add")
    public ResponseEntity<Map<String, Object>> addBuildingInfo(@RequestBody RealtyBuildingInfoVO data) {
        return realty_service.insertRealtyBuildingInfo(data);
    }
    //건물 주소지 중복 체크 기능
    @GetMapping("/building/check/addr")
    public ResponseEntity<Map<String, Object>> getBuildingAddressCheck(@RequestParam String address) {
        return realty_service.isExistBuldingAdrress(address);
    }
    //건물 리스트 불러오는 기능
    @GetMapping("/building/list")
    public ResponseEntity<Map<String, Object>> selectBuildingList(
        @RequestParam @Nullable String keyword, @RequestParam @Nullable Integer page
    ) {
        return realty_service.selectbuildingList(keyword, page);
    }
    //건물 정보 수정 기능
    @PatchMapping("/building/update")
    public ResponseEntity<Map<String, Object>> updateBuildingInfo(@RequestBody RealtyBuildingUpdateVO data) {
        return realty_service.updateBuildingInfo(data);
    }
    @DeleteMapping("/building/delete")
    public ResponseEntity<Map<String, Object>> deleteBuildingInfo(@RequestParam Integer building_seq) {
        return realty_service.deleteBuildingInfo(building_seq);
    }
    //건물 게시글 등록 기능
    @PutMapping("/post/add")
    public ResponseEntity<Map<String, Object>> insertBuildingInfo(@RequestBody RealtyTotalInfoVO data) {
        return realty_service.insertBuildingInfo(data);
    } 
    //건물 게시글 수정 기능
    @PatchMapping("/post/update/{type}")
    public ResponseEntity<Map<String, Object>> updateRealtyPostUpdate(@PathVariable String type, @RequestBody RealtyTotalInfoVO data) {
        if(type.equals("basic")){
            return  realty_service.updateRealtyPostBasicInfo(data.getBasic_info());
        }
        else if(type.equals("option")){
            return  realty_service.updateRealtyPostOptionInfo(data.getOption_info());
        }
        else if(type.equals("post")){
            return  realty_service.updateRealtyPostInfo(data.getPost_info());
        }
        else if(type.equals("all")){
            return realty_service.updateRealtyInfoAll(data);
        }
        else{
            Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
            resultMap.put("status", false);
            resultMap.put("status", "타입 정보가 잘못되었습니다. [basic, option, post, all]");
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
        }
    } 
    //관리비 항목 추가 및 중복체크 기능
    @PutMapping("/maintain/add")
    public ResponseEntity<Map<String, Object>> insertMaintainItem(@RequestParam String name) {
        return realty_service.insertMaintainItem(name);
    } 
    //관리비 항목 리스트 및 갯수 불러오는 기능
    @GetMapping("/maintain/list")
    public ResponseEntity<Map<String, Object>> insertMaintainItemList() {
        return realty_service.insertMaintainItemList();
    } 
    //관리 항목 삭제 기능
    @DeleteMapping("/maintain/delete")
    public ResponseEntity<Map<String, Object>> deleteMaintainItemList(@RequestParam String name) {
        return realty_service.deleteMaintainItemList(name);
    } 
}