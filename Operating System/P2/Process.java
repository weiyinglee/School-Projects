public class Process {
	private int pid;
	private int cycle;
	
	public Process(int pid, int cycle) {
		this.pid = pid;
		this.cycle = cycle;
	}

	public int getPid() {
		return this.pid;
	}

	public int getCycle() {
		return this.cycle;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public void setCycle(int cycle) {
		this.cycle = cycle;
	}
}