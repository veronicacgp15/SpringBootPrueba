# Correcciones

- 1 El Aplicacion debes de volverlo un archivo .yml

- Las excepciones de (validate) deben estar en una clase œRestControllerAdvice  y usar el GlobalException.

ejemplo:
```bash
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(OffsetDateTime.now());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setError(Constants.BAD_REQUEST_ERROR);
        errorResponse.setMessage(Constants.VALIDATION_ERROR_MESSAGE);
        errorResponse.setPath(request.getDescription(false).replace(Constants.URI_PREFIX, Constants.EMPTY_STRING));
        
        return ResponseEntity.badRequest().body(errorResponse);
    }
    
```
- Mala practica:
```bash
@Autowired
    private ClientRepository clientRepository;
```
No se debe usar el ClientRepository en el controller, usar DTO (Usar Record en los DTO o mapStrup)

- Usar lombok para los entity (se debe instalar la dependencia).
```bash
@Entity
@Table(name = "clients")
public class Client {
```

ejemplo:
```bash
@Entity
@Table(name = "racks")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RackEntity {
```
- La logica que esta dentro del controller debe de estar en un service:

@RestController
@RequestMapping("/api/clients")
public class ClientelaController {
```bash
    @Autowired
    private ClientRepository clientRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Client> clients(){
        return clientRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Client> obtenerPorId(@PathVariable Long id) {
        Optional<Client> client = clientRepository.findById(id);
        return client.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
```

es decir todo esto:
```bash
Optional<Client> client = clientRepository.findById(id);
        return client.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
```                
y el service se llama desde el controller

- eliminar las clases que no se estan usando por ejemplo
  TestEnMemoria.java y demas.

Para hacer Test usar la carpeta test


- Pasar todo el proyecto a arquitectura hexagonal (informacion en el enlace de como esta estructurado).
  https://medium.com/@edusalguero/arquitectura-hexagonal-59834bb44b7f

### Importante: Usar Interfaces, DTO 

