package com.vcgp.proyecto.infrastructure.config;

import jakarta.validation.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class ValidationConfig {
    // Este método configura el bean 'Validator' principal de la aplicación.
    @Bean
    public Validator validator(SpringConstraintValidatorFactory constraintValidatorFactory) {
        LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
        // Aquí está la magia: le decimos que use nuestra fábrica personalizada.
        factoryBean.setConstraintValidatorFactory(constraintValidatorFactory);
        return factoryBean;
    }
}

/*
Resumen del Flujo con la Nueva Configuración1.Un endpoint del
controlador con @Validated recibe una petición.
2.El framework de validación ve la anotación @UniqueClientName en la entidad Client.
3.Necesita una instancia de UniqueClientNameValidator.
4.En lugar de hacer new(), consulta a la fábrica que hemos configurado: nuestra
SpringConstraintValidatorFactory.
5.Nuestra fábrica delega la creación a Spring
(beanFactory.createBean(...)).
6.Spring ve que UniqueClientNameValidator es un @Component que necesita un
ClientRepository en su constructor.
7.Spring crea el UniqueClientNameValidator, inyectándole el ClientRepository.
8.La instancia, ya completamente funcional, se devuelve al framework de validación
para que ejecute el método isValid.¡Y listo! Has logrado usar inyección por constructor en todas partes, manteniendo tu código limpio, consistente y alineado con las mejores prácticas de Spring, incluso en los rincones más complejos de la integración de frameworks.
*  */
