package net.sw.crud.error.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class AuthorValidator implements ConstraintValidator<Author, String> {

    private List<String> authors = Arrays.asList("Raoul-Gabriel Urma",
            "Blinov", "Mihalcea");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return authors.contains(value);
    }
}
