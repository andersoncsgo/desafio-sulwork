-- backend/src/main/resources/db/migration/V1__init.sql
CREATE TABLE IF NOT EXISTS colaborador (
  id BIGSERIAL PRIMARY KEY,
  nome VARCHAR(200) NOT NULL UNIQUE,
  cpf VARCHAR(11) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS trazer (
  id BIGSERIAL PRIMARY KEY,
  colaborador_id BIGINT NOT NULL REFERENCES colaborador(id) ON DELETE CASCADE,
  data_do_cafe DATE NOT NULL,
  nome_item_normalizado VARCHAR(200) NOT NULL,
  trouxe BOOLEAN NULL,
  created_at TIMESTAMP NOT NULL DEFAULT NOW(),
  updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
  CONSTRAINT uq_trazer_data_item UNIQUE (data_do_cafe, nome_item_normalizado)
);

CREATE INDEX IF NOT EXISTS idx_trazer_data ON trazer(data_do_cafe);

-- Trigger para updated_at
CREATE OR REPLACE FUNCTION set_updated_at()
RETURNS TRIGGER AS $$
BEGIN
  NEW.updated_at = NOW();
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_set_updated_at ON trazer;
CREATE TRIGGER trg_set_updated_at BEFORE UPDATE ON trazer
FOR EACH ROW EXECUTE FUNCTION set_updated_at();