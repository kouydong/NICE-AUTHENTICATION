package com.aegisep.crudential.domain.repository.key;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
/*────────────────────────────────────────────────────────────────────
    작성일 : Mar 12th 2022
    작성자 : 고의동
    역할   : 특정 테이블에 복합키를 관리하기 위한 클래스
────────────────────────────────────────────────────────────────────*/
@Getter
@Setter
public class CrudentialCodePK implements Serializable {

    private String orgaType;;

    private String code;

}
