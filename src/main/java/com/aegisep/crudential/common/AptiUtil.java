package com.aegisep.crudential.common;

public class AptiUtil {

    /*─────────────────────────────────────────────────────────────────────────────
        작성자 : 고의동
        작성일 : Mar 18th 2022
        메소드정의 : 입력받은 문자열에 설정된 기호를 제거하여 리턴.
        입력받는 매게 변수 : 전각문자를 반각 문자로 변경할 문자열
     ─────────────────────────────────────────────────────────────────────────────*/
    public static String requestReplace (String paramValue) {

        String result = "";
        //─────────────────────────────────────────────────────────────────────────────
        if (paramValue != null) {
            //─────────────────────────────────────────────────────────────────────────────
            paramValue = paramValue.replaceAll("<"  , "&lt;").replaceAll(">", "&gt;");
            //─────────────────────────────────────────────────────────────────────────────
            paramValue = paramValue.replaceAll("\\*", "");
            paramValue = paramValue.replaceAll("\\?", "");
            paramValue = paramValue.replaceAll("\\[", "");
            paramValue = paramValue.replaceAll("\\{", "");
            paramValue = paramValue.replaceAll("\\(", "");
            paramValue = paramValue.replaceAll("\\)", "");
            paramValue = paramValue.replaceAll("\\^", "");
            paramValue = paramValue.replaceAll("\\$", "");
            paramValue = paramValue.replaceAll("'"  , "");
            paramValue = paramValue.replaceAll("@"  , "");
            paramValue = paramValue.replaceAll("%"  , "");
            paramValue = paramValue.replaceAll(";"  , "");
            paramValue = paramValue.replaceAll(":"  , "");
            paramValue = paramValue.replaceAll("-"  , "");
            paramValue = paramValue.replaceAll("#"  , "");
            paramValue = paramValue.replaceAll("--" , "");
            paramValue = paramValue.replaceAll("-"  , "");
            paramValue = paramValue.replaceAll(","  , "");
            //─────────────────────────────────────────────────────────────────────────────
            result = paramValue;
            //─────────────────────────────────────────────────────────────────────────────
        }
        return result;
    }


    /*─────────────────────────────────────────────────────────────────────────────
        작성자 : 고의동
        작성일 : Mar 18th 2022
        메소드정의 : 전각문자를 반각 문자로 치환
        입력받는 매게 변수 : 전각문자를 반각 문자로 변경할 문자열
     ─────────────────────────────────────────────────────────────────────────────*/
    private static String toHalfChar(String src)
    {
        // 문자열을 담기 위한 StringBuffer 클래스 생성
        StringBuffer strBuf = new StringBuffer();

        // 지역변수 초기화
        char c = 0;

        for (int i = 0; i < src.length(); i++)
        {

            // 한문자씩 끝어서 char변수 초기화
            c = src.charAt(i);
            /*==========================================================
             * 유니코드 65281(16진수 표기법 0xff01) 보다 크고
             * 유니코드 65374(16진수 표기법 0xff5e) 보다 작은 경우
             * 유니코드 65248(16진수 표기법 Oxfee0) 만큼 빼면 1 Byte짜리 특수문자 혹은 공백문자(유니코드 32, 16진수 표기법 Ox20)로 변경
             ============================================================*/
            if (c >= '！' && c <= '～')
            {
                System.out.print("전각특수문자발생 =>" + c);
                c -= 0xfee0;
                System.out.print("전각특수문자치환 =>" + c);
            }
            else if (c == '　')
            {
                System.out.print("전각공백문자발생 =>" + c);
                c = 0x20;
                System.out.print("전각공백문자치환 =>" + c);
            }
            // 문자열 버퍼에 변환된 문자를 쌓는다
            strBuf.append(c);
        }
        return strBuf.toString();
    }
}
