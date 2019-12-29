package com.timw.iprwc;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class iprwcApiConfiguration extends Configuration {

    @NotNull
    @Valid
    private DataSourceFactory dataSourceFactory = new DataSourceFactory();

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() { return dataSourceFactory; }

}
