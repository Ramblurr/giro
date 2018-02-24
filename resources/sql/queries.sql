-- :name create-giro! :<!
-- :doc creates a new giro record
INSERT INTO giro
(name, url_apple, url_googleplay, url_amazon, url_windows, url_fallback, keen_write_key, keen_project_id, keen_event, ga_tracking_id)
VALUES
  (:name, :url_apple, :url_googleplay, :url_amazon, :url_windows, :url_fallback, :keen_write_key,
   :keen_project_id, :keen_event, :ga_tracking_id)
RETURNING admin_id;

-- :name update-giro! :n
-- :doc updates an existing giro record
UPDATE giro
SET name          = :name,
  url_apple       = :url_apple,
  url_googleplay  = :url_googleplay,
  url_amazon      = :url_amazon,
  url_windows     = :url_windows,
  url_fallback    = :url_fallback,
  keen_write_key  = :keen_write_key,
  keen_project_id = :keen_project_id,
  keen_event      = :keen_event,
  ga_tracking_id  = :ga_tracking_id

WHERE admin_id = :admin_id::uuid
RETURNING admin_id

-- :name get-giro :? :1
-- :doc retrieves a giro record given the id
SELECT *
FROM giro
WHERE id = :id

-- :name get-giro-by-admin-id :? :1
-- :doc retrieves a giro record given the admin id
SELECT *
FROM giro
WHERE admin_id = :admin_id::uuid

-- :name delete-giro ! :! :n
-- :doc deletes a giro record given the id
DELETE FROM giro
WHERE id = :id
