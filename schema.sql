CREATE TABLE "public"."Song" (
  "id" text NOT NULL,
  "title" text NOT NULL,
  "genre" text,
  "cd_track_number" text,
  "duration" integer NOT NULL,
  "year" text,
  "path" text NOT NULL,
  "albumId" text,
  "artistId" text,
  PRIMARY KEY ("id")
);

CREATE TABLE "public"."Album" (
  "id" text NOT NULL,
  "title" text NOT NULL,
  "genre" text,
  "year" text   ,
  "artistId" text   ,
  PRIMARY KEY ("id")
);

CREATE TABLE "public"."Artist" (
  "id" text   NOT NULL ,
  "title" text   NOT NULL ,
  PRIMARY KEY ("id")
);

CREATE TABLE "public"."Playlist" (
  "id" text   NOT NULL ,
  "title" text   NOT NULL ,
  PRIMARY KEY ("id")
);

CREATE TABLE "public"."_PlaylistToSong" (
  "A" text   NOT NULL ,
  "B" text   NOT NULL 
);

CREATE UNIQUE INDEX "Song.path_unique" ON "public"."Song"("path");

CREATE UNIQUE INDEX "Album.artistId_title_unique" ON "public"."Album"("artistId", "title");

CREATE UNIQUE INDEX "Artist.title_unique" ON "public"."Artist"("title");

CREATE UNIQUE INDEX "_PlaylistToSong_AB_unique" ON "public"."_PlaylistToSong"("A", "B");

CREATE INDEX "_PlaylistToSong_B_index" ON "public"."_PlaylistToSong"("B");

ALTER TABLE "public"."Song" ADD FOREIGN KEY ("albumId")REFERENCES "public"."Album"("id") ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE "public"."Song" ADD FOREIGN KEY ("artistId")REFERENCES "public"."Artist"("id") ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE "public"."Album" ADD FOREIGN KEY ("artistId")REFERENCES "public"."Artist"("id") ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE "public"."_PlaylistToSong" ADD FOREIGN KEY ("A")REFERENCES "public"."Playlist"("id") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "public"."_PlaylistToSong" ADD FOREIGN KEY ("B")REFERENCES "public"."Song"("id") ON DELETE CASCADE ON UPDATE CASCADE;