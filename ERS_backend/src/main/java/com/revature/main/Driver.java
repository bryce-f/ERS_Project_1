package com.revature.main;

import com.revature.controller.AuthenticationController;
import com.revature.controller.Controller;
import com.revature.controller.ExceptionController;
import com.revature.controller.ReimbursementController;
import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Driver {

    public static Logger LOGGER = LoggerFactory.getLogger(Driver.class);

    public static void main(String[] args) {
        Javalin app = Javalin.create((config) -> {
            config.enableCorsForAllOrigins();
//            config.enableCorsForOrigin("http://localhost:8081");
        });
        // This will execute before every single request
        app.before((ctx) -> {
            LOGGER.info(ctx.method() + " request received for " + ctx.path());
        });


        map(app, new AuthenticationController(), new ReimbursementController(), new ExceptionController());

        app.start(8081);
    }

    public static void map(Javalin app, Controller... controllers) {
        for (Controller c : controllers) {
            c.mapEndpoints(app);
        }
    }
}
