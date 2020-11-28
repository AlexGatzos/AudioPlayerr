package com.universitymusic.app;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javafx.util.Duration;

public class AudioPlayerController {
	private final DecimalFormat formatter = new DecimalFormat("00.00");
	@FXML
	private Slider timeSlider = new Slider();
	@FXML
	private ListView<String> listViewSongs;
	@FXML
	private ListView<String> listViewAlbums;
	@FXML
	private ListView<String> listViewArtists;
	@FXML
	private ListView<String> listViewPlaylists;
	@FXML
	private Button play;
	@FXML
	private Button pause;
	@FXML
	private Button albums;
	@FXML
	private Button songs;
	@FXML
	private Button artists;
	@FXML
	private Button playlists;
	@FXML
	private Button createPl;
	@FXML
	private MenuButton addSongToPlaylist;//exists only in songs list which is not under playlist
	@FXML
	private Button deleteSongFromPlaylist;//exists only in songs list under playlist
	@FXML
	private Button deletePlaylist;
	@FXML
	private ProgressBar progressBar = new ProgressBar();
	@FXML
	private Text currentDuration;
	@FXML
	private Text totalDuration;
	@FXML 
	private Slider volumeSlider;
	@FXML
	private TextField newPlaylistName;

	private MediaView mediaView;
	private String currentSongPath;
	private Duration totalTime;
	private boolean firstTime = true;
	private MusicDatabase db;
	private List<Song> allsongs;
	private List<Artist> allArtists;
	private Map<String, String> titlePathMap = new HashMap();
	private Map<String, String> titleSongIdSong = new HashMap();
	private Map<String, String> albumidAlbumtitleMap = new HashMap();
	private Map<String, String> artistidArtisttitleMap = new HashMap();
	private Map<String, String> playlistidPlaylisttitleMap = new HashMap();
	private Map<String, String> playlistIdSongIdMap = new HashMap();
	private String selectedPlaylistId;

	ObservableList observableListSongs = FXCollections.observableArrayList();
	ObservableList observableListAlbums = FXCollections.observableArrayList();
	ObservableList observableListArtists = FXCollections.observableArrayList();
	ObservableList observableListPlaylists = FXCollections.observableArrayList();

	@FXML
	void initialize() {

		db = new MusicDatabase();
		pause.setDisable(true);
		
		//initialize all Songs
		allsongs = db.getAllSongs();
		
		//initialize albums
		List<Album> allAlbums = db.getAlbums();
		
		for (Album album: allAlbums) { 
			observableListAlbums.add(album.title);
			albumidAlbumtitleMap.put(album.title,album.id);
		}
		
		//initialize artists
		allArtists = db.getArtists();
		
		for (Artist artist: allArtists) {
			observableListArtists.add(artist.title);
			artistidArtisttitleMap.put(artist.title,artist.id);
		}
		
		//initialize playlists
		List<Playlist> allPlaylists = db.getPlaylists();
		for (Playlist playlist: allPlaylists) {
			observableListPlaylists.add(playlist.title);
			playlistidPlaylisttitleMap.put(playlist.title, playlist.id);
		}				
	}

	@FXML
	private void createPlaylist(ActionEvent event) {
		if (newPlaylistName != null && !newPlaylistName.getText().isEmpty()) {
			Playlist newPlaylist = db.createPlaylist(newPlaylistName.getText());
			observableListPlaylists.add(newPlaylist.title);
			playlistidPlaylisttitleMap.put(newPlaylist.title,newPlaylist.id);
			setListViewPlaylists();
		} else {
			System.out.println("Please give a name to playlist");
		}
	}
	
	@FXML
	private void deletePlaylist(ActionEvent event) {
		if (listViewPlaylists.getSelectionModel().getSelectedItem() != null) {
			db.deletePlaylist(playlistidPlaylisttitleMap.get(listViewPlaylists.getSelectionModel().getSelectedItem()));
			observableListPlaylists.remove(listViewPlaylists.getSelectionModel().getSelectedItem());
			playlistidPlaylisttitleMap.remove(listViewPlaylists.getSelectionModel().getSelectedItem());
			setListViewPlaylists();
		}
	}
	
	@FXML
	private void deletePlaylistSong(ActionEvent event) {
		if (listViewSongs.getSelectionModel().getSelectedItem() != null) {
			db.deletePlaylistSong(selectedPlaylistId, titleSongIdSong.get(listViewSongs.getSelectionModel().getSelectedItem()));
			setListViewPlaylistSongs(selectedPlaylistId);
		}
	}
	
	private void addPlaylistSongs() {
		if (listViewSongs.getSelectionModel().getSelectedItem() != null) {
			db.addPlaylistSong(selectedPlaylistId, titleSongIdSong.get(listViewSongs.getSelectionModel().getSelectedItem()));
		}
	}
	
	@FXML
	private void setListViewPlaylists() {
		createPl.setVisible(true);
		deletePlaylist.setVisible(true);
		newPlaylistName.setVisible(true);
		
		if (listViewPlaylists != null) {
			listViewPlaylists.getItems().clear();
			listViewPlaylists.getItems().addAll(observableListPlaylists);	
			listViewPlaylists.setVisible(true);
			if (listViewAlbums != null) listViewAlbums.setVisible(false);
			if (listViewSongs != null) listViewSongs.setVisible(false);
			
			listViewPlaylists.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			    @Override
			    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			    	selectedPlaylistId = playlistidPlaylisttitleMap.get(newValue);
			    	setListViewPlaylistSongs(selectedPlaylistId);
			    }
			});
		} else {
			System.out.println("There are no playlists to preview!");
		}
	}
	
	
	@FXML
	private void pauseAction(ActionEvent event) {
		mediaView.getMediaPlayer().pause();
		pause.setDisable(true);
		play.setDisable(false);
	}

	@FXML
	private void playAction(ActionEvent event) {
		Media media;
		String selectedSongTitle = listViewSongs.getSelectionModel().getSelectedItem();
		String selectedSongPath = titlePathMap.get(selectedSongTitle);
		if (firstTime) {
			currentSongPath = selectedSongPath;
		}
		if (selectedSongPath != null && !selectedSongPath.isEmpty()) {
			try {
				if (mediaView == null || currentSongPath != selectedSongPath) {
					String modifiedPath = modifyPath(selectedSongPath);
					media = new Media(modifiedPath);
					MediaPlayer mediaplayer = new MediaPlayer(media);
					mediaView = new MediaView(mediaplayer);
					currentSongPath = selectedSongPath;
				}
				mediaView.getMediaPlayer().play();

				play.setDisable(true);
				pause.setDisable(false);
				firstTime = false;
				applyDurationAndSlider(mediaView.getMediaPlayer());
				
				volumeSlider.valueProperty().addListener(new InvalidationListener() {
				    public void invalidated(Observable ov) {
				       if (volumeSlider.isValueChanging() && mediaView!= null) {
				    	   mediaView.getMediaPlayer().setVolume(volumeSlider.getValue() / 100.0);
				       }
				    }
				});

			} catch (Exception e) {
				System.err.println(
						e.getMessage() + " Path: " + selectedSongPath + " is not correct or the file is not existed");
			}
		}
	}
	
	@FXML
	private void setListViewSongs() {
		if (createPl != null) createPl.setVisible(false);
		if (deletePlaylist != null) deletePlaylist.setVisible(false);
		if (newPlaylistName != null) newPlaylistName.setVisible(false);
		setSongs(allsongs,false,false);
	}
	
	@FXML
	private void setListViewAlbums() {
		if (listViewAlbums != null) {
			listViewAlbums.getItems().clear();
			listViewAlbums.getItems().addAll(observableListAlbums);
			
			listViewAlbums.setVisible(true);
			if (createPl != null) createPl.setVisible(false);
			if (deletePlaylist != null) deletePlaylist.setVisible(false);
			if (newPlaylistName != null) newPlaylistName.setVisible(false);
			if (listViewSongs != null) listViewSongs.setVisible(false);
			if (listViewPlaylists!= null) listViewPlaylists.setVisible(false);
			if (listViewArtists != null) listViewArtists.setVisible(false);
			
			listViewAlbums.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			    @Override
			    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			    	String albumId = albumidAlbumtitleMap.get(newValue);
			    	setListViewAlbumSongs(albumId);
			    }
			});
		} else {
			System.out.println("There are no albums to preview!");
		}
	}
	
	@FXML
	private void setListViewArtists() {		
		if (listViewArtists != null) {
			listViewArtists.getItems().clear();
			listViewArtists.getItems().addAll(observableListArtists);
			listViewArtists.setVisible(true);
			if (createPl != null )createPl.setVisible(false);
			if (deletePlaylist != null) deletePlaylist.setVisible(false);
			if (newPlaylistName != null) newPlaylistName.setVisible(false);
			if (listViewAlbums!= null) listViewAlbums.setVisible(false);
			if (listViewSongs!= null) listViewSongs.setVisible(false);
			if (listViewPlaylists!= null) listViewPlaylists.setVisible(false);
			
			listViewArtists.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			    @Override
			    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			    	String artistId = artistidArtisttitleMap.get(newValue);
			    	setListViewArtistSongs(artistId);
			    }
			});
		} else {
			System.out.println("There are no Artists");
		}
		
	}
	
	private void setSongs (List<Song> songs, boolean remainVisible, boolean isCalledFromPlaylist) {
		assert listViewSongs != null : "fx:id=\"listViewSongs\" was not injected: check your FXML file 'CustomList.fxml'.";
		
		if (observableListPlaylists != null && !isCalledFromPlaylist) {
			addSongToPlaylist.setVisible(true);
			if (deleteSongFromPlaylist != null) deleteSongFromPlaylist.setVisible(false);
			addSongToPlaylist.getItems().clear();
			for (Object itemText: observableListPlaylists) {
				 MenuItem item = new MenuItem(itemText.toString());
				 item.setOnAction(a->{ //lambda expression
					 selectedPlaylistId = playlistidPlaylisttitleMap.get(item.getText());
					 addPlaylistSongs();
				     });
				 addSongToPlaylist.getItems().add(item);
			}
		} else {
			if (addSongToPlaylist != null) addSongToPlaylist.setVisible(false);
			deleteSongFromPlaylist.setVisible(true);
		}
		
		observableListSongs.clear();
		titlePathMap.clear(); 
		titleSongIdSong.clear();
		for (Song song : songs) {
			observableListSongs.add(song.title);
			titlePathMap.put(song.title, song.path);
			titleSongIdSong.put(song.title, song.id);
		}
		listViewSongs.getItems().clear();
		listViewSongs.getItems().addAll(observableListSongs);
		listViewSongs.setVisible(true);
		if (listViewAlbums != null && !remainVisible) listViewAlbums.setVisible(false);
		if (listViewArtists != null && !remainVisible) listViewArtists.setVisible(false);
		if (listViewPlaylists != null && !remainVisible) listViewPlaylists.setVisible(false);
		
		if (!isCalledFromPlaylist) {
			if (createPl != null) createPl.setVisible(false);
			if (deletePlaylist != null) deletePlaylist.setVisible(false);
			if (newPlaylistName != null) newPlaylistName.setVisible(false);
		}
	}
	
	private void setListViewAlbumSongs(String albumId) {
		List<Song> albumSongs = db.getAlbumSongs(albumId);
		setSongs(albumSongs,true,false);
	}
	
	private void setListViewPlaylistSongs(String playlistId) {
		if (deleteSongFromPlaylist != null) deleteSongFromPlaylist.setVisible(true);
		List<Song> playListSongs = db.getPlaylistSongs(playlistId);
		setSongs(playListSongs,true,true);
	}
	
	private void setListViewArtistSongs(String artistId) {
		if (deleteSongFromPlaylist != null) deleteSongFromPlaylist.setVisible(false);
		//εδώ το κάνουμρ από το array lisτ και όχι μέσω της db
		List<Song> artistSongs = new ArrayList();
		for (Song song: allsongs) {
			if (song.artistId.equals(artistId)) {
				artistSongs.add(song);
			}
		}
		setSongs(artistSongs,true,false);
	}
	
	private void applyDurationAndSlider(MediaPlayer player) {
		player.currentTimeProperty().addListener(new ChangeListener<Duration>() {
			@Override
			public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
				timeSlider.valueProperty().setValue(newValue.divide(totalTime.toMillis()).toMillis() * 100.0);
				currentDuration.setText(String.valueOf(formatter.format(newValue.toSeconds())));

			}
		});

		player.setOnReady(() -> {
			// Set the total duration
			totalTime = player.getMedia().getDuration();
			totalDuration.setText(" / " + String.valueOf(formatter.format(Math.floor(totalTime.toSeconds()))));
		});

		// Slider Binding
		timeSlider.valueProperty().addListener((ov) -> {
			if (timeSlider.isValueChanging()) {
				if (null != player)
					// multiply duration by percentage calculated by
					// slider position
					player.seek(totalTime.multiply(timeSlider.valueProperty().getValue() / 100.0));
				progressBar.prefWidthProperty().bind(timeSlider.widthProperty());
				progressBar.progressProperty().bind(timeSlider.valueProperty().divide(100));
			}
		});
		
		//volumeSlider
		volumeSlider.valueProperty().addListener((ov) -> {
            if (null != player) {
                // multiply duration by percentage calculated by
                // slider position
            	player.setVolume(volumeSlider.valueProperty()
                        .getValue() / 100.0);
            }
        });
	}

	private String modifyPath(String currentSongPath) {
		StringBuilder sb = new StringBuilder();
		String newPath = currentSongPath.replaceAll("\\\\", "/");
		sb.append("file:///").append(newPath);
		return sb.toString();
	}
}