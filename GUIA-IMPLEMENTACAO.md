# 📝 GUIA PASSO A PASSO - IMPLEMENTAÇÃO

Use este guia para implementar o problema de forma organizada.

---

## ⏱️ CRONÔMETRO: INICIE AGORA!

**Meta:** 60 minutos para API completa + testes

---

## 📋 PASSO A PASSO

### **FASE 1: SETUP E PREPARAÇÃO (5 min)**

#### 1.1 Adicionar dependência de validação
Abra `pom.xml` e adicione:
```xml
<!-- Validation -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

#### 1.2 Configurar application.properties
Abra `src/main/resources/application.properties` e adicione:
```properties
# Application Name
spring.application.name=order-api

# Server Configuration
server.port=8081

# Logging
logging.level.root=INFO
logging.level.com.lab13.order_api=DEBUG
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} - %msg%n

# Jackson (JSON)
spring.jackson.serialization.write-dates-as-timestamps=false
spring.jackson.time-zone=America/Sao_Paulo
```

#### 1.3 Testar compilação
```bash
mvn clean compile
```

---

### **FASE 2: DTOs (10 min)**

#### 2.1 Completar OrderRequest.java
Já existe em `dto/OrderRequest.java`. Adicione:
- Lombok annotations (@Data, @NoArgsConstructor, @AllArgsConstructor)
- Validações (@NotNull, @PastOrPresent)
- Mensagens de erro em português

#### 2.2 Criar OrderResponse.java
Em `dto/OrderResponse.java`:
```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private String restaurantId;
    private BigDecimal totalCommission;
    private Integer orderCount;
    private BigDecimal totalOrderValue;
    private BigDecimal averageOrderValue;
    private BigDecimal commissionRate;
    private List<OrderBreakdownDTO> breakdown;
}
```

#### 2.3 Criar OrderBreakdownDTO.java
Em `dto/OrderBreakdownDTO.java`:
```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderBreakdownDTO {
    private String orderId;
    private LocalDateTime timestamp;
    private BigDecimal orderValue;
    private BigDecimal commissionAmount;
    private BigDecimal commissionRate;
}
```

#### 2.4 Criar ErrorResponse.java
Em `dto/ErrorResponse.java`:
```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
    private List<String> validationErrors;
}
```

---

### **FASE 3: MODEL (5 min)**

#### 3.1 Criar Order.java
Em `model/Order.java`:
```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private String orderId;
    private String restaurantId;
    private String customerId;
    private BigDecimal orderValue;
    private LocalDateTime timestamp;
    private String status;
}
```

---

### **FASE 4: SERVICES (20 min)**

#### 4.1 Criar OrderService.java (Mock)
Em `service/OrderService.java`:
- Criar lista mock de pedidos
- Método: `getOrdersByRestaurantAndDate(String restaurantId, LocalDate date)`
- Inicializar dados mock no construtor (dados do problema)

#### 4.2 Criar OrderCommissionService.java
Em `service/OrderCommissionService.java`:
- Constantes para taxas de comissão (12%, 15%, 18%)
- Constantes para thresholds ($50, $200)
- Método principal: `calculateCommission(OrderRequest request)`
- Métodos auxiliares:
  - `calculateOrderCommission(Order order)` → calcula comissão individual
  - `determineCommissionRate(BigDecimal orderValue)` → determina taxa
  - `calculateTotalOrderValue(List<Order> orders)` → soma valores
  - `buildEmptyResponse(String restaurantId)` → resposta quando sem pedidos

**LEMBRE-SE:**
- Usar `BigDecimal` SEMPRE para valores monetários
- `.setScale(2, RoundingMode.HALF_UP)` para arredondamento
- Logging: `log.info()` em momentos importantes

---

### **FASE 5: CONTROLLER (5 min)**

#### 5.1 Criar OrderController.java
Em `controller/OrderController.java`:
```java
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Validated
@Slf4j
public class OrderController {
    
    private final OrderCommissionService commissionService;
    
    @PostMapping("/commission")
    public ResponseEntity<OrderResponse> calculateCommission(
            @Valid @RequestBody OrderRequest request) {
        // Log entrada
        // Chamar service
        // Log saída
        // Retornar ResponseEntity.ok()
    }
}
```

---

### **FASE 6: EXCEPTION HANDLER (5 min)**

#### 6.1 Criar GlobalExceptionHandler.java
Em `exception/GlobalExceptionHandler.java`:
```java
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(...) {
        // Extrair erros de validação
        // Montar ErrorResponse
        // Retornar 400 Bad Request
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(...) {
        // Retornar 400 Bad Request
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(...) {
        // Retornar 500 Internal Server Error
    }
}
```

---

### **FASE 7: TESTES (15 min)**

#### 7.1 Criar OrderCommissionServiceTest.java
Em `test/java/.../service/OrderCommissionServiceTest.java`:

Testes obrigatórios:
```java
@SpringBootTest
class OrderCommissionServiceTest {
    
    @Autowired
    private OrderCommissionService commissionService;
    
    @Test
    void shouldCalculateCommissionSuccessfully() {
        // Arrange: request com R001 e hoje
        // Act: chamar calculateCommission
        // Assert: verificar valores, orderCount > 0, etc.
    }
    
    @Test
    void shouldReturnEmptyResponseWhenNoOrders() {
        // Arrange: request com R999 (não existe)
        // Act: chamar calculateCommission
        // Assert: verificar orderCount = 0, valores zerados
    }
    
    @Test
    void shouldCalculateCorrectRatesForDifferentOrderValues() {
        // Verificar que taxas estão corretas no breakdown
        // 12% para <$50, 15% para $50-$200, 18% para >$200
    }
    
    @Test
    void shouldCalculateAverageOrderValueCorrectly() {
        // Verificar cálculo da média
    }
    
    @Test
    void shouldReturnCompleteBreakdown() {
        // Verificar que breakdown tem todos os campos preenchidos
    }
    
    @Test
    void shouldCalculateCommissionForPastDate() {
        // Testar com data no passado (deve retornar vazio no mock)
    }
}
```

---

## ✅ CHECKLIST FINAL (5 min)

Antes de considerar completo:

```bash
# 1. Compilar
mvn clean compile

# 2. Rodar testes
mvn test

# 3. Verificar se tudo passou
```

Verificar manualmente:
```
□ Todos os arquivos criados?
□ Imports corretos?
□ Código compila sem erros?
□ Testes passam?
□ Logging está funcionando?
□ Validações funcionam?
□ Exception handler funciona?
```

---

## 🎯 DICAS DE IMPLEMENTAÇÃO

### **BigDecimal Best Practices**
```java
// ✅ CERTO
BigDecimal value = new BigDecimal("150.00");
BigDecimal rate = new BigDecimal("0.15");
BigDecimal result = value.multiply(rate).setScale(2, RoundingMode.HALF_UP);

// ❌ ERRADO
double value = 150.00;
double rate = 0.15;
double result = value * rate; // Imprecisão!
```

### **Lombok Shortcuts**
```java
// Para DTOs de Request/Response
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

// Para Services/Controllers
@RequiredArgsConstructor // injeta final fields
@Slf4j // log.info(), log.error()
```

### **Validações**
```java
// No DTO
@NotNull(message = "Campo é obrigatório")
@PastOrPresent(message = "Data não pode ser no futuro")
private LocalDate date;

// No Controller
public ResponseEntity<OrderResponse> method(
    @Valid @RequestBody OrderRequest request) {
    // Spring valida automaticamente!
}
```

### **Stream API para Cálculos**
```java
// Somar valores
BigDecimal total = orders.stream()
    .map(Order::getOrderValue)
    .reduce(BigDecimal.ZERO, BigDecimal::add);

// Filtrar e transformar
List<OrderBreakdownDTO> breakdown = orders.stream()
    .map(this::calculateOrderCommission)
    .collect(Collectors.toList());
```

---

## 🚀 COMEÇE AGORA!

1. ⏱️ **INICIE O CRONÔMETRO** (60 minutos)
2. 📖 **Leia o problema** (`PROBLEMA-ENTREVISTA.md`)
3. 🤔 **Faça perguntas** (em voz alta!)
4. 📝 **Explique seu plano** (em voz alta!)
5. 💻 **COMECE A CODAR** (seguindo este guia)
6. 🧪 **TESTE regularmente** (compile frequentemente)
7. ✅ **Finalize com testes** (mínimo 10 min para testes)

**BOA SORTE! 🎯**
