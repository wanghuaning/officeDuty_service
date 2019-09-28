package com.local.common.push;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;

/**
 * spring boot的定时器是单线程的，但是我们实际上的项目中可能会有多个定时器启动这种单线程的情况下就不能满足我们的需求，
 * 如果一个定时器里面的数据量过大或者是处理逻辑复杂耗费时间很长就会出现后面的定时要等这个定时器处理完才执行，
 * 这就可能造成定时器执行延迟，所以这个时候我们就要使用多线程启动定时器。
 * 类或方法的功能描述：定时多线程配置
 * 增加一个定时器配置文件，里面配置多线程信息，我们设置线程池支持10个线程，并且使用完后60s自动结束。
 */
@Configuration
//替换xml配置文件被注解的类内部包含有一个或多个被@Bean注解的方法，这些方法将会被AnnotationConfigApplicationContext或AnnotationConfigWebApplicationContext类进行扫描，并用于构建bean定义，初始化Spring容器。
@EnableAsync//启用异步任务
@ComponentScan("com.twodb.common.tasks")
public class ScheduleConfig implements SchedulingConfigurer, AsyncConfigurer {
    /**
     * 并行任务
     *
     * @param scheduledTaskRegistrar
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        TaskScheduler taskScheduler=taskScheduler();
        scheduledTaskRegistrar.setTaskScheduler(taskScheduler);
    }

    /**
     * 多线程配置
     * ThreadPoolTaskScheduler创建线程池
     */
    @Bean(destroyMethod = "")
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(20);
        //设置线程名前缀
        scheduler.setThreadNamePrefix("task-");
        //线程内容执行完后60秒停
        scheduler.setAwaitTerminationSeconds(60);
        //等待所有线程执行完
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        return scheduler;
    }

    /**
     * 异步任务
     * @return
     */
    @Override
    public Executor getAsyncExecutor(){
        Executor executor=taskScheduler();
        return executor;
    }
    /**
     * 异常处理
     */
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler(){
        return new SimpleAsyncUncaughtExceptionHandler();
    }

}
