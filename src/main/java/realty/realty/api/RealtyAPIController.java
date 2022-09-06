package realty.realty.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import realty.realty.data.realty.RealtyBuildingInfoVO;
import realty.realty.data.realty.RealtyTotalInfoVO;
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
    //
    @PutMapping("/post/add")
    public ResponseEntity<Map<String, Object>> putBuildingPost(@RequestBody RealtyTotalInfoVO data) {
        return realty_service.putBuildingPost(data);
    } 
}