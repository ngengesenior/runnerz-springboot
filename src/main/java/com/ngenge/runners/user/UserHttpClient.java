package com.ngenge.runners.user;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

/**
 * With the Http client, we write less code compared to the Rest Client
 * We just need to annotate the required methods and call them later
 */
public interface UserHttpClient {

    @GetExchange("/users")
    List<User> findAll();
    @GetExchange("/users/{id}")
    User findById(@PathVariable Integer id);
}
