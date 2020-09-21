INSERT INTO `users_roles`(`user_id`, `role_id`) VALUES (1, 1);

INSERT INTO `persistent_logins`(`username`, `series`, `token`, `last_used`) VALUES ('test', 'D2daUEOOU09nffS8aB/8Nw==', 'MV4Bs0DPc/0U/dOUgBnSOw==', '2020-09-19 11:24:15');
INSERT INTO `persistent_logins`(`username`, `series`, `token`, `last_used`) VALUES ('test', 'PNeBWhHMzRDhXwUzCtWz0w==', 'f7XuO7RAjWAcb97ATXGk8A==', '2020-09-21 20:55:40');

INSERT INTO `role`(`id`, `name`) VALUES (1, 'ROLE_ADMINISTRATOR');
INSERT INTO `role`(`id`, `name`) VALUES (2, 'ROLE_STAFF');
INSERT INTO `role`(`id`, `name`) VALUES (3, 'ROLE_ANONYMOUS');

INSERT INTO `roles_authorities`(`role_id`, `authority_id`) VALUES (1, 1);
INSERT INTO `roles_authorities`(`role_id`, `authority_id`) VALUES (1, 2);
INSERT INTO `roles_authorities`(`role_id`, `authority_id`) VALUES (2, 2);

INSERT INTO `user`(`id`, `username`, `password`) VALUES (1, 'test', '$2a$10$87nDI72x/1uE.mN1Aj52guCMDa7bKZym7jtbHNyYdAKfnZtIei6Kq');

INSERT INTO `authority`(`id`, `name`) VALUES (1, 'READ_AUTHORITY');
INSERT INTO `authority`(`id`, `name`) VALUES (2, 'WRITE_AUTHORITY');
