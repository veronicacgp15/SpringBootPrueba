
# 🏗️  Correciones de Proyecto

## 📋 **Resumen de la Estructuración**

## 1 - 🔧 ** mockito en los test**

 clase: src/test/java/com/vcgp/proyecto/infrastructure/entity/ClientTest.java

```bash
/*
Lo mejor es usar mockito de esa manera simular un IdentifiableEntity del objeto client
*/

IdentifiableEntity client = Mockito.mock(IdentifiableEntity.class, Mockito.CALLS_REAL_METHODS);

```

## 2 - 🔧 setup
Para las entity agregar setup
src/test/java/com/vcgp/proyecto/infrastructure/entity/WarehouseTest.java

```bash
 //Crear un metodo setup para instanciar objetos

private Warehouse warehouse;
private Client client;

@BeforeEach
void setUp() {
    warehouse = new Warehouse();
    client = new Client();
    client.setId(1L);
    client.setName("Prueba de Almacen");
}
```

## 3 - 🔧 mejoras de codigo

src/test/java/com/vcgp/proyecto/infrastructure/repository/ClientRepositoryTest.java
en 
```bash 
 // THEN
assertThat(foundClient).isPresent();
```
mejorar por 

```bash
// THEN
assertThat(foundClient).isPresent()
.get()
.extracting(Client::getName)
.isEqualTo(JOHN);
```

## 4 - 🔧 test funcional
Puedes hacer un test funcional tambien de esta manere como recomendacion.
src/test/java/com/vcgp/proyecto/infrastructure/controllers/ClienteleControllerTest.java

```bash

Como sugerencia para Test funcionales, puedes utlizar:
/**

Functional tests for WarehouseController
*/
@SpringBootTest
@AutoConfigureMockMvc
@activeprofiles(Constants.TEST_PROFILE)
@transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class WarehouseControllerFunctionalTest { }
```