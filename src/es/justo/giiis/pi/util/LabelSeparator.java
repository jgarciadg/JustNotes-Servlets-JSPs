package es.justo.giiis.pi.util;

import java.util.ArrayList;
import java.util.List;

public class LabelSeparator {
	public static List<String> separateLabels(String labels){
		List<String> labelsseparated = new ArrayList<String>();
		
		String[] aux = labels.split(",");
		for(int i=0; i<aux.length; i++) {
			labelsseparated.add(aux[i]);
		}
		
		return labelsseparated;
	}
}
