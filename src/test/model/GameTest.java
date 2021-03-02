package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    private Game game;

    @BeforeEach
    void runBefore() {
        game = new Game();
    }

    @Test
    public void testConstructor() {
        game = new Game();
    }

    @Test
    public void testGetCoinsNone() {
        assertEquals(0, game.getCoins());
    }

    @Test
    public void testGetCoinsSome() {
        game.tapTreasure();
        game.tapTreasure();
        game.tapTreasure();
        assertEquals(3, game.getCoins());
    }

    @Test
    public void testGetAutoCoinSpeedNone() {
        assertEquals(0.0, game.getAutoCoinSpeed());
    }

    @Test
    public void testGetAutoCoinSpeedOne() {
        for (int i = 1; i <= 10; i++) {
            game.tapTreasure();
        }
        game.buyPirate();
        assertEquals(0.1, game.getAutoCoinSpeed());
    }

    @Test
    public void testGetAutoCoinSpeedSome() {
        for (int i = 1; i <= 40; i++) {
            game.tapTreasure();
        }
        game.buyPirate();
        game.buyBuccaneer();
        assertEquals(0.6, game.getAutoCoinSpeed());
    }

    @Test
    public void testGetNumberOfCrewMatesNone() {
        assertEquals(0, game.getNumberOfCrewMates());
    }

    @Test
    public void testGetNumberOfCrewMatesSome() {
        for (int i = 1; i <= 40; i++) {
            game.tapTreasure();
        }
        game.buyPirate();
        game.buyBuccaneer();
        assertEquals(2, game.getNumberOfCrewMates());
    }

    @Test
    public void testGetNumberOfPiratesNone() {
        assertEquals(0, game.getNumberOfPirates());
    }

    @Test
    public void testGetNumberOfPiratesMixedOne() {
        for (int i = 1; i <= 40; i++) {
            game.tapTreasure();
        }
        game.buyBuccaneer();
        assertEquals(0, game.getNumberOfPirates());
        game.buyPirate();
        assertEquals(1, game.getNumberOfPirates());
    }

    @Test
    public void testGetNumberOfPiratesSome() {
        for (int i = 1; i <= 40; i++) {
            game.tapTreasure();
        }
        game.buyPirate();
        game.buyPirate();
        assertEquals(2, game.getNumberOfPirates());
    }


    @Test
    public void testGetNumberOfBuccaneersNone() {
        assertEquals(0, game.getNumberOfBuccaneers());
    }

    @Test
    public void testGetNumberOfBuccaneersMixedOne() {
        for (int i = 1; i <= 40; i++) {
            game.tapTreasure();
        }
        game.buyPirate();
        assertEquals(0, game.getNumberOfBuccaneers());
        game.buyBuccaneer();
        assertEquals(1, game.getNumberOfBuccaneers());
    }

    @Test
    public void testGetNumberOfBuccaneersSome() {
        for (int i = 1; i <= 80; i++) {
            game.tapTreasure();
        }
        game.buyBuccaneer();
        game.buyBuccaneer();
        assertEquals(2, game.getNumberOfBuccaneers());
    }

    @Test
    public void testBuyPirateFailure() {
        for (int i = 1; i <= 9; i++) {
            game.tapTreasure();
        }
        assertFalse(game.buyPirate());
    }

    @Test
    public void testBuyPirateSuccess() {
        for (int i = 1; i <= 10; i++) {
            game.tapTreasure();
        }
        assertTrue(game.buyPirate());
    }

    @Test
    public void testBuyBuccaneerFailure() {
        for (int i = 1; i <= 29; i++) {
            game.tapTreasure();
        }
        assertFalse(game.buyBuccaneer());
    }

    @Test
    public void testBuyBuccaneerSuccess() {
        for (int i = 1; i <= 30; i++) {
            game.tapTreasure();
        }
        assertTrue(game.buyBuccaneer());
    }

    @Test
    public void testSetTimer() {
        for (int i = 1; i <= 10; i++) {
            game.tapTreasure();
        }
        game.buyPirate();

        assertEquals(10000, game.getTimerSpeed());

        game.autoPerSec = 0.2;
        game.timerSpeed = (int)Math.round(1 / 0.2 * 1000);
        game.setTimer((int)Math.round(1 / 0.2 * 1000));

        assertEquals(5000, game.getTimerSpeed());
    }
}
