package com.fpt.g2.service.impl;

import com.fpt.g2.constant.CommonConstant;
import com.fpt.g2.dto.CurriculumPoRequestDTO;
import com.fpt.g2.entity.Curriculum;
import com.fpt.g2.entity.Po;
import com.fpt.g2.repository.CurriculumRepository;
import com.fpt.g2.repository.PoRepository;
import com.fpt.g2.service.POService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class POServiceImpl implements POService {
    @Autowired
    private PoRepository poRepository;

    @Autowired
    private CurriculumRepository curriculumRepository;

    @Override
    public List<Po> getPoListByCurriculumId(Long id) {
        return poRepository.findAllByCurriculumId(id);
    }

    @Override
    public Po getPoByCurriculumIdAndPOId(Long curriculumId, Long poId) {
        return poRepository.findByCurriculumIdAndPOId(curriculumId, poId);
    }

    @Override
    public String saveOrUpdate(CurriculumPoRequestDTO curriculumPo) {
        String actionType = "";
        Po po;
        Po poByName = poRepository.existsPOByName(curriculumPo.getCurriculumId(), curriculumPo.getName());

        if (curriculumPo.getId() != null) {
            po = poRepository.findByCurriculumIdAndPOId(curriculumPo.getCurriculumId(), curriculumPo.getId());
            actionType = CommonConstant.UPDATE_SUCCESS;
            if (!poByName.getName().equalsIgnoreCase(po.getName()) && poByName != null) {
                return CommonConstant.CODE_EXIST;
            }
        } else {
            actionType = CommonConstant.ADD_SUCCESS;
            if (poByName != null) {
                return CommonConstant.CODE_EXIST;
            }
            po = new Po();
        }
        Curriculum curriculum = curriculumRepository.findCurriculumById(curriculumPo.getCurriculumId());
        po.setName(curriculumPo.getName());
        po.setDescription(curriculumPo.getDescription());

        po.setDeleteFlag(curriculumPo.getIsActive().equalsIgnoreCase("activate") ? true : false);

        po.setCurriculum(curriculum);
        poRepository.save(po);
        return actionType;
    }

    @Override
    public void deleteCurriculumPO(Long curriculumId, Long ploId) {
        Po po = poRepository.findByCurriculumIdAndPOId(curriculumId, ploId);
        poRepository.delete(po);
    }
}
