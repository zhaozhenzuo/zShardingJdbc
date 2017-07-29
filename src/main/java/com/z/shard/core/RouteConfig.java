package com.z.shard.core;

import java.util.HashMap;
import java.util.Map;

/**
 * 路由配置<br/>
 * 比如基于hash的路由配置，相应的实现只需要往对应map增加参数就可以了
 * 
 * @author zhao
 *
 */
public class RouteConfig {
	
	/**
	 * 实际物理db数量
	 */
	private Integer dbRealNums;

	/**
	 * 实际物理talbe数量
	 */
	private Integer tableRealNums;

	/**
	 * 最大数据库数量
	 */
	private Integer maxDbNums;

	/**
	 * 最大表数量
	 */
	private Integer maxTableNums;

	/**
	 * 单个分组的逻辑库数=maxDbNums/dbRealNums
	 */
	private Integer dbSegment;

	/**
	 * 单个分组的逻辑表数=maxTableNums/tableRealNums
	 */
	private Integer tableSegment;

	/**
	 * 数据库前缀
	 */
	private String dbPrefix;

	/**
	 * 表前缀
	 */
	private String tablePrefix;

	/**
	 * 数据库后缀长度
	 */
	private Integer dbSuffixLength;

	/**
	 * 表后缀长度
	 */
	private Integer tableSuffixLength;

	/**
	 * 其它配置map
	 */
	private Map<String, Object> otherConfigMap = new HashMap<String, Object>();

	public Integer getDbRealNums() {
		return dbRealNums;
	}

	public void setDbRealNums(Integer dbRealNums) {
		this.dbRealNums = dbRealNums;
	}

	public Integer getTableRealNums() {
		return tableRealNums;
	}

	public void setTableRealNums(Integer tableRealNums) {
		this.tableRealNums = tableRealNums;
	}

	public Integer getDbSegment() {
		return dbSegment;
	}

	public void setDbSegment(Integer dbSegment) {
		this.dbSegment = dbSegment;
	}

	public Integer getTableSegment() {
		return tableSegment;
	}

	public void setTableSegment(Integer tableSegment) {
		this.tableSegment = tableSegment;
	}

	public String getDbPrefix() {
		return dbPrefix;
	}

	public void setDbPrefix(String dbPrefix) {
		this.dbPrefix = dbPrefix;
	}

	public String getTablePrefix() {
		return tablePrefix;
	}

	public void setTablePrefix(String tablePrefix) {
		this.tablePrefix = tablePrefix;
	}

	public Integer getDbSuffixLength() {
		return dbSuffixLength;
	}

	public void setDbSuffixLength(Integer dbSuffixLength) {
		this.dbSuffixLength = dbSuffixLength;
	}

	public Integer getTableSuffixLength() {
		return tableSuffixLength;
	}

	public void setTableSuffixLength(Integer tableSuffixLength) {
		this.tableSuffixLength = tableSuffixLength;
	}

	public Map<String, Object> getOtherConfigMap() {
		return otherConfigMap;
	}

	public void setOtherConfigMap(Map<String, Object> otherConfigMap) {
		this.otherConfigMap = otherConfigMap;
	}

	public Integer getMaxDbNums() {
		return maxDbNums;
	}

	public void setMaxDbNums(Integer maxDbNums) {
		this.maxDbNums = maxDbNums;
	}

	public Integer getMaxTableNums() {
		return maxTableNums;
	}

	public void setMaxTableNums(Integer maxTableNums) {
		this.maxTableNums = maxTableNums;
	}

}
