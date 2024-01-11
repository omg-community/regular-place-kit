package games.omg.legacy;

import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public enum Text {

	LETTER_A('a', new boolean[][] {
			{ false , true , true , false } ,
			{ true , false , false , true } ,
			{ true , false , false , true } ,
			{ true , true , true , true } ,
			{ true , false , false , true } , }),
	LETTER_B('b', new boolean[][] {
			{ true , true , true , false } ,
			{ true , false , false , true } ,
			{ true , true , true , false } ,
			{ true , false , false , true } ,
			{ true , true , true , false } , }),
	LETTER_C('c', new boolean[][] {
			{ false , true , true , true } ,
			{ true , false , false , false } ,
			{ true , false , false , false } ,
			{ true , false , false , false } ,
			{ false , true , true , true } , }),
	LETTER_D('d', new boolean[][] {
			{ true , true , true , false } ,
			{ true , false , false , true } ,
			{ true , false , false , true } ,
			{ true , false , false , true } ,
			{ true , true , true , false } , }),
	LETTER_E('e', new boolean[][] {
			{ false , true , true , true } ,
			{ true , false , false , false } ,
			{ true , true , true , false } ,
			{ true , false , false , false } ,
			{ false , true , true , true } , }),
	LETTER_F('f', new boolean[][] {
			{ false , true , true , true } ,
			{ true , false , false , false } ,
			{ true , true , true , false } ,
			{ true , false , false , false } ,
			{ true , false , false , false } , }),
	LETTER_G('g', new boolean[][] {
			{ false , true , true , true } ,
			{ true , false , false , false } ,
			{ true , false , true , true  } ,
			{ true , false , false , true } ,
			{ false , true , true , true } , }),
	LETTER_H('h', new boolean[][] {
			{ true , false , false , true } ,
			{ true , false , false , true } ,
			{ true , true , true , true } ,
			{ true , false , false , true } ,
			{ true , false , false , true } , }),
	LETTER_I('i', new boolean[][] {
			{ true , true , true } ,
			{ false , true , false } ,
			{ false , true , false } ,
			{ false , true , false } ,
			{ true , true , true } , }),
	LETTER_J('j', new boolean[][] {
			{ false , true , true , true } ,
			{ false , false , false , true } ,
			{ false , false , false , true } ,
			{ true , false , false , true } ,
			{ false , true , true , false } , }),
	LETTER_K('k', new boolean[][] {
			{ true , false , false , true } ,
			{ true , false , false , true } ,
			{ true , true , true , false } ,
			{ true , false , false , true } ,
			{ true , false , false , true } , }),
	LETTER_L('l', new boolean[][] {
			{ true , false , false , false} ,
			{ true , false , false , false} ,
			{ true , false , false , false} ,
			{ true , false , false , false} ,
			{ false , true , true , true} , }),
	LETTER_M('m', new boolean[][] {
			{ true , false , false , false , true } ,
			{ true , true , false , true , true } ,
			{ true , true , true , true , true } ,
			{ true , false , true , false , true } ,
			{ true , false , false , false , true } , }),
	LETTER_N('n', new boolean[][] {
			{ true , false , false , true } ,
			{ true , true , false , true } ,
			{ true , true , true , true } ,
			{ true , false , true , true } ,
			{ true , false , false , true } , }),
	LETTER_O('o', new boolean[][] {
			{ false , true , true , false } ,
			{ true , false , false , true } ,
			{ true , false , false , true } ,
			{ true , false , false , true } ,
			{ false , true , true , false } , }),
	LETTER_P('p', new boolean[][] {
			{ true , true , true , false } ,
			{ true , false , false , true } ,
			{ true , false , false , true } ,
			{ true , true , true , false } ,
			{ true , false , false , false } , }),
	LETTER_Q('q', new boolean[][] {
			{ true , true , true , true } ,
			{ true , false , false , true } ,
			{ true , false , false , true } ,
			{ true , false , true , true } ,
			{ true , true , true , false } , }),
	LETTER_R('r', new boolean[][] {
			{ true , true , true , false } ,
			{ true , false , false , true  } ,
			{ true , true , true , false } ,
			{ true , false , false , true } ,
			{ true , false , false , true } , }),
	LETTER_S('s', new boolean[][] {
			{ false , true , true , true } ,
			{ true , false , false , false } ,
			{ true , true , true , true } ,
			{ false , false , false , true } ,
			{ true , true , true , false } , }),
	LETTER_T('t', new boolean[][] {
			{ true , true , true } ,
			{ false , true , false } ,
			{ false , true , false } ,
			{ false , true , false } ,
			{ false , true , false } , }),
	LETTER_U('u', new boolean[][] {
			{ true , false , false , true } ,
			{ true , false , false , true } ,
			{ true , false , false , true } ,
			{ true , false , false , true } ,
			{ false , true , true , false } , }),
	LETTER_V('v', new boolean[][] {
			{ true , false , false , false , true} ,
			{ true , false , false , false , true } ,
			{ true , true , false , true, true } ,
			{ false , true , true , true  , false } ,
			{ false, false , true , false , false } , }),
	LETTER_W('w', new boolean[][] {
			{ true , false , false , false , true } ,
			{ true , false , false , false , true } ,
			{ true , false , false , false , true } ,
			{ true , false , true , false , true } ,
			{ false , true , true , true , false } , }),
	LETTER_X('x', new boolean[][] {
			{ true , false , false , true } ,
			{ true , false , false , true } ,
			{ false , true , true , false } ,
			{ true , false , false , true } ,
			{ true , false , false , true } , }),
	LETTER_Y('y', new boolean[][] {
			{ true , false , false , true} ,
			{ true , false , false , true} ,
			{ false , true , true , true } ,
			{ false , false , false , true } ,
			{ false , true , true , false } , }),
	LETTER_Z('z', new boolean[][] {
			{ true , true , true , true } ,
			{ false , false , false , true } ,
			{ false , true , true , false } ,
			{ true , false , false , false } ,
			{ true , true , true , true } , }),
	LETTER_0('0', new boolean[][] {
			{ false , true , true , false} ,
			{ true , false , false , true} ,
			{ true , false , false , true} ,
			{ true , false , false , true} ,
			{ false , true , true , false} , }),
	LETTER_1('1', new boolean[][] {
			{ true , true , false } ,
			{ false , true , false } ,
			{ false , true , false } ,
			{ false , true , false } ,
			{ true , true , true } , }),
	LETTER_2('2', new boolean[][] {
			{ true , true , true , false} ,
			{ false , false , false , true} ,
			{ false , true , true , true} ,
			{ true , false , false , false} ,
			{ true , true , true , true} , }),
	LETTER_3('3', new boolean[][] {
			{ true , true , true , false } ,
			{ false , false , false , true} ,
			{ false , true , true , true} ,
			{ false , false , false , true} ,
			{ true , true , true , false} , }),
	LETTER_4('4', new boolean[][] {
			{ true , false , false , true} ,
			{ true , false , false , true} ,
			{ false , true , true , true} ,
			{ false , false , false , true} ,
			{ false , false , false , true} , }),
	LETTER_5('5', new boolean[][] {
			{ true , true , true , true} ,
			{ true , false , false , false} ,
			{ true , true , true , true} ,
			{ false , false , false , true} ,
			{ true , true , true , false} , }),
	LETTER_6('6', new boolean[][] {
			{ false , true , true , true} ,
			{ true , false , false , false} ,
			{ true , true , true , true} ,
			{ true , false , false , true} ,
			{ false , true , true , false} , }),
	LETTER_7('7', new boolean[][] {
			{ true , true , true , true} ,
			{ false , false , false , true} ,
			{ false , false , true , false} ,
			{ false , true , false , false} ,
			{ false , true , false , false} , }),
	LETTER_8('8', new boolean[][] {
			{ false , true , true , false} ,
			{ true , false , false , true} ,
			{ true , true , true , true} ,
			{ true , false , false , true} ,
			{ false , true , true , false} , }),
	LETTER_9('9', new boolean[][] {
			{ false , true , true , false} ,
			{ true , false , false , true} ,
			{ true , true , true , true} ,
			{ false , false , false , true} ,
			{ true , true , true , false} , }),
	LETTER_DOT('.', new boolean[][] {
			{ true } , }),
	LETTER_UNDERSCORE('_', new boolean[][] {
			{ true , true , true } , }),
	LETTER_SPACE(' ', new boolean[][] {
			{ false , false , false } , }),
	LETTER_PERCENT('%', new boolean[][] {
			{ true , false , false , false , true } ,
			{ false , false , false , true , false } ,
			{ false , false , true , false , false } ,
			{ false , true , false , false , false } ,
			{ true , false , false , false , true } , }),
	LETTER_UP_ARROW('^', new boolean[][] {
			{ false , false , true , false , false } ,
			{ false , true , false , true , false } ,
			{ true , false , false , false , true } ,
			{ false , false , false , false , false } ,
			{ false , false , false , false , false } , }),
	LETTER_LEFT_ARROW('<', new boolean[][] {
			{ false , false , true } ,
			{ false , true , false } ,
			{ true , false , false } ,
			{ false , true , false } ,
			{ false , false , true } , }),
	LETTER_RIGHT_ARROW('>', new boolean[][] {
			{ true , false , false } ,
			{ false , true , false } ,
			{ false , false , true } ,
			{ false , true , false } ,
			{ true , false , false } , }),
	LETTER_AMPERSAND('*', new boolean[][] {
			{ true , false , true , false , true } ,
			{ false , true , true , true , false } ,
			{ true , true , true , true , true } ,
			{ false , true , true , true , false } ,
			{ true , false , true , false , true } , }),
	LETTER_HASHTAG('#', new boolean[][] {
			{ false , true , false , true , false } ,
			{ true , true , true , true , true } ,
			{ false , true , false , true , false } ,
			{ true , true , true , true , true } ,
			{ false , true , false , true , false } , }),
	LETTER_COMMA(',', new boolean[][] {
			{ true } ,
			{ true } , }),
	LETTER_COLON(':', new boolean[][] {
			{ true } ,
			{ false } ,
			{ false } ,
			{ false } ,
			{ true } , }),
	LETTER_DASH('-', new boolean[][] {
			{ true , true , true } ,
			{ false , false , false } ,
			{ false , false , false } , }),
	LETTER_PLUS('+', new boolean[][] {
			{ false , false , true , false , false } ,
			{ false , false , true , false , false } ,
			{ true , true , true , true , true } ,
			{ false , false , true , false , false } ,
			{ false , false , true , false , false } , }),
	LETTER_MINUS('-', new boolean[][] {
			{ true , true , true , true , true } ,
			{ false , false , false , false , false } ,
			{ false , false , false , false , false } , }),
	LETTER_EQUAL('=', new boolean[][] {
			{ true , true , true , true , true } ,
			{ false , false , false , false , false } ,
			{ true , true , true , true , true } ,
			{ false , false , false , false , false } , }),
	LETTER_LEFT_ROUND_BRACKET('(', new boolean[][] {
			{ false , true } ,
			{ true , false } ,
			{ true , false } ,
			{ true , false } ,
			{ false , true } , }),
	LETTER_RIGHT_ROUND_BRACKET(')', new boolean[][] {
			{ true , false } ,
			{ false , true } ,
			{ false , true } ,
			{ false , true } ,
			{ true , false } , }),
	LETTER_LEFT_SQUARE_BRACKET('[', new boolean[][] {
			{ true , true } ,
			{ true , false } ,
			{ true , false } ,
			{ true , false } ,
			{ true , true } , }),
	LETTER_RIGHT_SQUARE_BRACKET(']', new boolean[][] {
			{ true , true } ,
			{ false , true } ,
			{ false , true } ,
			{ false , true } ,
			{ true , true } , }),
	LETTER_QUESTION('?', new boolean[][] {
			{ true , true , true } ,
			{ false , false , true } ,
			{ false , true , true } ,
			{ false , false , false } ,
			{ false , true , false } , }),
	LETTER_QUESTION_UPSIDE_DOWN('¿', new boolean[][] {
		{ false , true , false } ,
		{ false , false , false } ,
		{ true , true , false } ,
		{ true , false , false } ,
		{ true , true , true } , });

	//

	final private static HashMap<String, List<Location>> drawnText = new HashMap<>();

	//
	
	final private char character;
	final private int height;
	final private int width;
	private boolean[][] blocks;
	
	Text(char character, boolean[][] blocks) {
		this.character = character;
		
		this.blocks = blocks;
		
		this.height = blocks.length;
		this.width = blocks[0].length;
		
		boolean[][] reversed = new boolean[height][width];
		for (int i = 0; i < height; i++) {
			reversed[(height - i) - 1] = blocks[i];
		}
		this.blocks = reversed;
	}

	//
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public char getCharacter() {
		return character;
	}

	public void draw(Location location, Material material, Direction direction, String textID) {
		List<Location> drawnList = drawnText.get(textID);
		if (drawnList == null) {
			drawnList = new ArrayList<>();
			drawnText.put(textID, drawnList);
		}

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Location l = location.clone().add(x * direction.getX(), y, x * direction.getZ());
				if (blocks[y][x]) {
					l.getBlock().setType(material);
					drawnList.add(l);
				}
			}
		}
	}
	
	public static int strHeight() {
		return 5;
	}
	
	public static int strHeight(String[] str) {
		return (strHeight() + 1) * str.length - 1;
	}
	
	public static int strWidth(String str) {
		int w = 0;
		List<Text> letters = fromString(str);
		for (Text l : letters) {
			w += l.getWidth() + 1;
		}
		return w > 0 ? w - 1 : w;
	}
	
	public static int strWidth(String[] str) {
		int width = 0;
		for (String s : str) if (strWidth(s) > width) width = strWidth(s);
		return width;
	}
	
	public static void drawStringCentered(String text, Location location, Material material, Direction direction, String textID) {
		int width = strWidth(text);
		Location start = location.clone().subtract( ( width / 2 ) * direction.getX(), 0 , (width / 2) * direction.getZ());
		drawString(text, start, material, direction, textID);
	}

	public static void drawString(String text, Location location, Material material, Direction direction, String textID) {
		List<Text> letters = fromString(text);
		for (Text l : letters) {
			l.draw(location, material, direction, textID);
			location = location.add( (l.getWidth() + 1) * direction.getX(), 0, (l.getWidth() + 1) * direction.getZ());
		}
	}

	public static void destroyString(String textID) {
		List<Location> drawnList = drawnText.get(textID);
		if (drawnList != null) for (Location l : drawnList) l.getBlock().setType(Material.AIR);
	}

	public static void destroyAllStrings() {
		for (String textID : drawnText.keySet()) destroyString(textID);
	}
	
	public static Text fromCharacter(char character) {
		for (Text l : Text.values()) {
			if (String.valueOf(l.character).equalsIgnoreCase(String.valueOf(character))) {
				return l;
			}
		}
		return null;
	}
	
	public static List<Text> fromString(String string) {
		List<Text> letters = new ArrayList<>();
		for (char character : string.toCharArray()) {
			Text l = fromCharacter(character);
			if (l != null) letters.add(l);
		}
		return letters;
	}
}