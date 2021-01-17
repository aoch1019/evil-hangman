import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DictionaryReaderTest {

	@BeforeEach
	void setUp() throws Exception {
		
	}

	@Test
	void testGetWords() {
		DictionaryReader test = new DictionaryReader("short_list.txt");
		assertTrue(test.getWords().size() == 57);
		assertTrue(test.getWords().contains("recall"));
		assertFalse(test.getWords().contains("applesauce"));
	}
	
	@Test
	void testGetWordsOfLength() {
		DictionaryReader test = new DictionaryReader("short_list.txt");
		assertTrue(test.getWordsOfLength(5).size() == 15);
		assertTrue(test.getWordsOfLength(2).size() == 0);
		assertTrue(test.getWordsOfLength(11).contains("inexpensive"));
	}	

}
