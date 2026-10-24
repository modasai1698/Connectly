FROM mysql:8.4

# The official MySQL image runs .sql files in this directory when the
# database data directory is first initialized.
COPY init/01-schema.sql /docker-entrypoint-initdb.d/01-schema.sql
