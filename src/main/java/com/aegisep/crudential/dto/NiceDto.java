package com.aegisep.crudential.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NiceDto {
    private String decoTime;    // 복호화시간
    private int    retuCode;    // 결과값코드
    private String mess;        // 결과메시지
    private String custType;    // 호출타입(웹, 모바일 등)
    private String authType;    // 인증수단
    private String encrData;    // NICE 암호화 데이터
    private String requNumb;    // 요청번호
    private String respNumb;    // 인증고유번호
    private String name;        // 성명
    private String birtDate;    // 생년월일(YYYYMMDD)
    private String gend;        // 성별
    private String natiInfo;    // 내/외국인정보 (개발가이드 참조)
    private String duplInfo;    // 중복가입확인값 (DI_64 byte)
    private String connInfo;    // 연계정보확인값 (CI_88 byte)
    private String mobiNo;      // 휴대폰번호
    private String mobiCo;      // 통신사
}