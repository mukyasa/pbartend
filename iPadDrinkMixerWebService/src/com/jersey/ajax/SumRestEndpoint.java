package com.jersey.ajax;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.sun.jersey.spi.inject.Inject;

@Path("/sum")
public class SumRestEndpoint {
    @Inject
    private MathService mathService = new MathService();

    @GET
    @Produces("application/json")
    public SumRestResult sum(
            @QueryParam("firstNumber") String firstNumber,
            @QueryParam("secondNumber") String secondNumber) {
        SumRestResult result = new SumRestResult();
        result.setFirstNumber(Integer.parseInt(firstNumber));
        result.setSecondNumber(Integer.parseInt(secondNumber));
        result.setTotal(mathService.sum(Integer.parseInt(firstNumber), Integer.parseInt(secondNumber)));
        return result;
    }
}