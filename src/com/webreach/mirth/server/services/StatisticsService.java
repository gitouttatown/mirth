package com.webreach.mirth.server.services;

import java.util.Hashtable;

import org.apache.log4j.Logger;

import com.webreach.mirth.model.Statistics;
import com.webreach.mirth.server.core.util.JMXConnection;

public class StatisticsService {
	private Logger logger = Logger.getLogger(StatisticsService.class);
	private JMXConnection jmxConnection = null;
	
	public Statistics getChannelStatistics(int id) throws ServiceException {
		logger.debug("retrieving statistics: " + id);
		
		Statistics statistics = new Statistics();
		statistics.setErrorCount(getChannelErrorCount(id));
		statistics.setQueueSize(getChannelQueueSize(id));
		statistics.setReceivedCount(getChannelReceivedCount(id));
		statistics.setSentCount(getChannelSentCount(id));
		return statistics;
	}
	
	public void clearChannelStatistics(int id) throws ServiceException {
		logger.debug("clearing statistics: " + id);
		
		try {
			jmxConnection = new JMXConnection();
			Hashtable<String, String> properties = new Hashtable<String, String>();
			properties.put("type", "statistics");
			properties.put("name", String.valueOf(id));
			jmxConnection.invokeOperation(properties, "clear", null);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	private int getChannelSentCount(int id) {
		logger.debug("retrieving message sent count: " + id);
		
		try {
			jmxConnection = new JMXConnection();
			return getStatistic(id, "TotalEventsSent");
		} catch (Exception e) {
			return -1;
		}
	}

	// This is a hack to address the fact that this statistic is incorrectly
	// incrememnted by 2 in Mule.
	private int getChannelReceivedCount(int id) {
		logger.debug("retrieving message received count: " + id);
		
		try {
			jmxConnection = new JMXConnection();
			Hashtable<String, String> properties = new Hashtable<String, String>();
			properties.put("type", "statistics");
			properties.put("name", String.valueOf(id));
			Double count = ((Long) jmxConnection.getAttribute(properties, "TotalEventsReceived")).doubleValue();
			return Double.valueOf(Math.ceil(count / 2)).intValue();
		} catch (Exception e) {
			return -1;
		}
	}

	private int getChannelErrorCount(int id) {
		logger.debug("retrieving error count: " + id);
		
		try {
			jmxConnection = new JMXConnection();
			return getStatistic(id, "ExecutionErrors");
		} catch (Exception e) {
			return -1;
		}
	}

	private int getChannelQueueSize(int id) {
		logger.debug("retrieving message queue count: " + id);
		
		try {
			jmxConnection = new JMXConnection();
			return getStatistic(id, "QueuedEvents");
		} catch (Exception e) {
			return -1;
		}
	}

	private int getStatistic(int id, String statistic) throws ServiceException {
		try {
			jmxConnection = new JMXConnection();
			Hashtable<String, String> properties = new Hashtable<String, String>();
			properties.put("type", "statistics");
			properties.put("name", String.valueOf(id));

			return ((Long) jmxConnection.getAttribute(properties, statistic)).intValue();
		} catch (Exception e) {
			throw new ServiceException();
		}
	}

}
