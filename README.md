## 📋 CONTEXTO DO NEGÓCIO

Você foi contratado pela DoorDash para criar um sistema de cálculo de comissões. A DoorDash cobra uma comissão (fee) de cada pedido processado através da plataforma. Diferentes restaurantes podem ter diferentes taxas de comissão baseadas no volume de pedidos e valores.

Seu trabalho é criar uma API REST que calcule o valor total de comissão que um restaurante deve pagar em uma data específica.

---

## 🎯 PROBLEMA

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

## 💰 REGRAS DE COMISSÃO

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

## 📊 DADOS MOCK (SIMULAR BANCO DE DADOS)

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

## ✅ REQUISITOS TÉCNICOS

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

- ✅ Teste happy path (restaurante com pedidos)
- ✅ Teste empty (restaurante sem pedidos)
- ✅ Teste taxas corretas para diferentes valores
- ✅ Teste cálculo de média
- ✅ Teste breakdown completo
- ✅ Teste data passada (sem pedidos)

---

## 🎯 CRITÉRIOS DE AVALIAÇÃO

1. **Perguntas de Clarificação (5 min)**

   - Você pergunta sobre QPS, idempotência, formato de timestamps?
   - Identifica ambiguidades?
2. **Plano de Solução (5 min)**

   - Verbaliza estrutura antes de codar?
   - Explica camadas (Controller → Service → DTOs)?
3. **Código Limpo (30 min)**

   - Nomes descritivos (`calculateTotalCommission`, não `calc`)
   - Métodos pequenos (<20 linhas)
   - Single Responsibility Principle
   - Comentários apenas onde necessário
4. **Validações (parte do código)**

   - @Valid nos DTOs
   - Validação de negócio no Service
   - Exception handling profissional
5. **Testes (10 min)**

   - Cobertura de cenários felizes
   - Testes de edge cases
   - Testes RODAM com sucesso (`mvn test`)
6. **Comunicação (durante todo o processo)**

   - Pensa alto constantemente?
   - Explica decisões técnicas?
   - Menciona trade-offs quando relevante?

---

## 🚀 DICAS PARA A ENTREVISTA

### **Antes de Codar (CRÍTICO!):**

```
□ "Posso fazer algumas perguntas de clarificação?"
  - Volume esperado de pedidos por restaurante?
  - Preciso garantir idempotência?
  - Formato dos timestamps? (ISO-8601?)
  - Como lidar com falhas temporárias?
  
□ Explicar plano em voz alta:
  "Vou criar DTOs de entrada/saída com validações,
   um Service mock para buscar pedidos,
   um Service de cálculo de comissão,
   um Controller REST, e testes unitários."
```

### **Durante Implementação:**

```
□ PENSAR ALTO o tempo todo!
  - "Criando DTO de entrada com validações..."
  - "Usando BigDecimal para precisão monetária..."
  - "Separando lógica no Service para facilitar testes..."
  - "Aplicando taxa de 15% para pedidos entre $50-$200..."

□ Mencionar trade-offs:
  - "Para 1 hora, foco em funcional + testes básicos"
  - "Em produção, eu adicionaria cache distribuído..."
  - "Se tivéssemos mais tempo, implementaria paginação..."
```

### **Gestão de Tempo (CRUCIAL!):**

```
00-05 min: Ler problema + perguntas de clarificação
05-10 min: Plano verbal (camadas e estrutura)
10-15 min: DTOs (Request, Response, Breakdown, ErrorResponse)
15-25 min: Services (OrderService mock + OrderCommissionService)
25-35 min: Controller + Exception Handler
35-45 min: application.properties + ajustes
45-55 min: TESTES (mínimo 3 cenários!)
55-60 min: Rodar testes + explicar decisões
```

---

## ✅ CHECKLIST DE ENTREGA

Antes de submeter, verifique:

```
□ DTOs criados com validações (@Valid, @NotNull, @PastOrPresent)
□ OrderService mock retorna pedidos do restaurante na data
□ OrderCommissionService calcula comissão corretamente
□ Controller tem @PostMapping("/commission") com @Valid
□ GlobalExceptionHandler trata validações (400) e erros (500)
□ application.properties configurado (porta 8081, logging)
□ 3-6 testes criados e RODANDO com sucesso
□ Código compila sem erros (mvn clean compile)
□ Testes passam sem erros (mvn test)
□ Código está limpo e bem organizado
```

---

## 🎓 EDGE CASES PARA CONSIDERAR

- **Restaurante sem pedidos na data:** retornar response com valores zerados
- **Data futura:** validação deve rejeitar (@PastOrPresent)
- **Pedido exatamente $50 ou $200:** qual taxa aplicar? (defina claramente!)
- **Lista de pedidos vazia:** não quebrar, retornar resposta válida
- **Valores negativos:** validar no DTO (@Positive se relevante)

---

## 💡 EXEMPLO DE RESPOSTA ESPERADA

Para `R001` na data de hoje (5 pedidos):

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
    },
    {
      "orderId": "ORD003",
      "timestamp": "2026-02-18T19:45:00",
      "orderValue": 220.00,
      "commissionAmount": 39.60,
      "commissionRate": 0.18
    },
    {
      "orderId": "ORD004",
      "timestamp": "2026-02-18T20:10:00",
      "orderValue": 95.00,
      "commissionAmount": 14.25,
      "commissionRate": 0.15
    },
    {
      "orderId": "ORD005",
      "timestamp": "2026-02-18T21:30:00",
      "orderValue": 175.00,
      "commissionAmount": 26.25,
      "commissionRate": 0.15
    }
  ]
}
```

**Cálculos:**

- ORD001 ($150): $150 × 0.15 = $22.50 ✅
- ORD002 ($85.50): $85.50 × 0.15 = $12.83 ✅
- ORD003 ($220): $220 × 0.18 = $39.60 ✅ (>$200!)
- ORD004 ($95): $95 × 0.15 = $14.25 ✅
- ORD005 ($175): $175 × 0.15 = $26.25 ✅
- **Total:** $115.43 (pode ter pequena diferença por arredondamento)

---

## 🚨 ERROS COMUNS A EVITAR

1. ❌ Usar `double` ou `float` para valores monetários → Use **BigDecimal**!
2. ❌ Esquecer `setScale(2, RoundingMode.HALF_UP)` → Arredondamento inconsistente
3. ❌ Não validar entrada (@Valid) → Dados inválidos quebram sistema
4. ❌ Não pensar alto → Entrevistador não sabe o que você está pensando
5. ❌ Deixar testes para o final → Pode não dar tempo!
6. ❌ Nomes genéricos (calc, svc, ctrl) → Use nomes descritivos!
7. ❌ Over-engineering → Foco em funcional primeiro, otimizar depois
8. ❌ Não rodar os testes → mvn test ANTES de submeter!

---

## ⏱️ SIMULAÇÃO DE ENTREVISTA

Quando for implementar, siga EXATAMENTE como seria na entrevista real:

1. **Inicie cronômetro** de 60 minutos
2. **Leia o problema** (3-5 min)
3. **Faça perguntas** (em voz alta, mesmo sozinho!)
4. **Explique seu plano** (em voz alta!)
5. **Code while talking** (narre TUDO que está fazendo)
6. **Teste regularmente** (compila? roda?)
7. **Deixe tempo para testes** (mínimo 10 min)
8. **Rode testes antes de "submeter"**

---

## 🎯 BOA SORTE!

**Lembre-se:**

- 🧘 Respire fundo e mantenha calma
- 🤔 Pergunte MUITO (clarificar ambiguidades)
- 📢 Pense ALTO o tempo todo
- ⏰ Gerencie o tempo (relógio visível)
- ✅ Chegue nos TESTES (não deixe para o final!)
- 💬 Explique trade-offs (mostra maturidade)
- 🎯 Foque em funcional PRIMEIRO, depois otimize

**Você vai conseguir! 🚀**

---

## 📚 RECURSOS

- Plano completo: `delivery-api/PLANO-DOORDASH.md`
- API de referência: `delivery-api/` (PayoutService como exemplo)
- Teste quando pronto: `mvn clean compile && mvn test`
