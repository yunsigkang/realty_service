package realty.realty.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import realty.realty.data.realty.RealtyBasicInfoVO;
import realty.realty.data.realty.RealtyBuildingInfoVO;
import realty.realty.data.realty.RealtyOptionInfoVO;
import realty.realty.data.realty.RealtyPostinfoVO;
import realty.realty.data.realty.RealtyTotalInfoVO;
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
    public ResponseEntity<Map<String, Object>> putBuildingPost(RealtyTotalInfoVO data){
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
}
