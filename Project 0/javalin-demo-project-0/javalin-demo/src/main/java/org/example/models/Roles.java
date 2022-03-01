package org.example.models;

import io.javalin.core.security.RouteRole;

public enum Roles implements RouteRole {
    USER, ADMIN
}
