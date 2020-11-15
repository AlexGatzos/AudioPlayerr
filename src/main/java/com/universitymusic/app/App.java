package com.universitymusic.app;

import java.util.List;
import java.net.URL;
import java.util.ArrayList;
import javafx.application.Application;
// import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class App extends Application {
    // List<Song> songs = new ArrayList<Song>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("uMusic");

        try {
            // FXMLLoader loader = new FXMLLoader();
            // URL xmlUrl = getClass().getResource("/AudioPlayer.fxml");
            // loader.setLocation(xmlUrl);
            // Parent root = loader.load();
            Label label = new Label("Hello World");

            primaryStage.setScene(new Scene(label));
            primaryStage.show();
        } catch (Exception e) {
            System.out.print("[AUDIO_PLAYER]: FXML ERROR");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        System.out.println("[AUDIO_PLAYER]: 🚀 Program Started");

        MusicDatabase db = new MusicDatabase();
        System.out.println("[AUDIO_PLAYER]: 🚀 MusicDatabase Connection Started");

        // Boolean seed = db.seedDatabase("/Users/alex/Downloads/music");
        // System.out.println("Seed DB " + seed);

        // List<Album> albums = db.getAlbums();
        // System.out.println("All albums " + albums.get(0).title);

        // List<Artist> artists = db.getArtists();
        // System.out.println("All artists " + artists.get(1).title);

        // List<Song> songs = db.getAlbumSongs(albums.get(0).id);
        // System.out.println("Songs of first album " + songs.get(0).title);

        // List<Album> artistAlbums = db.getArtistAlbums(artists.get(0).id);
        // System.out.println("Albums of first artist " + artistAlbums.get(0).title);

        // List<Song> allsongs = db.getAllSongs();
        // System.out.println("All songs " + allsongs.get(0).title);

        // for (int i = 0; i < allsongs.size(); i++) {
        // System.out.println("All songs " + allsongs.get(i).title + "___" +
        // allsongs.get(i).id);
        // }

        // this.songs = allsongs;

        // List<Playlist> playlists = db.getPlaylists();
        // System.out.println("All playlists " + playlists.get(0).title);

        // List<Song> playlistSongs = db.getPlaylistSongs(playlists.get(0).id);
        // if (playlistSongs.size() > 0) {
        // System.out.println("Playlist Songs " + playlistSongs.get(0).title);
        // }

        // Playlist playlist = db.createPlaylist("New Playlist Test1");
        // System.out.println("Playlist " + playlist.title);

        // Playlist upadateplaylist = db.updatePlaylistTitle(playlist.id, "ALEX Ole1");
        // System.out.println("Playlist " + upadateplaylist.title);

        // Boolean addSongToPlaylist = db.addPlaylistSong(playlist.id, songs.get(0).id);
        // System.out.println("Added Song To Playlist " + addSongToPlaylist);

        // Boolean deletePlaylistSong = db.deletePlaylistSong(playlist.id,
        // songs.get(0).id);
        // System.out.println("Delete Playlist " + deletePlaylistSong);

        // Boolean deletePlaylist = db.deletePlaylist(playlist.id);
        // System.out.println("Delete Playlist " + deletePlaylist);

        Application.launch(args);
    }

}
