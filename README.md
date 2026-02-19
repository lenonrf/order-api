## CONTEXTO DO NEGÓCIO

Você foi contratado para criar um sistema de cálculo de comissões. A Empresa cobra uma comissão (fee) de cada pedido processado através da plataforma. Diferentes restaurantes podem ter diferentes taxas de comissão baseadas no volume de pedidos e valores.

Seu trabalho é criar uma API REST que calcule o valor total de comissão que um restaurante deve pagar em uma data específica.

---

## PROBLEMA

Implemente uma API REST que:

**Endpoint:** `POST /api/orders/commission`

**Input (JSON):**

```json
{
  "restaurantId": "R001",
  "date": "2026-02-18"
}
```

**Output (JSON):**

```json
{
  "restaurantId": "R001",
  "totalCommission": 115.35,
  "orderCount": 5,
  "totalOrderValue": 725.50,
  "averageOrderValue": 145.10,
  "commissionRate": 0.159,
  "breakdown": [
    {
      "orderId": "ORD001",
      "timestamp": "2026-02-18T12:30:00",
      "orderValue": 150.00,
      "commissionAmount": 22.50,
      "commissionRate": 0.15
    },
    {
      "orderId": "ORD002",
      "timestamp": "2026-02-18T13:15:00",
      "orderValue": 85.50,
      "commissionAmount": 12.83,
      "commissionRate": 0.15
    }
    // ... mais pedidos
  ]
}
```

---

## REGRAS DE COMISSÃO

A taxa de comissão varia baseada no **valor individual do pedido**:


| Faixa de Valor  | Taxa de Comissão |
| --------------- | ----------------- |
| Pedidos < $50   | 12%               |
| Pedidos$50-$200 | 15%               |
| Pedidos > $200  | 18%               |

**Exemplos:**

- Pedido de $45.00 → Comissão = $5.40 (12%)
- Pedido de $120.00 → Comissão = $18.00 (15%)
- Pedido de $250.00 → Comissão = $45.00 (18%)

---

## DADOS MOCK (SIMULAR BANCO DE DADOS)

Para facilitar, crie um serviço mock com os seguintes pedidos:

### Restaurante R001 (hoje):

```
ORD001: $150.00 às 12:30
ORD002: $85.50 às 13:15
ORD003: $220.00 às 19:45
ORD004: $95.00 às 20:10
ORD005: $175.00 às 21:30
```

### Restaurante R002 (hoje):

```
ORD006: $120.00 às 12:00
ORD007: $65.00 às 14:30
ORD008: $180.00 às 19:00
```

---

## REQUISITOS TÉCNICOS

### 1. **DTOs (Data Transfer Objects)**

- `OrderRequest`: restaurantId (String), date (LocalDate)
- `OrderResponse`: todos os campos do output acima
- `OrderBreakdownDTO`: detalhes individuais de cada pedido
- `ErrorResponse`: timestamps, status, error, message, path, validationErrors

### 2. **Validações**

- `@NotNull` em restaurantId e date
- `@PastOrPresent` na data (não aceitar datas futuras)
- `@Valid` no controller
- Mensagens de erro claras em português

### 3. **Estrutura de Camadas**

```
controller/
  └── OrderController.java          → REST endpoints
service/
  ├── OrderService.java              → busca pedidos (mock)
  └── OrderCommissionService.java    → lógica de cálculo
model/
  └── Order.java                     → entidade Order
dto/
  ├── OrderRequest.java
  ├── OrderResponse.java
  ├── OrderBreakdownDTO.java
  └── ErrorResponse.java
exception/
  └── GlobalExceptionHandler.java    → @ControllerAdvice
```

### 4. **Lógica de Negócio**

- Usar `BigDecimal` para todos os valores monetários (precisão!)
- `setScale(2, RoundingMode.HALF_UP)` para arredondamento
- Calcular taxa média: totalCommission / totalOrderValue
- Breakdown deve conter TODOS os pedidos do restaurante na data

### 5. **Exception Handling**

- `@RestControllerAdvice` global
- `@ExceptionHandler(MethodArgumentNotValidException.class)` → 400
- `@ExceptionHandler(IllegalArgumentException.class)` → 400
- `@ExceptionHandler(Exception.class)` → 500

### 6. **Testes (OBRIGATÓRIO!)**

Crie `OrderCommissionServiceTest.java` com:

- Teste happy path (restaurante com pedidos)
- Teste empty (restaurante sem pedidos)
- Teste taxas corretas para diferentes valores
- Teste cálculo de média
- Teste breakdown completo
- Teste data passada (sem pedidos)
