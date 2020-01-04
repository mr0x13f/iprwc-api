package com.timw.iprwc;

import com.github.toastshaman.dropwizard.auth.jwt.JwtAuthFilter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.timw.iprwc.db.*;
import com.timw.iprwc.models.*;
import com.timw.iprwc.resources.*;
import com.timw.iprwc.services.*;
import io.dropwizard.Application;
import io.dropwizard.auth.*;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.jwt.consumer.JwtContext;
import org.jose4j.keys.HmacKey;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

public class iprwcApiApplication extends Application<iprwcApiConfiguration> {

    private final HibernateBundle<iprwcApiConfiguration> hibernateBundle
            = new HibernateBundle<iprwcApiConfiguration>(Product.class, User.class, CartItem.class, Order.class, WishlistItem.class) {
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

        // DAOs
        final UserDAO userDAO = new UserDAO(hibernateBundle.getSessionFactory());
        final ProductDAO productDAO = new ProductDAO(hibernateBundle.getSessionFactory());
        final CartDAO cartDAO = new CartDAO(hibernateBundle.getSessionFactory());
        final OrderDAO orderDAO = new OrderDAO(hibernateBundle.getSessionFactory());
        final WishlistDAO wishlistDAO = new WishlistDAO(hibernateBundle.getSessionFactory());

        // Services
        UserService.setDAO(userDAO);
        CartService.setDAO(cartDAO, productDAO, orderDAO);
        WishlistService.setDAO(wishlistDAO, productDAO);

        // Resources
        bulkRegister(environment,
                new UserResource(userDAO),
                new ProductResource(productDAO),
                new CartResource(cartDAO),
                new OrderResource(orderDAO),
                new WishlistResource(wishlistDAO)
        );

        if (configuration.isEnableDebugResource()) {
            environment.jersey().register(new DebugResource(userDAO, productDAO));
        }

        // Authenticator
        BasicAuthenticationService basicAuthenticationService = new UnitOfWorkAwareProxyFactory(hibernateBundle).create(BasicAuthenticationService.class, UserDAO.class, userDAO);
        BasicCredentialAuthFilter<BasicAuth> basicFilter = new BasicCredentialAuthFilter.Builder<BasicAuth>().setAuthenticator(basicAuthenticationService).setPrefix("Basic").buildAuthFilter();

        JwtAuthenticationService jwtAuthenticationService = new UnitOfWorkAwareProxyFactory(hibernateBundle)
                .create(JwtAuthenticationService.class, UserDAO.class, userDAO);
        final JwtConsumer consumer = new JwtConsumerBuilder().setAllowedClockSkewInSeconds(300).setRequireSubject()
                .setVerificationKey(new HmacKey(JwtAuthenticationService.JWT_SECRET_KEY)).build();
        AuthFilter<JwtContext, User> jwtFilter = new JwtAuthFilter.Builder<User>().setJwtConsumer(consumer).setRealm("realm").setPrefix("Bearer")
                .setAuthenticator(jwtAuthenticationService).setAuthorizer(new AuthorizationService()).buildAuthFilter();

        final PolymorphicAuthDynamicFeature feature = new PolymorphicAuthDynamicFeature<>(
                ImmutableMap.of(BasicAuth.class, basicFilter, User.class, jwtFilter));
        final AbstractBinder binder = new PolymorphicAuthValueFactoryProvider.Binder<>(
                ImmutableSet.of(BasicAuth.class, User.class));

        environment.jersey().register(feature);
        environment.jersey().register(binder);
        environment.jersey().register(RolesAllowedDynamicFeature.class);;

        // CORS headers
        final FilterRegistration.Dynamic cors = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "*");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

    }

    private static void bulkRegister(Environment environment, Object ... resources) {

        for (Object resource : resources) {
            environment.jersey().register(resource);
        }

    }

}
