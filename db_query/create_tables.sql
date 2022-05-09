USE balance_game_community;

CREATE TABLE member
(
    id BIGINT AUTO_INCREMENT,
    email VARCHAR(30) NOT NULL,
    password VARCHAR(30) NOT NULL,
    nickname VARCHAR(30) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE balanceGame
(
    id BIGINT AUTO_INCREMENT,
    memberId BIGINT,
    question VARCHAR(255) NOT NULL,
    answer1 VARCHAR(255) NOT NULL,
    answer2 VARCHAR(255) NOT NULL,
    enrollmentTime DATETIME NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (memberId) REFERENCES member(id)
);

CREATE TABLE balanceGameComment
(
    id BIGINT AUTO_INCREMENT,
    memberId BIGINT,
    balanceGameId BIGINT,
    writeTime DATETIME NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (memberId) REFERENCES member(id),
    FOREIGN KEY (balanceGameId) REFERENCES balanceGame(id)
);

CREATE TABLE balanceGameDifficultyVote
(
    id BIGINT AUTO_INCREMENT,
    memberId BIGINT,
    balanceGameId BIGINT,
    difficulty VARCHAR(20) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (memberId) REFERENCES member(id),
    FOREIGN KEY (balanceGameId) REFERENCES balanceGame(id)
);