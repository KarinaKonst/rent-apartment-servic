package com.example.rentapartment.service;

import com.example.rentapartment.entity.ClientEntity;
import com.example.rentapartment.repository.ClientRepository;
import com.example.rentapartment.service.impl.ApartmentRegistrationServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@EnableScheduling
@Service
public class SchedulerService {
    private static final Logger logger = LoggerFactory.getLogger(SchedulerService.class);

    @Autowired
    private ClientRepository clientRepository;

    @Scheduled(fixedDelay = 60000)
    public void checkValidUserToken() {
        logger.info("rent-apartment :checkValidUserToken -> started");
        List<ClientEntity> clientEntities = clientRepository.getClientEntitiesBySessionTokenIsNotNull();
        for (ClientEntity a : clientEntities) {
            if (parsingTokenString(a.getSessionToken()).isBefore(LocalDateTime.now())) {
                a.setSessionToken(null);
                clientRepository.save(a);
                logger.info("rent-apartment :checkValidUserToken -> Удален просроченный токен у пользователя: " +
                     a.getEmail()) ;
            }
        }
        logger.info("rent-apartment :checkValidUserToken -> end");
    }

    private LocalDateTime parsingTokenString(String s) {
        int index = s.indexOf("|");
        String substring = s.substring(index+1);
        return LocalDateTime.parse(substring);
    }
}
