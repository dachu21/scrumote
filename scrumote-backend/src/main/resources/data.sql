insert into role_t (id, version, name) values (1, 1, 'ADMINISTRATOR');
insert into role_t (id, version, name) values (2, 1, 'STANDARD');
alter sequence role_seq restart with 3;

insert into permission_t (id, version, name) values (1, 1, 'admin');
insert into permission_t (id, version, name) values (2, 1, 'standard');
insert into permission_t (id, version, name) values (3, 1, 'swagger');
alter sequence permission_seq restart with 4;

insert into role_permissions_t (role_id, permission_id) values (1, 1);
insert into role_permissions_t (role_id, permission_id) values (2, 2);

insert into user_t (id, version, active, password, username, email, first_name, last_name)
values (1, 1, true, '$2a$10$4dA/YnrD2OhqYcN3KrnrZegKS7eJQRnwIkXhIkkOCBXCwNOnQ54vq', 'admin', 'john@server.com', 'John', 'Doe');
insert into user_t (id, version, active, password, username, email, first_name, last_name)
values (2, 1, true, '$2a$10$4dA/YnrD2OhqYcN3KrnrZegKS7eJQRnwIkXhIkkOCBXCwNOnQ54vq', 'swagger', 'jane@server.com', 'Jane', 'Doe');
alter sequence user_seq restart with 3;

insert into user_roles_t (user_id, role_id) values (1, 1);
insert into user_roles_t (user_id, role_id) values (1, 2);
insert into user_roles_t (user_id, role_id) values (2, 1);
insert into user_roles_t (user_id, role_id) values (2, 2);

insert into user_permissions_t (user_id, permission_id) values (2, 3);