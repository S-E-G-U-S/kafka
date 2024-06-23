CREATE TABLE IF NOT EXISTS TAXI_CLIENT
(
    ID                         BIGINT AUTO_INCREMENT PRIMARY KEY,
    USER_NAME                  VARCHAR(255) NOT NULL,
    CREATION_TIME_UTC          TIMESTAMP    NOT NULL,
    LAST_MODIFICATION_TIME_UTC TIMESTAMP
);

CREATE TABLE IF NOT EXISTS TAXI_CALL
(
    id                         BIGINT AUTO_INCREMENT PRIMARY KEY,
    USER_ID                    BIGINT                                            NOT NULL,
    DEPARTURES                 INT                                               NOT NULL,
    ARRIVALS                   INT                                               NOT NULL,
    STATUS                     ENUM ('PENDING','CONFIRMED','CANCELED','DELETED') NOT NULL,
    DRIVER_ID                  BIGINT,
    CREATION_TIME_UTC          TIMESTAMP                                         NOT NULL,
    LAST_MODIFICATION_TIME_UTC TIMESTAMP,
    FOREIGN KEY (USER_ID) REFERENCES TAXI_CLIENT (ID)
);

CREATE INDEX idx_taxi_call_user_id ON TAXI_CALL (USER_ID);

CREATE TABLE IF NOT EXISTS TAXI_RIDE
(
    id                         BIGINT AUTO_INCREMENT PRIMARY KEY,
    USER_ID                    BIGINT                                         NOT NULL,
    CALL_ID                    BIGINT                                         NOT NULL,
    DEPARTURES                 INT                                            NOT NULL,
    ARRIVALS                   INT                                            NOT NULL,
    STATUS                     ENUM ('NOT_STARTED','COMPLETED','IN_PROGRESS') NOT NULL,
    START_TIME                 TIMESTAMP                                      NOT NULL,
    ARRIVAL_TIME               TIMESTAMP,
    DRIVER_ID                  BIGINT,
    DRIVER_NAME                VARCHAR(255),
    DRIVER_PHONE_NUMBER        VARCHAR(255),
    VEHICLE_NUMBER             VARCHAR(255),
    CREATION_TIME_UTC          TIMESTAMP                                      NOT NULL,
    LAST_MODIFICATION_TIME_UTC TIMESTAMP,
    FOREIGN KEY (USER_ID) REFERENCES TAXI_CLIENT (ID),
    FOREIGN KEY (CALL_ID) REFERENCES TAXI_CALL (ID)
);

DROP TABLE TAXI_CALL;

CREATE INDEX idx_taxi_ride_call_id ON TAXI_RIDE (CALL_ID);
CREATE INDEX idx_taxi_call_user_id ON TAXI_RIDE (USER_ID);