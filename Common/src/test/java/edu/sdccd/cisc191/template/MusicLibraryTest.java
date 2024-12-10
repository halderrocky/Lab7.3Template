package edu.sdccd.cisc191.template;

import edu.sdccd.cisc191.template.Song;
import edu.sdccd.cisc191.template.MusicLibrary;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MusicLibraryTest {
    private MusicLibrary musicLibrary;

    @Before
    public void setUp() {
        musicLibrary = new MusicLibrary();
    }

    @Test
    public void testAddAndRemoveSong() {
        Song song = new Song();
        song.setTitle("Test Song");
        musicLibrary.addSong(song);
        assertEquals(1, musicLibrary.getSongs().size());

        musicLibrary.removeSong(song);
        assertEquals(0, musicLibrary.getSongs().size());
    }

    @Test
    public void testSaveAndLoadSongs() {
        Song song = new Song();
        song.setTitle("Test Song");
        song.setArtist("Test Artist");
        musicLibrary.addSong(song);

        musicLibrary.saveSongsToFile("testSongs.dat");
        musicLibrary.loadSongsFromFile("testSongs.dat");

        List<Song> loadedSongs = musicLibrary.getSongs();
        assertTrue(loadedSongs.size() > 0);
        assertEquals("Test Song", loadedSongs.get(0).getTitle());
        assertEquals("Test Artist", loadedSongs.get(0).getArtist());
    }
}
