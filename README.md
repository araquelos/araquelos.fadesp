# Desafio Técnico - Nível 1

### Objetivo

> Seu objetivo é criar uma API para possibilitar o recebimento de pagamentos de débitos de pessoas físicas e jurídicas. Quando a API receber um pagamento, este deverá ser armazenado no banco de dados com status Pendente de Processamento. Uma aplicação terceira (não se preocupe com ela) irá processar o pagamento e efetuar uma chamada para sua API, solicitando a alteração do status do pagamento de Pendente para Processado. Sua API também deve listar todos os pagamentos recebidos e oferecer para o cliente a possibilidade de filtrar os pagamentos.

### Requisitos

+ A API deve ser capaz de receber um pagamento.
  + Um pagamento possui os seguintes campos:
    + Código do Débito sendo pago (valor inteiro)
    + CPF ou CNPJ do Pagador
    + Método de pagamento (boleto, pix, cartao_credito ou cartao_debito)
    + Número do cartão (Este campo só será enviado se o método de pagamento for cartao_credito ou cartao_debito)
    + Valor do pagamento
+ A API deve ser capaz de atualizar o status de um pagamento.
  + A atualização do status de um pagamento sempre irá conter o ID do Pagamento e o novo status.
  + Quando o pagamento está Pendente de Processamento, ele pode ser alterado para Processado com Sucesso ou Processado com Falha.
  + Quando o pagamento está Processado com Sucesso, ele não pode ter seu status alterado.
  + Quando o pagamento está Processado com Falha, ele só pode ter seu status alterado para Pendente de Processamento.
+ A API deve ser capaz de listar todos os pagamentos recebidos e oferecer filtros de busca para o cliente.
  + Os filtros de busca devem ser:
  + Por código do débito
  + Por CPF/CNPJ do pagador
  + Por status do pagamento
+ A API deve ser capaz de realizar uma exclusão logica, mantendo o pagamento, porem com status inativo, desde que este ainda esteja com status Pendente de Processamento.

### Tecnologias

- Utilize o padrão REST na construção da API
- Todos os payloads enviados para a API estarão em formato JSON
- Todas as respostas da API devem estar em formato JSON
- Utilize Spring Boot com Java 8, 11 ou 17
- Utilize o banco de dados H2 embutido no Spring Boot

Se preferir, você pode utilizar outro banco de dados como MySQL, PostgreSQL, Oracle, etc, mas lembre-se de que precisamos testar a sua API, portanto você precisará subir o banco em uma plataforma como por exemplo Heroku ou Docker. Recomendamos a utilização do H2 para simplificar o processo.

### O que gostaríamos de ver

- Estrutura e organização do projeto
- Reutilização de código
- Código padronizado
- Boas práticas de programação
- Não crie complexidade desnecessária, mantenha o projeto simples 🙂

### Dúvidas
> Se você possui alguma dúvida sobre o desafio fique a vontade para encontrar em contato conosco.

### Entrega
> Crie um repositório público no GitHub com o código da sua solução. Quando você concluir o desafio, envie o link do seu repositório por e-mail para selecaoti@fadesp.org.br

### Boa sorte! 🙂 
