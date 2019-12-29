package com.timw.iprwc;

import com.timw.iprwc.db.ProductDAO;
import com.timw.iprwc.db.UserDAO;
import com.timw.iprwc.models.Product;
import com.timw.iprwc.models.User;
import com.timw.iprwc.resources.DebugResource;
import com.timw.iprwc.resources.OrderResource;
import com.timw.iprwc.resources.ProductResource;
import com.timw.iprwc.services.AuthenticationService;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;

public class iprwcApiApplication extends Application<iprwcApiConfiguration> {

    private final HibernateBundle<iprwcApiConfiguration> hibernateBundle
            = new HibernateBundle<iprwcApiConfiguration>(Product.class, User.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(iprwcApiConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

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
        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    public void run(final iprwcApiConfiguration configuration,
                    final Environment environment) {

        final UserDAO userDAO = new UserDAO(hibernateBundle.getSessionFactory());
        final ProductDAO productDAO = new ProductDAO(hibernateBundle.getSessionFactory());

        // TODO: implement application
        bulkRegister(environment,
                new DebugResource(userDAO, productDAO),
                new OrderResource(),
                new ProductResource(productDAO)
        );

        // Registreer authenticator
        AuthenticationService authenticationService = new UnitOfWorkAwareProxyFactory(hibernateBundle).create(AuthenticationService.class, UserDAO.class, userDAO);
        environment.jersey().register(new AuthDynamicFeature(
                new BasicCredentialAuthFilter.Builder<User>()
                        .setAuthenticator(authenticationService)
                        .setRealm("SECURITY REALM")
                        .buildAuthFilter()
        ));
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));

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
