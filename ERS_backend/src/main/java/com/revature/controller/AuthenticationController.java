package com.revature.controller;

import com.revature.dto.LoginDTO;
import com.revature.model.User;
import com.revature.service.JWTService;
import com.revature.service.UserService;
import io.javalin.Javalin;
import io.javalin.http.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthenticationController implements Controller {
    Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);
    private UserService userService;
    private JWTService jwtService;

    public AuthenticationController() {
        this.userService = new UserService();
        this.jwtService = JWTService.getInstance();
    }

    private Handler login = (ctx) -> {
        LOGGER.warn("Login endpoint invoked");

        LoginDTO loginInfo = ctx.bodyAsClass(LoginDTO.class);

        User user = userService.login(loginInfo.getUsername(), loginInfo.getPassword());
        LOGGER.warn("User login successfully");

        // If FailedLoginException did not occur, then we will move on to the subsequent lines of code

        String jwt = this.jwtService.createJWT(user);
        System.out.println(jwt);
        // Chrome has some security settings that prevent the frontend from accessing the
        // custom header "Token" without setting Access-Control-Expose-Headers to a value of *
        ctx.header("Access-Control-Expose-Headers", "*");

        // Send the JSON web token after logging in back to the client (frontend / postman)
        ctx.header("Token", jwt);
        ctx.json(user);
    };

    @Override
    public void mapEndpoints(Javalin app) {
        app.post("/login", login);
    }
}
