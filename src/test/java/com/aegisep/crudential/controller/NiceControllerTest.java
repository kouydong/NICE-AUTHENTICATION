package com.aegisep.crudential.controller;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

class NiceControllerTest {

    @Test
    public void authReqByNice() throws Exception {
        System.out.println("인증 정보 확인");
        String sSiteCode            = "BD793";          // NICE로부터 부여받은 사이트 코드
        String sSitePassword        = "GxH5D1jomX79";   // NICE로부터 부여받은 사이트 패스워드
        String sRequestNumber       = "REQ"; // 나중의 임의값으로 변경가능
        sRequestNumber = "REQ" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        System.out.println("리퀘스트 넘버 ==>" + sRequestNumber);
        Random random = new Random();
        int randomNum = random.nextInt(9999) + 1000;
        randomNum = (randomNum >= 10000)?randomNum-1000:randomNum;



    }



    @Test
    public void NiceEncryptedTest() throws Exception {

    }
}