package net.dugged.nessie.himmelbjerget;

import com.google.common.collect.EvictingQueue;

@SuppressWarnings("UnstableApiUsage")
public class TPSCalculation {
	private final EvictingQueue<Long> fastTimeUpdates = EvictingQueue.create(10);
	private final EvictingQueue<Long> slowTimeUpdates = EvictingQueue.create(60);
	public int fastMspt = 50;
	public int slowMspt = 50;
	public int fastTps = 20;
	public int slowTps = 20;

	public void calculateFastTps() {
		final var mspt = this.calculateMspt(this.fastTimeUpdates, true);
		final var tps = 1000 / mspt;
		this.fastMspt = mspt;
		this.fastTps = tps;
	}

	public void calculateSlowTps() {
		final var mspt = this.calculateMspt(this.slowTimeUpdates, false);
		final var tps = 1000 / mspt;
		this.slowMspt = mspt;
		this.slowTps = tps;
	}

	public void resetTpsTimes() {
		this.fastTimeUpdates.clear();
		this.slowTimeUpdates.clear();
	}

	@SuppressWarnings("DataFlowIssue")
	private int calculateMspt(final EvictingQueue<Long> list, final boolean isFast) {
		final var currentTime = System.nanoTime();
		list.add(currentTime);
		final var dt = currentTime - list.peek();
		final var conversionConstant = isFast ? 1E-7D : 5E-8D;
		return (int) Math.max(50, dt *  conversionConstant/ list.size());
	}
}
