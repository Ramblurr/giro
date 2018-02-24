CREATE TABLE giro
(
  id              TEXT PRIMARY KEY,
  admin_id        UUID UNIQUE       NOT NULL  DEFAULT gen_random_uuid(),
  created         TIMESTAMP                   DEFAULT current_timestamp,
  name            TEXT              NOT NULL,
  url_apple       TEXT,
  url_googleplay  TEXT,
  url_amazon      TEXT,
  url_windows     TEXT,
  url_fallback    TEXT,
  keen_write_key  TEXT,
  keen_project_id TEXT,
  keen_event      TEXT,
  ga_tracking_id  TEXT
);
--;;
CREATE TRIGGER trigger_genid
  BEFORE INSERT
  ON giro
  FOR EACH ROW EXECUTE PROCEDURE unique_short_id();



