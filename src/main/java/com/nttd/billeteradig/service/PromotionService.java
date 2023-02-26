package com.nttd.billeteradig.service;

import com.nttd.billeteradig.dto.ResponseDto;
import com.nttd.billeteradig.entity.PromotionEntity;

import io.smallrye.mutiny.Uni;

public interface PromotionService {

    public Uni<ResponseDto> getTodayPromotion1();
    
    public Uni<ResponseDto> getTodayPromotion();

    public Uni<ResponseDto> addPromotion(PromotionEntity promotionEntity);

    public Uni<ResponseDto> updatePromotion(String id,PromotionEntity promotionEntity);

    public Uni<ResponseDto> deletePromotion(String id);

}
