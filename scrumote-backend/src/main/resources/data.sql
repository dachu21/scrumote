insert into role_t (id, version, name) values
(1, 1, 'ADMINISTRATOR'),
(2, 1, 'SCRUM_MASTER'),
(3, 1, 'PRODUCT_OWNER'),
(4, 1, 'DEVELOPER');
alter sequence role_seq restart with 5;

insert into permission_t (id, version, name) values
(1, 1, 'swagger'),
(2, 1, 'createPlanning'), -- PO & SM
(3, 1, 'getAnyPlanning'), -- PO & SM
(4, 1, 'getMyPlanning'), -- DEV
(5, 1, 'getAllPlannings'), -- PO & SM
(6, 1, 'getMyPlannings'), -- DEV
(7, 1, 'updatePlanning'), -- PO & SM
(8, 1, 'finishPlanning'), -- PO & SM
(9, 1, 'deletePlanning'), -- ADM
(10, 1, 'createIssue'), -- PO & SM
(11, 1, 'getIssue'), -- DEV
(12, 1, 'getIssuesForPlanning'), -- DEV
(13, 1, 'updateIssue'), -- PO & SM
(14, 1, 'activateIssue'), -- PO & SM
(15, 1, 'estimateIssue'), -- PO & SM
(16, 1, 'deleteIssue'), -- PO & SM
(17, 1, 'createVote'), -- DEV
(18, 1, 'getVotesForIssue'), -- DEV
(19, 1, 'createDeck'), -- ADM
(20, 1, 'getDeck'), -- ADM & DEV & PO & SM
(21, 1, 'updateDeck'), -- ADM
(22, 1, 'deleteDeck'); -- ADM
alter sequence permission_seq restart with 19;

insert into role_permissions_t (role_id, permission_id) values
(1, 9), (1, 19), (1, 20), (1, 21), (1, 22),
(2, 2), (2, 3), (2, 5), (2, 7), (2, 8), (2, 10), (2, 13), (2, 14), (2, 15), (2, 16), (2, 20),
(3, 2), (3, 3), (3, 5), (3, 7), (3, 8), (3, 10), (3, 13), (3, 14), (3, 15), (3, 16), (3, 20),
(4, 4), (4, 6), (4, 11), (4, 12), (4, 17), (4, 18), (4, 20);

insert into user_t (id, version, active, password, username, email, first_name, last_name) values
(1, 1, true, '$2a$10$4dA/YnrD2OhqYcN3KrnrZegKS7eJQRnwIkXhIkkOCBXCwNOnQ54vq', 'admin', 'a@server.com', 'A', 'Xyz'),
(2, 1, true, '$2a$10$4dA/YnrD2OhqYcN3KrnrZegKS7eJQRnwIkXhIkkOCBXCwNOnQ54vq', 'master', 'm@server.com', 'M', 'Xyz'),
(3, 1, true, '$2a$10$4dA/YnrD2OhqYcN3KrnrZegKS7eJQRnwIkXhIkkOCBXCwNOnQ54vq', 'owner', 'o@server.com', 'O', 'Xyz'),
(4, 1, true, '$2a$10$4dA/YnrD2OhqYcN3KrnrZegKS7eJQRnwIkXhIkkOCBXCwNOnQ54vq', 'developer', 'd@server.com', 'D', 'Xyz'),
(5, 1, true, '$2a$10$4dA/YnrD2OhqYcN3KrnrZegKS7eJQRnwIkXhIkkOCBXCwNOnQ54vq', 'amod', 'amod@server.com', 'AMOD', 'Xyz');
alter sequence user_seq restart with 5;

insert into user_history_t(id, version, average_first_vote_level_difference, first_votes_above_estimate, first_votes_below_estimate, first_votes_equal_estimate, issues, plannings, votes, user_id) values
(1, 1, 0, 0, 0, 0, 0, 0, 0, 1),
(2, 1, 0, 0, 0, 0, 0, 0, 0, 2);
alter sequence user_history_seq restart with 3;

insert into user_roles_t (user_id, role_id) values
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 1),
(5, 2),
(5, 3),
(5, 4);

insert into user_permissions_t (user_id, permission_id) values
(1, 1),
(2, 1),
(3, 1),
(4, 1),
(5, 1);

insert into deck_t (id, version, name) values
(1, 1, 'DECK_1');
alter sequence deck_seq restart with 2;

insert into card_t (id, version, level, "value", deck_id) values
(1, 1, 1, '1', 1),
(2, 1, 2, '2', 1),
(3, 1, 3, '3', 1),
(4, 1, 4, '5', 1),
(5, 1, 5, '8', 1),
(6, 1, 6, '13', 1),
(7, 1, 7, '21', 1);
alter sequence card_seq restart with 8;

insert into planning_t (id, version, code, description, finished, name, deck_id, moderator_id) values
(1, 1, 'PLANNING_1', 'Planning description 1', false, 'Planning 1', 1, 3);
alter sequence planning_seq restart with 2;

insert into planning_users_t (planning_id, user_id) values
(1, 4),
(1, 5);

insert into issue_t (id, version, active, code, description, estimate, finished_iterations, name, planning_id) values
(1, 1, false, 'ISSUE_1', 'Issue description 1', null, 0, 'Issue 1', 1);
alter sequence issue_seq restart with 2;