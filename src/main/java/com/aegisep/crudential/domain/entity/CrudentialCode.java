package com.aegisep.crudential.domain.entity;

import com.aegisep.crudential.domain.repository.key.CrudentialCodePK;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="crudential_code")
@IdClass(CrudentialCodePK.class)
@Getter
@Setter
@NoArgsConstructor
public class CrudentialCode {

    @Id
    @Column(name="orga_type")
    private String orgaType;

    @Id
    @Column(name="code")
    private String code;

    @Column(name="orga_type_desc")
    private String orgaTypeDesc;

    @Column(name="code_desc")
    private String codeDesc;


}
