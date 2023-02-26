package com.nttd.billeteradig.redis;


import com.nttd.billeteradig.entity.PromotionEntity;

import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.quarkus.redis.datasource.value.ReactiveValueCommands;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PromotionRedis {

    private final ReactiveValueCommands<String, PromotionEntity> commands;

    public PromotionRedis(ReactiveRedisDataSource rds) {
        commands = rds.value(PromotionEntity.class);
    }

    public Uni<PromotionEntity> get(String key) {
        return commands.get(key);
    }

    public Uni<Void> setUni(String key, PromotionEntity promotionEntity) {
        return commands.setex(key, 5, promotionEntity);
    }
    
}
