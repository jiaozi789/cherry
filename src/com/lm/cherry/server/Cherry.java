package com.lm.cherry.server;

public class Cherry {

	private static Catalina cla;
	/**
	 * @param args
	 * start表示启动 stop表示关闭
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		String command="start";
		if(args.length>0){
			command=args[0];
		}
		if("start".equals(command)){
			int port=0;
			if(args.length>1){
				port=Integer.parseInt(args[1]);
			}
			cla=new Catalina();
			cla.init();
			cla.run(port);
		}
		if("stop".equals(command)){
			cla.stop();
		}
	}

}
