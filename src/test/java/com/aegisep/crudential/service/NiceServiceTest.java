package com.aegisep.crudential.service;

import com.aegisep.crudential.domain.entity.CrudentialCode;
import com.aegisep.crudential.domain.repository.CrudentialCodeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class NiceServiceTest {

    @Autowired
    CrudentialCodeRepository crudentialCodeRepository;

    @Test
    public void getCrudentialCodeDesc() {

        String returnCode = "0040";
        List<CrudentialCode> crudentialCode = crudentialCodeRepository.findByOrgaTypeAndCode("NICE_01", returnCode);

    }
}