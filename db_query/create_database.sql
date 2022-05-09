CREATE DATABASE balance_game_community;
CREATE USER 'balance_game_community_dev'@'%' IDENTIFIED BY '1234';
GRANT ALL ON balance_game_community.* TO 'balance_game_community_dev'@'%';
