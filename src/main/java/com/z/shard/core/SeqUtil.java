package com.z.shard.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 序列产生类
 * 
 * @author zhao
 *
 */
public class SeqUtil {

	private Integer seq;

	private Lock lock = new ReentrantLock();

	private Long curTimeInMillSeconds;

	private static Map<Long, Integer> staticDataMapForTest = new HashMap<Long, Integer>();

	/**
	 * 获取序列，保证相同一毫秒内，多个线程获取时，序列不会冲突
	 * 
	 * @return
	 */
	public int getSeq() {
		lock.lock();

		try {
			long curTime = System.currentTimeMillis();

			if (curTimeInMillSeconds == null || curTime != curTimeInMillSeconds) {
				curTimeInMillSeconds = curTime;
				seq = 0;
			}

			seq = seq + 1;

			staticDataMapForTest.put(curTimeInMillSeconds, seq);

			long cost = System.currentTimeMillis() - curTime;
			System.out.println("====get seq[" + seq + "],time[" + curTimeInMillSeconds + "],threadId["
					+ Thread.currentThread().getId() + "],cost[" + cost + "]");

			return seq;
		} finally {
			lock.unlock();
		}

	}

	public static void main(String[] args) throws InterruptedException {
		// testPerSecondsGenerateNoNums();
		testCurrent();
	}

	private static void testPerSecondsGenerateNoNums() {
		SeqUtil seqUtil = new SeqUtil();

		long time = System.currentTimeMillis();
		while (true) {

			long curTime = System.currentTimeMillis() - time;
			if (curTime > 5) {
				break;
			}

			seqUtil.getSeq();
		}

		for (Entry<Long, Integer> entry : staticDataMapForTest.entrySet()) {
			System.out.println(">time[" + entry.getKey() + "],nums[" + entry.getValue() + "]");
		}

	}

	private static void testCurrent() throws InterruptedException {
		int numsThread = 16;

		CountDownLatch countDownLatch = new CountDownLatch(numsThread);

		ExecutorService executorService = Executors.newFixedThreadPool(numsThread);

		SeqUtil seqUtil = new SeqUtil();

		long oldTime = System.currentTimeMillis();

		for (int i = 0; i < numsThread; i++) {
			executorService.submit(new GetSeqTask(countDownLatch, seqUtil));
		}

		countDownLatch.await();

		long cost = System.currentTimeMillis() - oldTime;

		System.out.println(">cost[" + cost + "]");
	}

	static class GetSeqTask implements Runnable {

		private CountDownLatch countDownLatch;

		private SeqUtil seqUtil;

		public GetSeqTask(CountDownLatch countDownLatch, SeqUtil seqUtil) {
			this.countDownLatch = countDownLatch;
			this.seqUtil = seqUtil;
		}

		public void run() {
			countDownLatch.countDown();
			try {
				countDownLatch.await();

				// getSeq
				int seq = seqUtil.getSeq();

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
