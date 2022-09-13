package realty.realty.data.realty;

import lombok.Data;

@Data
public class RealtyPostinfoVO {
    private Integer rpi_seq;            //매물 게시글 번호
    private String rpi_title;           //매물 게시글 제목
    private String rpi_content;         //매물 게시슬 내용
    private Integer rpi_rbi_seq;        //매물 기본 정보 번호
    private Integer rpi_bork_seq;
}
