insert into role_t (id, version, name) values
(1, 0, 'ADMINISTRATOR'),
(2, 0, 'SCRUM_MASTER'),
(3, 0, 'PRODUCT_OWNER'),
(4, 0, 'DEVELOPER');
alter sequence role_seq restart with 5;

insert into permission_t (id, version, name) values
(1, 0, 'swagger'),                              -- | ADM | PO & SM | DEV

(2, 0, 'getAllSystemFeatures'),                 -- |  +  |    -    |  -
(3, 0, 'updateSystemFeature'),                  -- |  +  |    -    |  -

(4, 0, 'getAllRoles'),                          -- |  +  |    -    |  -

(5, 0, 'createUser'),                           -- |  +  |    -    |  -
(6, 0, 'getMyUser'),                            -- |  +  |    +    |  +
(7, 0, 'getAnyUser'),                           -- |  +  |    -    |  -
(8, 0, 'getAllUsers'),                          -- |  +  |    +    |  -
(9, 0, 'getAllDevelopers'),                     -- |  +  |    +    |  -
(10, 0, 'getUsersForPlanning'),                 -- |  +  |    +    |  +
(11, 0, 'updateMyUser'),                        -- |  +  |    +    |  +
(12, 0, 'updateMyUsersPassword'),               -- |  +  |    +    |  +
(13, 0, 'updateAnyUser'),                       -- |  +  |    -    |  -
(14, 0, 'updateAnyUsersPassword'),              -- |  +  |    -    |  -
(15, 0, 'manageAnyUser'),                       -- |  +  |    -    |  -

(16, 0, 'getMyUserStats'),                      -- |  +  |    +    |  +
(17, 0, 'getAnyUserStats'),                     -- |  +  |    +    |  -

(18, 0, 'createDeck'),                          -- |  +  |    -    |  -
(19, 0, 'getDeck'),                             -- |  +  |    +    |  +
(20, 0, 'getAllDecks'),                         -- |  +  |    +    |  -
(21, 0, 'updateDeck'),                          -- |  +  |    -    |  -
(22, 0, 'deleteDeck'),                          -- |  +  |    -    |  -

(23, 0, 'createPlanning'),                      -- |  -  |    +    |  -
(24, 0, 'getMyPlanning'),                       -- |  -  |    -    |  +
(25, 0, 'getMyPlannings'),                      -- |  -  |    -    |  +
(26, 0, 'getAnyPlanning'),                      -- |  -  |    +    |  -
(27, 0, 'getAllPlannings'),                     -- |  -  |    +    |  -
(28, 0, 'updatePlanning'),                      -- |  -  |    +    |  -
(29, 0, 'finishPlanning'),                      -- |  -  |    +    |  -
(30, 0, 'deletePlanning'),                      -- |  -  |    +    |  -

(31, 0, 'createIssue'),                         -- |  -  |    +    |  -
(32, 0, 'getIssue'),                            -- |  -  |    +    |  +
(33, 0, 'getIssuesForPlanning'),                -- |  -  |    +    |  +
(34, 0, 'updateIssue'),                         -- |  -  |    +    |  -
(35, 0, 'activateIssue'),                       -- |  -  |    +    |  -
(36, 0, 'estimateIssue'),                       -- |  -  |    +    |  -
(37, 0, 'deleteIssue'),                         -- |  -  |    +    |  -

(38, 0, 'createVote'),                          -- |  -  |    -    |  +
(39, 0, 'getVotesForIssue'),                    -- |  -  |    +    |  +
(40, 0, 'checkIfMyVoteExists'),                 -- |  -  |    -    |  +
alter sequence permission_seq restart with 41;

insert into role_permissions_t (role_id, permission_id) values
-- ADMINISTRATOR
(1, 2), (1, 3),
(1, 4),
(1, 5), (1, 6), (1, 7), (1, 8), (1, 9), (1, 10), (1, 11), (1, 12), (1, 13), (1, 14), (1, 15),
(1, 16), (1, 17),
(1, 18), (1, 19), (1, 20), (1, 21), (1, 22),
-- SCRUM_MASTER
(2, 6), (2, 8), (2, 9), (2, 10), (2, 11), (2, 12)
(2, 16), (2, 17),
(2, 19), (2, 20),
(2, 23), (2, 26), (2, 27), (2, 28), (2, 29), (2, 30),
(2, 31), (2, 32), (2, 33), (2, 34), (2, 35), (2, 36), (2, 37),
(2, 39),
-- PRODUCT_OWNER
(3, 6), (3, 8), (3, 9), (3, 10), (3, 11), (3, 12)
(3, 16), (3, 17),
(3, 19), (3, 20),
(3, 23), (3, 26), (3, 27), (3, 28), (3, 29), (3, 30),
(3, 31), (3, 32), (3, 33), (3, 34), (3, 35), (3, 36), (3, 37),
(3, 39),
-- DEVELOPER
(4, 6), (4, 10), (4, 11), (4, 12)
(4, 16),
(4, 19),
(4, 24), (4, 25),
(4, 32), (4, 33),
(4, 38), (4, 39), (4, 40);

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

insert into user_stats_t(id, version, average_first_vote_level_difference, first_votes_above_estimate, first_votes_below_estimate, first_votes_equal_estimate, issues, plannings, votes, user_id) values
(1, 0, 0, 0, 0, 0, 0, 0, 0, 1),
(2, 0, 0, 0, 0, 0, 0, 0, 0, 2),
(3, 0, 0, 0, 0, 0, 0, 0, 0, 3),
(4, 0, 0, 0, 0, 0, 0, 0, 0, 4),
(5, 0, 0, 0, 0, 0, 0, 0, 0, 5);
alter sequence user_stats_seq restart with 6;

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