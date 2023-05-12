package com.fpt.g2.dto;

import com.fpt.g2.entity.Clo;
import com.fpt.g2.entity.Plo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PloCloDTO {
    private Plo plo;
    private List<String> clos;
}
