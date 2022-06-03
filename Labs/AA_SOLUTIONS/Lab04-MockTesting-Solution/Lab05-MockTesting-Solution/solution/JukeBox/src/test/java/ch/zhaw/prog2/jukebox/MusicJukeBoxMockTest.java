package ch.zhaw.prog2.jukebox;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.stubbing.Answer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class MusicJukeBoxMockTest {
    private static final String SONGTITLE_FROZEN = "Frozen";
    private static final String SONGTITLE_DIAMONDS = "Diamonds";
    private static final String SONGTITLE_COLDASICE = "Cold as Ice";
    private static final String SONGTITLE_NOT_EXISTING = "not existing song";

    // 1e) use @Mock annotation to create mock object
    @Mock private Song songMock;
    // 1e) use @Spy annotation to a create spy object
    // spy objects are real objects, which you can mock some methods
    @Spy private MusicJukeBox jukeBox;

    @BeforeEach
    void setUp() throws Exception {
        // 1e) setup default behavior of global song mock object
        MockitoAnnotations.openMocks(this);
        when(songMock.getTitle()).thenReturn(SONGTITLE_FROZEN);
        when(songMock.getPlayTime()).thenReturn(210.4f);
        when(songMock.isPlaying()).thenReturn(false);
        doNothing().when(songMock).start();  // redundant default behaviour (example of mocking void method)
    }

    /**
     * 1.1a)
     */
    @Test
    void testPlayOfNonexistingSong() {
        assertThrows(JukeBoxException.class, () -> jukeBox.playTitle(SONGTITLE_NOT_EXISTING));
    }

    /**
     * 1.1b)
     */
    @Test
    void testGetPlayList() {
        Song songMockFrozen = mock(Song.class);
        when(songMockFrozen.getTitle()).thenReturn(SONGTITLE_FROZEN);
        Song songMockDiamonds = mock(Song.class);
        when(songMockDiamonds.getTitle()).thenReturn(SONGTITLE_DIAMONDS);
        Song songMockColdAsIce = mock(Song.class);
        when(songMockColdAsIce.getTitle()).thenReturn(SONGTITLE_COLDASICE);

        jukeBox.addSong(songMockFrozen);
        jukeBox.addSong(songMockDiamonds);
        jukeBox.addSong(songMockColdAsIce);
        jukeBox.addSong(songMockColdAsIce); // try to add a second time
        List<Song> playList = jukeBox.getPlayList();
        assertNotNull(playList);
        List<Song> expectedSongList = List.of(songMockFrozen, songMockDiamonds, songMockColdAsIce);
        // because the songs are not ordered, we can not use assertIterableEquals(expectedSongList, playList);
        assertTrue(expectedSongList.containsAll(playList), "only expected songs allowed");
        assertTrue(playList.containsAll(expectedSongList), "playlist has all songs, in any order");

        verify(songMockFrozen).getTitle();
        verify(songMockDiamonds).getTitle();
        verify(songMockColdAsIce, times(2)).getTitle();
    }

    /*
     * 1.1c) variant modifying behavior after first call
     */
    @Test
    void testPlayOfAlreadyPlayingSong() {
        // 2b) use global mock property created using @Mock

        jukeBox.addSong(songMock);
        // first call (must NOT throw an exception)
        assertDoesNotThrow(() -> jukeBox.playTitle(SONGTITLE_FROZEN));

        // add new behavior, mock throws exception,
        // if start is called a second time
        doThrow(new JukeBoxException("already playing")).when(songMock).start();

        // second call (must throw an exception)
        assertThrows(JukeBoxException.class, () -> jukeBox.playTitle(SONGTITLE_FROZEN));
        verify(songMock, times(2)).start();
        verify(songMock, times(1)).getTitle();
    }

    /*
     * 1.1c) variant defining consecutive call behavior
     */
    @Test
    void testPlayOfAlreadyPlayingSong2() {
        // 2b) use global mock property created using @Mock

        // declare behavior on consecutive calls
        doNothing().   // first call
        doThrow(new JukeBoxException("already playing")). // second & consecutive calls
        when(songMock).start();

        jukeBox.addSong(songMock);
        // first call (must not throw an exception)
        assertDoesNotThrow(() -> jukeBox.playTitle(SONGTITLE_FROZEN));

        // second call (must throw an exception)
        assertThrows(JukeBoxException.class, () -> jukeBox.playTitle(SONGTITLE_FROZEN));
        verify(songMock, times(2)).start();
        verify(songMock, times(1)).getTitle();
    }

    /*
     * 1.1d)
     */
    @Test
    void testPlayMock() {
        // 2b) use global mock property created using @Mock and @BeforeEach
        jukeBox.addSong(songMock);
        jukeBox.playTitle(SONGTITLE_FROZEN);

        InOrder inOrder = inOrder(songMock);
        inOrder.verify(songMock).getTitle();
        inOrder.verify(songMock).start();
    }


    /*
     * 1.1f) Argument Matcher
     */
    @Test
    void testArgumentMatcher() {

        doThrow(new JukeBoxException("Title not found")).when(jukeBox).playTitle(ArgumentMatchers.anyString());
        doNothing().when(jukeBox).playTitle(SONGTITLE_FROZEN);
        assertDoesNotThrow(() -> jukeBox.playTitle(SONGTITLE_FROZEN));
        assertThrows(JukeBoxException.class, () -> jukeBox.playTitle(SONGTITLE_DIAMONDS));
    }


}

