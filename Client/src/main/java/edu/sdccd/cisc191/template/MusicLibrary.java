package edu.sdccd.cisc191.template;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MusicLibrary {
    private List<Song> songs;

    public MusicLibrary() {
        this.songs = new ArrayList<>();
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public void removeSong(Song song) {
        songs.remove(song);
    }

    public List<Song> getSongs() {
        return new ArrayList<>(songs);
    }

    public void saveSongsToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(songs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadSongsFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            songs = (List<Song>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
