package com.lm.cherry.server;
/**
 * 
 *  启动类 作者:LM 
 * 创建日期:2015-1-5下午06:51:24gi 
 * 项目名称:cherry 
 * 包名称:com.lm.cherry.server
 *  直接使用 将该类作为启动的主类 默认为启动
 */
public class Cherry {

	private static final String CATALINA_ARGS_STOP = "stop";
	private static final String CATALINA_ARGS_START = "start";
	private static Catalina cla;
	/**
	 * @param args
	 * start表示启动 stop表示关闭
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		String command=CATALINA_ARGS_START;
		if(args.length>0){
			command=args[0];
		}
		if(CATALINA_ARGS_START.equals(command)){
			int port=0;
			if(args.length>1){
				port=Integer.parseInt(args[1]);
			}
			cla=new Catalina();
			cla.init();
			cla.run(port);
		}
		if(CATALINA_ARGS_STOP.equals(command)){
			cla.stop();
		}
	}

}
