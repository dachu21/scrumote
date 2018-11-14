insert into role_t (id, version, name) values
(1, 1, 'ADMINISTRATOR'),
(2, 1, 'STANDARD');
alter sequence role_seq restart with 3;

insert into permission_t (id, version, name) values
(1, 1, 'admin'),
(2, 1, 'standard'),
(3, 1, 'swagger');
alter sequence permission_seq restart with 4;

insert into role_permissions_t (role_id, permission_id) values
(1, 1),
(2, 2);

insert into user_t (id, version, active, password, username, email, first_name, last_name) values
(1, 1, true, '$2a$10$4dA/YnrD2OhqYcN3KrnrZegKS7eJQRnwIkXhIkkOCBXCwNOnQ54vq', 'admin', 'john@server.com', 'John', 'Doe'),
(2, 1, true, '$2a$10$4dA/YnrD2OhqYcN3KrnrZegKS7eJQRnwIkXhIkkOCBXCwNOnQ54vq', 'swagger', 'jane@server.com', 'Jane', 'Doe');
alter sequence user_seq restart with 3;

insert into user_history_t(id, version, average_first_vote_level_difference, first_votes_above_estimate, first_votes_below_estimate, first_votes_equal_estimate, issues, plannings, votes, user_id) values
(1, 1, 0, 0, 0, 0, 0, 0, 0, 1),
(2, 1, 0, 0, 0, 0, 0, 0, 0, 2);
alter sequence user_history_seq restart with 3;

insert into user_roles_t (user_id, role_id) values
(1, 1),
(1, 2),
(2, 1),
(2, 2);

insert into user_permissions_t (user_id, permission_id) values
(2, 3);