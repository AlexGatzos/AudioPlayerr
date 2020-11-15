package com.universitymusic.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.util.Callback;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class AudioPlayerController {
  @FXML
  private ListView<String> listView;
  
  ObservableList observableList = FXCollections.observableArrayList();

  public void setListView() {
    MusicDatabase db = new MusicDatabase();
    List<Song> allsongs = db.getAllSongs();

    for (Song song: allsongs) {
      observableList.add(song.title);
    }

    listView.getItems().addAll(observableList);
  }

  @FXML
  void initialize() {
    assert listView != null : "fx:id=\"listView\" was not injected: check your FXML file 'CustomList.fxml'.";

    setListView();
  }
}