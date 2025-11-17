package com.vcgp.proyecto.infrastructure.validation.annotations;

import com.vcgp.proyecto.infrastructure.validation.validators.UniqueClientNameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueClientNameValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueClientName {
    String message() default "El nombre del cliente ya está en uso.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
