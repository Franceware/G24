package com.example.demo.constraints;

//import org.apache.commons.beanutils.BeanUtils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.BeanWrapper;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(final FieldMatch constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
    }

    
    //Problema getProperty - Osservare getClass()
    
    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        try {
            //final Object firstObj = BeanUtils.getPropertyDescriptor(value.getClass(), firstFieldName);
            //final Object secondObj = BeanUtils.getPropertyDescriptor(value.getClass(), secondFieldName);
            
            BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(value);
            final Object firstObj = beanWrapper.getPropertyValue(firstFieldName);
            final Object secondObj = beanWrapper.getPropertyValue(secondFieldName);
            
            
            return firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);
        } catch (final Exception ignore) {}
        return true;
    }
}