package com.pb.ProjetoGrupo2.config.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class InstitutionalEmailValidation implements ConstraintValidator <InstitutionalEmail, String> {

    @Override
    public void initialize(InstitutionalEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == ""){
            return false;
        }
        String emailDomain = "fatec.sp.gov.br";
        String[] splitedEmail = s.split("@");

        return splitedEmail[1].equals(emailDomain);
    }
}
