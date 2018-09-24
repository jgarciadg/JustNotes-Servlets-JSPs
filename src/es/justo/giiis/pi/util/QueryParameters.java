package es.justo.giiis.pi.util;

import java.util.ArrayList;
import java.util.List;

public class QueryParameters {
	public static final int OWNER = 1;
	public static final int NOT_OWNER = 0;

	public static final int ARCHIVED = 1;
	public static final int NOT_ARCHIVED = 0;
	
	public static final int PINNED = 1;
	public static final int NOT_PINNED = 0;
	
	public static final int IN_TRASH = 1;
	public static final int NOT_IN_TRASH = 0;
	
	public static final int DONT_MATTER = -1;
	
	public static final int NO_COLOR = 0;
	public static final int COLOR_GREEN = 1;
	public static final int COLOR_BLUE = 2;
	public static final int COLOR_PINK = 3;
	public static final int COLOR_BLACK = 4;
	
	public static final List<Integer> COLORS = fillColors();
	
	
	
	public static List<Integer> fillColors() {
		List<Integer> colors = new ArrayList<Integer>();
		colors.add(NO_COLOR);
		colors.add(COLOR_GREEN);
		colors.add(COLOR_BLUE);
		colors.add(COLOR_PINK);
		colors.add(COLOR_BLACK);
		
		return colors;
	}
}
