package com.lm.cherry.server;
/**
 * 
 *  ������ ����:LM 
 * ��������:2015-1-5����06:51:24gi 
 * ��Ŀ����:cherry 
 * ������:com.lm.cherry.server
 *  ֱ��ʹ�� ��������Ϊ���������� Ĭ��Ϊ����
 */
public class Cherry {

	private static final String CATALINA_ARGS_STOP = "stop";
	private static final String CATALINA_ARGS_START = "start";
	private static Catalina cla;
	/**
	 * @param args
	 * start��ʾ���� stop��ʾ�ر�
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
