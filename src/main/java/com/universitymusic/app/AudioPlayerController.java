package com.universitymusic.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AudioPlayerController {
	@FXML
	private ListView<String> listView;
	@FXML
	private Button play;
	@FXML
	private Button pause;

	private MediaView mediaView;
	private String currentSongPath;
	boolean firstTime = true;

	ObservableList observableList = FXCollections.observableArrayList();
	Map<String, String> titlePathMap = new HashMap();

	public void setListView() {
		MusicDatabase db = new MusicDatabase();
		List<Song> allsongs = db.getAllSongs();

		for (Song song : allsongs) {
			observableList.add(song.title);
			titlePathMap.put(song.title, song.path);
		}
		listView.getItems().addAll(observableList);
	}

	@FXML
	void initialize() {
		assert listView != null : "fx:id=\"listView\" was not injected: check your FXML file 'CustomList.fxml'.";

		setListView();
		pause.setDisable(true);
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
		String selectedSongTitle = listView.getSelectionModel().getSelectedItem();
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
			} catch (Exception e) {
				System.err.println(
						e.getMessage() + " Path: " + selectedSongPath + " is not correct or the file is not existed");
			}
		}
	}

	private String modifyPath(String currentSongPath) {
		StringBuilder sb = new StringBuilder();
		String newPath = currentSongPath.replaceAll("\\\\", "/");
		sb.append("file:///").append(newPath);
		return sb.toString();
	}
}