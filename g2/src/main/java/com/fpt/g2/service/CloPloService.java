package com.fpt.g2.service;

import com.fpt.g2.dto.CloPloListDTO;
import com.fpt.g2.dto.CloPloResponseDTO;

import java.util.List;

public interface CloPloService {
    List<CloPloResponseDTO> getCloPLoMapping(Long syllabusId);

    void updateCloPloMapping(CloPloListDTO request);
}
