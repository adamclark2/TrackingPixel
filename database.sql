-- SQL to run when creating a new db
-- Remember to setup permissions

CREATE SCHEMA `trackingpixels` DEFAULT CHARACTER SET DEFAULT ;

CREATE TABLE `trackingpixels`.`users` (
  `username` LONGTEXT NOT NULL,
  `password` LONGTEXT NOT NULL);

CREATE TABLE `trackingpixels`.`pixels` (
  `id` INT NOT NULL,
  `email` LONGTEXT NULL,
  `campaign` LONGTEXT NULL,
  PRIMARY KEY (`id`));

ALTER TABLE `trackingpixels`.`pixels` 
CHANGE COLUMN `id` `id` INT(11) NOT NULL AUTO_INCREMENT ;



CREATE TABLE `trackingpixels`.`pixel_visits` (
  `pixel_id` INT NOT NULL,
  `timestamp` INT NULL,
  `content` LONGTEXT NULL,
  PRIMARY KEY (`pixel_id`));

ALTER TABLE `trackingpixels`.`pixel_visits` 
CHANGE COLUMN `pixel_id` `pixel_id` INT(11) NULL ,
DROP PRIMARY KEY;
;

-- Insert a new Password
INSERT into users (username, password) 
values ("adam.clark", sha2("Hello World", 224));

-- Update a Password
UPDATE users set password = sha2("abcdef12345ASDFGHJ", 224) where username="adam.clark";