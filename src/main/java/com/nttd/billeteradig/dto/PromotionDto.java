package com.nttd.billeteradig.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
public class PromotionDto {
    
    private boolean respuesta;
    private String mensaje;
    private String iduser;

    


    public PromotionDto() {
    }


    public PromotionDto(boolean respuesta) {
        this.respuesta = respuesta;
    }    

    
}
