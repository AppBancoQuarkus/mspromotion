package com.nttd.billeteradig.resource;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.jboss.logging.Logger;

import com.nttd.billeteradig.dto.PromotionDayDto;
import com.nttd.billeteradig.dto.ResponseDto;
import com.nttd.billeteradig.entity.PromotionEntity;
import com.nttd.billeteradig.redis.PromotionRedis;
import com.nttd.billeteradig.service.PromotionService;

import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;



@Path("/api/promotion")
public class PromotionResource {

   @Inject
   PromotionService promotionService;

   @Inject
   PromotionRedis promotionRedis;

   @Inject
    ReactiveRedisDataSource reactiveRedisDataSource;

   @Inject
   Logger logger;

    @GET
    @Path("/redis/all")
    public Uni<PromotionDayDto> getAllRedis(){
        return promotionRedis.get();
        // return this.reactiveRedisDataSource
        // .value(String.class, PromotionDayDto.class)
        // .set("promo", new PromotionDayDto("ajjajaj"))
        // .flatMap(r -> {
        //     return this.reactiveRedisDataSource
        //             .value(String.class, PromotionDayDto.class)
        //             .get("promo");
        // });
    }

    @POST
    @Path("/redis/registro")
    public Uni<PromotionDayDto> setRegistro(PromotionDayDto promotionDayDto){
        return promotionRedis.set(promotionDayDto).flatMap(r->{
            return promotionRedis.get();
        });
    }

    /* Buscar la promocion del dia en la billetera digital */
    @GET
    @Path("/today1")
    @Operation(summary = "Obtener todas las promociones digitales de hoy",description = "Permite obtener todas las promociones digitales de hoy")
    public Uni<Object> getTodayPromotion1(){
         logger.info("Iniciando el metodo getTodayPromotion1 - Resource.");
         return promotionService.getTodayPromotion1();
    }

  /* Buscar la promocion del dia en la billetera digital */
   @GET
   @Path("/today")
   @Operation(summary = "Obtener todas las promociones digitales de hoy",description = "Permite obtener todas las promociones digitales de hoy")
   public Uni<ResponseDto> getTodayPromotion(){
        logger.info("Iniciando el metodo getTodayPromotion - Resource.");
        return promotionService.getTodayPromotion();
   }

    /* Crear la promocion de billetera digital */
    @POST
    @Operation(summary = "Crear la promocion de billetera digital",description = "Permite registrar la promocion de billetera digital")
    public Uni<ResponseDto> addPromotion(PromotionEntity promotionEntity){
        logger.info("Iniciando el metodo de Crear la promocion de billetera digital - Resource.");
        return promotionService.addPromotion(promotionEntity);
    } 

     /* Actualizar la promocion de billetera digital */
     @PUT
     @Path("{id}")
     @Operation(summary = "Actualizar la promocion de billetera digital",description = "Permite actualizar la promocion de billetera digital")
     public Uni<ResponseDto> updatePromotion(@PathParam("id") String id,PromotionEntity promotionEntity){
         logger.info("Iniciando el metodo de Actualizacion de la promocion de billetera digital - Resource.");
         return promotionService.updatePromotion(id,promotionEntity);
     } 

      /* Eliminar la promocion de billetera digital */
    @DELETE
    @Path("{id}")
    @Operation(summary = "Eliminar la promocion de billetera digital",description = "Permite eliminar la promocion de billetera digital")
    public Uni<ResponseDto> deletePromotion(@PathParam("id") String id){
        logger.info("Iniciando el metodo de Eliminar la promocion de billetera digital - Resource.");
        return promotionService.deletePromotion(id);
    } 


}
