# OSWorks : Java RESTful API ☕

Este é um projeto que visa fornecer a criação de uma API de gerenciamento de contrato, ordem de serviços (OS), projetos,cliente 
e fornecedor juntamente com a emissão de OS com base no contrato de serviço criado. 
O projeto inclui uma API em Java com Spring.

<p align="center">
     <a alt="Java">
        <img src="https://img.shields.io/badge/Java-v17-blue.svg" />
    </a>
    <a alt="Spring Boot">
        <img src="https://img.shields.io/badge/Spring%20Boot-v2.7.9-brightgreen.svg" />
    </a>
    <a alt="Maven">
        <img src="https://img.shields.io/badge/Maven-v3.9-lightgreen.svg" />
    </a>
</p>

---

## Sprints Do Projeto
O Projeto foi desenvolvido durante a matéria de Laboratório De Software organizada pelo Dr. Rodrigo Lisboa. Separados em grupo
cada grupo desenvolveu uma aplicação Web usando linguagem e framework de sua escolha, sendo o processo de desenvolvimento
a junção do RUP (*Rational Unified Process*) juntamente com a metodologia ágil *scrum*, no qual o desenvolvimento do produto é divido em
*sprints* sendo uma semana para execução de cada *sprint*

   - 1ª Sprint: Criação Das Entidades, Empresa, Endereço, Contrato e Ordem de Serviço juntamente com CRUD Inicial
   - 2ª Sprint: Criação Das Entidades, Projeto, Responsável, Autoridade e Usuário juntamente com CRUD Inicial
   - 3ª Sprint: Criação Das Regras Principais De Negócio e Ciclo de vida do Contrato, Ordem De Serviço e Projeto
   - 4ª Sprint: Criação Dos *Handles* da API, Adição De Segurança da aplicação e Adição da Documentação no *Swagger*

--- 

## Diagrama de Classes (Domínio da API)

```mermaid
classDiagram
    class UserModel {
        -String name
        -String username
        -String password
        -String email
        -Roles[] roles
        -DateTime creationDate
        -DateTime lastAccess
        -String status
    }

    class Additive {
        -Long functionPoints
        -LocalDate endDate
        -BigDecimal unitValue
        -ContractType[] contractTypes
        -String[] descriptions
        -Contract contract
    }

    class Address {
        -String postalCode
        -String backyard
        -String number
        -String complement
        -String neighborhood
        -String city
        -String uf
        -String status
    }

    class Company {
        -String CNPJ
        -CompanyType companyType
        -String companyName
        -String stateRegistration
        -Responsible responsible
        -String phoneNumber
        -String email
        -Address address
        -LocalDate bornDate
        -String status
    }

    class Contract {
        -String contractCode
        -Company company
        -LocalDate beginDate
        -LocalDate endDate
        -Long numberFunctionPoints
        -Long numberFunctionPointsTotal
        -BigDecimal unitValue
        -BigDecimal unitValueTotal
        -String[] descriptions
        -ContractType[] contractTypes
        -ServiceOrder[] os
        -Additive[] additives
    }

    class Project {
        -String name
        -String description
        -LocalDate beginDate
        -LocalDate endDate
        -String status
        -ServiceOrder serviceOrder
        -UserModel projectManager
    }

    class Responsible {
        -String CPF
        -String name
        -String RG
        -String phoneNumber
        -String email
        -String department
        -String post
        -String status
        -Address address
    }

    class Role {
        -String roleName
    }

    class ServiceOrder {
        -String osCode
        -String description
        -String hoursNumber
        -Integer numberFunctionPoints
        -LocalDate openDate
        -String status
        -Responsible responsible
        -Company company
        -Contract contract
    }

    UserModel "N" *-- "N" Role
    Additive "N" *-- "1" Contract
    Company "1" *-- "1" Responsible
    Company "1" *-- "1" Address
    Contract "1" *-- "1" Company
    Contract "1" *-- "N" ServiceOrder
    Contract "1" *-- "N" Additive
    Project "1" *-- "1" ServiceOrder
    Project "N" *-- "1" UserModel
    Responsible "1" *-- "1" Address
    ServiceOrder "1" *-- "1" Responsible
    ServiceOrder "N" *-- "1" Company
    ServiceOrder "N" *-- "1" Contract
```
--- 

## Configuração

Essas instruções fornecerão aos usuários as etapas necessárias para clonar o repositório e iniciar a aplicação em
diferentes ambientes (Unix e Windows).

1. Clone o repositório: `git clone git@github.com:samuelfilho-dev/osworks-api.git`
2. Instale o Container Postgres `docker run -p 5432:5432 --env POSTGRES_USER=root --env POSTGRES_PASSWORD=root --env POSTGRES_DB=os_works --pull missing postgres:latest`
3. Configure as Varivaveis de Ambiente em `application.yml`
4. Compile sua aplicação usando Maven: `mvn clean install`
5. Execulte a Aplicação Usando Java v17: `java -jar target/osworks-0.0.1-SNAPSHOT.jar`


> Caso Tenha dúvida de como instalar:
>  - [Docker](https://docs.docker.com/get-docker/)
>  - [Java v17](https://www.youtube.com/watch?v=QekeJBShCy4)
>  - [Maven](https://www.youtube.com/watch?v=edF1G8RYDTU)
--- 

## Documentação do Swagger

A documentação da API pode ser encontrada no Swagger. Para visualizá-la,
acesse: [Documentação do Swagger](http://localhost:8080/swagger-ui/index.html#/).

---

## Licença

Este projeto está licenciado sob a licença MIT. Consulte o
arquivo [(LICENSE)](https://github.com/samuelfilho-dev/osworks-api/blob/master/LICENSE) para obter.

--- 
## Autores

| [<img src="https://avatars3.githubusercontent.com/u/81279868?s=96&v=4"><br><sub>Samuel Filho</sub>](https://github.com/samuelfilho-dev) | [<img src="https://avatars3.githubusercontent.com/u/109193749?s=96&v=4"><br><sub>Paulo Sergio</sub>](https://github.com/ArautD) | [<img src="https://avatars3.githubusercontent.com/u/90012369?s=96&v=4"><br><sub>Fransualdo Lopes	</sub>](https://github.com/Fransualdo-Lopes) |
|:---------------------------------------------------------------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------------------------------------------------------------:|
|                                        [Linkedin](https://www.linkedin.com/in/samuelfilho-dev/)                                         |                              [Linkedin](https://www.linkedin.com/in/paulo-sergio-lemos-29741b125/)                              |                                      [Linkedin](https://www.linkedin.com/in/fransualdo-lopes-27ab2165/)                                       |
|                                                           Back-end Developer                                                            |                                                       Back-end Developer                                                        |                                                                    DevOps                                                                     |

