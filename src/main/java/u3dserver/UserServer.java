package u3dserver;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import net.sf.json.JSONObject;
import util.Constants;
import util.LogUtil;

public class UserServer extends Thread{
	InputStream input;
	BufferedReader reader;
	OutputStream out;
	PrintWriter pw;
	private Socket socket = null;
	public UserServer(Socket socket){
		try{
			this.socket = socket;
			input = socket.getInputStream();
			reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
			//输出流
			out = socket.getOutputStream();
			pw = new PrintWriter(out);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		try{
			//输入流
			String data = reader.readLine();
			while (data!=null) {
				LogUtil.log("服务器接收到请求："+data);
				//将收到的数据发送给客户端
				if(data.equals("exit")){
					pw.write("connect closed;\n");
					pw.flush();
					pw.close();
					out.close();
					reader.close();
					input.close();
					socket.close();
					MainServer.instance().unonline();
					return;
				}else{
					this.readData(data);
					data = reader.readLine();
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			MainServer.instance().unonline();
		}
		
	}
	/**
	 * 发送消息
	 * @param data
	 */
	public void sendMsg(String data){
		pw.write(data+"\n");
		pw.flush();
	}
	
	private void readData(String data){
		//sendMsg(data);
		JSONObject obj = null;
		try{
			obj = JSONObject.fromObject(data);
		}catch(Exception e){
			LogUtil.log("收到数据非json");;
			//e.printStackTrace();
			return;
		}
		Object a = obj.get("a");
		if(a!=null){
			try{
				//{"a":"123","un":"123123123123123123"}
				//{"a":"1","un":"1234"}
				int active = Integer.parseInt(a.toString());
				switch (active) {
					case Constants.AVTIVE_TYPE.CONNECT:{
						String username = obj.getString("un");
						MainServer.instance().addUser(username, this);
					}break;
					case Constants.AVTIVE_TYPE.CREATE_OBJ:{
						
					}break;
					case Constants.AVTIVE_TYPE.CREATE_USER:{
						
					}break;
					default:{
						MainServer.instance().sendData2All(data);
					}break;
				}
			}catch(Exception e){
				LogUtil.log("json解析错误"+obj.toString());
				//e.printStackTrace();
				return;
			}
			
			
		}
		//向所有客户端发出
	}
}
