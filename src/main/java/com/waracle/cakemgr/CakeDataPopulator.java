package com.waracle.cakemgr;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakemgr.model.CakeModel;
import com.waracle.cakemgr.repository.CakeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.Set;

@Component
public class CakeDataPopulator {

    private static final Logger LOG = LoggerFactory.getLogger(CakeDataPopulator.class);

    @Value("${cake.data.url}")
    private String cakeDataUrl;

    private final CakeRepository cakeRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public CakeDataPopulator(CakeRepository cakeRepository, ObjectMapper objectMapper) {
        this.cakeRepository = cakeRepository;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void populateFromExternalURL() {
        try {
            Set<CakeModel> cakes = objectMapper.readValue(new URL(cakeDataUrl), new TypeReference<Set<CakeModel>>() {});

            cakeRepository.saveAll(cakes);
        } catch (Exception e) {
            LOG.warn("Error occurred while parsing external JSON cake data.  Cake data will not be pre-populated", e);
        }
    }
}
