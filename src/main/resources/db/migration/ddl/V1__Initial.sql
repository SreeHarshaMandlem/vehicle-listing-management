CREATE TABLE IF NOT EXISTS `DEALER` (
    `ID`                                VARCHAR(255) NOT NULL PRIMARY KEY,
    `NAME`                              VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS `LISTING` (
    `ID`                                VARCHAR(255) NOT NULL PRIMARY KEY,
    `CODE`                              VARCHAR(255) DEFAULT NULL,
    `COLOR`                             VARCHAR(255) DEFAULT NULL,
    `DEALER_ID`                         VARCHAR(255) NOT NULL,
    `MAKE`                              VARCHAR(255) DEFAULT NULL,
    `MODEL`                             VARCHAR(255) DEFAULT NULL,
    `POWER`                             INTEGER(4) DEFAULT NULL,
    `PRICE`                             NUMBER(10) DEFAULT NULL,
    `YEAR`                              INTEGER(4) DEFAULT NULL,
    FOREIGN KEY (`DEALER_ID`) REFERENCES DEALER(ID)
);