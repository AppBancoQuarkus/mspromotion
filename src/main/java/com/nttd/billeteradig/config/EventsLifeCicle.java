package com.nttd.billeteradig.config;

import org.jboss.logging.Logger;


import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

@ApplicationScoped
public class EventsLifeCicle {

    @Inject
    Logger logger;

    void onStart(@Observes StartupEvent startupEvent){
        logger.info("...:: Microservice Promotion is initializing ::......... ");
    }

    void onStop(@Observes ShutdownEvent shutdownEvent){
        logger.info("...:: Microservice Promotion is stopping ::......... ");
    }
}
