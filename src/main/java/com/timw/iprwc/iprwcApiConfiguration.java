package com.timw.iprwc;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.timw.iprwc.services.DatabaseService;
import io.dropwizard.Configuration;

import javax.validation.constraints.NotEmpty;

public class iprwcApiConfiguration extends Configuration {

    private String databaseUrl = "";

    @JsonProperty
    public void setDatabaseUrl(String databaseUrl) {
        DatabaseService.setDatabaseUrl(databaseUrl);
    }

}
