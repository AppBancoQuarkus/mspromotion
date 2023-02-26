package com.nttd.billeteradig.entity;



import org.bson.codecs.pojo.annotations.BsonProperty;

import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MongoEntity(collection="DWMPROMOTION")
public class PromotionEntity extends ReactivePanacheMongoEntity {

    private String title;
    private String storename;
    private double amountpay;
    @BsonProperty("discountrate")
    private double porcentajedescuento;
    private double amountorigin;
    private String descriptionpromotion;
    private String startdate;
    private String finaldate;    
    private int stock;
    private String imageurl;
    private String state;

    public PromotionEntity() {
    }

}
