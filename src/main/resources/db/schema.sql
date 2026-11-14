CREATE TABLE IF NOT EXISTS issue (
  id            uuid PRIMARY KEY,
  business_key  text NOT NULL UNIQUE,
  status        text NOT NULL,
  title         text NOT NULL,
  created_at    timestamptz NOT NULL DEFAULT now(),
  CONSTRAINT issue_status_known
    CHECK (status IN ('TODO', 'IN_PROGRESS', 'DONE'))
);