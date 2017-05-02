package dev.codenmore.tilegame.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Utils
{
	public static String loadFileAsString(String path)
	{
		//adds characters to string
		StringBuilder builder = new StringBuilder();

		try
		{
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line;

			while((line = br.readLine()) != null)
			{
				builder.append(line + "\n");
			}
			br.close();
		}

		catch(IOException e)
		{
			e.printStackTrace();
		}

		//everything from builder to string
		return builder.toString();
	}

	public static int parseInt(String number)
	{
		try
		{
			return Integer.parseInt(number);
		}
			//not numbers throw errors
			catch(NumberFormatException e)
			{
				e.printStackTrace();
				return 0;
			}
		}
	}
