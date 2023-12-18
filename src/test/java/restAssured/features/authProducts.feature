Feature: operation practice

  Scenario: GET with authentication
    Given I perform an authentication for "auth/login" with a body of login
      | email            | password |
      | nilson@email.com | nilson   |
    And I perform a "GET" op for "products"
#    Then I should see the "name" as "Product001" for "products"
  Then validate schema

  Scenario: POST call with authentication
    Given I perform an authentication for "auth/login" with a body of login
      | email            | password |
      | nilson@email.com | nilson   |
    And I perform a "POST" op for "transactions" with datatable
      | cost | quantity | productId | shipment |
      | 32   | 12       | 2         | 1        |
    Then I should see the "cost" as "32" for "transactions"

  Scenario: DELETE call with authentication
    Given I perform an authentication for "auth/login" with a body of login
      | email            | password |
      | nilson@email.com | nilson   |
    And I perform a "delete" op for "products/2"
    Then I should have the status code as "200"

  Scenario: PATCH call with authentication
    Given I perform an authentication for "auth/login" with a body of login
      | email            | password |
      | nilson@email.com | nilson   |
    And I perform a "PATCH" op for "products/4" with datatable
      | familyId |
      | 2324     |
    Then I should have the status code as "200"

  Scenario: PUT call with authentication
    Given I perform an authentication for "auth/login" with a body of login
      | email            | password |
      | nilson@email.com | nilson   |
    And I perform a "put" op for "products/4" with datatable
      | name       | familyId |
      | Product001 | 32       |
    Then I should have the status code as "200"








