# FLIGHT SEARCH API

### Requirement
- JDK 17
- Maven
- PostgreSQL

### Before Running 
- Create a database which name is f_s_db


### When Running

- To open swagger ui : http://localhost:8082/swagger-ui/index.html#/
- Create a new user with role "ADMIN" http://localhost:8082/api/portal/user/create 
- {
  "name":"test",
  "password":"testpass",
  "email":"test@test.com",
  "roles":"ADMIN"
  }
- OR insert an admin to the t_user_info table manually
- name: gokhan
- email: gokhan@test.com
- password: $2a$10$dphCbIWc2hTSbIp.7D6ioOoS2w9lwkHmbUW8hbSbWrOZoC37.MPdO
- roles: ADMIN
