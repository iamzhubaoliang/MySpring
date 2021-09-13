# 手写Spring项目

本项目主要是为了练习仿照Spring项目的功能手写一个spring

项目实现的功能

1. IOC ：实现了控制反转，可以通过注解进行注入
2. AOP：实现了代理功能，但是功能不完善，后期改进
3. SpringMVC ：实现了web层业务的解耦，可以将数据，业务处理逻辑，视图做基本的解耦

项目的思路：

项目的三级缓存是按照真实Spring的做法，不同的是此项目并没有使用Lambda表达式而是使用了一个DynamicBeanFactory一个动态工厂，当从第三级缓存取对象的时候依然是如果判断为代理对象则生成代理，否则返回原对象。

此项目可以帮助大家理解三级缓存，嘻嘻~，本人菜鸟大佬勿喷，欢迎提建议

**欢迎Pull request**

由于项目初期并没有做太多的优化因此会有Bug，可以讨论 :)

SpringMVC部分借鉴了博主  [椰子Tyshawn](https://tyshawnlee.blog.csdn.net/)   https://blog.csdn.net/litianxiang_kaola/article/details/86646947

项目中有一个测试工程

功能修复日志

1. 2021/9/12 添加了前后置处理器
2. 2021/9/12 添加了SpringMVC基本功能

# 博文

1. [（一）手写Spring项目之IOC](https://www.cnblogs.com/AI-Creator/p/15260200.html)

