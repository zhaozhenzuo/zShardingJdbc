package com.z.shard.core;

/**
 * 订单号配置规则
 * 
 * @author zhao
 *
 */
public class OrderNoConfig extends RouteConfig{

	/**
	 * 这台机子唯一的ip值，可以是zk上的一个唯一值
	 */
	private String hostIp;

	/**
	 * 每个table分组有多个物理表
	 */
	private Integer tableSegment;

	/**
	 * 每个db分组有多个物理表
	 */
	private Integer dbSegment;

	public String getHostIp() {
		return hostIp;
	}

	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}

	public Integer getTableSegment() {
		return tableSegment;
	}

	public void setTableSegment(Integer tableSegment) {
		this.tableSegment = tableSegment;
	}

	public Integer getDbSegment() {
		return dbSegment;
	}

	public void setDbSegment(Integer dbSegment) {
		this.dbSegment = dbSegment;
	}

}
