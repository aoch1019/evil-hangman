
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class DictionaryReader {

	ArrayList<String> words = new ArrayList<String>();
	
	/**
	 * Creates a new DictionaryReader object
	 * @param fileName The name of the file to read as the dictionary
	 */
	public DictionaryReader(String fileName) {
		File dict = new File(fileName);

		try {
			Scanner s = new Scanner(dict);
			
			while(s.hasNextLine()) {
				String currWord = s.nextLine();
				words.add(currWord);
			}		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets all word from the attached dictionary
	 * @return The list of words from the attached dictionary
	 */
	public ArrayList<String> getWords() {
		return words;
	}
	
	/**
	 * Gets all words from the dictionary that have a certain length
	 * @param length The desired length for all returned words
	 * @return The list of words of the suggested length
	 */
	public ArrayList<String> getWordsOfLength(int length) {
		ArrayList<String> newWords = new ArrayList<String>();
		for(String word : words) {
			if(word.length() == length) {
				newWords.add(word);
			}
		}
		return newWords;
	}	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
