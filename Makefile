setup:
	make install && make db-setup

install:
	mvn install

build:
	mvn compile

run:
	mvn exec:java -Dexec.mainClass="com.universitymusic.app.App"

dev:
	make db-start && make build && make run

db-start:
	docker start music_player

db-build-image:
	docker run --detach --publish 5432:5432 -e POSTGRES_PASSWORD=postgres --name music_player postgres:10.12

db-copy-schema:
	docker cp schema.sql music_player:/schema.sql

db-create-tables:
	docker exec -d music_player psql postgres -h 127.0.0.1 -d music_player -f schema.sql

db-create:
	docker exec -d music_player createdb -h localhost -p 5432 -U postgres music_player

db-init:
	make db-create && make db-copy-schema && make db-create-tables

db-setup:
	make db-build-image && make db-start && make db-init