package com.fpt.g2.service.impl;

import com.fpt.g2.dto.PloPoDTO;
import com.fpt.g2.dto.PloPoRequestDTO;
import com.fpt.g2.entity.Plo;
import com.fpt.g2.entity.Po;
import com.fpt.g2.entity.PoPlo;
import com.fpt.g2.repository.PloRepository;
import com.fpt.g2.repository.PoPloRepository;
import com.fpt.g2.repository.PoRepository;
import com.fpt.g2.service.PoPloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PoPloServiceImpl implements PoPloService {
    @Autowired
    private PoPloRepository poPloRepository;
    @Autowired
    private PloRepository ploRepository;
    @Autowired
    private PoRepository poRepository;

    @Override
    public PoPlo findByPoIdAndPloId(Long poId, Long ploId) {
        return poPloRepository.findByPoIdAndPloId(poId, ploId);
    }

    @Override
    public void updateMapping(PloPoRequestDTO request) {

        Long cId = request.getCurriculumId();
        for(PloPoDTO pp: request.getPlopos()) {
            List<PoPlo> poPlos = poPloRepository.findByPloName(pp.getPlo(), cId);
            poPloRepository.deleteAll(poPlos);

            List<PoPlo> poPlos1 = new ArrayList<>();
            Plo plo = ploRepository.findByPloNameAndCurriculumId(pp.getPlo(), cId);
            for (String poName : pp.getPos()) {
                Po po = poRepository.findByCurriculumIdAndPOName(cId, poName);
                PoPlo poPlo = new PoPlo();
                poPlo.setPlo(plo);
                poPlo.setPo(po);
                poPlos1.add(poPlo);
            }
            poPloRepository.saveAll(poPlos1);
        }

    }
}
