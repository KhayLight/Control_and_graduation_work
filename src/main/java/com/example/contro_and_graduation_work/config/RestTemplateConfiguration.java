package com.example.contro_and_graduation_work.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class RestTemplateConfiguration {

    /*@Value("${api.key}")
    private String apiKey;*/
    /*@Value("${content.type}")
    private String contentType;*/

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /*@Bean(name = "apiRestTemplate")
    public RestTemplate apiRestTemplate()
            throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();

        // Connect timeout
        clientHttpRequestFactory.setConnectTimeout(60000);

        *//* Read timeout
        clientHttpRequestFactory.setReadTimeout(60000);*//*

        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);

        // Interceptor section
        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        if (CollectionUtils.isEmpty(interceptors)) {
            interceptors = new ArrayList<ClientHttpRequestInterceptor>();
        }
        interceptors.add(new HttpClientRequestInterceptor("X-API-KEY", apiKey));
        interceptors.add(new HttpClientRequestInterceptor("Content-Type", contentType));
        restTemplate.setInterceptors(interceptors);

        return restTemplate;
    }*/

}
