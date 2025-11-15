package com.vcgp.proyecto.proyecto.infrastructure.utils;

import java.text.MessageFormat;

public class SuccessMessages {

    private static final String ENTITY_DELETED_SUCCESSFULLY = "{0} con ID: {1} eliminado correctamente.";
    private static final String ENTITY_CREATED_SUCCESSFULLY = "{0} creado exitosamente.";

    public static String entityDeleted(String entityName, Long id) {
        return MessageFormat.format(ENTITY_DELETED_SUCCESSFULLY, entityName, id);
    }
}
