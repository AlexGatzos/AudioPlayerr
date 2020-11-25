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
	private ProgressBar progressBar = new ProgressBar();
	@FXML
	private Text currentDuration;
	@FXML
	private Text totalDuration;
	@FXML 
	private Slider volumeSlider;

	private MediaView mediaView;
	private String currentSongPath;
	private Duration totalTime;
	private boolean firstTime = true;
	private MusicDatabase db;
	private List<Song> allsongs;
	private List<Artist> allArtists;

	ObservableList observableListSongs = FXCollections.observableArrayList();
	ObservableList observableListAlbums = FXCollections.observableArrayList();
	ObservableList observableListArtists = FXCollections.observableArrayList();
	Map<String, String> titlePathMap = new HashMap();;
	Map<String, String> albumidAlbumtitleMap = new HashMap();
	Map<String, String> artistidArtisttitleMap = new HashMap();

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
		setSongs(allsongs,false);
	}
	
	@FXML
	private void setListViewAlbums() {
		if (listViewAlbums != null) {
			listViewAlbums.getItems().clear();
			listViewAlbums.getItems().addAll(observableListAlbums);
			
			listViewAlbums.setVisible(true);
			listViewSongs.setVisible(false);
			
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
			listViewAlbums.setVisible(false);
			listViewSongs.setVisible(false);
			
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
	
	private void setSongs (List<Song> songs, boolean remainVisible) {
		assert listViewSongs != null : "fx:id=\"listViewSongs\" was not injected: check your FXML file 'CustomList.fxml'.";
		
		observableListSongs.clear();
		titlePathMap.clear(); 
		for (Song song : songs) {
			observableListSongs.add(song.title);
			titlePathMap.put(song.title, song.path);
		}
		listViewSongs.getItems().clear();
		listViewSongs.getItems().addAll(observableListSongs);
		listViewSongs.setVisible(true);
		if (listViewAlbums != null && !remainVisible) listViewAlbums.setVisible(false);
		if (listViewArtists != null && !remainVisible) listViewArtists.setVisible(false);
		
	}
	
	private void setListViewAlbumSongs(String albumId) {
		List<Song> albumSongs = db.getAlbumSongs(albumId);
		setSongs(albumSongs,true);
	}
	
	private void setListViewArtistSongs(String artistId) {
		//εδώ το κάνουμρ από το array lisτ και όχι μέσω της db
		List<Song> artistSongs = new ArrayList();
		for (Song song: allsongs) {
			if (song.artistId.equals(artistId)) {
				artistSongs.add(song);
			}
		}
		setSongs(artistSongs,true);
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