package com.universitymusic.app;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.util.Duration;

public class AudioPlayerController {
	private final DecimalFormat formatter = new DecimalFormat("00.00");
	@FXML
	private Slider timeSlider = new Slider();
	@FXML
	private ListView<String> listView;
	@FXML
	private Button play;
	@FXML
	private Button pause;
	@FXML
	private ProgressBar progressBar = new ProgressBar();

	@FXML
	private Text currentDuration;
	@FXML
	private Text totalDuration;

	private MediaView mediaView;
	private String currentSongPath;
	private Duration totalTime;
	private boolean firstTime = true;

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
				applyDurationAndSlider(mediaView.getMediaPlayer());

			} catch (Exception e) {
				System.err.println(
						e.getMessage() + " Path: " + selectedSongPath + " is not correct or the file is not existed");
			}
		}
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
	}

	private String modifyPath(String currentSongPath) {
		StringBuilder sb = new StringBuilder();
		String newPath = currentSongPath.replaceAll("\\\\", "/");
		sb.append("file:///").append(newPath);
		return sb.toString();
	}
}