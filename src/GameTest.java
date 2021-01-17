import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameTest {

	@BeforeEach
	void setUp() throws Exception {
	}
	
	@Test
	void testGetRandomWord() {
		DictionaryReader testDict = new DictionaryReader("short_list.txt");
		Game g = new Game(testDict, false, false);
		String rand = g.getRandomWord(testDict.getWords());
		assertTrue(testDict.getWords().contains(rand));
		String rand2 = g.getRandomWord(testDict.getWords());
		assertTrue(testDict.getWords().contains(rand2));
	}	

}
