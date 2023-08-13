package com.nagasai.springbootlibrary.config;

import com.nagasai.springbootlibrary.entity.Book;
import com.nagasai.springbootlibrary.entity.Message;
import com.nagasai.springbootlibrary.entity.Review;
import com.nagasai.springbootlibrary.entity.Book;
import com.nagasai.springbootlibrary.entity.Message;
import com.nagasai.springbootlibrary.entity.Review;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    private String theAllowedOrgins = "http://react-library-bucket.s3-website.ap-south-1.amazonaws.com"

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config,
                                                     CorsRegistry cors) {
        HttpMethod[] theUnsupportedActions ={HttpMethod.POST,
                           HttpMethod.PATCH,
                           HttpMethod.PUT,
                           HttpMethod.DELETE};

        config.exposeIdsFor(Book.class);
        config.exposeIdsFor(Review.class);
        config.exposeIdsFor(Message.class);

        disableHttpMethods(Book.class,config,theUnsupportedActions);
        disableHttpMethods(Review.class,config,theUnsupportedActions);
        disableHttpMethods(Message.class,config,theUnsupportedActions);

        /* Configure CORS Mapping */
        cors.addMapping(config.getBasePath() +"/**")
                .allowedOrigins(theAllowedOrgins);
    }


    private void disableHttpMethods(Class theClass,
                                    RepositoryRestConfiguration config,
                                    HttpMethod[] theUnsupportedActions){
     config.getExposureConfiguration()
             .forDomainType(theClass)
             .withItemExposure((metdata,httpMethods) ->
                     httpMethods.disable(theUnsupportedActions))
             .withCollectionExposure((metdata,httpMethods) ->
                     httpMethods.disable(theUnsupportedActions));
    }
}
