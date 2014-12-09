package sangmuk.jo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Converter {

    /**
     * @param args
     */
    public static void main(String[] args) {
	Converter.readText();
    }

    static public void readText() {
	BufferedReader br;
	String everything = "Fail";
	try {
	    br = new BufferedReader(new FileReader("res/input.txt"));
	    StringBuilder sb = new StringBuilder();
	    String line = br.readLine();

	    while (line != null) {
		sb.append(line);
		sb.append(System.lineSeparator());
		line = br.readLine();
	    }
	    everything = sb.toString();
	    br.close();
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}

	ArrayList<String[]> idSet = new ArrayList<String[]>();
	while (!everything.isEmpty()) {
	    int startIndex = everything.indexOf("<");
	    int endIndex = everything.indexOf(" ", startIndex);

	    if (startIndex == -1) {
		break;
	    } else if (endIndex == -1) {
		everything = everything.substring(everything.indexOf(">") + 1);
		continue;
	    }

	    String layoutName = everything.substring(startIndex + 1, endIndex)
		    .trim();
	    String otherString = everything.substring(endIndex + 1);

	    endIndex = otherString.indexOf(">");
	    String layoutInfo = otherString.substring(0, endIndex);

	    if (layoutInfo.contains("android:id=")) {
		int index = layoutInfo.indexOf("@+id/");
		if (index >= 0) {
		    endIndex = layoutInfo.indexOf("\"", index);
		    String id = layoutInfo.substring(index + 5, endIndex);

		    if (layoutName.contains(".")) {
			layoutName = layoutName.substring(layoutName
				.lastIndexOf(".") + 1);
		    }

		    String[] str = new String[] { layoutName, id };
		    idSet.add(str);
		}
	    }
	    everything = otherString.substring(endIndex + 1);
	}

	for (String[] pair : idSet) {
	    String layoutClass = pair[0];
	    String layoutId = pair[1];

	    System.out.println("\t" + layoutClass + " " + layoutId + ";");
	}

	String root = "root";
	System.out.println("\n\n\tvoid setComponent(){");
	for (String[] pair : idSet) {
	    String layoutClass = pair[0];
	    String layoutId = pair[1];

	    System.out.println("\t\t" + layoutId + " = (" + layoutClass + ") "
		    + root + ".findViewById(R.id." + layoutId + ");");
	}

	System.out.println("\t}");

    }
}
