
import java.util.*;

public class EvilHangman {

	ArrayList<String> possibleWords;
	boolean viewEvilness;
	
	/**
	 * 
	 * @param words The initial list of words that can be obtained from the game's beginning state.
	 * @param viewEvilness An option for the developer to view the actions taken by EvilHangman
	 */
	public EvilHangman(ArrayList<String> words, boolean viewEvilness) {
		this.possibleWords = words;
		this.viewEvilness = viewEvilness;
	}
	
	/**
	 * Takes in a user's guess and determines if there's a potential adjustment to be made to the game's word
	 * to make it harder on the user.
	 * @param chosenLetter The letter guessed by the user
	 * @param currBoard The current status of the board
	 */
	public void beEvil(char chosenLetter, char[] currBoard) {
		HashMap<String, ArrayList<String>> families = new HashMap<String, ArrayList<String>>();
		chosenLetter = Character.toLowerCase(chosenLetter);
		
		// Creates a hashmap with the keys as sequences of characters and values as an array list of words with each sequence.
		for(String word : possibleWords) {
			if(viewEvilness) {
				System.out.println("Checking the word " + word + " for the letter " + chosenLetter);				
			}
			String family = "";
			for(int i = 0; i < word.length(); i++) {
				if(word.charAt(i) == chosenLetter) {
					family += chosenLetter;
				}
				else if(currBoard[i] != '_') {
					family += currBoard[i];
				}
				else {
					family += '_';
				}
			}
			if(viewEvilness) {
				System.out.println("Its sequence is " + family);				
			}
			if(families.containsKey(family)) {
				families.get(family).add(word);
			}
			else {
				ArrayList<String> newList = new ArrayList<String>();
				newList.add(word);
				families.put(family, newList);
			}
		}
		
		// Iterates through the fully formed hashmap to determine what sequence has the most potential words.
		String maxSeq = "";
		ArrayList<String> maxFam = new ArrayList<String>();
		for(Map.Entry<String, ArrayList<String>> entry : families.entrySet()) {
			ArrayList<String> curr = families.get(entry.getKey());
			if(curr.size() > maxFam.size() || maxSeq.equals("")) {
				maxSeq = entry.getKey();
				maxFam = curr;
			}
		}
		if(viewEvilness) {
			System.out.println("The most common sequence is " + maxSeq);			
		}
		// Updates the EvilHangman object to store the new list of possible words
		setPossibleWords(maxFam);
		if(viewEvilness) {
			System.out.println("Here are all words with that sequence...");
			for(String word : getPossibleWords()) {
				System.out.println(word);
			}
		}
	}
	
	/*
	 * Get all words at the disposal of Evil Hangman
	 * @return possibleWords An array list containing words that Evil Hangman can use to fool the user
	 */
	public ArrayList<String> getPossibleWords() {
		return possibleWords;
	}

	/*
	 * Update the words that are at the disposal of Evil Hangman
	 */
	public void setPossibleWords(ArrayList<String> possibleWords) {
		this.possibleWords = possibleWords;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub	
	}

}
