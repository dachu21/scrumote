insert into role_t (id, version, name) values
(1, 0, 'ADMINISTRATOR'),
(2, 0, 'SCRUM_MASTER'),
(3, 0, 'PRODUCT_OWNER'),
(4, 0, 'DEVELOPER');
alter sequence role_seq restart with 5;

insert into permission_t (id, version, name) values
(1, 0, 'swagger'),
                                                -- | ADM | PO & SM | DEV
(2, 0, 'createPlanning'),                       -- |  -  |    +    |  -
(3, 0, 'getAnyPlanning'),                       -- |  +  |    +    |  -
(4, 0, 'getMyPlanning'),                        -- |  -  |    +    |  +
(5, 0, 'getAllPlannings'),                      -- |  +  |    +    |  -
(6, 0, 'getMyPlannings'),                       -- |  -  |    -    |  +
(7, 0, 'updatePlanning'),                       -- |  -  |    +    |  -
(8, 0, 'finishPlanning'),                       -- |  -  |    +    |  -
(9, 0, 'deletePlanning'),                       -- |  +  |    -    |  -

(10, 0, 'createIssue'),                         -- |  -  |    +    |  -
(11, 0, 'getIssue'),                            -- |  +  |    +    |  +
(12, 0, 'getIssuesForPlanning'),                -- |  +  |    +    |  +
(13, 0, 'updateIssue'),                         -- |  -  |    +    |  -
(14, 0, 'activateIssue'),                       -- |  -  |    +    |  -
(15, 0, 'estimateIssue'),                       -- |  -  |    +    |  -
(16, 0, 'deleteIssue'),                         -- |  -  |    +    |  -

(17, 0, 'createVote'),                          -- |  -  |    -    |  +
(18, 0, 'getVotesForIssue'),                    -- |  +  |    +    |  +

(19, 0, 'createDeck'),                          -- |  +  |    -    |  -
(20, 0, 'getDeck'),                             -- |  +  |    +    |  +
(21, 0, 'updateDeck'),                          -- |  +  |    -    |  -
(22, 0, 'deleteDeck'),                          -- |  +  |    -    |  -

(23, 0, 'getAllSystemFeatures'),                -- |  +  |    -    |  -
(24, 0, 'updateSystemFeature'),                  -- |  +  |    -    |  -

(25, 0, 'createUser'),                          -- |  +  |    -    |  -
(26, 0, 'getMyUser'),                           -- |  +  |    +    |  +
(27, 0, 'getAnyUser'),                          -- |  +  |    -    |  -
(28, 0, 'getAllUsers'),                         -- |  +  |    +    |  -
(29, 0, 'getUsersForPlanning'),                 -- |  +  |    +    |  +
(30, 0, 'updateMyUser'),                        -- |  +  |    +    |  +
(31, 0, 'updateAnyUser'),                       -- |  +  |    -    |  -
(32, 0, 'updateMyUsersPassword'),               -- |  +  |    +    |  +
(33, 0, 'updateAnyUsersPassword'),              -- |  +  |    -    |  -

(34, 0, 'getMyUserHistory'),                    -- |  +  |    +    |  +
(35, 0, 'getAnyUserHistory');                   -- |  +  |    -    |  -
alter sequence permission_seq restart with 36;

insert into role_permissions_t (role_id, permission_id) values
-- ADMINISTRATOR
(1, 3), (1, 5), (1, 9),
(1, 11), (1, 12),
(1, 18),
(1, 19), (1, 20), (1, 21), (1, 22),
(1, 23), (1, 24),
(1, 25), (1, 26), (1, 27), (1, 28), (1, 29), (1, 30), (1, 31), (1, 32), (1, 33),
(1, 34), (1, 35),
-- SCRUM_MASTER
(2, 2), (2, 3), (2, 4), (2, 5), (2, 7), (2, 8),
(2, 10), (2, 11), (2, 12), (2, 13), (2, 14), (2, 15), (2, 16),
(2, 20),
(2, 26), (2, 28), (2, 29), (2, 30), (2, 32),
(2, 34),
-- PRODUCT_OWNER
(3, 2), (3, 3), (3, 4), (3, 5), (3, 7), (3, 8),
(3, 10), (3, 11), (3, 12), (3, 13), (3, 14), (3, 15), (3, 16),
(3, 20),
(3, 26), (3, 28), (3, 29), (3, 30), (3, 32),
(3, 34),
-- DEVELOPER
(4, 4), (4, 6),
(4, 11), (4, 12),
(4, 17), (4, 18),
(4, 20),
(4, 26), (4, 29), (4, 30), (4, 32),
(4, 34);

insert into system_feature_t (id, version, code, enabled) values
(1, 1, 'REGISTRATION', true);
alter sequence system_feature_seq restart with 2;

insert into user_t (id, version, active, password, username, email, first_name, last_name) values
(1, 0, true, '$2a$10$4dA/YnrD2OhqYcN3KrnrZegKS7eJQRnwIkXhIkkOCBXCwNOnQ54vq', 'admin', 'a@server.com', 'A', 'Xyz'),
(2, 0, true, '$2a$10$4dA/YnrD2OhqYcN3KrnrZegKS7eJQRnwIkXhIkkOCBXCwNOnQ54vq', 'master', 'm@server.com', 'M', 'Xyz'),
(3, 0, true, '$2a$10$4dA/YnrD2OhqYcN3KrnrZegKS7eJQRnwIkXhIkkOCBXCwNOnQ54vq', 'owner', 'o@server.com', 'O', 'Xyz'),
(4, 0, true, '$2a$10$4dA/YnrD2OhqYcN3KrnrZegKS7eJQRnwIkXhIkkOCBXCwNOnQ54vq', 'developer', 'd@server.com', 'D', 'Xyz'),
(5, 0, true, '$2a$10$4dA/YnrD2OhqYcN3KrnrZegKS7eJQRnwIkXhIkkOCBXCwNOnQ54vq', 'amod', 'amod@server.com', 'AMOD', 'Xyz');
alter sequence user_seq restart with 6;

insert into user_history_t(id, version, average_first_vote_level_difference, first_votes_above_estimate, first_votes_below_estimate, first_votes_equal_estimate, issues, plannings, votes, user_id) values
(1, 0, 0, 0, 0, 0, 0, 0, 0, 1),
(2, 0, 0, 0, 0, 0, 0, 0, 0, 2),
(3, 0, 0, 0, 0, 0, 0, 0, 0, 3),
(4, 0, 0, 0, 0, 0, 0, 0, 0, 4),
(5, 0, 0, 0, 0, 0, 0, 0, 0, 5);
alter sequence user_history_seq restart with 6;

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
(1, 0, 'DECK_1');
alter sequence deck_seq restart with 2;

insert into card_t (id, version, level, "value", deck_id) values
(1, 0, 1, '1', 1),
(2, 0, 2, '2', 1),
(3, 0, 3, '3', 1),
(4, 0, 4, '5', 1),
(5, 0, 5, '8', 1),
(6, 0, 6, '13', 1),
(7, 0, 7, '21', 1);
alter sequence card_seq restart with 8;

insert into planning_t (id, version, code, description, finished, name, deck_id, moderator_id) values
(1, 0, 'PLANNING_1', 'Planning description 1', false, 'Planning 1', 1, 5);
alter sequence planning_seq restart with 2;

insert into planning_users_t (planning_id, user_id) values
(1, 4),
(1, 5);

insert into issue_t (id, version, active, code, description, estimate, finished_iterations, name, planning_id) values
(1, 0, false, 'ISSUE_1', 'Issue description 1', null, 0, 'Issue 1', 1);
alter sequence issue_seq restart with 2;