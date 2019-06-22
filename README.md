# diyBlockingQueue

通过ReentrantLock和condition实现以个阻塞队列
 1.当队列为空时，请求take会被阻塞，直到队列不为空


2.当队列满了以后，存储元素的线程需要备阻塞直到队列可以添加数据
