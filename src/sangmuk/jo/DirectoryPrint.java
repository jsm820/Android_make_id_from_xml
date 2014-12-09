package sangmuk.jo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DirectoryPrint
{

	public static void main(String[] args)
	{
		subDirList(
				"directory",
				0, new ArrayList<Integer>());
	}

	static public void subDirList(String source, int count,
			ArrayList<Integer> directoryCountArray)
	{
		ArrayList<Integer> dArray = new ArrayList<Integer>(directoryCountArray);

		File dir = new File(source);
		File[] fileList = dir.listFiles();

		String str = "";
		String fileStr = "";
		int directoryCount = 0;
		for (int i = 0; i < count; i++)
		{

			if (dArray.contains(i))
			{
				str += "¦¢";
				fileStr += "¦¢";
			} else
			{
				str += "  ";
				fileStr += "  ";

			}
		}

		// fileStr += "¦¢";

		try
		{
			for (int i = 0; i < fileList.length; i++)
			{
				File file = fileList[i];
				if (file.isFile())
				{
					// System.out.println(fileStr + "    " + file.getName()
					// + ",java");
				} else if (file.isDirectory())
				{
					directoryCount++;
				}
			}

			if (directoryCount >= 2)
			{
				dArray.add(count);
			}

			String tmpStr;
			for (int i = 0; i < fileList.length; i++)
			{
				if (directoryCount >= 1)
				{
					tmpStr = fileStr + "¦¢  ";
				} else
				{
					tmpStr = fileStr + "    ";
				}

				File file = fileList[i];
				if (file.isFile())
				{
					System.out.println(tmpStr + "    " + file.getName()
							+ ",java");
				}
			}

			for (int i = 0; i < fileList.length; i++)
			{
				File file = fileList[i];
				if (file.isDirectory())
				{
					directoryCount--;
					if (directoryCount == 0)
					{
						tmpStr = str + "¦¦¦¡";
						if (dArray.contains(count))
						{
							dArray.remove((Integer) count);
						}
					} else
					{
						tmpStr = str + "¦§¦¡";
					}

					System.out.println(tmpStr + file.getName() + ",package");
					subDirList(file.getCanonicalPath().toString(), count + 2,
							dArray);
				}
			}

		} catch (IOException e)
		{

		}
	}
}
