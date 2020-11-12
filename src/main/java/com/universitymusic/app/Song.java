package com.universitymusic.app;



/**
 * Album
 */
public class Song {
  public String id;
  public String title;
  public String genre;
  public String cd_track_number;
  public int duration;
  public String year;
  public String path;
  public String albumId;
  public String artistId;

  public Song(String id, String title, String genre, String cd_track_number2, int duration, 
      String year2,
      String path, String albumId2, String artistId2) {
    this.id = id;
    this.title = title;
    this.genre = genre;
    this.cd_track_number = cd_track_number2;
    this.duration = duration;
    this.year = year2;
    this.path = path;
    this.albumId = albumId2;
    this.artistId = artistId2;
  }
}