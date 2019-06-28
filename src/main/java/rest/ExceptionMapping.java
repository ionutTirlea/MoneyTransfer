package rest;

import exceptions.BadRequestException;
import exceptions.InsufficientFundsException;
import exceptions.ResourceNotFoundException;

import static spark.Spark.exception;

class ExceptionMapping {

    private ExceptionMapping() {
    }

    static void configure() {

        exception(ResourceNotFoundException.class, (e, request, response) -> {
            response.status(404);
            response.body(e.getMessage());
            response.type("application/json");
        });

        exception(BadRequestException.class, (e, request, response) -> {
            response.status(400);
            response.body(e.getMessage());
            response.type("application/json");
        });

        exception(InsufficientFundsException.class, (e, request, response) -> {
            response.status(400);
            response.body(e.getMessage());
            response.type("application/json");
        });
    }

}