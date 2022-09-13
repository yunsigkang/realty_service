package realty.realty.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import realty.realty.data.realty.RealtyBasicInfoVO;
import realty.realty.data.realty.RealtyBuildingInfoVO;
import realty.realty.data.realty.RealtyOptionInfoVO;
import realty.realty.data.realty.RealtyPostinfoVO;
import realty.realty.data.realty.RealtySearchVO;
import realty.realty.data.realty.RealtyTotalInfoVO;
import realty.realty.data.realty.update.RealtyBuildingUpdateVO;
import realty.realty.mapper.RealtyMapper;

@Service
public class RealtyService {
    @Autowired RealtyMapper Realty_mapper;
    //건물 추가 service
    public ResponseEntity<Map<String, Object>> insertRealtyBuildingInfo(RealtyBuildingInfoVO data){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        HttpStatus stat = null;

        String address = data.getBi_address();
        address = address.replace(" ", "");

        if (Realty_mapper.isExistBuildingInfo(address)){
            resultMap.put("status", false);
            resultMap.put("message", "이미 존재하는 건물 정보입니다.");
            stat = HttpStatus.ACCEPTED;
        }
        else {
            Realty_mapper.insertrealtyBuildingInfo(data);
            resultMap.put("status", true);
            resultMap.put("message", "건물 정보가 추가되었습니다.");
            stat = HttpStatus.OK;
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, stat);
    }
    //건물 주소지 중복 체크 service
    public ResponseEntity<Map<String, Object>> isExistBuldingAdrress(String address){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        address = address.replace(" ", "");
        if (Realty_mapper.isExistBuildingInfo(address)){
            resultMap.put("status", false);
            resultMap.put("message", "이미 등록된 주소지 정보입니다.");
        }
        else {
            resultMap.put("status", true);
            resultMap.put("message", "아직 등록되지 않은 주소지입니다.");
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }
    //건물 리스트 불러오는 service
    public ResponseEntity<Map<String, Object>> selectbuildingList(String keyword, Integer page){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        // list, keyword, currentPage, totalPage, totalCount
        if(keyword == null) keyword="";
        if(page == null) page=1;

        resultMap.put("curPage",page);
        resultMap.put("keyword", keyword);
        resultMap.put("pageCnt",Realty_mapper.selectBuildingTotalPage(keyword,page));
        resultMap.put("totalCnt",Realty_mapper.selectBuildingTotalCount(keyword, page));
        resultMap.put("list",Realty_mapper.selectBuildingList(keyword, page));

        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }
    //건물 정보 수정 service
    public ResponseEntity<Map<String, Object>> updateBuildingInfo(RealtyBuildingUpdateVO data){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        
        if(data.getUpdate_address()){
            String addr = data.getBuilding_info().getBi_address().replace(" ", "");
            if (Realty_mapper.isExistBuildingInfo(addr)){
                resultMap.put("status", false);
                resultMap.put("message", "이미 등록된 주소지입니다.");
                return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
            }
        }
        Realty_mapper.updateBulidingInfo(data);
        resultMap.put("status", true);
        resultMap.put("message", "건물 정보가 변경되었습니다.");
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }
    //건물 삭제 service
    public ResponseEntity<Map<String, Object>> deleteBuildingInfo(Integer building_seq){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        HttpStatus stat = null;
        if(Realty_mapper.isExistBuildingBySeq(building_seq)){
            Realty_mapper.deleteBuildingInfo(building_seq);
            resultMap.put("status", true);
            resultMap.put("message", "빌딩(건물) 데이터를 삭제했습니다.");
            stat=HttpStatus.ACCEPTED;
        }
        else{
            resultMap.put("status", false);
            resultMap.put("message", "빌딩(건물) 데이터가 존재하지 않습니다.");
            stat=HttpStatus.ACCEPTED;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, stat);
    }
    //건물 게시글 등록 service
    @Transactional
    public ResponseEntity<Map<String, Object>> insertBuildingInfo(RealtyTotalInfoVO data){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

        RealtyOptionInfoVO option = data.getOption_info();
        RealtyPostinfoVO post = data.getPost_info();
        RealtyBasicInfoVO basic = data.getBasic_info();

        Realty_mapper.insertRealtyOptionInfo(option);
        basic.setRbi_ro_seq(option.getRo_seq());
        
        Realty_mapper.insertRealtyBasicInfo(basic);
        post.setRpi_rbi_seq(basic.getRbi_seq());

        Realty_mapper.insertRealtyPostInfo(post);

        resultMap.put("status", true);
        resultMap.put("message", "부동산 매매.임대 정보를 추가하였습니다.");
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }
    //게시글 수정 service(Basic/Option/Post/All)
    public ResponseEntity<Map<String, Object>> updateRealtyPostBasicInfo(RealtyBasicInfoVO data){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        HttpStatus stat = null;
        Realty_mapper.updateRealtyPostBasicInfo(data);
        resultMap.put("status", true);
        resultMap.put("message", "매물 기본정보가 수정되었습니다.");
        stat=HttpStatus.ACCEPTED;
        return new ResponseEntity<Map<String, Object>>(resultMap, stat);
    }
    public ResponseEntity<Map<String, Object>> updateRealtyPostOptionInfo(RealtyOptionInfoVO data){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        HttpStatus stat = null;
        Realty_mapper.updateRealtyPostOptioncInfo(data);
        resultMap.put("status", true);
        resultMap.put("message", "매물 옵션정보가 수정되었습니다.");
        stat=HttpStatus.ACCEPTED;
        return new ResponseEntity<Map<String, Object>>(resultMap, stat);
    }
    public ResponseEntity<Map<String, Object>> updateRealtyPostInfo(RealtyPostinfoVO data){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        HttpStatus stat = null;
        Realty_mapper.updateRealtyPostInfo(data);
        resultMap.put("status", true);
        resultMap.put("message", "매물 글 정보가 수정되었습니다.");
        stat=HttpStatus.ACCEPTED;
        return new ResponseEntity<Map<String, Object>>(resultMap, stat);
    }
    @Transactional
    public ResponseEntity<Map<String, Object>> updateRealtyInfoAll(RealtyTotalInfoVO data){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        HttpStatus stat = null;
        Realty_mapper.updateRealtyPostBasicInfo(data.getBasic_info());
        Realty_mapper.updateRealtyPostOptioncInfo(data.getOption_info());
        Realty_mapper.updateRealtyPostInfo(data.getPost_info());
        resultMap.put("status", true);
        resultMap.put("message", "매물 정보가 수정되었습니다.");
        stat=HttpStatus.ACCEPTED;
        return new ResponseEntity<Map<String, Object>>(resultMap, stat);
    }

    //관리비 항목 추가 및 중복체크 기능
    public ResponseEntity<Map<String, Object>> insertMaintainItem(String name){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        HttpStatus stat = null;

        if(Realty_mapper.isExistBuildingItem(name)){
            resultMap.put("status", false);
            resultMap.put("message", name+"항목은 이미 존재합니다.");
            stat = HttpStatus.ACCEPTED;
        }
        else{
            Realty_mapper.insertMaintainItem(name);
            resultMap.put("status", true);
            resultMap.put("message", name+"항목은 추가했습니다.");
            stat = HttpStatus.CREATED;
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, stat);
    }
    //관리비 항목 리스트 및 갯수 불러오는 service
    public ResponseEntity<Map<String, Object>> insertMaintainItemList(){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

        resultMap.put("status", true);
        resultMap.put("count", Realty_mapper.selectMaintainItemCount());
        resultMap.put("list", Realty_mapper.selectMaintainItemList());

        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }
    //관리비 항목 삭제 service
    public ResponseEntity<Map<String, Object>> deleteMaintainItemList(String name){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

        if(Realty_mapper.isExistBuildingItem(name)){
            Realty_mapper.deleteMaintainItemList(name);
            resultMap.put("status", true);
            resultMap.put("message", name + "관리비 항목을 삭제했습니다.");
        }
        else{
            resultMap.put("status", false);
            resultMap.put("message", name + "관리비 항목은 존재하지 않습니다.");    
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }
    //게시글 검색 service
    public ResponseEntity<Map<String, Object>> getPostList(RealtySearchVO search){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

        if(search.getPage() == null) search.setPage(1);
        if(search.getAddress() == null) search.setAddress("");
        if(search.getLon() == null) {
            resultMap.put("message","위도(lat), 경도(lon)은 필수 값입니다.");
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
        }

        resultMap.put("list", Realty_mapper.selectPostList(search));
        resultMap.put("total", Realty_mapper.selectPostCnt(search));
        resultMap.put("pageCnt", Realty_mapper.selectPostPageCnt(search));
        resultMap.put("page", search.getPage());
        resultMap.put("keyword", search.getAddress());

        if(search.getRad() == null){
            resultMap.put("radius",3.0);
        }
        else{
            resultMap.put("radius",search.getRad());
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }
    //한 개의 게시글 검색 service
    public ResponseEntity<Map<String, Object>> selectPostInfoBySeq(Integer seq){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        
            resultMap.put("post", Realty_mapper.selectPostInfoBySeq(seq));

        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }
}
