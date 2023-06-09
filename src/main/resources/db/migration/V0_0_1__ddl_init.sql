CREATE TABLE IF NOT EXISTS release (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   name VARCHAR(255) NOT NULL,
   description VARCHAR(255) NOT NULL,
   status VARCHAR(255) NOT NULL,
   release_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   CONSTRAINT pk_release PRIMARY KEY (id)
);
