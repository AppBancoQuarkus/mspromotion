package com.nttd.billeteradig.resource;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.jboss.logging.Logger;

import com.nttd.billeteradig.dto.ResponseDto;
import com.nttd.billeteradig.entity.PromotionEntity;
import com.nttd.billeteradig.service.PromotionService;

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
   Logger logger;

    /* Buscar la promocion del dia en la billetera digital */
    @GET
    @Path("/redis/today")
    @Operation(summary = "Obtener todas las promociones digitales de hoy con Redis",description = "Permite obtener todas las promociones digitales de hoy con Redis")
    public Uni<ResponseDto> getTodayPromotionRedis(){
         logger.info("Iniciando el metodo getTodayPromotionRedis con Redis - Resource.");
         return promotionService.getTodayPromotionRedis();
    }

  /* Buscar la promocion del dia en la billetera digital */
   @GET
   @Path("/today")
   @Operation(summary = "Obtener todas las promociones digitales de hoy - Sin Redis",description = "Permite obtener todas las promociones digitales de hoy - Sin Redis")
   public Uni<ResponseDto> getTodayPromotion(){
        logger.info("Iniciando el metodo getTodayPromotion Sin Redis - Resource.");
        return promotionService.getTodayPromotion();
   }

    /* Crear la promocion de billetera digital */
    @POST
    @Operation(summary = "Crear la promocion de billetera digital",description = "Permite registrar la promocion de billetera digital")
    public Uni<ResponseDto> addPromotion(PromotionEntity promotionEntity){
        logger.info("Iniciando el metodo  addPromotion - Resource.");
        return promotionService.addPromotion(promotionEntity);
    } 

     /* Actualizar la promocion de billetera digital */
     @PUT
     @Path("{id}")
     @Operation(summary = "Actualizar la promocion de billetera digital",description = "Permite actualizar la promocion de billetera digital")
     public Uni<ResponseDto> updatePromotion(@PathParam("id") String id,PromotionEntity promotionEntity){
         logger.info("Iniciando el metodo updatePromotion - Resource.");
         return promotionService.updatePromotion(id,promotionEntity);
     } 

      /* Eliminar la promocion de billetera digital */
    @DELETE
    @Path("{id}")
    @Operation(summary = "Eliminar la promocion de billetera digital",description = "Permite eliminar la promocion de billetera digital")
    public Uni<ResponseDto> deletePromotion(@PathParam("id") String id){
        logger.info("Iniciando el metodo deletePromotion - Resource.");
        return promotionService.deletePromotion(id);
    } 


}
