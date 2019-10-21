package com.local.common.slog;

import com.local.entity.sys.SYS_Log;
import com.local.entity.sys.SYS_USER;
import io.swagger.models.auth.In;
import org.nutz.dao.Dao;
import org.nutz.json.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.*;
import java.util.logging.Logger;

@Repository
public class SLogService implements Runnable{
    private static Logger log = Logger.getLogger(SLogService.class.toString());
    ExecutorService es; //表述了异步执行的机制
    LinkedBlockingQueue<SYS_Log> queue;
    @Autowired
    private Dao dao;

    /**
     * 异步插入日志
     * LinkedBlockingQueue采用可重入锁(ReentrantLock)来保证在并发情况下的线程安全。
     * 内部是使用链表实现一个队列的，但是却有别于一般的队列，在于该队列至少有一个节点，头节点不含有元素。
     */
    public void async(SYS_Log log){
        LinkedBlockingQueue<SYS_Log> queue=this.queue;
        if (queue!=null){
            try{
                //这里用offer方法往阻塞队列里面添加对象，此方法表示若队列满了，则等待1秒，1秒后若队列还是满的，则丢弃数据。
                boolean re=queue.offer(log,50, TimeUnit.MICROSECONDS);
            }catch (InterruptedException e){
                e.printStackTrace();;
            }
        }
    }

    /**
     * 同步插入日志
     */
    public void sync(SYS_Log log){
        try {
            dao.fastInsert(log);
        }catch (Throwable e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true){
            LinkedBlockingQueue<SYS_Log> queue=this.queue;
            if (queue == null){
                break;
            }
            try {
                SYS_Log log=queue.poll(1,TimeUnit.SECONDS);
                if (log!=null){
                    sync(log);
                }
            }catch (InterruptedException e){
                break;
            }
        }
    }

    /**
     * 本方法通常由aop拦截器调用.
     * @param type         日志类型
     * @param tag          日志描述
     * @param src          方法名
     * @param args         方法参数
     * @param async        是否异步插入
     */
    public void log(String type, String tag, String src, String ip, SYS_USER user,Object[] args,boolean async){
    String param="";
    if (args !=null){
        param = Json.toJson(args);
    }
    SYS_Log log=SYS_Log.c(type, tag, src, ip, user, param);
    if (async){
        async(log);
    }else {
        sync(log);
    }
    }
    @PostConstruct
    public void init(){
        queue=new LinkedBlockingQueue<SYS_Log>();
        int c=Runtime.getRuntime().availableProcessors();
        es = Executors.newFixedThreadPool(c);
        for (int i =0;i<c;i++){
            es.submit(this);
        }
    }
    @PreDestroy
    public void close() throws InterruptedException {
        queue = null; // 触发关闭
        if (es != null && !es.isShutdown()) {
            es.shutdown();
            es.awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
