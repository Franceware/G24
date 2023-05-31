package com.g24.authentication.constraints;

import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.BeanWrapper;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> 
{
    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(final FieldMatch constraintAnnotation) 
    {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
    }
    
    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) 
    {
        try 
        {
            
	        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(value);
	        final Object firstObj = beanWrapper.getPropertyValue(firstFieldName);
	        final Object secondObj = beanWrapper.getPropertyValue(secondFieldName);
            
            return firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);
        } 
        catch (final Exception ignore) {}
        
        return true;
    }
}