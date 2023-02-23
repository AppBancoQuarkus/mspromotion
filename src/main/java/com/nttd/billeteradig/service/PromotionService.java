package com.nttd.billeteradig.service;

import io.smallrye.mutiny.Uni;

public interface PromotionService {

    public Uni<String> getAllPromotion();

}
