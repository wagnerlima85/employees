CREATE DATABASE IF NOT EXISTS `exercise` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
CREATE TABLE IF NOT EXISTS `exercise`.`teams` (
		`id` mediumint not null AUTO_INCREMENT,
		`name` varchar(255) not null,
		`min_maturity` tinyint default 0 not null,
		`curr_maturity` tinyint default 0 not null,
		PRIMARY KEY  (`id`)
	);
	
CREATE TABLE IF NOT EXISTS `exercise`.`employees` (
		`id` mediumint NOT NULL AUTO_INCREMENT,
		`name` varchar(255) NOT NULL,
		`level` tinyint default 1,
		`birth_year` date not null,
		`admission_year` date not null,
		`last_progression_year` date not null,
		`weight` mediumint,
		`team_id` mediumint,
		PRIMARY KEY (`id`),
		FOREIGN KEY (`team_id`) references `teams`(`id`)
	);
