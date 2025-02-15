INSERT INTO PROJECT VALUES (default, 'Developer Kadaster');
INSERT INTO PROJECT VALUES (default, 'Developer Belastingdienst');
INSERT INTO PROJECT VALUES (default, 'Developer BOL');
INSERT INTO PROJECT VALUES (default, 'Developer NS');

INSERT INTO SKILL VALUES (default, 'Java');
INSERT INTO SKILL VALUES (default, 'Angular');
INSERT INTO SKILL VALUES (default, 'Kotlin');
INSERT INTO SKILL VALUES (default, 'OpenShift');
INSERT INTO SKILL VALUES (default, 'Google Cloud');
INSERT INTO SKILL VALUES (default, 'AWS');

INSERT INTO PROJECT_SKILLS VALUES (1, 3);
INSERT INTO PROJECT_SKILLS VALUES (1, 4);
INSERT INTO PROJECT_SKILLS VALUES (2, 1);
INSERT INTO PROJECT_SKILLS VALUES (2, 2);
INSERT INTO PROJECT_SKILLS VALUES (2, 4);
INSERT INTO PROJECT_SKILLS VALUES (3, 1);
INSERT INTO PROJECT_SKILLS VALUES (3, 2);
INSERT INTO PROJECT_SKILLS VALUES (4, 3);

INSERT INTO COMPANY VALUES (default, 'Craftsmen', 'Herenweg 82', 'Wilnis', '0302270934');
INSERT INTO COMPANY VALUES (default, 'TechForce1', 'De Regenboog 51', 'Nieuwkoop', '0302271818');
INSERT INTO COMPANY VALUES (default, 'CodeFoundry', 'Smaragd 6', 'McFarland', '0302270099');

INSERT INTO DEVELOPER VALUES (default, 'Dazzling', 'Developer', 1);
INSERT INTO DEVELOPER VALUES (default, 'Exciting', 'Engineer', 1);
INSERT INTO DEVELOPER VALUES (default, 'Funny', 'Frontender', 1);
INSERT INTO DEVELOPER VALUES (default, 'Optimistic', 'Opser', 2);
INSERT INTO DEVELOPER VALUES (default, 'Amazing', 'Angularian', 3);
INSERT INTO DEVELOPER VALUES (default, 'Confident', 'Cloud engineer', 1);

INSERT INTO DEVELOPER_SKILLS VALUES (1, 1);
INSERT INTO DEVELOPER_SKILLS VALUES (1, 3);
INSERT INTO DEVELOPER_SKILLS VALUES (2, 1);
INSERT INTO DEVELOPER_SKILLS VALUES (2, 3);
INSERT INTO DEVELOPER_SKILLS VALUES (2, 4);
INSERT INTO DEVELOPER_SKILLS VALUES (3, 2);
INSERT INTO DEVELOPER_SKILLS VALUES (4, 4);
INSERT INTO DEVELOPER_SKILLS VALUES (4, 5);
INSERT INTO DEVELOPER_SKILLS VALUES (4, 6);
INSERT INTO DEVELOPER_SKILLS VALUES (5, 2);
INSERT INTO DEVELOPER_SKILLS VALUES (6, 1);
INSERT INTO DEVELOPER_SKILLS VALUES (6, 3);
INSERT INTO DEVELOPER_SKILLS VALUES (6, 5);
INSERT INTO DEVELOPER_SKILLS VALUES (6, 6);

INSERT INTO JOB VALUES (default, 1, 1, '2022-01-01', 'back end developer');
INSERT INTO JOB VALUES (default, 2, 2, '2020-09-01', 'back end developer');
INSERT INTO JOB VALUES (default, 3, 3, '2021-10-01', 'front end developer');
INSERT INTO JOB VALUES (default, 4, 2, '2020-09-01', 'OpenShift engineer');
INSERT INTO JOB VALUES (default, 5, 1, '2020-09-01', 'Cloud engineer');