import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

class EvilHangmanTest {


	@Test
	void testBeEvil() {
		DictionaryReader d = new DictionaryReader("short_list.txt");
		EvilHangman ev = new EvilHangman(d.getWordsOfLength(5), false);
		// Create a sample board that has no letters guessed
		char[] board = {'_', '_', '_', '_', '_'};
		// Mimic the guessing of an 'a'		
		ev.beEvil('a', board);
		ArrayList<String> posWords = ev.getPossibleWords();
		assertTrue(posWords.size() == 11);
		assertTrue(posWords.contains("woozy"));
		assertTrue(posWords.contains("bench"));
		assertFalse(posWords.contains("train"));
		
		// Mimic the guessing of an 'o'		
		ev.beEvil('o', board);
		ArrayList<String> posWords2 = ev.getPossibleWords();
		assertTrue(posWords2.size() == 3);
		assertTrue(posWords2.contains("woozy"));
		assertFalse(posWords2.contains("bench"));
		
		// Mimic what the game's board will look like after having added an 'o'.
		//       This was determined by examining the input .txt file
		char[] board2 = {'_', 'O', 'O', '_', '_'};
		// Mimic the guessing of a 'y'		
		ev.beEvil('y', board2);
		ArrayList<String> posWords3 = ev.getPossibleWords();
		assertTrue(posWords3.size() == 2);
		assertTrue(posWords3.contains("goofy"));
		assertFalse(posWords3.contains("books"));
	}
}
