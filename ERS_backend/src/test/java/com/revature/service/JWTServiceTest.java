package com.revature.service;
import com.revature.model.User;
import io.javalin.http.UnauthorizedResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.FileNotFoundException;
import java.sql.SQLException;


public class JWTServiceTest {

    private JWTService jwtService = new JWTService();

    @Test
    public void testCreateValidJwt() throws SQLException, FileNotFoundException {
        String jwt = jwtService.createJWT(new User(1, "admin", "password", 2));

        Assertions.assertEquals(141, jwt.length());
    }

    @Test
    public void testCreatesDifferentJwtsForDifferentUsers() throws SQLException, FileNotFoundException {

        String jwt1 = jwtService.createJWT(new User(1, "admin", "password1", 2));
        String jwt2 = jwtService.createJWT(new User(2, "user", "password2", 1));


        Assertions.assertNotEquals(jwt1, jwt2);
    }

    @Test
    public void testParseValidJWT() throws SQLException, FileNotFoundException {
        User user = new User(1, "admin", "password", 2);
        String jwt = jwtService.createJWT(user);

        Jws<Claims> token = jwtService.parseJwt(jwt);

        String username = token.getBody().getSubject();
        Object id = token.getBody().get("user_id");
        Object role = token.getBody().get("user_role");

        Assertions.assertEquals(user.getId(), id);
        Assertions.assertEquals(user.getUsername(), username);
        Assertions.assertEquals(user.getUserRole(), role);
    }

    @Test
    public void testParseInvalidJWT() throws SQLException, FileNotFoundException {
        String jwt = jwtService.createJWT(new User(1, "admin", "password", 2));


        String invalidJwt = jwt.substring(0, jwt.length() - 2) + "@";

        Assertions.assertThrows(UnauthorizedResponse.class, () ->{
            jwtService.parseJwt(invalidJwt);
        });
    }
}