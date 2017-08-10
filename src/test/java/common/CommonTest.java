package common;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import org.junit.Test;

public class CommonTest {
	
	@Test
	public void 机器信息() throws UnknownHostException, UnsupportedEncodingException{
		InetAddress inetaddres = InetAddress.getLocalHost();
		;
		System.out.println(inetaddres.getHostName());
		System.out.println(inetaddres.getHostAddress());
		System.out.println(inetaddres.getAllByName("CK_DEV01").length);
		for(InetAddress i:inetaddres.getAllByName("CK_DEV01")){
			System.out.println(i.getHostName()+"=="+i.getHostAddress());
		}
		
		InetAddress inetaddres1 = InetAddress.getByName("192.168.0.222");
		System.out.println(inetaddres1.getHostName());
		System.out.println(inetaddres1.getHostAddress());
		for(InetAddress i:inetaddres1.getAllByName(inetaddres1.getHostName())){
			System.out.println(i.getHostName()+"=="+i.getHostAddress());
		}
	}
	
	@Test
	public void 获取网络信息() throws MalformedURLException{
		URL xcz = new URL("http://www.x-cz.cn");
		URL url = new URL(xcz, "/#page2");
	}
	
}
