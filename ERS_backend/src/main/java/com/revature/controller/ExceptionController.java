package com.revature.controller;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.revature.exception.*;
import io.javalin.Javalin;
import io.javalin.http.ExceptionHandler;

import io.javalin.http.UnauthorizedResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.SizeLimitExceededException;
import javax.security.auth.login.FailedLoginException;
import java.io.IOException;

public class ExceptionController implements Controller{

    Logger LOGGER = LoggerFactory.getLogger(ExceptionController.class);

    private ExceptionHandler<FailedLoginException> failedLogin = ((exception, ctx) -> {
        LOGGER.warn(exception.getLocalizedMessage());
        ctx.status(400);
        ctx.json(exception.getMessage());
    });

    private ExceptionHandler<IllegalArgumentException> invalidIntegerParam = ((exception, ctx) -> {
        LOGGER.warn(exception.getLocalizedMessage());
        ctx.status(400);
        ctx.json(exception.getMessage());
    });

    private ExceptionHandler<UnauthorizedResponse> unauthorizedUser = ((exception, ctx) -> {
        LOGGER.warn(exception.getLocalizedMessage());
        ctx.status(401);
        ctx.json(exception.getMessage());
    });

    private ExceptionHandler<MismatchedInputException> invalidOrIncompleteBodySent = ((exception, ctx) -> {
        LOGGER.warn(exception.getLocalizedMessage());
        ctx.status(418);
        ctx.json(exception.getMessage());
    });

    private ExceptionHandler<SizeLimitExceededException> fileSizeTooLarge = ((exception, ctx) -> {
        LOGGER.warn(exception.getMessage());
        ctx.status(413);
        ctx.json(exception.getMessage());
    });

    private ExceptionHandler<IOException> invalidFileType = ((exception, ctx) -> {
        LOGGER.warn(exception.getLocalizedMessage());
        ctx.status(400);
        ctx.json(exception.getLocalizedMessage());
    });

    private ExceptionHandler<IndexOutOfBoundsException> noFileUploaded = (((exception, ctx) -> {
        LOGGER.warn("User did not upload an image file.");
        ctx.status(400);
        ctx.json("You must upload an image of your receipt.");
    }));

    private ExceptionHandler<ReimbursementDoesNotExist> reimbursementNotFound = (((exception, ctx) -> {
        LOGGER.warn("User attempted to access a reimbursement that does not exist. Message: " + exception.getMessage());
        ctx.status(404);
        ctx.json(exception.getMessage());
    }));

    private ExceptionHandler<ReimbursementResolved> reimbursementResolved = (((exception, ctx) -> {
        LOGGER.warn("User attempted to change an already resolved reimbursement. Message: " + exception.getMessage());
        ctx.status(404);
        ctx.json(exception.getMessage());
    }));

    @Override
    public void mapEndpoints(Javalin app) {
        app.exception(FailedLoginException.class, failedLogin);
        app.exception(IllegalArgumentException.class, invalidIntegerParam);
        app.exception(UnauthorizedResponse.class, unauthorizedUser);
        app.exception(MismatchedInputException.class, invalidOrIncompleteBodySent);
        app.exception(SizeLimitExceededException.class, fileSizeTooLarge);
        app.exception(IndexOutOfBoundsException.class, noFileUploaded);
        app.exception(ReimbursementDoesNotExist.class, reimbursementNotFound);
        app.exception(ReimbursementResolved.class, reimbursementResolved);

    }
}