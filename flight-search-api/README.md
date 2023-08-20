# FLIGHT SEARCH API

### Requirement
- JDK 17
- Maven
- PostgreSQL

### Before Running 
- Create a database which name is f_s_db


### When Running

- Open swagger ui : http://localhost:8082/swagger-ui/index.html#/
- Create a new user with role "ADMIN" http://localhost:8082/api/portal/user/create 
- {
  "name":"gokhan",
  "password":"testpass",
  "email":"gokhan@test.com",
  "roles":"ADMIN"
  }
- OR insert a admin to t_user_info table manually
- name: gokhan
- email: gokhan@test.com
- password: $2a$10$dphCbIWc2hTSbIp.7D6ioOoS2w9lwkHmbUW8hbSbWrOZoC37.MPdO
- roles: ADMIN