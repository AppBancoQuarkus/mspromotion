package com.nttd.billeteradig.redis;

import com.nttd.billeteradig.dto.ResponseDto;

import io.quarkus.redis.client.RedisClientName;
import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import org.eclipse.microprofile.config.inject.ConfigProperty;


@Singleton
public class PromotionRedis {


    @Inject
    @RedisClientName("promo-day")
    ReactiveRedisDataSource rrds;


    @ConfigProperty(name = "valor.mykey.redis")
    String valormykey;

   
    

    public Uni<ResponseDto> get() {
       
            return rrds.value(String.class,ResponseDto.class).get(valormykey);
       
    }

    public Uni<Void> set(ResponseDto responseDto) {     
                 
        return rrds.value(String.class,ResponseDto.class)
                   .setex(valormykey, 86400, responseDto); 
                
    }
 
      
    
    

}
