insert into role_t (id, version, name) values (1, 1, 'ADMINISTRATOR');
insert into role_t (id, version, name) values (2, 1, 'STANDARD');
alter sequence role_seq restart with 3;

insert into permission_t (id, version, name) values (1, 1, 'swagger');
alter sequence permission_seq restart with 2;

insert into role_permissions_t (role_id, permission_id) values (1, 1);

insert into user_t (id, version, active, password, username)
values (1, 1, true, '$2a$10$4dA/YnrD2OhqYcN3KrnrZegKS7eJQRnwIkXhIkkOCBXCwNOnQ54vq', 'admin');
alter sequence user_seq restart with 2;

insert into user_roles_t (user_id, role_id) values (1, 1);
insert into user_roles_t (user_id, role_id) values (1, 2);