package com.fpt.g2.service.impl;

import com.fpt.g2.dto.CloPloDto;
import com.fpt.g2.dto.CloPloListDTO;
import com.fpt.g2.dto.CloPloResponseDTO;
import com.fpt.g2.dto.PloCloDTO;
import com.fpt.g2.entity.Clo;
import com.fpt.g2.entity.CloPlo;
import com.fpt.g2.entity.Curriculum;
import com.fpt.g2.entity.Plo;
import com.fpt.g2.repository.CloPloRepository;
import com.fpt.g2.repository.CloRepository;
import com.fpt.g2.repository.PloRepository;
import com.fpt.g2.service.CloPloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CloPloServiceImpl implements CloPloService {

    @Autowired
    private CloPloRepository cloPloRepository;

    @Autowired
    private PloRepository ploRepository;

    @Autowired
    private CloRepository cloRepository;

    @Override
    public List<CloPloResponseDTO> getCloPLoMapping(Long syllabusId) {
        List<CloPloResponseDTO> responses = new ArrayList<>();
        List<Curriculum> curricula = cloPloRepository.getCurriculumInCloPloMapping(syllabusId);

        for(Curriculum curriculum : curricula){
            CloPloResponseDTO response = new CloPloResponseDTO();
            response.setSyllabusId(syllabusId);
            response.setCurriculum(curriculum);

            List<PloCloDTO> ploCloDTOs = new ArrayList<>();
            List<Plo> plos = ploRepository.findAllByCurriculumId(curriculum.getId());
            for(Plo plo : plos){
                PloCloDTO ploCloDTO = new PloCloDTO();
                ploCloDTO.setPlo(plo);
                List<Clo> clos = cloPloRepository.getClosByPloAndSyllabus(syllabusId, plo.getId());
                List<String> cloCodes = new ArrayList<>();
                for(Clo clo : clos){
                    cloCodes.add(clo.getCode());
                }
                ploCloDTO.setClos(cloCodes);

                ploCloDTOs.add(ploCloDTO);
            }
            response.setPloClos(ploCloDTOs);

            responses.add(response);
        }

        if(responses.size() > 0){
            return responses;
        }
        return null;
    }

    @Override
    public void updateCloPloMapping(CloPloListDTO request) {
        Long syllabusId = request.getSyllabusId();
        for(CloPloDto dto : request.getCloPloList()){
            List<CloPlo> cloPlos = cloPloRepository.getMappingByPloAndSyllabus(syllabusId, dto.getPloId());
            cloPloRepository.deleteAll(cloPlos);

            List<CloPlo> cloPloList = new ArrayList<>();
            Plo plo = ploRepository.findPloById(dto.getPloId());
            for(Long cloId : dto.getClos()){
                CloPlo cloPlo = new CloPlo();
                Clo clo = cloRepository.findCloById(cloId);
                cloPlo.setPlo(plo);
                cloPlo.setClo(clo);

                cloPloList.add(cloPlo);
            }
            cloPloRepository.saveAll(cloPloList);
        }
    }
}
