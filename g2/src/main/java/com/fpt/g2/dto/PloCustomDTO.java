package com.fpt.g2.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class PloCustomDTO {
    private Object plo;

    private List<String> pos;
}
