import java.nio.charset.Charset;

import com.umpay.util.ToolBox;


public class ToolTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String s = "abc";
		byte[] x = s.getBytes(Charset.forName("UTF-8"));
		System.out.println(x.length);
		System.out.println(ToolBox.b2hex(x));
		byte[] newb = new byte[32];
		System.arraycopy(x, 0, newb, 0, x.length);
		System.out.println(ToolBox.b2hex(newb));
	}

}
