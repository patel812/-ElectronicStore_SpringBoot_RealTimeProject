package com.Icwd.electronic.store.validate;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

//Where to use this @ImageNameValid annotation
@Target({ElementType.FIELD, ElementType.PARAMETER})

//
@Retention(RetentionPolicy.RUNTIME)
@Documented

//In which class the logic written
@Constraint(validatedBy = ImageNameValidator.class)


//To make as a Annotation for validate use a interface as a @interface
public @interface  ImageNameValid {

    //error message
    String message() default "Invalid Image Name !!";

    //represent group of constraints
    Class<?>[] groups() default {};

    //additional information about annotation
    Class<? extends Payload>[] payload() default {};


}
