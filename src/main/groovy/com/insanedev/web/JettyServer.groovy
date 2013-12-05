package com.insanedev.web

import java.io.*;
import java.net.*;
import java.util.*;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.*;
import org.eclipse.jetty.server.nio.*;
import org.eclipse.jetty.util.thread.*;
import org.eclipse.jetty.webapp.*;

class JettyServer {

	static main(args) {
		JettyServer server = new JettyServer(8080, null);
		server.start();
		server.join();
	}

	// TODO: You should configure this appropriately for your environment
	private static final String LOG_PATH = "./var/logs/access/yyyy_mm_dd.request.log";
	
	private static final String WEB_XML = "WEB-INF/web.xml";
	private static final String CLASS_ONLY_AVAILABLE_IN_IDE = "com.sjl.IDE";
	private static final String PROJECT_RELATIVE_PATH_TO_WEBAPP = "src/main/webapp";
	
	public static interface WebContext
	{
		public File getWarPath();
		public String getContextPath();
	}
	
	
	private Server server;
	private int port;
	private String bindInterface;
	
	public WebServer(int aPort, String aBindInterface)
	{
		port = aPort;
		bindInterface = aBindInterface;
	}
	
	public void start() throws Exception
	{
		server = new Server(port, createThreadPool());

		// server.setThreadPool(createThreadPool());
		server.addConnector(createConnector());
		server.setHandler(createHandlers());
		server.setStopAtShutdown(true);
				
		server.start();
	}
	
	public void join() throws InterruptedException
	{
		server.join();
	}
	
	public void stop() throws Exception
	{
		server.stop();
	}
	
	private ThreadPool createThreadPool()
	{
		// TODO: You should configure these appropriately
		// for your environment - this is an example only
		QueuedThreadPool threadPool = new QueuedThreadPool();
		threadPool.setMinThreads(1);
		threadPool.setMaxThreads(100);
		return threadPool;
	}
	
	private ServerConnector createConnector()
	{
		ServerConnector http = new ServerConnector();
		http.setHost(bindInterface)
		http.setPort(port)
		return http;
	}
	
	private HandlerCollection createHandlers()
	{
		WebAppContext ctx = new WebAppContext();
		ctx.setContextPath("/");
		
		if(isRunningInShadedJar())
		{
			ctx.setWar(getShadedWarUrl());
		}
		else
		{
			ctx.setWar(PROJECT_RELATIVE_PATH_TO_WEBAPP);
		}
		
		List<Handler> handlers = new ArrayList<Handler>();
		
		handlers.add(ctx);
		
		HandlerList contexts = new HandlerList();
		contexts.setHandlers(handlers.toArray(new Handler[0]));
		
		RequestLogHandler log = new RequestLogHandler();
		log.setRequestLog(createRequestLog());
		
		HandlerCollection result = new HandlerCollection();
		result.addHandler(contexts)
		result.addHandler(log)
		
		return result;
	}
	
	private RequestLog createRequestLog()
	{
		NCSARequestLog log = new NCSARequestLog();
		
		File logPath = new File(LOG_PATH);
		logPath.getParentFile().mkdirs();
				
		log.setFilename(logPath.getPath());
		log.setRetainDays(90);
		log.setExtended(false);
		log.setAppend(true);
		log.setLogTimeZone("GMT");
		log.setLogLatency(true);
		return log;
	}
	
//---------------------------
// Discover the war path
//---------------------------
	
	private boolean isRunningInShadedJar()
	{
		return false;
		/*
		try
		{
			Class.forName(CLASS_ONLY_AVAILABLE_IN_IDE);
			return false;
		}
		catch(ClassNotFoundException anExc)
		{
			return true;
		}
		*/
	}
	
	private URL getResource(String aResource)
	{
		return Thread.currentThread().getContextClassLoader().getResource(aResource);
	}
	
	private String getShadedWarUrl()
	{
		String urlStr = getResource(WEB_XML).toString();
		// Strip off "WEB-INF/web.xml"
		return urlStr.substring(0, urlStr.length() - 15);
	}

}
