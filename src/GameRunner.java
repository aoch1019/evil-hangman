
public class GameRunner {

	public static void main(String[] args) {
		DictionaryReader d = new DictionaryReader("short_list.txt");
		// The second parameter in Game(x, y, z) determines whether Evil Mode is active.
		// The third parameter in Game(x, y, z) determines whether
		//     the player can see under the hood of Evil Mode.
		Game g = new Game(d, true, true);
		g.runGame();
	}

}
