package com.timw.iprwc;

import com.timw.iprwc.resources.DebugResource;
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
        bulkRegister(environment,
                new DebugResource()
        );

    }

    /** Register API resources in bulk.
     *
     * @param environment The DropWizard environment to register the resources to
     * @param resources The resources to register
     *
     * @version 11/10/2019
     * @author Tim W
     */
    private static void bulkRegister(Environment environment, Object ... resources) {

        // Register alle meegegeven resources
        for (Object resource : resources) {
            environment.jersey().register(resource);
        }

    }

}
