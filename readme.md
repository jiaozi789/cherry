CHERRY服务器
=====================

## 1.简要介绍 ##
 cherry服务器是使用java开发的支持javaee5的web服务器 目前支持http协议 https正在完善 jsp的实现使用apache jspc 具体目录结构
   
   * /conf 表示配置文件目录 其中server.xml有配置示例
   * /lib 类加载加载的第三方jar
   * /app 表示发布的网页应用或者 javaee规范的war包或目录
   * /src 表示源代码 其中包括com.lm.cherry源代码 以及javax的javaee5规范

## 2.启动流程 ##
 ### 2.1 使用源代码启动 ###
git克隆源代码  开发工具导入 （默认使用eclipse） 运行
```start
  com.lm.cherry.server.Cherry  start|stop
```
### 2.2 使脚本启动 ###
 脚本方式运行 以下给出完整的打包程序 （源代码根目录下的cherry运行文件2.0.zip ）
 运行流程：
 * 解压cherry运行文件2.0.zip 
 * 部署应用到app目录 
 * 运行bin\startup.cmd 
 * 访问应用 默认端口8080  地址:ip:8080/context-path/
  eclipse发布项目插件 暂未给出 有需要联系   
  
作者:lm  联系方式 lixin1112003@126.com
