package com.aegisep.crudential.service;

import com.aegisep.crudential.common.AptiException;
import com.aegisep.crudential.domain.entity.CrudentialCode;
import com.aegisep.crudential.domain.repository.CrudentialCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
//@Transactional
public class NiceService {


    private final CrudentialCodeRepository crudentialCodeRepository;


    public void getCrudentialCodeDesc(String orgaType, String returnCode) throws AptiException {

        List<CrudentialCode> crudentialCode = crudentialCodeRepository.findByOrgaTypeAndCode(orgaType, returnCode);

        //return crudentialCode;
    }

}
