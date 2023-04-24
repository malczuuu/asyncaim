# MicroAIM

A prototype service for users management in MongoDB and Keycloak, developed for testing of **async synchronization**
between MongoDB and Keycloak.

Application serves a simple REST API that manages users. Creation (`POST`) and update (`PUT`) saves data only to
MongoDB. Synchronization with Keycloak is performed asynchronously.

This solution would require lots of additional work for error handling, especially in cases of user already existing in
Keycloak realm.

## API Docs

* ```txt
  GET /api/users
  ```

  Endpoint allows to retrieve list of all users.

* ```txt
  GET /api/users/{id}

  Response Body:
  {
      "id" : "3e148c3c-f57b-4599-a611-34a0c408107a",
      "username" : "john.doe",
      "email" : "john.doe@example.com",
      "create_status" : "completed",
      "update_status" : "idle",
      "update": {
          "username": "john.doe",
          "email": "john.doe@example.com"
      },
      "keycloak_id": "8173174b-0b23-4547-9a0d-59aabe11c844",
      "keycloak_profile": {
          "id": "8173174b-0b23-4547-9a0d-59aabe11c844",
          "username": "john.doe",
          "email": "john.doe@example.com"
      },
      "version": 7
  }
  ```

  Endpoint allows to retrieve user or to monitor its creation or update status, which are performed asynchronously.

* ```txt
  POST /api/users
  
  Request Body:
  {
      "username" : "username",
      "email" : "username@example.com"
  }
  
  Response Body:
  {
      "id" : "3e148c3c-f57b-4599-a611-34a0c408107a",
      "username" : "username",
      "email" : "username@example.com",
      "create_status" : "pending",
      "update_status" : "idle",
      "update" : null,
      "keycloak_id" : null,
      "keycloak_profile" : null,
      "version" : 0
  }
  ```

  Endpoint registers user creation. Creation status can be monitored using `GET /api/users/{id}` method. After user is
  created, Keycloak profile will be created asynchronously. Eventually, mentioned `GET` endpoint will
  provide `keycloak_id` and `keycloak_profile` and `create_status` will be `completed`.

* ```txt
  PUT /api/users/{id}
  
  Request Body:
  {
      "username" : "updated",
      "email" : "updated@example.com"
  }
  
  Response Body:
  {
      "id" : "3e148c3c-f57b-4599-a611-34a0c408107a",
      "username" : "username",
      "email" : "username@example.com",
      "create_status" : "completed",
      "update_status" : "pending",
      "update": {
          "username": "updated",
          "email": "updated@example.com"
      },
      "keycloak_id": "8173174b-0b23-4547-9a0d-59aabe11c844",
      "keycloak_profile": {
          "id": "8173174b-0b23-4547-9a0d-59aabe11c844",
          "username": "username",
          "email": "username@example.com"
      },
      "version": 3
  }
  ```

  Similarly to creation, update does not perform an actual update, but rather schedules update to execute
  asynchronously. Update procedure can be monitored with `GET` endpoint, just as before. It will eventually be updated
  and `update_status` will be set to `idle` again.

  **Note** that this is only demonstration and errors from Keycloak (such as `409 Conflict`) are not handled.
