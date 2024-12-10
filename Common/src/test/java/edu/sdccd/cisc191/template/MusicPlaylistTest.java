package edu.sdccd.cisc191.template;

import edu.sdccd.cisc191.template.Song;
import edu.sdccd.cisc191.template.MusicPlaylist;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
//**Module 8 :Used LinkedList for the playlist**//
public class MusicPlaylistTest {
    private MusicPlaylist musicPlaylist;

    @Before
    public void setUp() {
        musicPlaylist = new MusicPlaylist();
    }

    @Test
    public void testAddAndRemoveSong() {
        Song song = new Song();
        song.setTitle("Test Song");
        musicPlaylist.addSong(song);
        assertEquals(1, musicPlaylist.getPlaylist().size());

        musicPlaylist.removeSong(song);
        assertEquals(0, musicPlaylist.getPlaylist().size());
    }

    @Test
    public void testGetNextAndPreviousSong() {
        Song song1 = new Song();
        song1.setTitle("Song 1");
        Song song2 = new Song();
        song2.setTitle("Song 2");

        musicPlaylist.addSong(song1);
        musicPlaylist.addSong(song2);

        assertEquals(song2, musicPlaylist.getNextSong(song1));
        assertNull(musicPlaylist.getNextSong(song2));

        assertEquals(song1, musicPlaylist.getPreviousSong(song2));
        assertNull(musicPlaylist.getPreviousSong(song1));
    }
    @Test
    public void testFindSongByTitle() {
        Song song = new Song();
        song.setTitle("Find Me");
        musicPlaylist.addSong(song);

        Song foundSong = musicPlaylist.findSongByTitle("Find Me");
        assertEquals("Find Me", foundSong.getTitle());
    }
        //** Module 9 Added Methods for Searching and Sorting*//
    @Test
    public void testSortByReleaseDate() {
        Song song1 = new Song();
        song1.setTitle("Song 1");
        song1.setReleaseDate("2021-01-01");

        Song song2 = new Song();
        song2.setTitle("Song 2");
        song2.setReleaseDate("2020-01-01");

        musicPlaylist.addSong(song1);
        musicPlaylist.addSong(song2);

        musicPlaylist.sortByReleaseDate();

        LinkedList<Song> sortedSongs = musicPlaylist.getPlaylist();
        assertEquals("2020-01-01", sortedSongs.get(0).getReleaseDate());
        assertEquals("2021-01-01", sortedSongs.get(1).getReleaseDate());
    }
    @Test
    public void testFilterByArtist() {
        Song song1 = new Song();
        song1.setArtist("Artist 1");
        Song song2 = new Song();
        song2.setArtist("Artist 2");

        musicPlaylist.addSong(song1);
        musicPlaylist.addSong(song2);

        List<Song> filteredSongs = musicPlaylist.filterByArtist("Artist 1");
        assertEquals(1, filteredSongs.size());
        assertEquals("Artist 1", filteredSongs.get(0).getArtist());
    }
    //** Module 12 StreamAPI and Lambdas**//
    @Test
    public void testSortByTitle() {
        Song song1 = new Song();
        song1.setTitle("B Song");
        Song song2 = new Song();
        song2.setTitle("A Song");

        musicPlaylist.addSong(song1);
        musicPlaylist.addSong(song2);

        List<Song> sortedSongs = musicPlaylist.sortByTitle();
        assertEquals("A Song", sortedSongs.get(0).getTitle());
        assertEquals("B Song", sortedSongs.get(1).getTitle());
    }


}
