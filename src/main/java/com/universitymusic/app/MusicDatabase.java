package com.universitymusic.app;

import java.io.File;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.*;
import com.mpatric.mp3agic.*;

public class MusicDatabase {
  Connection c = null;

  public MusicDatabase() {
    DBConnection dbc = new DBConnection();

    try {
      c = dbc.getConnection();
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
  }
  // Artist
  List<Artist> getArtists() {
    String query = "SELECT \"public\".\"Artist\".\"id\", \"public\".\"Artist\".\"title\" FROM \"public\".\"Artist\" WHERE 1=1 ORDER BY \"public\".\"Artist\".\"title\" ASC";

    try {
      System.out.print("[AUDIO_PLAYER]: RUNNING QUERY");

      PreparedStatement pst = c.prepareStatement(query);

      pst.setEscapeProcessing(false);

      ResultSet rs = pst.executeQuery();

      List<Artist> artists = new ArrayList<Artist>();

      while (rs.next()) {
        String id = rs.getString(1);
        String title = rs.getString(2);

        artists.add(new Artist(id, title));
      }
      return artists;
    } catch (SQLException e) {
      System.out.print("[AUDIO_PLAYER]: SQL ERROR");
      e.printStackTrace();
      List<Artist> artists = new ArrayList<Artist>();

      return artists;
    }
  }

  List<Album> getArtistAlbums(String artist_id) {
    String query = "SELECT \"public\".\"Album\".\"id\", \"public\".\"Album\".\"title\" FROM \"public\".\"Album\" WHERE \"public\".\"Album\".\"artistId\" = ? ORDER BY \"public\".\"Album\".\"year\" DESC";

    try {
      System.out.print("[AUDIO_PLAYER]: RUNNING QUERY");

      PreparedStatement pst = c.prepareStatement(query);

      pst.setEscapeProcessing(false);
      pst.setString(1, artist_id);

      ResultSet rs = pst.executeQuery();

      List<Album> albums = new ArrayList<Album>();

      while (rs.next()) {
        String id = rs.getString(1);
        String title = rs.getString(2);

        albums.add(new Album(id, title));

      }

      return albums;

    } catch (SQLException e) {
      System.out.print("[AUDIO_PLAYER]: SQL ERROR");
      e.printStackTrace();
      List<Album> albums = new ArrayList<Album>();
      return albums;
    }
  }

  // Album
  List<Album> getAlbums() {
    String query = "SELECT public.\"Album\".id, public.\"Album\".title FROM public.\"Album\" WHERE 1=1 ORDER BY public.\"Album\".title ASC";

    try {
      System.out.print("[AUDIO_PLAYER]: RUNNING QUERY");

      PreparedStatement pst = c.prepareStatement(query);

      pst.setEscapeProcessing(false);

      ResultSet rs = pst.executeQuery();

      List<Album> albums = new ArrayList<Album>();

      while (rs.next()) {
        String id = rs.getString(1);
        String title = rs.getString(2);

        albums.add(new Album(id, title));
      }

      return albums;
    } catch (SQLException e) {
      System.out.print("[AUDIO_PLAYER]: SQL ERROR");
      e.printStackTrace();

      List<Album> albums = new ArrayList<Album>();

      return albums;
    }
  }

  List<Song> getAlbumSongs(String album_id) {
    String query = "SELECT \"public\".\"Song\".\"id\", \"public\".\"Song\".\"title\", \"public\".\"Song\".\"genre\", \"public\".\"Song\".\"cd_track_number\", \"public\".\"Song\".\"duration\", \"public\".\"Song\".\"year\", \"public\".\"Song\".\"path\", \"public\".\"Song\".\"albumId\", \"public\".\"Song\".\"artistId\" FROM \"public\".\"Song\" WHERE \"public\".\"Song\".\"albumId\" = ? ORDER BY \"public\".\"Song\".\"cd_track_number\" ASC";

    try {
      System.out.print("[AUDIO_PLAYER]: RUNNING QUERY");

      PreparedStatement pst = c.prepareStatement(query);

      pst.setEscapeProcessing(false);
      pst.setString(1, album_id);

      ResultSet rs = pst.executeQuery();
      List<Song> songs = new ArrayList<Song>();

      while (rs.next()) {
        String id = rs.getString(1);
        String title = rs.getString(2);
        String genre = rs.getString(3);
        String cd_track_number = rs.getString(4);
        int duration = rs.getInt(5);
        String year = rs.getString(6);
        String path = rs.getString(7);
        String albumId = rs.getString(8);
        String artistId = rs.getString(9);

        songs.add(new Song(id, title, genre, cd_track_number, duration, year, path, albumId, artistId));
      }

      return songs;
    } catch (SQLException e) {
      System.out.print("[AUDIO_PLAYER]: SQL ERROR");
      e.printStackTrace();

      List<Song> songs = new ArrayList<Song>();

      return songs;
    }
  }

  /**
   * Returns all the songs in the database
   */
  List<Song> getAllSongs() {
    String query = "SELECT \"public\".\"Song\".\"id\", \"public\".\"Song\".\"title\", \"public\".\"Song\".\"genre\", \"public\".\"Song\".\"cd_track_number\", \"public\".\"Song\".\"duration\", \"public\".\"Song\".\"year\", \"public\".\"Song\".\"path\", \"public\".\"Song\".\"albumId\", \"public\".\"Song\".\"artistId\" FROM \"public\".\"Song\" WHERE 1=1 ORDER BY \"public\".\"Song\".\"title\" ASC";
    try {
      System.out.print("[AUDIO_PLAYER]: RUNNING QUERY");

      PreparedStatement pst = c.prepareStatement(query);

      pst.setEscapeProcessing(false);

      ResultSet rs = pst.executeQuery();

      List<Song> songs = new ArrayList<Song>();

      while (rs.next()) {
        String id = rs.getString(1);
        String title = rs.getString(2);
        String genre = rs.getString(3);
        String cd_track_number = rs.getString(4);
        int duration = rs.getInt(5);
        String year = rs.getString(6);
        String path = rs.getString(7);
        String albumId = rs.getString(8);
        String artistId = rs.getString(9);

        songs.add(new Song(id, title, genre, cd_track_number, duration, year, path, albumId, artistId));
      }
      return songs;
    } catch (SQLException e) {
      System.out.print("[AUDIO_PLAYER]: SQL ERROR");
      e.printStackTrace();
      List<Song> songs = new ArrayList<Song>();
      return songs;
    }
  }

  /**
   * Returns all the playlists in the database
   */
  List<Playlist> getPlaylists() {
    String query = "SELECT \"public\".\"Playlist\".\"id\", \"public\".\"Playlist\".\"title\" FROM \"public\".\"Playlist\" WHERE 1=1";
    try {
      System.out.print("[AUDIO_PLAYER]: RUNNING QUERY");

      PreparedStatement pst = c.prepareStatement(query);

      pst.setEscapeProcessing(false);

      ResultSet rs = pst.executeQuery();
      List<Playlist> playlists = new ArrayList<Playlist>();

      while (rs.next()) {
        String id = rs.getString(1);
        String title = rs.getString(2);

        playlists.add(new Playlist(id, title));
      }

      return playlists;
    } catch (SQLException e) {
      System.out.print("[AUDIO_PLAYER]: SQL ERROR");
      e.printStackTrace();

      List<Playlist> Playlists = new ArrayList<Playlist>();

      return Playlists;
    }
  }

  /**
   * Returns all the songs data from the ids argument
   */
  List<Song> getSongsFromIds(List<String> ids) {
    String query = "SELECT \"public\".\"Song\".\"id\", \"public\".\"Song\".\"title\", \"public\".\"Song\".\"genre\", \"public\".\"Song\".\"cd_track_number\", \"public\".\"Song\".\"duration\", \"public\".\"Song\".\"year\", \"public\".\"Song\".\"path\", \"public\".\"Song\".\"albumId\", \"public\".\"Song\".\"artistId\" FROM \"public\".\"Song\" WHERE \"public\".\"Song\".\"id\" = ANY(?)";

    try {
      System.out.print("[AUDIO_PLAYER]: RUNNING QUERY");

      PreparedStatement pst = c.prepareStatement(query);

      Array idsArray = c.createArrayOf("text", ids.toArray());

      pst.setEscapeProcessing(false);
      pst.setArray(1, idsArray);
      ResultSet rs = pst.executeQuery();
      List<Song> songs = new ArrayList<Song>();

      while (rs.next()) {
        String id = rs.getString(1);
        String title = rs.getString(2);
        String genre = rs.getString(3);
        String cd_track_number = rs.getString(4);
        int duration = rs.getInt(5);
        String year = rs.getString(6);
        String path = rs.getString(7);
        String albumId = rs.getString(8);
        String artistId = rs.getString(9);

        songs.add(new Song(id, title, genre, cd_track_number, duration, year, path, albumId, artistId));
      }
      return songs;
    } catch (SQLException e) {
      System.out.print("[AUDIO_PLAYER]: SQL ERROR");
      e.printStackTrace();
      List<Song> songs = new ArrayList<Song>();
      return songs;
    }
  }

  /**
   * Returns all the songs for the given playlist_id
   */
  List<Song> getPlaylistSongs(String playlist_id) {
    String query = "SELECT \"public\".\"_PlaylistToSong\".\"A\", \"public\".\"_PlaylistToSong\".\"B\" FROM \"public\".\"_PlaylistToSong\" WHERE \"public\".\"_PlaylistToSong\".\"A\" IN (?)";

    try {
      System.out.print("[AUDIO_PLAYER]: RUNNING QUERY");

      PreparedStatement pst = c.prepareStatement(query);

      pst.setEscapeProcessing(false);
      pst.setString(1, playlist_id);
      ResultSet rs = pst.executeQuery();
      List<String> ids = new ArrayList<String>();

      while (rs.next()) {
        String id = rs.getString(2);

        ids.add(id);
      }

      List<Song> songs = this.getSongsFromIds(ids);

      return songs;
    } catch (SQLException e) {
      System.out.print("[AUDIO_PLAYER]: SQL ERROR");
      e.printStackTrace();
      List<Song> songs = new ArrayList<Song>();
      return songs;
    }
  }

  Playlist createPlaylist(String _title) {
    String query = "INSERT INTO \"public\".\"Playlist\" (\"id\",\"title\") VALUES (?,?) RETURNING \"public\".\"Playlist\".\"id\"";

    try {
      System.out.print("[AUDIO_PLAYER]: RUNNING QUERY");

      PreparedStatement pst = c.prepareStatement(query);
      pst.setString(1, UUID.randomUUID().toString());
      pst.setString(2, _title);
      pst.setEscapeProcessing(false);

      ResultSet rs = pst.executeQuery();

      Playlist playlist = new Playlist("", "");

      while (rs.next()) {
        String id = rs.getString(1);

        playlist = new Playlist(id, _title);
      }

      return playlist;
    } catch (SQLException e) {
      System.out.print("[AUDIO_PLAYER]: SQL ERROR");
      e.printStackTrace();

      Playlist playlist = new Playlist("", "");

      return playlist;
    }
  }

  Playlist updatePlaylistTitle(String playlist_id, String _title) {
    String query = "UPDATE \"public\".\"Playlist\" SET \"title\" = ? WHERE \"public\".\"Playlist\".\"id\" = ?";

    try {
      System.out.print("[AUDIO_PLAYER]: RUNNING QUERY");

      PreparedStatement pst = c.prepareStatement(query);
      pst.setString(1, _title);
      pst.setString(2, playlist_id);
      pst.setEscapeProcessing(false);

      pst.executeUpdate();

      Playlist playlist = new Playlist(playlist_id, _title);

      return playlist;
    } catch (SQLException e) {
      System.out.print("[AUDIO_PLAYER]: SQL ERROR");
      e.printStackTrace();

      Playlist playlist = new Playlist("", "");

      return playlist;
    }
  }

  Boolean addPlaylistSong(String playlist_id, String song_id) {
    String query = "INSERT INTO \"public\".\"_PlaylistToSong\" (\"A\",\"B\") VALUES (?,?) ON CONFLICT DO NOTHING";

    try {
      System.out.print("[AUDIO_PLAYER]: RUNNING QUERY");

      PreparedStatement pst = c.prepareStatement(query);
      pst.setString(1, playlist_id);
      pst.setString(2, song_id);
      pst.setEscapeProcessing(false);

      pst.executeUpdate();

      return true;
    } catch (SQLException e) {
      System.out.print("[AUDIO_PLAYER]: SQL ERROR");
      e.printStackTrace();

      return false;
    }
  }

  Boolean deletePlaylistSong(String playlist_id, String song_id) {
    String query = "DELETE FROM \"public\".\"_PlaylistToSong\" WHERE (\"public\".\"_PlaylistToSong\".\"A\" = ? AND \"public\".\"_PlaylistToSong\".\"B\" = ?)";

    try {
      System.out.print("[AUDIO_PLAYER]: RUNNING QUERY");

      PreparedStatement pst = c.prepareStatement(query);
      pst.setString(1, playlist_id);
      pst.setString(2, song_id);
      pst.setEscapeProcessing(false);

      pst.executeUpdate();

      return true;
    } catch (SQLException e) {
      System.out.print("[AUDIO_PLAYER]: SQL ERROR");
      e.printStackTrace();

      return false;
    }
  }

  Boolean deletePlaylist(String playlist_id) {
    String query = "DELETE FROM \"public\".\"Playlist\" WHERE \"public\".\"Playlist\".\"id\" = ?";

    try {
      System.out.print("[AUDIO_PLAYER]: RUNNING QUERY");

      PreparedStatement pst = c.prepareStatement(query);
      pst.setString(1, playlist_id);

      pst.setEscapeProcessing(false);

      pst.executeUpdate();

      return true;
    } catch (SQLException e) {
      System.out.print("[AUDIO_PLAYER]: SQL ERROR");
      e.printStackTrace();

      return false;
    }
  }

  Album getAlbum(String _title, String artistId) {
    String query = "SELECT \"public\".\"Album\".\"id\", \"public\".\"Album\".\"title\", \"public\".\"Album\".\"genre\", \"public\".\"Album\".\"year\", \"public\".\"Album\".\"artistId\" FROM \"public\".\"Album\" WHERE (\"public\".\"Album\".\"artistId\" = ? AND \"public\".\"Album\".\"title\" = ?)";

    try {
      System.out.print("[AUDIO_PLAYER]: RUNNING QUERY");

      PreparedStatement pst = c.prepareStatement(query);

      pst.setEscapeProcessing(false);
      pst.setString(1, artistId);
      pst.setString(2, _title);

      ResultSet rs = pst.executeQuery();

      Album album = new Album("", "");

      while (rs.next()) {
        String id = rs.getString(1);
        String title = rs.getString(2);

        album = new Album(id, title);
      }
      return album;
    } catch (SQLException e) {
      System.out.print("[AUDIO_PLAYER]: SQL ERROR");
      e.printStackTrace();
      Album album = new Album("", "");

      return album;
    }
  }

  Boolean createAlbum(Album album) {
    String query = "INSERT INTO \"public\".\"Album\" (\"id\",\"title\") VALUES (?,?) RETURNING \"public\".\"Album\".\"id\"";

    try {
      System.out.print("[AUDIO_PLAYER]: RUNNING QUERY");

      PreparedStatement pst = c.prepareStatement(query);

      pst.setString(1, album.id);
      pst.setString(2, album.title);

      pst.setEscapeProcessing(false);

      ResultSet rs = pst.executeQuery();

      return true;
    } catch (SQLException e) {
      System.out.print("[AUDIO_PLAYER]: SQL ERROR");
      e.printStackTrace();

      return false;
    }
  }

  Boolean createArtist(Artist artist) {
    String query = "INSERT INTO \"public\".\"Artist\" (\"id\",\"title\") VALUES (?,?) RETURNING \"public\".\"Artist\".\"id\"";

    try {
      System.out.print("[AUDIO_PLAYER]: RUNNING QUERY");

      PreparedStatement pst = c.prepareStatement(query);

      pst.setString(1, artist.id);
      pst.setString(2, artist.title);

      pst.setEscapeProcessing(false);

      ResultSet rs = pst.executeQuery();

      return true;
    } catch (SQLException e) {
      System.out.print("[AUDIO_PLAYER]: SQL ERROR");
      e.printStackTrace();

      return false;
    }
  }

  private String seedAlbum(String title, String artistId) {
    // Checkare an yparxei album
    Album album = this.getAlbum(title, artistId);
    if (album.id.isEmpty()) {
      return album.id;
    }
    // An den yparxei dhmiourghse to
    else {
      Album newAlbum = new Album(UUID.randomUUID().toString(), title);
      Boolean album2 = this.createAlbum(newAlbum);
      return newAlbum.id;

    }

    // Bale to tragoudi sto album

  }

  Artist getArtist(String _title) {
    String query = "SELECT \"public\".\"Artist\".\"id\", \"public\".\"Artist\".\"title\" FROM \"public\".\"Artist\" WHERE \"public\".\"Artist\".\"title\" = ?";

    try {
      System.out.print("[AUDIO_PLAYER]: RUNNING QUERY");

      PreparedStatement pst = c.prepareStatement(query);

      pst.setEscapeProcessing(false);

      pst.setString(1, _title);

      ResultSet rs = pst.executeQuery();

      Artist artist = new Artist("", "");

      while (rs.next()) {
        String id = rs.getString(1);
        String title = rs.getString(2);

        artist = new Artist(id, title);
      }
      return artist;
    } catch (SQLException e) {
      System.out.print("[AUDIO_PLAYER]: SQL ERROR");
      e.printStackTrace();
      Artist artist = new Artist("", "");

      return artist;
    }
  }

  private String seedArtist(String title) {
    // Checkare an yparxei artist
    Artist artist = this.getArtist(title);
    if (!artist.id.isEmpty() && artist.id != null) {
      return artist.id;
    }
    // An den yparxei dhmiourghse to
    else {
      Artist newArtist = new Artist(UUID.randomUUID().toString(), title);
      this.createArtist(newArtist);
      return newArtist.id;
    }
  }

  Song getSong(String _path) {
    String query = "SELECT \"public\".\"Song\".\"id\", \"public\".\"Song\".\"title\", \"public\".\"Song\".\"genre\", \"public\".\"Song\".\"cd_track_number\", \"public\".\"Song\".\"duration\", \"public\".\"Song\".\"year\", \"public\".\"Song\".\"path\", \"public\".\"Song\".\"albumId\", \"public\".\"Song\".\"artistId\" FROM \"public\".\"Song\" WHERE \"public\".\"Song\".\"path\" = ?";

    try {
      System.out.print("[AUDIO_PLAYER]: RUNNING QUERY");

      PreparedStatement pst = c.prepareStatement(query);

      pst.setEscapeProcessing(false);

      pst.setString(1, _path);

      ResultSet rs = pst.executeQuery();

      Song song = new Song("", "", "", "", 0, "", "", "", "");

      while (rs.next()) {
        String id = rs.getString(1);
        String title = rs.getString(2);
        String genre = rs.getString(3);
        String cd_track_number = rs.getString(4);
        int duration = rs.getInt(5);
        String year = rs.getString(6);
        String path = rs.getString(7);
        String albumId = rs.getString(8);
        String artistId = rs.getString(9);

        song = new Song(id, title, genre, cd_track_number, duration, year, path, albumId, artistId);
      }

      return song;
    } catch (SQLException e) {
      System.out.print("[AUDIO_PLAYER]: SQL ERROR");
      e.printStackTrace();
      Song song = new Song("", "", "", "", 0, "", "", "", "");

      return song;
    }
  }

  Boolean addAlbumToArtist(String _artistId, String _albumId) {
    String query = "UPDATE \"public\".\"Album\" SET \"artistId\" = ? WHERE \"public\".\"Album\".\"id\" = ?";

    try {
      System.out.print("[AUDIO_PLAYER]: RUNNING QUERY");

      PreparedStatement pst = c.prepareStatement(query);

      pst.setEscapeProcessing(false);

      pst.setString(1, _artistId);
      pst.setString(2, _albumId);

      pst.executeUpdate();

      return true;
    } catch (SQLException e) {
      System.out.print("[AUDIO_PLAYER]: SQL ERROR");
      e.printStackTrace();

      return false;
    }

  }

  Boolean createSong(Song song) {
    String query = "INSERT INTO \"public\".\"Song\" (\"id\",\"title\",\"genre\",\"cd_track_number\",\"duration\",\"year\",\"path\",\"albumId\",\"artistId\") VALUES (?,?,?,?,?,?,?,?,?) RETURNING \"public\".\"Song\".\"id\"";

    try {
      System.out.print("[AUDIO_PLAYER]: RUNNING QUERY");

      PreparedStatement pst = c.prepareStatement(query);

      pst.setString(1, (song.id != null) ? song.id : "");
      pst.setString(2, (song.title != null) ? song.title : "");
      pst.setString(3, (song.genre != null) ? song.genre : "");
      pst.setString(4, (song.cd_track_number != null) ? song.cd_track_number : "");
      pst.setInt(5, song.duration);
      pst.setString(6, (song.year != null) ? song.year : "");
      pst.setString(7, (song.path != null) ? song.path : "");

      if (song.albumId != null && !song.albumId.isEmpty()) {
        pst.setString(8, song.albumId);
      } else {
        pst.setNull(8, Types.VARCHAR);
      }

      if (song.artistId != null && !song.artistId.isEmpty()) {
        pst.setString(9, song.artistId);
      } else {
        pst.setNull(9, Types.VARCHAR);
      }

      pst.setEscapeProcessing(false);

      ResultSet rs = pst.executeQuery();

      String songId = "";

      while (rs.next()) {
        String id = rs.getString(1);

        songId = id;
      }

      System.out.print("[AUDIO_PLAYER]: Added song " + songId);

      if (song.artistId != null && song.albumId != null) {
        this.addAlbumToArtist(song.albumId, song.artistId);
      }

      return true;
    } catch (SQLException e) {
      System.out.print("[AUDIO_PLAYER]: SQL ERROR");
      e.printStackTrace();

      return false;
    }
  }

  private Boolean seedSong(String path) {
    try {
      Song existingSong = this.getSong(path);

      if (existingSong.id.isEmpty()) {
        Mp3File mp3file = new Mp3File(path);
        System.out.println("Length of this mp3 is: " + mp3file.getLengthInSeconds() + " seconds");

        if (mp3file.hasId3v2Tag()) {
          ID3v2 id3v2Tag = mp3file.getId3v2Tag();

          String id = UUID.randomUUID().toString();
          String title = (id3v2Tag.getTitle() != null && !id3v2Tag.getTitle().isEmpty()) ? id3v2Tag.getTitle() : mp3file.getFilename();
          String cd_track_number = id3v2Tag.getTrack();

          int duration = (int) mp3file.getLengthInSeconds();
          String genre = id3v2Tag.getGenreDescription();

          String artistTitle = id3v2Tag.getArtist();
          String albumTitle = id3v2Tag.getAlbum();

          String year = id3v2Tag.getYear();

          String artistId = "";
          String albumId = "";

          if (artistTitle != null && !artistTitle.isEmpty()){
            System.out.println("artistTitle " + artistTitle);
            artistId = this.seedArtist(artistTitle);

            if (albumTitle != null && !albumTitle.isEmpty()) {
              System.out.println("albumTitle " + albumTitle);
              albumId = this.seedAlbum(albumTitle, artistId);
            }
          }

          Song song = new Song(id, title, genre, cd_track_number, duration, year, path, albumId, artistId);

          System.out.println("Adding new song " + mp3file.getFilename());
          Boolean createSong = this.createSong(song);

          return createSong;
        }
      }

      return true;
    } catch (Exception e) {
      System.out.print("[AUDIO_PLAYER]: Create Song ERROR");
      e.printStackTrace();
      return false;
    }
  }

  Boolean seedDatabase(String folderPath) {
    try {
      File folder = new File(folderPath);
      File[] listOfFiles = folder.listFiles();

      for (File file : listOfFiles) {
        if (file.isFile()) {
          String filePath = file.getAbsolutePath();
          
          String extension = "";

          int i = filePath.lastIndexOf('.');
          if (i > 0) {
              extension = filePath.substring(i+1);
          }

          if (extension.equals("mp3")) {
            this.seedSong(filePath);
          } else {
            System.out.println("Unsuported file extension " + extension);
          }
        }
      }

      return true;
    } catch (Exception e) {
      System.out.print("[AUDIO_PLAYER]: SEED ERROR");
      e.printStackTrace();

      return false;
    }
  }
}
