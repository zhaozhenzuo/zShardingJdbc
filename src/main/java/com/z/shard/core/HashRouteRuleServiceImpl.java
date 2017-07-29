package com.z.shard.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.z.shard.util.StringUtil;

import junit.framework.Assert;

/**
 * hash路由
 * 
 * @author zhao
 *
 */
public class HashRouteRuleServiceImpl implements RouteRuleService {

	private static final Logger logger = LoggerFactory.getLogger(HashRouteRuleServiceImpl.class);

	/**
	 * 路由配置规则
	 */
	private RouteConfig routeConfig;

	public String getRealDBNameByRouteKey(String logicDBName, String routeKey) throws Exception {
		/**
		 * 1.检查参数
		 */
		Assert.assertNotNull(routeConfig);
		Assert.assertNotNull(logicDBName);
		Assert.assertNotNull(routeKey);

		/**
		 * 2.路由
		 */
		try {
			int routeKeyInt = Integer.valueOf(routeKey);

			Integer dbRealNums = routeConfig.getDbRealNums();
			if (dbRealNums == null) {
				throw new IllegalArgumentException("dbRealNums不能为空");
			}

			String dbPrefix = routeConfig.getDbPrefix();
			if (dbPrefix == null) {
				throw new IllegalArgumentException("dbPrefix不能为空");
			}

			Integer dbSuffixLength = routeConfig.getDbSuffixLength();
			if (dbSuffixLength == null) {
				throw new IllegalArgumentException("dbSuffixLength不能为空");
			}

			int modRes = routeKeyInt % dbRealNums;

			return dbPrefix + StringUtil.leftPadZero(String.valueOf(modRes), dbSuffixLength);
		} catch (Exception e) {
			logger.error(">getRealDBNameByRouteKey exception,routeKey[" + routeKey + "]", e);
			throw e;
		}
	}

	public String getRealTableNameByRouteKey(String logicTableName, String routeKey) throws Exception {
		/**
		 * 1.检查参数
		 */
		Assert.assertNotNull(routeConfig);
		Assert.assertNotNull(logicTableName);
		Assert.assertNotNull(routeKey);

		/**
		 * 2.路由
		 */
		try {
			int routeKeyInt = Integer.valueOf(routeKey);

			Integer tableRealNums = routeConfig.getDbRealNums();
			if (tableRealNums == null) {
				throw new IllegalArgumentException("tableRealNums不能为空");
			}

			String tablePrefix = routeConfig.getTablePrefix();
			if (tablePrefix == null) {
				throw new IllegalArgumentException("tablePrefix不能为空");
			}

			Integer tableSuffixLength = routeConfig.getTableSuffixLength();
			if (tableSuffixLength == null) {
				throw new IllegalArgumentException("tableSuffixLength不能为空");
			}

			int modRes = routeKeyInt % tableRealNums;

			return tablePrefix + StringUtil.leftPadZero(String.valueOf(modRes), tableSuffixLength);
		} catch (Exception e) {
			logger.error(">getRealTableNameByRouteKey exception,routeKey[" + routeKey + "]", e);
			throw e;
		}
	}

	public RouteConfig getRouteConfig() {
		return routeConfig;
	}

	public void setRouteConfig(RouteConfig routeConfig) {
		this.routeConfig = routeConfig;
	}

}
