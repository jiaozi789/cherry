CHERRY服务器
=====================

## 1.简要介绍 ##
 cherry服务器是使用java开发的支持javaee5的web服务器 目前支持http协议 https正在完善 jsp的实现使用apache jspc 具体目录结构
   * /conf 表示配置文件目录 其中有配置示例
   * /lib 类加载加载的第三方jar
   * /app 表示发布的网页应用或者 javaee规范的war包或目录
   * /src 表示源代码 其中包括com.lm.cherry源代码 以及javax的javaee5规范
 源代码中未给出可执行脚本 
 作者:lm  联系方式 lixin1112003@126.com

## 2.启动流程 ##
 ### 2.1 使用源代码启动 ###
git克隆源代码  开发工具导入 （默认使用eclipse） 运行
```start
  com.lm.cherry.server.Cherry  start|stop
```

    
    
    
    
