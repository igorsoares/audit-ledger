CREATE TABLE audit_log(
    id BIGSERIAL PRIMARY KEY,
    event_type TEXT NOT NULL,
    ip_address TEXT NOT NULL,
    payload TEXT NOT NULL,
    previous_hash TEXT NOT NULL,
    current_hash TEXT NOT NULL,
    signature TEXT NOT NULL,
    signature_algo TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL
);

