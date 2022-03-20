package com.aegisep.crudential.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/*────────────────────────────────────────────────────────────────────
    작성일 : Mar 11th 2022
    작성자 : 고의동
    역할   : 예외처리를 위한 클래스 생성
────────────────────────────────────────────────────────────────────*/
@Getter
@RequiredArgsConstructor
public class AptiException extends Exception {

    //private final HttpStatus status;

    private final String mess;










}
