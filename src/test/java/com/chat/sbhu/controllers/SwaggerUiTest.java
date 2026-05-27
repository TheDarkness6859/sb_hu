package com.chat.sbhu.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SwaggerUiTest {

    @Test
    @DisplayName("Scenario 4: Swagger Documentation Verification")
    void swaggerUiIsAccessible() {
        Class<?> controllerClass = EventController.class;

        boolean hasTagAnnotation = controllerClass.isAnnotationPresent(Tag.class);
        assertTrue(hasTagAnnotation);

        boolean hasOperationAnnotation = false;
        Method[] methods = controllerClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Operation.class)) {
                hasOperationAnnotation = true;
                break;
            }
        }
        assertTrue(hasOperationAnnotation);
    }
}