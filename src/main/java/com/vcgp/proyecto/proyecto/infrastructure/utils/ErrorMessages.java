package com.vcgp.proyecto.proyecto.infrastructure.utils;

import java.text.MessageFormat;

public class ErrorMessages {
    // Hacemos el constructor privado para que no se pueda instanciar esta clase de utilidad.
    private ErrorMessages() {
    }

    // --- PLANTILLAS DE MENSAJES GENÉRICOS ---
    // Usamos %s como marcador para el nombre de la entidad y %d para el ID.
    private static final String ENTITY_NOT_FOUND_BY_ID = "{0} no encontrado con ID: {1}";
    private static final String ENTITY_ASSOCIATION_NOT_FOUND = "El {0} a asociar no fue encontrado con ID: {1}";
    private static final String ENTITY_EDIT_NOT_FOUND = "{0} a editar no encontrado con ID: {1}";
    private static final String ENTITY_DELETE_NOT_FOUND = "{0} a eliminar no encontrado con ID: {1}";

    private static final String CLIENT_NOT_FOUND = "Cliente con ID: {0} no existe";


    // --- MÉTODOS DE UTILIDAD PÚBLICOS ---

    /**
     * Genera un mensaje de error para una entidad no encontrada por su ID.
     * @param entityName El nombre de la entidad (ej. "Cliente", "Rack").
     * @param id El ID que se buscó.
     * @return El mensaje de error formateado.
     */
    public static String notFoundById(String entityName, Long id) {
        // MessageFormat es una forma segura y clara de formatear cadenas.
        return MessageFormat.format(ENTITY_NOT_FOUND_BY_ID, entityName, id);

    }

    /**
     * Genera un mensaje de error para cuando una entidad a asociar no se encuentra.
     * @param entityName El nombre de la entidad a asociar (ej. "Warehouse").
     * @param id El ID que se buscó.
     * @return El mensaje de error formateado.
     */
    public static String associationNotFound(String entityName, Long id) {
        return MessageFormat.format(ENTITY_ASSOCIATION_NOT_FOUND, entityName, id);
    }

    /**
     * Genera un mensaje de error para cuando una entidad a editar no se encuentra.
     * @param entityName El nombre de la entidad (ej. "Rack").
     * @param id El ID que se buscó.
     * @return El mensaje de error formateado.
     */
    public static String editNotFound(String entityName, Long id) {
        return MessageFormat.format(ENTITY_EDIT_NOT_FOUND, entityName, id);
    }

    /**
     * Genera un mensaje de error para cuando una entidad a eliminar no se encuentra.
     * @param entityName El nombre de la entidad (ej. "Rack").
     * @param id El ID que se buscó.
     * @return El mensaje de error formateado.
     */
    public static String deleteNotFound(String entityName, Long id) {
        return MessageFormat.format(ENTITY_DELETE_NOT_FOUND, entityName, id);
    }

    public static String clientNotFound(Long id) {
        // En este caso, solo necesitamos el ID, por lo que {0} es el ID.
        return MessageFormat.format(CLIENT_NOT_FOUND, id);
    }
}
