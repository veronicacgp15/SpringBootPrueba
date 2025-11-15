package com.vcgp.proyecto.proyecto.infrastructure.utils;

import java.text.MessageFormat;

public class ErrorMessages {
    private ErrorMessages() {
    }

    private static final String ENTITY_NOT_FOUND_BY_ID = "{0} no encontrado con ID: {1}";
    private static final String ENTITY_ASSOCIATION_NOT_FOUND = "El {0} a asociar no fue encontrado con ID: {1}";
    private static final String ENTITY_EDIT_NOT_FOUND = "{0} a editar no encontrado con ID: {1}";
    private static final String ENTITY_DELETE_NOT_FOUND = "{0} a eliminar no encontrado con ID: {1}";

    private static final String CLIENT_NOT_FOUND = "Cliente con ID: {0} no existe";


    public static String notFoundById(String entityName, Long id) {

        return MessageFormat.format(ENTITY_NOT_FOUND_BY_ID, entityName, id);

    }

    public static String associationNotFound(String entityName, Long id) {
        return MessageFormat.format(ENTITY_ASSOCIATION_NOT_FOUND, entityName, id);
    }


    public static String editNotFound(String entityName, Long id) {
        return MessageFormat.format(ENTITY_EDIT_NOT_FOUND, entityName, id);
    }


    public static String deleteNotFound(String entityName, Long id) {
        return MessageFormat.format(ENTITY_DELETE_NOT_FOUND, entityName, id);
    }

    public static String clientNotFound(Long id) {
        return MessageFormat.format(CLIENT_NOT_FOUND, id);
    }
}
