package com.bill99.mam.platform.remote.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import retrofit2.Retrofit;

import com.bill99.mam.platform.remote.api.IHatService;
@Configuration
public class RemoteServieConfig {
    @Resource(name="hatRestAdapter")
    private Retrofit adapter;
    @Bean
    public IHatService getHatService(){
        return adapter.create(IHatService.class);
    }
}
