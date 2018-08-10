package org.kurento.orion.connector.entities.commons;

import javax.validation.Payload;

public @interface ValidateString {

    String[] acceptedValues();

    String message() default "Invalid value";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { }; 
}
