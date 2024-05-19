SET SEARCH_PATH TO public, "$user","public";

-- Lock Database
UPDATE public.databasechangeloglock SET LOCKED = TRUE, LOCKEDBY = 'MacBook-Pro-cua-Hoang-2.local (192.168.1.40)', LOCKGRANTED = NOW() WHERE ID = 1 AND LOCKED = FALSE;

SET SEARCH_PATH TO public, "$user","public";

SET SEARCH_PATH TO public, "$user","public";

-- *********************************************************************
-- Update Database Script
-- *********************************************************************
-- Change Log: src/main/resources/config/liquibase/changelog.xml
-- Ran at: 5/18/24, 12:11 AM
-- Against: postgres@jdbc:postgresql://localhost:5432/postgres
-- Liquibase version: 4.27.0
-- *********************************************************************

SET SEARCH_PATH TO public, "$user","public";

-- Changeset src/main/resources/config/liquibase/changelog.xml::1715965906865-1::hoangtheanh (generated)
SET SEARCH_PATH TO public, "$user","public";

CREATE TABLE public.game (game_id VARCHAR(255) NOT NULL, CONSTRAINT pk_game PRIMARY KEY (game_id));

INSERT INTO public.databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('1715965906865-1', 'hoangtheanh (generated)', 'src/main/resources/config/liquibase/changelog.xml', NOW(), 18, '9:f21277af96be76deb77b7c50588b320c', 'createTable tableName=game', '', 'EXECUTED', NULL, NULL, '4.27.0', '5965915786');

-- Changeset src/main/resources/config/liquibase/changelog.xml::1715965906865-2::hoangtheanh (generated)
SET SEARCH_PATH TO public, "$user","public";

CREATE TABLE public.ganswer (aid BIGINT NOT NULL, content VARCHAR(255), is_correct BOOLEAN, qid BIGINT NOT NULL, game_id VARCHAR(255) NOT NULL, CONSTRAINT pk_ganswer PRIMARY KEY (aid, qid, game_id));

INSERT INTO public.databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('1715965906865-2', 'hoangtheanh (generated)', 'src/main/resources/config/liquibase/changelog.xml', NOW(), 19, '9:b6d1166d92c8681d81c57314311e010b', 'createTable tableName=ganswer', '', 'EXECUTED', NULL, NULL, '4.27.0', '5965915786');

-- Changeset src/main/resources/config/liquibase/changelog.xml::1715965906865-3::hoangtheanh (generated)
SET SEARCH_PATH TO public, "$user","public";

CREATE TABLE public.gquestion (qid BIGINT NOT NULL, statement VARCHAR(255), time INTEGER, game_id VARCHAR(255) NOT NULL, CONSTRAINT pk_gquestion PRIMARY KEY (qid, game_id));

INSERT INTO public.databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('1715965906865-3', 'hoangtheanh (generated)', 'src/main/resources/config/liquibase/changelog.xml', NOW(), 20, '9:971f761b89563ff9fa1b53673a17a9ee', 'createTable tableName=gquestion', '', 'EXECUTED', NULL, NULL, '4.27.0', '5965915786');

-- Changeset src/main/resources/config/liquibase/changelog.xml::1715965906865-4::hoangtheanh (generated)
SET SEARCH_PATH TO public, "$user","public";

ALTER TABLE public.ganswer ADD CONSTRAINT FK_GANSWER_ON_QIGAID FOREIGN KEY (qid, game_id) REFERENCES public.gquestion (qid, game_id);

INSERT INTO public.databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('1715965906865-4', 'hoangtheanh (generated)', 'src/main/resources/config/liquibase/changelog.xml', NOW(), 21, '9:5896fee87270543ee5e99f7dbb78ddf4', 'addForeignKeyConstraint baseTableName=ganswer, constraintName=FK_GANSWER_ON_QIGAID, referencedTableName=gquestion', '', 'EXECUTED', NULL, NULL, '4.27.0', '5965915786');

-- Changeset src/main/resources/config/liquibase/changelog.xml::1715965906865-5::hoangtheanh (generated)
SET SEARCH_PATH TO public, "$user","public";

ALTER TABLE public.gquestion ADD CONSTRAINT FK_GQUESTION_ON_GAME FOREIGN KEY (game_id) REFERENCES public.game (game_id);

INSERT INTO public.databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('1715965906865-5', 'hoangtheanh (generated)', 'src/main/resources/config/liquibase/changelog.xml', NOW(), 22, '9:9899253d7fa5fe3c1ee4edc6526a461d', 'addForeignKeyConstraint baseTableName=gquestion, constraintName=FK_GQUESTION_ON_GAME, referencedTableName=game', '', 'EXECUTED', NULL, NULL, '4.27.0', '5965915786');

-- Release Database Lock
SET SEARCH_PATH TO public, "$user","public";

UPDATE public.databasechangeloglock SET LOCKED = FALSE, LOCKEDBY = NULL, LOCKGRANTED = NULL WHERE ID = 1;

SET SEARCH_PATH TO public, "$user","public";

