package com.timw.iprwc;

import com.timw.iprwc.resources.DebugResource;
import com.timw.iprwc.resources.OrderResource;
import com.timw.iprwc.resources.ProductResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;

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
        bootstrap.setConfigurationSourceProvider(
                new ResourceConfigurationSourceProvider());
    }

    @Override
    public void run(final iprwcApiConfiguration configuration,
                    final Environment environment) {

        // TODO: implement application
        bulkRegister(environment,
                new DebugResource(),
                new OrderResource(),
                new ProductResource()
        );

        // Registreer authenticator
//        environment.jersey().register(new AuthDynamicFeature(
//                new BasicCredentialAuthFilter.Builder<User>()
//                        .setAuthenticator(new AuthenticationService())
//                        .setRealm("SECURITY REALM")
//                        .buildAuthFilter()
//        ));
//        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));

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
