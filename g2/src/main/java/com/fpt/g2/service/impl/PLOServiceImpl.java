package com.fpt.g2.service.impl;

import com.fpt.g2.constant.CommonConstant;
import com.fpt.g2.dto.CurriculumPloDTO;
import com.fpt.g2.entity.Curriculum;
import com.fpt.g2.entity.Plo;
import com.fpt.g2.repository.CurriculumRepository;
import com.fpt.g2.repository.PloRepository;
import com.fpt.g2.service.PLOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PLOServiceImpl implements PLOService {

    @Autowired
    private PloRepository ploRepository;
    @Autowired
    private CurriculumRepository curriculumRepository;

    @Override
    public List<Plo> getPloListByCurriculumId(Long id) {
        return ploRepository.findAllByCurriculumId(id);
    }

    @Override
    public Plo getPLOByCurriculumIdAndPLOId(Long curriculumId, Long ploId) {
        return ploRepository.findByCurriculumIdAndPLOId(curriculumId, ploId);
    }

    @Override
    public String saveOrUpdate(CurriculumPloDTO curriculumPlo) {
        String actionType = "";
        Plo plo;
        Plo ploByName = ploRepository.existsPLOByName(curriculumPlo.getCurriculumId(), curriculumPlo.getName());

        if (curriculumPlo.getId() != null) {
            plo = ploRepository.findByCurriculumIdAndPLOId(curriculumPlo.getCurriculumId(), curriculumPlo.getId());
            actionType = CommonConstant.UPDATE_SUCCESS;
            if (!ploByName.getName().equalsIgnoreCase(plo.getName()) && ploByName != null) {
                return CommonConstant.CODE_EXIST;
            }
        } else {
            actionType = CommonConstant.ADD_SUCCESS;
            if (ploByName != null) {
                return CommonConstant.CODE_EXIST;
            }
            plo = new Plo();
        }
        Curriculum curriculum = curriculumRepository.findCurriculumById(curriculumPlo.getCurriculumId());
        plo.setName(curriculumPlo.getName());
        plo.setDescription(curriculumPlo.getDescription());

        plo.setDeleteFlag(curriculumPlo.getIsActive().equalsIgnoreCase("activate") ? true : false);

        plo.setCurriculum(curriculum);
        ploRepository.save(plo);
        return actionType;
    }

    @Override
    public void deleteCurriculumPLO(Long curriculumId, Long ploId) {
        Plo plo = ploRepository.findByCurriculumIdAndPLOId(curriculumId, ploId);
        ploRepository.delete(plo);
    }
}
