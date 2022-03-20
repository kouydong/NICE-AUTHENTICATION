package com.aegisep.crudential.domain.repository;

import com.aegisep.crudential.domain.entity.CrudentialCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CrudentialCodeRepository extends JpaRepository<CrudentialCode, Long> {

    List<CrudentialCode> findByOrgaTypeAndCode(String orgaType, String code);
}
