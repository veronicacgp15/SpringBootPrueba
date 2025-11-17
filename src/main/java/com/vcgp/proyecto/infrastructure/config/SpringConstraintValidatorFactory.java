package com.vcgp.proyecto.infrastructure.config;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

@Component // 👈 Hacemos que la propia fábrica sea un bean de Spring@
public class SpringConstraintValidatorFactory implements ConstraintValidatorFactory {

    private final AutowireCapableBeanFactory beanFactory;

    public SpringConstraintValidatorFactory(AutowireCapableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> key) {
        // Pide a Spring que cree una instancia de la clase validadora (ej: UniqueClientNameValidator)
        // Spring usará la inyección por constructor automáticamente.
        return beanFactory.createBean(key);
    }

    @Override
    public void releaseInstance(ConstraintValidator<?, ?> instance) {
        // Permite a Spring gestionar el ciclo de vida del bean creado.
        beanFactory.destroyBean(instance);
    }
}
