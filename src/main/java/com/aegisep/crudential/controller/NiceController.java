package com.aegisep.crudential.controller;

import com.aegisep.crudential.common.AptiException;
import com.aegisep.crudential.common.AptiUtil;
import com.aegisep.crudential.dto.NiceDto;
import com.aegisep.crudential.service.NiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@Controller
@RequestMapping(value="/nice")
public class NiceController {

    Logger logger = LoggerFactory.getLogger(NiceController.class);

    @Value("${nice.siteCode}")
    private String siteCode;    // NICE로부터 부여받은 사이트 코드

    @Value("${nice.sitePass}")
    private String sitePass;    // NICE로부터 부여받은 사이트 패스워드

    @Value("${nice.succURL}")
    private String succURL;     // 성공시 이동될 URL

    @Value("${nice.failURL}")
    private String failURL;     // 실패시 이동될 URL


    //private final NiceService niceService;
    /*────────────────────────────────────────────────────────────────────
    작성일 : Mar 16th 2022
    작성자 : 고의동
    역할   : Nice 사용자인증(표준방식)
    프로세스흐름 : 1. 회사인증
                 2. 인증화면 호출
                 3. 인증 결과(성공, 실패)
     ────────────────────────────────────────────────────────────────────*/
    @GetMapping(value = "/{custType}/{authType}")
    public String authReqByNice(@PathVariable("custType") String custType, @PathVariable("authType") String authType, Model model) throws AptiException {
        /*──────────────────────────────────────────────────────────────────
        http://192.168.38.36:8080/nice/Web/M    :   웹을 통한 후대폰 인증
        http://192.168.38.36:8080/nice/Web/U    :   웹을 통한 공동인증서 인증
        http://192.168.38.36:8080/nice/Web/F    :   웹을 통한 금융인증서 인증
        http://192.168.38.36:8080/nice/Web/S    :   웹을 통한 PASS인증서 인증
        http://192.168.38.36:8080/nice/Web/C    :   웹을 통한 신용카드 인증
        ──────────────────────────────────────────────────────────────────
        http://192.168.38.36:8080/nice/Mobile/M   :   모바일을 통한 후대폰 인증
        http://192.168.38.36:8080/nice/Mobile/U   :   모바일을 통한 공동인증서 인증
        http://192.168.38.36:8080/nice/Mobile/F   :   모바일을 통한 금융인증서 인증
        http://192.168.38.36:8080/nice/Mobile/S   :   모바일을 통한 PASS인증서 인증
        http://192.168.38.36:8080/nice/Mobile/C   :   모바일을 통한 신용카드 인증
        ──────────────────────────────────────────────────────────────────*/
        logger.info("==================================================="  );
        logger.info("NICE 회사인증 시작                                   "  );
        logger.info("NICE 회사인증 시작시간 ==>" +  LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmssSSS")));
        logger.info("NICE 인증방식 ==>" + custType                          );
        logger.info("NICE 인증수단 ==>" + authType                          );
        /*──────────────────────────────────────────────────────────────────*/
        NiceDto niceDto = new NiceDto();
        /*────────────────────────────────────────────────────────────────────
        웹페이지 모바일페이지 구분값 필요
        ──────────────────────────────────────────────────────────────────────
        String custType 	= "";       // 기본 웹페이지 호출
        String custType 	= "Mobile"; // 모바일페이지 호출
        ────────────────────────────────────────────────────────────────────*/
        niceDto.setCustType((custType.equals("Web"))?"":custType);
        /*────────────────────────────────────────────────────────────────────
        인증수단 정보 설정
        ──────────────────────────────────────────────────────────────────────
        String authType     = ""; // 기본 선택화면
        String authType     = "M" // 휴대폰
        String authType     = "X" // 인증서공통
        String authType     = "U" // 공동인증서
        String authType     = "F" // 금융인증서
        String authType     = "S" // PASS인증서
        String authType     = "C" // 신용카드
        ────────────────────────────────────────────────────────────────────*/
        niceDto.setAuthType(authType);
        /*──────────────────────────────────────────────────────────────────*/
        model.addAttribute("encrData", compAuthByNice(niceDto)   ) ;
        /*──────────────────────────────────────────────────────────────────*/
        logger.info("NICE 회사인증 끝                                        ");
        logger.info("==================================================="   );
        /*──────────────────────────────────────────────────────────────────*/
        //  To do Lost
        //  추후 인증 방식에 따른 UI 변경 필요
        //  인증 실패 시 관리자에게 문자 발송?
        /*──────────────────────────────────────────────────────────────────*/
        return "/nice/niceWebService";
    }

    /*────────────────────────────────────────────────────────────────────
    작성일 : Mar 20th 2022
    작성자 : 고의동
    역할   : 인증성공
    ────────────────────────────────────────────────────────────────────*/
    @GetMapping(value = "/niceAuthOk")
    public String niceAuthOk(@RequestParam(value = "EncodeData") String encoData, Model model) throws AptiException {
        logger.info("==================================================="   );
        logger.info("NICE 인증성공 시작                                   "   );
        /*──────────────────────────────────────────────────────────────────*/
        NiceID.Check.CPClient niceCheck = new  NiceID.Check.CPClient();
        /*──────────────────────────────────────────────────────────────────*/
        NiceDto niceDto = new NiceDto();
        /*──────────────────────────────────────────────────────────────────*/
        String mess     = ""; // 결과메시지
        /*──────────────────────────────────────────────────────────────────*/
        encoData        = AptiUtil.requestReplace(encoData); // 암호화된데이터
        /*──────────────────────────────────────────────────────────────────*/
        niceDto.setRetuCode(niceCheck.fnDecode(siteCode, sitePass, encoData)); // 결과코드
        /*──────────────────────────────────────────────────────────────────*/
        niceDto.setDecoTime (niceCheck.getCipherDateTime()); // 복호화한시간 (YYMMDDHHMMSS)
        /*──────────────────────────────────────────────────────────────────*/
        if(niceDto.getRetuCode() == 0 ) {
            /*──────────────────────────────────────────────────────────────────*/
            mess = "정상";
            /*──────────────────────────────────────────────────────────────────*/
            String planData = niceCheck.getPlainData();
            /*──────────────────────────────────────────────────────────────────
            데이타를 추출
            ──────────────────────────────────────────────────────────────────*/
            HashMap <String, String> resuMap = niceCheck.fnParse(planData);
            /*──────────────────────────────────────────────────────────────────*/
            niceDto.setMess     (mess                        ); // 결과메시지
            niceDto.setRequNumb (resuMap.get("REQ_SEQ"      )); // 요청번호
            niceDto.setRespNumb (resuMap.get("RES_SEQ"      )); // 인증고유번호
            niceDto.setAuthType (resuMap.get("AUTH_TYPE"    )); // 인증수단
            niceDto.setName     (resuMap.get("NAME"         )); // 성명
            niceDto.setBirtDate (resuMap.get("BIRTHDATE"    )); // 생년월일(YYYYMMDD)
            niceDto.setGend     (resuMap.get("GENDER"       )); // 성별
            niceDto.setNatiInfo (resuMap.get("NATIONALINFO" )); // 내/외국인정보 (개발가이드 참조)
            niceDto.setDuplInfo (resuMap.get("DI"           )); // 중복가입확인값 (DI_64 byte)
            niceDto.setConnInfo (resuMap.get("CI"           )); // 연계정보확인값 (CI_88 byte)
            niceDto.setMobiNo   (resuMap.get("MOBILE_NO"    )); // 휴대폰번호
            niceDto.setMobiCo   (resuMap.get("MOBILE_CO"    )); // 통신사
            /*──────────────────────────────────────────────────────────────────*/
            logger.info("NICE 인증성공 복호화시간 ==> " + niceDto.getDecoTime ());
            logger.info("NICE 인증성공 결과코드 ==> " + niceDto.getRetuCode());
            logger.info("NICE 인증성공 결과메시지 ==> " + niceDto.getMess());
            logger.info("NICE 인증성공 요청번호 ==> " + niceDto.getRequNumb());
            logger.info("NICE 인증성공 인증고유번호 ==> " + niceDto.getRespNumb());
            logger.info("NICE 인증성공 인증수단 ==> " + niceDto.getAuthType());
            logger.info("NICE 인증성공 성명 ==> " + niceDto.getName());
            logger.info("NICE 인증성공 생년월일 ==> " + niceDto.getBirtDate());
            logger.info("NICE 인증성공 성별 ==> " + niceDto.getGend());
            logger.info("NICE 인증성공 내/외국인정보 ==> " + niceDto.getNatiInfo());
            logger.info("NICE 인증성공 중복가입확인값 ==> " + niceDto.getDuplInfo());
            logger.info("NICE 인증성공 연계정보확인값 ==> " + niceDto.getConnInfo());
            logger.info("NICE 인증성공 휴대폰번호 ==> " + niceDto.getMobiNo());
            logger.info("NICE 인증성공 통신사 ==> " + niceDto.getMobiCo());
            /*──────────────────────────────────────────────────────────────────*/
            /*
            String session_sRequestNumber = (String)session.getAttribute("REQ_SEQ");
            세션값 불일치 로직 추가 여부 확인
            String session_sRequestNumber = (String)session.getAttribute("REQ_SEQ");
            if(!sRequestNumber.equals(session_sRequestNumber))
            {
                sMessage = "세션값 불일치 오류입니다.";
                sResponseNumber = "";
                sAuthType = "";
            }
            */
        }else if(niceDto.getRetuCode() != 0) {
            /*──────────────────────────────────────────────────────────────────*/
            mess = "알수 없는 에러 입니다. retuCode : " + niceDto.getRetuCode();
            mess = (niceDto.getRetuCode() == -1) ? "복호화 시스템 오류입니다." + niceDto.getRetuCode() : "";
            mess = (niceDto.getRetuCode() == -4) ? "복호화 처리 오류입니다." + niceDto.getRetuCode() : "";
            mess = (niceDto.getRetuCode() == -5) ? "복호화 해쉬 오류입니다." + niceDto.getRetuCode() : "";
            mess = (niceDto.getRetuCode() == -6) ? "복호화 데이터 오류입니다." + niceDto.getRetuCode() : "";
            mess = (niceDto.getRetuCode() == -9) ? "입력 데이터 오류입니다." + niceDto.getRetuCode() : "";
            mess = (niceDto.getRetuCode() == -12) ? "사이트 패스워드 오류입니다" + niceDto.getRetuCode() : "";
            /*──────────────────────────────────────────────────────────────────*/
            niceDto.setMess     (mess                        ); // 결과메시지
            /*──────────────────────────────────────────────────────────────────*/
            logger.error("NICE 인증성공 복호화시간 ==> " + niceDto.getDecoTime());
            logger.error("NICE 인증성공 결과코드 ==> " + niceDto.getRetuCode());
            logger.error("NICE 인증성공 결과메시지 ==> " + mess);
            // To do : 인증 실패 시 사용자에게 문자 발송?
            /*──────────────────────────────────────────────────────────────────*/
        }
        logger.info("NICE 인증성공 끝                                        "                  );
        logger.info("==================================================="                     );
        model.addAttribute("niceDto",niceDto);
        return "/nice/niceAuthOk";
    }

    /*────────────────────────────────────────────────────────────────────
    작성일 : Mar 20th 2022
    작성자 : 고의동
    역할   : 인증실패 로직 추가 필요
            NICE에서 제공하는 '표준 방식의 인증'의 경우 실패의 정의는 시스템적 에러에 의한 실패를 의미함.
            사용자 인증 실패(E.g : 인증번호 오입력, 생년월일 오입력 등)은 Nice Web Service 에서 모두 처리 함.
    ────────────────────────────────────────────────────────────────────*/
    @RequestMapping(value="/nice/niceAuthFail")
    public String niceAuthFail() {
        logger.info("Implementing the fail processi if it is needed"    );
        return "/nice/niceAuthFail";
    }


    /*────────────────────────────────────────────────────────────────────
    작성일 : Mar 19th 2022
    작성자 : 고의동
    역할   : 회사인증
     ────────────────────────────────────────────────────────────────────*/
    public String compAuthByNice(NiceDto niceDto) throws AptiException  {
        /*──────────────────────────────────────────────────────────────────*/
        NiceID.Check.CPClient niceCheck = new  NiceID.Check.CPClient();
        /*──────────────────────────────────────────────────────────────────*/
        String mess         = "";   // 결과메세지
        String encrData     = "";   // 암호화데이터
        String requNumb     = "AUTH_REQ" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        String plaiData     = "7:REQ_SEQ" + requNumb.getBytes().length + ":" + requNumb +
                "8:SITECODE" + siteCode.getBytes().length + ":" + siteCode +
                "9:AUTH_TYPE" + niceDto.getAuthType().getBytes().length + ":" + niceDto.getAuthType() +
                "7:RTN_URL" + succURL.getBytes().length + ":" + succURL +
                "7:ERR_URL" + failURL.getBytes().length + ":" + failURL +
                "9:CUSTOMIZE" + niceDto.getCustType().getBytes().length + ":" + niceDto.getCustType();
        /*────────────────────────────────────────────────────────────────────
        회사인증 전문 송신
         ────────────────────────────────────────────────────────────────────*/
        int retuCode = niceCheck.fnEncode(siteCode, sitePass, plaiData);
        /*──────────────────────────────────────────────────────────────────*/
        if(retuCode == 0 ) {
            mess    = "정상";
            /*──────────────────────────────────────────────────────────────────*/
            // 회사인증 전문 수신 암호화 데이터
            /*──────────────────────────────────────────────────────────────────*/
            encrData    = niceCheck.getCipherData();
            /*──────────────────────────────────────────────────────────────────*/
        } else if( retuCode != 0) {
            mess = "알수 없는 에러 입니다. retuCode : " + retuCode;
            mess = (retuCode == -1) ? "암호화 시스템 에러입니다." + retuCode : "";
            mess = (retuCode == -2) ? "암호화 처리오류입니다." + retuCode : "";
            mess = (retuCode == -3) ? "암호화 데이터 오류입니다." + retuCode : "";
            mess = (retuCode == -9) ? "입력 데이터 오류입니다." + retuCode : "";
        }
        /*──────────────────────────────────────────────────────────────────*/
        logger.info("NICE 인증코드 ==>" + retuCode);
        logger.info("NICE 인증메시지 ==>" + mess);
        /*──────────────────────────────────────────────────────────────────*/
        return encrData;
    }
}


