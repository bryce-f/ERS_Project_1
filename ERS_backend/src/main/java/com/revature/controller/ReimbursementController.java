package com.revature.controller;

import com.revature.dto.AddReimbursementDTO;
import com.revature.dto.ResponseReimbursementDTO;
import com.revature.dto.UpdateReimbursementDTO;
import com.revature.dto.UpdateReimbursementStatusDTO;
import com.revature.exception.ImageNotFoundException;
import com.revature.model.Reimbursement;
import com.revature.service.JWTService;
import com.revature.service.ReimbursementService;
import com.revature.service.UserService;

import io.javalin.Javalin;
import io.javalin.http.Handler;
import io.javalin.http.UnauthorizedResponse;
import io.javalin.http.UploadedFile;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

public class ReimbursementController implements Controller{

    private JWTService jwtService;
    private ReimbursementService reimbursementService;
    private UserService userService;

    public ReimbursementController() {
        this.jwtService = new JWTService();
        this.reimbursementService = new ReimbursementService();
        this.userService = new UserService();
    }

    private Handler addReimbursement = (ctx) -> {
        if (ctx.header("Authorization") == null) {
            throw new UnauthorizedResponse("You must be logged in to access this endpoint.");
        }
        String jwt = ctx.header("Authorization").split(" ")[1];

        // check token
        Jws<Claims> token = this.jwtService.parseJwt(jwt);
        Integer userRoleId = (Integer) token.getBody().get("user_role");
        String userId = ctx.pathParam("user_id");

        if (userRoleId != 1) {
            if (!(token.getBody().get("user_id")).toString().equals(userId)) {
                throw new UnauthorizedResponse("You cannot add a reimbursement request for anyone other than yourself.");
            }
        }
        AddReimbursementDTO dto = new AddReimbursementDTO();
        System.out.println(ctx.formParamMap());

        if(ctx.uploadedFiles().isEmpty()) {
            throw new ImageNotFoundException("Upload failed: No image was found to uploaded");
        }

        UploadedFile uploadedImage = ctx.uploadedFiles().get(0);
        dto.setReimbReceipt(uploadedImage);
        System.out.println();

        dto.setReimbAuthor((Integer)token.getBody().get("user_id"));
        dto.setReimbDescription(ctx.formParam("desc"));

        try {
            dto.setReimbAmount(Double.parseDouble(ctx.formParam("amount")));
        } catch(IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid input for 'amount' was given");
        }

        try {
            int type = Integer.parseInt(ctx.formParam("type"));
            dto.setReimbType(type);

        } catch(IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid input for 'type' was given. " + e);
        }

        ResponseReimbursementDTO newReimbursement = reimbursementService.addReimbursement(dto);
        System.out.println(newReimbursement);
        ctx.status(201);
        ctx.json(newReimbursement);

    };

    private Handler getReimbursementById = (ctx) -> {
        if (ctx.header("Authorization") == null) {
            throw new UnauthorizedResponse("You must be logged in to access this endpoint.");
        }
        String jwt = ctx.header("Authorization").split(" ")[1];

        Jws<Claims> token = this.jwtService.parseJwt(jwt);

        String userId = ctx.pathParam("user_id");

        System.out.println(token.getBody());

        if((Integer) (token.getBody().get("user_role")) > 1) {
            if (!(token.getBody().get("user_id")).toString().equals(userId)){
                throw new UnauthorizedResponse("You cannot access a reimbursement that does not belong to you.");
            }
        }
        String reimbId = ctx.pathParam("reimb_id");
        Reimbursement reimbursement = reimbursementService.getReimbursementById(reimbId);
        ctx.status(200);
        ctx.json(reimbursement);

    };

    private Handler getAllReimbursements = (ctx) -> {
        if (ctx.header("Authorization") == null) {
            throw new UnauthorizedResponse("You must be logged in.");
        }
        String jwt = ctx.header("Authorization").split(" ")[1];

        Jws<Claims> token = this.jwtService.parseJwt(jwt);

        if (((Integer) token.getBody().get("user_role")) > 1) {
            throw new UnauthorizedResponse("You must be an admin to access this source.");
        }

       List<ResponseReimbursementDTO> reimbursements;
       reimbursements = reimbursementService.getAllReimbursements();

        ctx.status(200);
        ctx.json(reimbursements);
    };

    private Handler getAllReimbursementsForUser = (ctx) ->  {
        if (ctx.header("Authorization") == null) {
            throw new UnauthorizedResponse("You must be logged in to access this endpoint.");
        }
        String jwt = ctx.header("Authorization").split(" ")[1];

        Jws<Claims> token = this.jwtService.parseJwt(jwt);
        System.out.println(token.getBody());
        Integer userRoleId = (Integer) token.getBody().get("user_role");
        System.out.println("userRoleId = " + userRoleId);
        String userId = ctx.pathParam("user_id");
        System.out.println("userId = " + userId);
        System.out.println(token.getBody().get("user_id").toString().equals(userId));
        if(userRoleId > 1) {
            if (!((token.getBody().get("user_id")).toString().equals(userId))){
                throw new UnauthorizedResponse("You cannot access reimbursements that do not belong to you.");
            }
        }

        List<ResponseReimbursementDTO> reimbursements = reimbursementService.getReimbursementsByUser(userId);
        ctx.status(200);
        ctx.json(reimbursements);
    };


    private Handler editPendingReimbursement = (ctx) -> {
        if (ctx.header("Authorization") == null) {
            throw new UnauthorizedResponse("You must be logged in to access this endpoint.");
        }
        String jwt = ctx.header("Authorization").split(" ")[1];

        Jws<Claims> token = this.jwtService.parseJwt(jwt);

        String reimbId = ctx.pathParam("reimb_id");
        String userId = ctx.pathParam("user_id");
        System.out.println("userID: " + userId );
        String t_userId = token.getBody().get("user_id").toString();
        System.out.println("tokenID: " + t_userId);

        if (!(t_userId.equals(userId))) {
            throw new UnauthorizedResponse("This reimbursement does not belong to you.");
        }

        UpdateReimbursementDTO updateDTO = new UpdateReimbursementDTO();

        if (ctx.formParam("desc") != null){
            String desc = ctx.formParam("desc");
            updateDTO.setDesc(desc);
        }


        if (ctx.formParam("amount") != null) {
            double amount = Double.parseDouble(ctx.formParam("amount"));
            updateDTO.setAmount(amount);
        }

        if (ctx.formParam("type") != null) {
            int typeId = Integer.parseInt(ctx.formParam("type"));
            System.out.println("type: " + typeId);
            updateDTO.setTypeId(typeId);
        }

        if (ctx.uploadedFile("receipt") != null) {
            UploadedFile file = ctx.uploadedFile("receipt");
            assert file != null;
            updateDTO.setReceipt(file);
        }

        Reimbursement reimbursement = reimbursementService.editPendingReimbursement(reimbId, updateDTO);
        ctx.status(201);
        ctx.json(reimbursement);
    };

    private Handler updateReimbursementStatus = (ctx) -> {
        if (ctx.header("Authorization") == null) {
            throw new UnauthorizedResponse("You must be logged in.");
        }


        String jwt = ctx.header("Authorization").split(" ")[1];
        Jws<Claims> token = this.jwtService.parseJwt(jwt);
        UpdateReimbursementStatusDTO statusDTO = new UpdateReimbursementStatusDTO();
        String reimbId = ctx.pathParam("reimb_id");
        int reimbAuthorId = reimbursementService.getReimbursementById(reimbId).getReimbAuthor();
        int currentUserId = (int) token.getBody().get("user_id");
        String statusString = ctx.formParam("status");

        System.out.println(statusDTO);
        statusDTO.setResolverId((Integer) token.getBody().get("user_id"));


        if (((Integer) token.getBody().get("user_role")) != 1) {
            throw new UnauthorizedResponse("You must be admin to update reimbursement status.");
        }


        if (reimbAuthorId == currentUserId) {
            throw new UnauthorizedResponse("You cannot process your own reimbursement ticket. Please contact a different admin.");
        }

        boolean accomplished = reimbursementService.updateReimbursementStatus(statusDTO, reimbId, statusString);

        ctx.status(201);
        ctx.json(accomplished);
    };

    private Handler deletePendingReimbursement = (ctx) -> {
        if (ctx.header("Authorization") == null) {
            throw new UnauthorizedResponse("You must be logged in to access this endpoint.");
        }
        String jwt = ctx.header("Authorization").split(" ")[1];

        Jws<Claims> token = this.jwtService.parseJwt(jwt);
        Integer userRoleId = (Integer) token.getBody().get("user_role");
        String userId = ctx.pathParam("user_id");

        if (userRoleId != 1) {
            if (!("" + token.getBody().get("user_id")).equals(userId)) {
                throw new UnauthorizedResponse("You cannot delete a reimbursement that does not belong to you.");
            }
        }

        String reimbId = ctx.pathParam("reimb_id");
        System.out.println("Author: " + reimbursementService.getReimbursementById(reimbId).getReimbAuthor());
        System.out.println("User: " + token.getBody().get("user_id"));


        boolean accomplished = reimbursementService.deletePendingReimbursement(reimbId, userRoleId);
        ctx.status(200);
        ctx.json(accomplished);
    };


    @Override
    public void mapEndpoints(Javalin app) {
        app.post("/users/{user_id}/reimbursements", addReimbursement);
        app.get("/users/{user_id}/reimbursements", getAllReimbursementsForUser);
        app.get("/reimbursements", getAllReimbursements);
        app.get("/users/{user_id}/reimbursements/{reimb_id}", getReimbursementById);
        app.put("/users/{user_id}/reimbursements/{reimb_id}", editPendingReimbursement);
        app.patch("/reimbursements/{reimb_id}", updateReimbursementStatus);
        app.delete("/users/{user_id}/reimbursements/{reimb_id}", deletePendingReimbursement);
    }

}
