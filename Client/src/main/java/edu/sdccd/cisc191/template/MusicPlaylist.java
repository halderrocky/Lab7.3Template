package edu.sdccd.cisc191.template;


import java.util.Collection;
import java.util.LinkedList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MusicPlaylist {
    private LinkedList<Song> playlist;

    public MusicPlaylist() {
        this.playlist = new LinkedList<>();
    }

    public void addSong(Song song) {
        playlist.add(song);
    }

    public void removeSong(Song song) {
        playlist.remove(song);
    }

    public Song getNextSong(Song current) {
        int index = playlist.indexOf(current);
        if (index >= 0 && index < playlist.size() - 1) {
            return playlist.get(index + 1);
        } else {
            return null; // No next song
        }
    }

    public Song getPreviousSong(Song current) {
        int index = playlist.indexOf(current);
        if (index > 0) {
            return playlist.get(index - 1);
        } else {
            return null; // No previous song
        }
    }

    public Song findSongByTitle(String title) {
        for (Song song : playlist) {
            if (song.getTitle().equals(title)) {
                return song;
            }
        }
        return null; // Song not found
    }

    public void sortByReleaseDate() {
        Collections.sort(playlist, (song1, song2) -> song1.getReleaseDate().compareTo(song2.getReleaseDate()));
    }

    public List<Song> filterByArtist(String artist) {
        return playlist.stream()
                .filter(song -> song.getArtist().equals(artist))
                .collect(Collectors.toList());
    }

    public List<Song> sortByTitle() {
        return playlist.stream()
                .sorted((song1, song2) -> song1.getTitle().compareTo(song2.getTitle()))
                .collect(Collectors.toList());
    }

    public Collection<Object> getPlaylist() {
    }
}
