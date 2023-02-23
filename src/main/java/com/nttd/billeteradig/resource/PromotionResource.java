package com.nttd.billeteradig.resource;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.jboss.logging.Logger;

import com.nttd.billeteradig.service.IncrementService;
import com.nttd.billeteradig.service.PromotionService;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;



@Path("/api/promotion")
public class PromotionResource {

   @Inject
   PromotionService promotionService;

   @Inject
   IncrementService incrementService;

  
   @Inject
   Logger logger;

   @GET
   @Path("/redis/{key}")
   @Operation(summary = "Agregando redis del microservicio",description = "Permite agregar redis para este microservicio")
   public Uni<String> get(@PathParam("key") String key){
        logger.info("Iniciando el metodo redis get - Resource.");
        return incrementService.get(key);
   }


   @GET
   @Operation(summary = "Obtener todos los registros de las promociones digitales",description = "Permite obtener todas las promociones digitales")
   public Uni<String> getAllPromotion(){
        logger.info("Iniciando el metodo getAllPromotion - Resource.");
        return promotionService.getAllPromotion();
   }

}
