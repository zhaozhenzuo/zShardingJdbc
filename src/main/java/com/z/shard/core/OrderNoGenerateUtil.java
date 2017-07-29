package com.z.shard.core;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.z.shard.util.StringUtil;

/**
 *  订单号生成类
 * @author zhao
 *
 */
public class OrderNoGenerateUtil {

	private OrderNoConfig orderNoConfig;

	private SeqUtil seqUtil;

	public OrderNoGenerateUtil() {
		seqUtil = new SeqUtil();
	}

	/**
	 * 17位时间 + 4位服务器唯一值 + 4位自增序列 + 3位随机数 + 2位分库 + 2位分表
	 * 
	 * @param userId
	 * @return
	 */
	public String generateNo(String userId) {

		/**
		 * 0.参数校验
		 */
		if (userId == null) {
			throw new IllegalArgumentException("userId cannot be null");
		}

		if (orderNoConfig == null) {
			throw new IllegalArgumentException("orderNoConfig cannot be null");
		}

		String hostIp = orderNoConfig.getHostIp();
		if (hostIp == null) {
			throw new IllegalArgumentException("hostIp cannot be null");
		}

		if (hostIp.length() != 4) {
			throw new IllegalArgumentException("hostIp length must be 4");
		}

		/**
		 * 1.生成订单号
		 */
		StringBuilder buffer = new StringBuilder(32);

		// 17位时间
		String timePrefix = getTimePrefix();
		buffer.append(timePrefix);

		// 4位hostIp唯一值
		buffer.append(hostIp);

		// 4位自增序列
		long seq = seqUtil.getSeq();
		String seqStr = StringUtil.leftPadZero(String.valueOf(seq), 4);
		buffer.append(seqStr);

		// 3位随机数
		String random = getThreeRandom();
		buffer.append(random);

		// 2位分库
		String dbSuffix = this.getDBLogicSuffixForOrderNo(userId);
		buffer.append(dbSuffix);

		// 2位分表 
		String tableSuffix = this.getTableLogicSuffixForOrderNo(userId);
		buffer.append(tableSuffix);

		return buffer.toString();
	}

	/**
	 * 根据用户id后4位，得到逻辑库后缀
	 * 
	 * @param userId
	 * @return
	 */
	private String getDBLogicSuffixForOrderNo(String userId) {
		if (userId == null || userId.length() <= 4) {
			throw new IllegalArgumentException("userId必须大于4");
		}

		int length = userId.length();

		String lastUserId = userId.substring(length - 4, length);

		long lastUserIdLong = Long.valueOf(lastUserId);

		long res = lastUserIdLong / orderNoConfig.getMaxDbNums() % orderNoConfig.getMaxDbNums();

		return StringUtil.leftPadZero(String.valueOf(res), 2);
	}

	/**
	 * 根据用户id后4位，mod 64，得到逻辑表后缀
	 * 
	 * @param userId
	 * @return
	 */
	private String getTableLogicSuffixForOrderNo(String userId) {
		if (userId == null || userId.length() <= 4) {
			throw new IllegalArgumentException("userId必须大于4");
		}

		int length = userId.length();

		String lastUserId = userId.substring(length - 4, length);

		long lastUserIdLong = Long.valueOf(lastUserId);

		long res = lastUserIdLong % orderNoConfig.getMaxTableNums();

		return StringUtil.leftPadZero(String.valueOf(res), 2);
	}

	private static String getThreeRandom() {
		Random random = new Random();
		int res = random.nextInt(999);

		return StringUtil.leftPadZero(String.valueOf(res), 3);
	}

	private static final String getTimePrefix() {
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return simpleDateFormat.format(date);
	}

	public static void main(String[] args) {
		OrderNoGenerateUtil orderNoGenerateUtil = new OrderNoGenerateUtil();

		OrderNoConfig orderNoConfig = new OrderNoConfig();
		orderNoConfig.setHostIp("1000");
		orderNoConfig.setDbSegment(64);
		orderNoConfig.setTableSegment(4);
		orderNoConfig.setMaxDbNums(64);
		orderNoConfig.setMaxTableNums(64);

		orderNoGenerateUtil.setOrderNoConfig(orderNoConfig);

		String orderNo = orderNoGenerateUtil.generateNo("10000016");
		System.out.println(orderNo);
		System.out.println(orderNo.length());

	}

	public OrderNoConfig getOrderNoConfig() {
		return orderNoConfig;
	}

	public void setOrderNoConfig(OrderNoConfig orderNoConfig) {
		this.orderNoConfig = orderNoConfig;
	}

	public SeqUtil getSeqUtil() {
		return seqUtil;
	}

	public void setSeqUtil(SeqUtil seqUtil) {
		this.seqUtil = seqUtil;
	}

}
