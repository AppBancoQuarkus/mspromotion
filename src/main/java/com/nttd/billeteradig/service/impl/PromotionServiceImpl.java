package com.nttd.billeteradig.service.impl;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.nttd.billeteradig.service.IncrementService;
import com.nttd.billeteradig.service.PromotionService;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PromotionServiceImpl  implements PromotionService {

    @Inject
    IncrementService  incrementService;

    //@RestClient
    //UserApi userApi;


    @ConfigProperty(name="mensaje.general")
    String mensajeGeneral;
   
    @Override
    public Uni<String> getAllPromotion(){
        return Uni.createFrom().item(mensajeGeneral);
    }

}
