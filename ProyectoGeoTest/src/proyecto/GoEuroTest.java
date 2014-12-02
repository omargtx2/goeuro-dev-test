package proyecto;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.databind.JavaType;  
import com.fasterxml.jackson.databind.ObjectMapper; 

public class GoEuroTest {

	private static Scanner scanner;

	public static void main(String[] args) throws IOException {
	

		if(args.length == 0) {
			System.out.println("Input Parameter ");
		}else{

		String palabra = args[0];
		System.out.println(" input Parameter :  " + palabra);

		String str = "";

		try {
			URL url = new URL(
					"http://api.goeuro.com/api/v2/position/suggest/en/"
							+ palabra);

			URLConnection site = url.openConnection();

			InputStream is = site.getInputStream();
			scanner = new Scanner(new BufferedInputStream(is), "UTF-8");

			str = scanner.nextLine();
			scanner.close();

			System.out.println("Json :" + str);

		} catch (IOException ioe) {
			System.err.println("Could not open ");
		}

	
		try {
			
			
			makeDir();
		
			List<Country> gsonResponseMapper = str2list(str,Country.class);
			
			FileWriter writer1 = new FileWriter("c:\\archivos2\\test1.csv");
			

			for (Country countrie : gsonResponseMapper) {
				System.out.println("------------------------------------");
				System.out.println("_id :" + countrie.get_id());
				System.out.println("Name :" + countrie.getName());
				System.out.println("Type : " + countrie.getType());
				System.out.println("Latitude :" + countrie.getGeo_position().getLatitude());
				System.out.println("Longitude :" + countrie.getGeo_position().getLongitude());
				System.out.println("------------------------------------");
				
				
				generateCsvFile(writer1, countrie.get_id(), countrie.getName(),
						countrie.getType(), countrie.getGeo_position()
								.getLatitude(), countrie.getGeo_position()
								.getLongitude());
			}

			writer1.flush();
			writer1.close();
			
			
			
			
            System.out.println("path file : c:\\archivos2\\test1.csv");
			

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		}
	

	}
	
	private static  ObjectMapper getObjectMapper(){  
        ObjectMapper mapper = new ObjectMapper();  
        return mapper;  
    }  
	
	
   public static List<Country> str2list(String json, Class<?> clazz) {  
	        try {  
	            ObjectMapper mapper = getObjectMapper();  
	            JavaType type = mapper.getTypeFactory().constructCollectionType(ArrayList.class, clazz);  
	  
	            List<Country> domain = mapper.readValue(json, type);  
	            return domain;  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	            return null;  
	        }  
	    }  

	private static void generateCsvFile(FileWriter writer, String _id,
			String name, String type, String latitude, String longitude) {
		
		try {

			

			writer.append("_id");
			writer.append(',');
			writer.append(_id);
			writer.append('\n');

			writer.append("name");
			writer.append(',');
			writer.append(name);
			writer.append('\n');

			writer.append("type");
			writer.append(',');
			writer.append(type);
			writer.append('\n');

			writer.append("latitude");
			writer.append(',');
			writer.append(latitude);
			writer.append('\n');

			writer.append("longitude");
			writer.append(',');
			writer.append(longitude);
			writer.append('\n');

		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void makeDir() throws IOException
	{
	    File directory = new File("C:\\archivos2");
	    if (directory.exists() && directory.isFile())
	    {
	        System.out.println("The dir with name could not be" +
	        " created as it is a normal file");
	    }
	    else
	    {
	        if (!directory.exists())
			{
			    directory.mkdir();
			}
		
	    }
	}

}
