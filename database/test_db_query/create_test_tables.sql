USE balance_game_community_test;

SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS member, balanceGame, balanceGameComment, balanceGameVote;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE member
(
    id BIGINT AUTO_INCREMENT,
    email VARCHAR(30) NOT NULL,
    password VARCHAR(30) NOT NULL,
    nickname VARCHAR(30) NOT NULL,
    isTempMember BOOL NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE balanceGame
(
    id BIGINT AUTO_INCREMENT,
    memberId BIGINT,
    question VARCHAR(255) NOT NULL,
    answer1 VARCHAR(255) NOT NULL,
    answer2 VARCHAR(255) NOT NULL,
    picture1 VARCHAR(255),
    picture2 VARCHAR(255),
    enrollmentTime DATETIME NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (memberId) REFERENCES member(id)
);

CREATE TABLE balanceGameComment
(
    id BIGINT AUTO_INCREMENT,
    memberId BIGINT,
    balanceGameId BIGINT,
    content VARCHAR(255) NOT NULL,
    writeTime DATETIME NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (memberId) REFERENCES member(id),
    FOREIGN KEY (balanceGameId) REFERENCES balanceGame(id)
);

CREATE TABLE balanceGameVote
(
    id BIGINT AUTO_INCREMENT,
    memberId BIGINT,
    balanceGameId BIGINT,
    answerNumber INT NOT NULL,
    difficulty INT,
    preference VARCHAR(20),

    PRIMARY KEY (id),
    FOREIGN KEY (memberId) REFERENCES member(id),
    FOREIGN KEY (balanceGameId) REFERENCES balanceGame(id)
);