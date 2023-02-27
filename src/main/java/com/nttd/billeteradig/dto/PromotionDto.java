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
    
    private String title;
    private String storename;
    private double amountpay;
    private double porcentajedescuento;
    private double amountorigin;
    private String descriptionpromotion;
    private String startdate;
    private String finaldate;    
    private int stock;
    private String imageurl;
    private String state;

    


    public PromotionDto() {
    }


}
