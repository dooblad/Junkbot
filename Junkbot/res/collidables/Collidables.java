package collidables;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.senior.junkbot.util.BB;

public class Collidables {
	
	public static BB[] loadCollidables(String URL) {
		List<BB> temp = new ArrayList<BB>();
		BB[] bbs;
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(Collidables.class.getResourceAsStream(URL)));
			String line;
			String[] split;
			while((line = reader.readLine()) != null) {
				split = line.split(";");
				temp.add(new BB(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]), Integer.parseInt(split[3])));
			}
			reader.close();
			
			bbs = new BB[temp.size()];
			
			for(int i = 0; i < temp.size(); i++) {
				bbs[i] = temp.get(i); 
			}
			
			return bbs;
		} catch(IOException e) {
			e.printStackTrace();
			System.err.println("Could not load " + URL);
			System.exit(1);
		}
		
		return null;
	}
}
