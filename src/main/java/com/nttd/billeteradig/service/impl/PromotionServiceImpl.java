package com.nttd.billeteradig.service.impl;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.nttd.billeteradig.dto.ResponseDto;
import com.nttd.billeteradig.entity.PromotionEntity;
import com.nttd.billeteradig.redis.PromotionRedis;
import com.nttd.billeteradig.service.PromotionService;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class PromotionServiceImpl implements PromotionService {


    @Inject
    PromotionRedis promotionRedis;

    @ConfigProperty(name = "mensaje.general")
    String mensajeGeneral;

    @ConfigProperty(name = "exception.general")
    String excepcionGeneral;

    @ConfigProperty(name = "valor.activo")
    String valorActivo;

    @ConfigProperty(name = "valor.inactivo")
    String valorInactivo;

    @Override
    public Uni<ResponseDto> getTodayPromotionRedis() {
        try{
                return promotionRedis.get().map(objredis -> {
                        if (objredis != null)
                           return objredis.getResponse();                        
                        return null;
                    }).flatMap((respuesta)->{
                        if(respuesta == null){
                            Uni<List<PromotionEntity>> promoactivo = PromotionEntity
                            .list("state", valorActivo);
                            return promoactivo.map(result -> {
                                    List<PromotionEntity> lista = new ArrayList<>();
                                    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                                    try {
                                        Date today = formato.parse(formato.format(new Date()));
                                        for (PromotionEntity promo : result) {
                                            Date startdate = formato.parse(promo.getStartdate());
                                            Date finaldate = formato.parse(promo.getFinaldate());
                                            if (startdate.getTime() <= today.getTime() && finaldate.getTime() >= today.getTime())
                                                lista.add(promo);
                                        }
                                    } catch (Exception e) {
                                        System.out.println("::"+e.getMessage());;
                                    }
                                return lista;
                            });
                        }else return Uni.createFrom().item(respuesta);

                    }).flatMap(rsp->{
                        return promotionRedis.set(new ResponseDto(Response.Status.OK.getStatusCode(), mensajeGeneral, rsp)).flatMap(r->{
                                    return promotionRedis.get();
                                }); 
                    });

        } catch (Exception e) {
            return  Uni.createFrom().item( new ResponseDto(Response.Status.BAD_REQUEST.getStatusCode(),
                                    excepcionGeneral, e.getMessage()));
        }

    }

    @Override
    public Uni<ResponseDto> getTodayPromotion() {

        Uni<List<PromotionEntity>> promoactivo = PromotionEntity
                .list("state", valorActivo);
        return promoactivo.map(res -> {
            List<PromotionEntity> listado = new ArrayList<>();
            if (res.size() > 0)
                listado = res;
            return listado;
        }).map(obj -> {
            if (obj.size() == 0)
                return new ResponseDto(Response.Status.BAD_REQUEST.getStatusCode(), excepcionGeneral, "");
            List<PromotionEntity> lista = new ArrayList<>();
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date today = formato.parse(formato.format(new Date()));
                for (PromotionEntity promo : obj) {
                    Date startdate = formato.parse(promo.getStartdate());
                    Date finaldate = formato.parse(promo.getFinaldate());
                    if (startdate.getTime() <= today.getTime() && finaldate.getTime() >= today.getTime())
                        lista.add(promo);
                }
            } catch (Exception e) {
                return new ResponseDto(Response.Status.BAD_REQUEST.getStatusCode(),
                        excepcionGeneral, e.getMessage());
            }

            return new ResponseDto(Response.Status.OK.getStatusCode(), mensajeGeneral, lista);

        }).onFailure().recoverWithItem(
                ex -> new ResponseDto(Response.Status.BAD_REQUEST.getStatusCode(), excepcionGeneral, ex.getMessage()));

    }

    @Override
    public Uni<ResponseDto> addPromotion(PromotionEntity promotionEntity) {
        promotionEntity.setState(valorActivo);
        return promotionEntity.persist().map(obj -> {
            return new ResponseDto(Response.Status.OK.getStatusCode(),
                    mensajeGeneral, obj);
        }).onFailure()
                .recoverWithItem(ex -> new ResponseDto(Response.Status.BAD_REQUEST.getStatusCode(),
                        excepcionGeneral,
                        ex.getMessage()));
    }

    @Override
    public Uni<ResponseDto> updatePromotion(String id, PromotionEntity promotionEntity) {

        Uni<PromotionEntity> promoreactive = PromotionEntity.findById(new ObjectId(id));

        return promoreactive.flatMap(obj -> {

            obj.setTitle(promotionEntity.getTitle());
            obj.setStorename(promotionEntity.getStorename());
            obj.setAmountpay(promotionEntity.getAmountpay());
            obj.setPorcentajedescuento(promotionEntity.getPorcentajedescuento());
            obj.setAmountorigin(promotionEntity.getAmountorigin());
            obj.setDescriptionpromotion(promotionEntity.getDescriptionpromotion());
            obj.setStartdate(promotionEntity.getStartdate());
            obj.setFinaldate(promotionEntity.getFinaldate());
            obj.setStock(promotionEntity.getStock());
            obj.setImageurl(promotionEntity.getImageurl());
            return obj.persistOrUpdate().map((result) -> {
                return new ResponseDto(Response.Status.OK.getStatusCode(),
                        mensajeGeneral, result);
            }).onFailure().recoverWithItem(ex -> new ResponseDto(Response.Status.BAD_REQUEST.getStatusCode(),
                    excepcionGeneral,
                    ex.getMessage()));
        });

    }

    @Override
    public Uni<ResponseDto> deletePromotion(String id) {

        Uni<PromotionEntity> promoreactive = PromotionEntity.findById(new ObjectId(id));

        return promoreactive.flatMap(obj -> {
            obj.setState(valorInactivo);
            return obj.persistOrUpdate().map((result) -> {
                return new ResponseDto(Response.Status.OK.getStatusCode(),
                        mensajeGeneral, result);
            }).onFailure().recoverWithItem(ex -> new ResponseDto(Response.Status.BAD_REQUEST.getStatusCode(),
                    excepcionGeneral,
                    ex.getMessage()));
        });

    }

}
