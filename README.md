# Etaskify
## Services

- Authorization
- Organization
- Task Manager
- User Manager

## Run


```sh
./run.sh
```

## Endpoints


| Name | URL | Note |
| ------ | ------ | ------ |
| Create Admin and Organization | [localhost:8083/api/admin/organization][PlDb] | POST |
| Login as admin | [localhost:8083/api/admin/login][PlGh] | POST (Bearer admin token required) |
| Create user | [localhost:8082/api/createUser][PlGd] |  POST (Bearer admin token required)|
| Login as user | [localhost:8081/api/user/login][PlOd] | POST |
| Create task | [localhost:8084/api/task][PlMe] | POST (Bearer user token required) |
| Get tasks | [localhost:8084/api/task/<organization_id>/tasks][PlMe] | GET (Bearer user token required) |

## MODELS


Create Admin and Organization :
```json
{
"name": String,
"phone": String,
"address": String,
"adminUsername": String,
"adminEmail": String,
"adminPassword": String
}
```

Login as admin :
```json
{
   "username": String,
   "password": String
}
```

Create User :
```json
{
	"name": String,
	"surname": String,
	"email": String
}
```

Login as user :
```json
{
	"email": String,
	"password": String
}
```

Create task :
```json
{
	"title": String,
	"description": String,
	"status": String,
	"deadline": LocalDate,
	"assignees": Set<UUID>,
	"organizationId": UUID
}
```

