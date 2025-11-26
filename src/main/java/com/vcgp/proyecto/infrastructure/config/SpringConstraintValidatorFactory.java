package com.vcgp.proyecto.infrastructure.config;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class SpringConstraintValidatorFactory implements ConstraintValidatorFactory {

    private final AutowireCapableBeanFactory beanFactory;

    public SpringConstraintValidatorFactory(AutowireCapableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> key) {

        return beanFactory.createBean(key);
    }

    @Override
    public void releaseInstance(ConstraintValidator<?, ?> instance) {
        beanFactory.destroyBean(instance);
    }
}
