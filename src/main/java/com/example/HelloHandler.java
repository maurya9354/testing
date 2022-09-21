package com.example;

import com.example.model.Context;
import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import com.mozu.api.contracts.productadmin.Product;
import org.springframework.cloud.function.adapter.azure.FunctionInvoker;

import java.util.Optional;


public class HelloHandler extends FunctionInvoker<Context, Product> {

    @FunctionName("addProduct")
    public HttpResponseMessage execute(
            @HttpTrigger(name = "request", methods = {HttpMethod.GET, HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS, route = "add-product") HttpRequestMessage<Optional<Context>> request,
            ExecutionContext context) {
        Context context1 = request.getBody().get();
        return request.createResponseBuilder(HttpStatus.OK).body(handleRequest(context1, context))
                .header("Content-Type", "application/json").build();
    }
}