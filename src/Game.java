
import java.util.*;

public class Game {

	String theWord;
	char[] board;
	ArrayList<Character> incorrect;
	DictionaryReader dict;
	boolean gameOver;
	EvilHangman evil;
	boolean evilMode;
	boolean viewEvilness;
	
	
	/**
	 * Create a new Game object
	 * @param dict A DictionaryReader containing a list of words
	 * @param evilMode Whether or not this round is in evil mode
	 * @param viewEvilness A temporary option for the developer to view the adjustments made by EvilHangman
	 */
	public Game(DictionaryReader dict, boolean evilMode, boolean viewEvilness) {
		this.dict = dict;
		this.evilMode = evilMode;
		//Only enable viewEvilness if we are in evilMode to begin with
		if(evilMode) {
			this.viewEvilness = viewEvilness;			
		}
		else {
			this.viewEvilness = false;
		}
	}
	
	/**
	 * Start a new game
	 */
	private void setNewGame() {
		this.gameOver = false;
		Scanner s = new Scanner(System.in);
		System.out.println("How long of a word would you like? (number from 4 to 11, inclusive)");
		int length = 0;
		try{
			length = s.nextInt();
			if(length < 4 || length > 11) {
				length = invalidLengthInput();
			}
		}
		catch(InputMismatchException e){
			length = invalidLengthInput();
		}
		System.out.println("Let the game begin. After 6 incorrect guesses, the game is over.");
		this.theWord = getRandomWord(dict.getWordsOfLength(length)).toUpperCase();
		this.board = new char[this.theWord.length()];
		for(int i = 0; i < board.length; i++) {
			board[i] = '_';
		}
		if(this.evilMode) {
			this.evil = new EvilHangman(dict.getWordsOfLength(length), this.viewEvilness);	
		}
		this.incorrect = new ArrayList<Character>();
	}
	
	/**
	 * Handles invalid length inputs by informing the user that we will choose the length for them
	 * @return the default value of 6 letters
	 */
	private int invalidLengthInput() {
		System.out.println("You didn't enter a valid number, so we chose 6 for you... good luck!");
		return 6;
	}
	
	/**
	 * Displays the board
	 */
	public void printBoard() {
		System.out.println("");
		for(char letter : this.board) {
			System.out.print(letter + " ");
		}
		System.out.println("");
		System.out.println("");
		
		System.out.println("Incorrect Guesses:");
		System.out.print("[");
		for(int i = 0; i < this.incorrect.size(); i++) {
			if(i == this.incorrect.size() - 1) {
				System.out.print(this.incorrect.get(i));				
			}
			else {
				System.out.print(this.incorrect.get(i) + ", ");				
			}

		}
		System.out.println("]");
	}
	
	/**
	 * Takes in user input and runs the game until either the word has been guessed or all wrong guesses have been used.
	 */
	public void runGame() {
		setNewGame();
		while(!gameOver) {
			printBoard();
			System.out.print("Guess a letter: ");
			Scanner s = new Scanner(System.in);
			String guess = s.next().toUpperCase();
			while(!checkGuessValidity(guess)) {
				System.out.println("Please enter a valid letter");
				guess = s.next().toUpperCase();				
			}
			char guessedChar = guess.charAt(0);
			if(this.evilMode) {
				// Have EvilHangman asses if it should switch up the target word
				evil.beEvil(guessedChar, board);
				// Now that the EvilHangman object has updated the list of potential words, grab a new word randomly
				theWord = getRandomWord(evil.getPossibleWords()).toUpperCase();
			}
			if(this.viewEvilness) {
				System.out.println("The new word is " + theWord);				
			}

			if(!addToBoard(guessedChar)) {
				// Adds to list of incorrect letters and checks if that addition ends the game.
				if(addToIncorrect(guessedChar)){
					System.out.println("Game Over! You lost :(");
					System.out.println("The word was " + this.theWord);
					break;
				}
			}
		}
		System.out.println("Do you want to play again? (Y/N)");
		Scanner s = new Scanner(System.in);
		String answer = s.next();
		if(answer.equals("Y") || answer.equals("y")) {
			runGame();
		}
	}
	
	/**
	 * Checks whether the word has been fully guessed by the user
	 * @return true if the word is guessed, false otherwise.
	 */
	private boolean wordFullyGuessed() {
		for(char letter : this.board) {
			if(letter == '_') {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Checks whether a guess is a valid character and whether it has already been guessed
	 * @param guess The string input by the user as their guess, which is hopefully one character long.
	 * @return true if the user has input an unguessed character
	 */
	private boolean checkGuessValidity(String guess) {
		// Did the user enter a single character?
		if(guess.length() != 1) {
			return false;
		}
		
		Character c = guess.charAt(0);
		
		// Did the user enter a letter?
		if(!Character.isLetter(c)) {
			return false;
		}

		// Did the user enter a repeat letter? Let's check the incorrect letter list.
		if(incorrect.contains(c)) {
			return false;
		}

		// Let's check the board too for a repeat letter.
		for(char letter : board) {
			if(letter == c) {
				return false;
			}
		}

		return true;
	}
	
	/**
	 * Attempts to add a character to our board's word.
	 * Also checks if the user has guessed the entire word to see if the game is over.
	 * @param guess The character guessed by the user
	 * @return true if the character was successfully added, false if it should be added to incorrect instead
	 */
	private boolean addToBoard(char guess) {
		char[] charList = theWord.toCharArray();
		if(theWord.indexOf(guess) == -1) {
			System.out.println("There's no letter " + guess);
			return false;
		}
		else {
			for(int i = 0; i < charList.length; i++) {
				if(charList[i] == guess) {
					board[i] = guess;
				}
			}
			if(wordFullyGuessed()) {
				gameOver = true;
				printBoard();
				System.out.println("Congratulations, you won!");
			}
			return true;
		}
	}
	
	/**
	 * Adds the incorrect guess to the list of incorrect guesses
	 * @param guess The character guessed by the user
	 * @return true if the incorrect guess ends the game
	 */
	private boolean addToIncorrect(char guess) {
		incorrect.add(guess);
		if(incorrect.size() >= 6) {
			return true;
		}
		return false;
	}
	
	/**
	 * Obtains a random String from an array list of Strings.
	 * @param wordList A list of words
	 * @return A random string from wordList
	 */
	public String getRandomWord(ArrayList<String> wordList) {
		Random rand = new Random();
		
		int randIndex = rand.nextInt(wordList.size());
		
		return wordList.get(randIndex);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
