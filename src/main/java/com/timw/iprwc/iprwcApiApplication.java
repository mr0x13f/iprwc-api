package com.timw.iprwc;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class iprwcApiApplication extends Application<iprwcApiConfiguration> {

    public static void main(final String[] args) throws Exception {
        new iprwcApiApplication().run(args);
    }

    @Override
    public String getName() {
        return "iprwc-api";
    }

    @Override
    public void initialize(final Bootstrap<iprwcApiConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final iprwcApiConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
    }

}
