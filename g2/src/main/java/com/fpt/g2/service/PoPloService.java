package com.fpt.g2.service;

import com.fpt.g2.dto.PloPoRequestDTO;
import com.fpt.g2.entity.PoPlo;

public interface PoPloService {
    PoPlo findByPoIdAndPloId(Long poId, Long ploId);

    void updateMapping(PloPoRequestDTO request);
}
