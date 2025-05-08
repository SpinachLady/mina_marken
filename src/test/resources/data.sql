
CREATE TABLE scout_group (ID BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(20), min_age INT, max_age INT);
INSERT INTO scout_group (id, name, min_age, max_age) VALUES (1L, 'testGroup_one', 8, 9),
                                                            (2L, 'testGroup_two', 12, 14),
                                                            (3L, 'testGroup_three', 15, 17);

CREATE TABLE patch_type (ID BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(40));

CREATE TABLE patch (ID BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(40), img_url VARCHAR(500), type_id BIGINT, FOREIGN KEY (type_id) REFERENCES patch_type(ID));
INSERT INTO patch(ID, name, type_id, img_url) VALUES (1L, 'testPatch_one', null, 'testLink_one'),
                                                     (2L, 'testPatch_two', null, 'testLink_two'),
                                                     (3L, 'testPatch_three', null, 'testLink_three');

CREATE TABLE patch_order (ID BIGINT AUTO_INCREMENT PRIMARY KEY, patch_id BIGINT, FOREIGN KEY (patch_id) REFERENCES patch(ID), scout_group_id BIGINT, FOREIGN KEY (scout_group_id) REFERENCES scout_group(ID), term varchar(2), order_year int, is_archived varchar(2));
INSERT INTO patch_order(ID, patch_id, scout_group_id, term, order_year, is_archived) VALUES (1L, 1L, 1L, 'HT', 2024, 0),
                                                                                     (2L, 2L, 1L, 'HT', 2024, 0),
                                                                                     (3L, 3L, 1L, 'VT', 2020, 1);
