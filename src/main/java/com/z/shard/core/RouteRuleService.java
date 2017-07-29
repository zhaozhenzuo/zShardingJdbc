package com.z.shard.core;

/**
 * 路由规则接口
 */
public interface RouteRuleService {

	/**
	 * 根据相应key，获取到最终的物理库名
	 * 
	 * @param logicDBName
	 *            逻辑库名，一般指前缀
	 * @param routeKey
	 * @return
	 * @throws exception
	 */
	public String getRealDBNameByRouteKey(String logicDBName, String routeKey) throws Exception;

	/**
	 * 根据相应key，获取到最终的物理表名
	 * 
	 * @param logicTableName
	 *            逻辑表名，一般指前缀
	 * @param routeKey
	 * @return
	 * @throws exception
	 */
	public String getRealTableNameByRouteKey(String logicTableName, String routeKey) throws Exception;

}
