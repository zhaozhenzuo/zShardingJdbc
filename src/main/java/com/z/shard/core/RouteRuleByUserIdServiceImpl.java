package com.z.shard.core;

import com.z.shard.util.StringUtil;

/**
 * 根据userId进行路由
 * 
 * @author zhao
 *
 */
public class RouteRuleByUserIdServiceImpl implements RouteRuleService {

	private RouteConfig routeConfig;

	public String getRealDBNameByRouteKey(String logicDBName, String routeKey) throws Exception {
		if (logicDBName == null) {
			throw new IllegalArgumentException("logicDBName cannot be null");
		}

		if (routeKey == null) {
			throw new IllegalArgumentException("routeKey cannot be null");
		}

		int length = routeKey.length();
		if (length < 4) {
			throw new IllegalArgumentException("routeKey length must larger than 4");
		}

		Integer maxDbNums = routeConfig.getMaxDbNums();
		if (maxDbNums == null) {
			throw new IllegalArgumentException("maxDbNums cannot be null");
		}

		String userIdLast = routeKey.substring(length - 4);
		long userIdLastLong = Long.valueOf(userIdLast);

		// 得到逻辑库名后两位
		long logicLastDbName = userIdLastLong / maxDbNums % maxDbNums;

		Integer dbSegment = routeConfig.getDbSegment();
		if (dbSegment == null || dbSegment <= 0) {
			throw new IllegalArgumentException("dbSegment不能为空并且不能小于等于0");
		}

		Integer dbSuffixLength = routeConfig.getDbSuffixLength();
		if (dbSuffixLength == null || dbSuffixLength <= 0) {
			throw new IllegalArgumentException("dbSuffixLength不能为空并且不能小于等于0");
		}

		long calRes = logicLastDbName / dbSegment * dbSegment;
		String dbSuffix = StringUtil.leftPadZero(String.valueOf(calRes), routeConfig.getDbSuffixLength());

		return logicDBName + dbSuffix;
	}

	public String getRealTableNameByRouteKey(String logicTableName, String routeKey) throws Exception {
		if (logicTableName == null) {
			throw new IllegalArgumentException("logicTableName cannot be null");
		}

		if (routeKey == null) {
			throw new IllegalArgumentException("routeKey cannot be null");
		}

		int length = routeKey.length();
		if (length < 4) {
			throw new IllegalArgumentException("routeKey length must larger than 4");
		}

		String userIdLast = routeKey.substring(length - 4);
		long userIdLastLong = Long.valueOf(userIdLast);

		Integer maxTableNums = routeConfig.getMaxTableNums();
		if (maxTableNums == null || maxTableNums <= 0) {
			throw new IllegalArgumentException("maxTableNums必须大于0");
		}

		long logicTableLastNmae = userIdLastLong % maxTableNums;

		Integer tableSegment = routeConfig.getTableSegment();
		if (tableSegment == null || tableSegment <= 0) {
			throw new IllegalArgumentException("tableSegment不能为空并且不能小于等于0");
		}

		Integer tableSuffixLength = routeConfig.getTableSuffixLength();
		if (tableSuffixLength == null || tableSuffixLength <= 0) {
			throw new IllegalArgumentException("tableSuffixLength不能为空并且不能小于等于0");
		}

		long calRes = logicTableLastNmae / tableSegment * tableSegment;
		String tableSuffix = StringUtil.leftPadZero(String.valueOf(calRes), routeConfig.getTableSuffixLength());

		return logicTableName + tableSuffix;
	}

	public RouteConfig getRouteConfig() {
		return routeConfig;
	}

	public void setRouteConfig(RouteConfig routeConfig) {
		this.routeConfig = routeConfig;
	}

	public static void main(String[] args) throws Exception {
		RouteRuleByUserIdServiceImpl routeRuleService = new RouteRuleByUserIdServiceImpl();

		RouteConfig routeConfig = new RouteConfig();
		routeConfig.setMaxDbNums(64);
		routeConfig.setMaxTableNums(64);
		routeConfig.setDbRealNums(16);
		routeConfig.setTableRealNums(64);

		routeConfig.setDbSegment(routeConfig.getMaxDbNums() / routeConfig.getDbRealNums());
		routeConfig.setTableSegment(routeConfig.getMaxTableNums() / routeConfig.getTableRealNums());

		routeConfig.setDbSuffixLength(4);
		routeConfig.setTableSuffixLength(4);

		routeConfig.setDbPrefix("db_order_");
		routeConfig.setTablePrefix("order_");

		routeRuleService.setRouteConfig(routeConfig);

		String userId = "1000000256";
		String dbName = routeRuleService.getRealDBNameByRouteKey(routeConfig.getDbPrefix(), userId);
		System.out.println("dbName[" + dbName + "]");

		String tableName = routeRuleService.getRealTableNameByRouteKey(routeConfig.getTablePrefix(), userId);
		System.out.println("tableName[" + tableName + "]");

	}

}
