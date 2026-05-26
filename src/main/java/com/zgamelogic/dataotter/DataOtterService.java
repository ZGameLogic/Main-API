package com.zgamelogic.dataotter;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class DataOtterService {
    private final RestClient restClient;

    public DataOtterService() {
        restClient = RestClient.builder()
            .baseUrl("http://monitoring.zgamelogic.com:8080")
            .build();
    }

    @Cacheable(cacheManager = "data-otter-cache", cacheNames = "data otter applications")
    public DataOtterApplication getDataOtterApplication(long applicationId){
        return restClient.get()
            .uri("/applications/" + applicationId + "?include-status=true")
            .retrieve()
            .body(DataOtterApplication.class);
    }
}
