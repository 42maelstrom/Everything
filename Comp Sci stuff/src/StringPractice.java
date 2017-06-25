
public class StringPractice {
	public static void main( String[] args)
	{
		System.out.println(withoutString("Hello there", "llo"));
		
	}
	public static String withoutString(String base, String remove) {
		  for(int i = 0; i < base.length(); i++)
		  {
		     if(base.substring(i, i+ remove.length()).equals(remove))
		     {
		        base = base.substring(0, i) + base.substring(i+ remove.length());
		     }
		  }
		  return base;
		}
	public int countHi(String str) {
		  int count = 0;
		  int index = str.length();
		  while( -1 != index )
		  {
		     index = str.lastIndexOf("hi", index);
		     count++;
		  }
		  return count;
		}
	public static int catDog(String str) {

		  int count = 0;
		  for(int i = 0; i < str.length() -2; i++)
		  {
		    if(str.substring(i, i+3).equals("cat")) count++;
		  }
		  for(int i = 0; i < str.length() -2; i++)
		  {
		    if(str.substring(i, i+3).equals("dog")) count--;
		  }
		  return count;  
		}
}
