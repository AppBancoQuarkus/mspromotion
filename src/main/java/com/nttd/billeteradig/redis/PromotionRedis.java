package com.nttd.billeteradig.redis;

import com.nttd.billeteradig.dto.PromotionDayDto;

import io.quarkus.redis.client.RedisClientName;
import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


@ApplicationScoped
public class PromotionRedis {

    private final String my_key = "promo";
    @Inject
    @RedisClientName("promo-day")
    ReactiveRedisDataSource rrds;

    public Uni<PromotionDayDto> get() {
       
            return rrds.value(String.class,PromotionDayDto.class).get(my_key);
       
    }

    public Uni<Void> set(PromotionDayDto promotionDayDto) {     
                 
        return rrds.value(String.class,PromotionDayDto.class)
                   .setex(my_key, 91000, promotionDayDto); 
                
    }
 
      
    
    

}
