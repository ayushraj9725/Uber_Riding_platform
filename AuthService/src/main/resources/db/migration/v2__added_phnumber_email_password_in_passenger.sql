
use uber1 ;

ALTER TABLE passenger
    ADD phone_number VARCHAR(255) NOT NULL,
    Add email        VARCHAR(255) NOT NULL,
    ADD password     VARCHAR(255) NOT NULL,
    MODIFY name VARCHAR(255) NOT NULL;