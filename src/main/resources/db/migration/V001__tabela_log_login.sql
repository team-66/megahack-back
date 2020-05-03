CREATE TABLE public.login_log (
  id BIGSERIAL NOT NULL,
  usuario VARCHAR(50) NOT NULL,
  ip VARCHAR(50),
  PRIMARY KEY(id)
) 