import java.util.Random;

public class Process {

	//Process control block information
	private int pid;			//Process ID
	private String program; 	//Program
	private String username; 	//User name running the program
	private int currentStatus;	//Current status of process, 0 -> running, 1 -> ready, 2 -> blocked

	//Register contents information
	private	int pc;	//program counter
	private	int sp;	//stack pointer
	private	int r0;	//register 0
	private	int r1;	//register 1
	private	int r2;	//register 2
	private	int r3;	//register 3

	//constructor
	public Process(int pid, String program, String username, int currentStatus, int pc, int sp, int r0, int r1, int r2, int r3) {
		this.pid = pid;
		this.program = program;
		this.username = username;
		this.currentStatus = currentStatus;
		this.pc = pc;
		this.sp = sp;
		this.r0 = r0;
		this.r1 = r1;
		this.r2 = r2;
		this.r3 = r3;
	}

	//getters
	public int getPid() {
		return pid;
	}

	public String getProgram() {
		return program;
	}

	public String getUsername() {
		return username;
	}

	public int getCurrentStatus() {
		return currentStatus;
	}

	public int getPC() {
		return pc;
	}

	public int getSP() {
		return sp;
	}
	
	public int getR0() {
		return r0;
	}
	
	public int getR1() {
		return r1;
	}
	
	public int getR2() {
		return r2;
	}
	
	public int getR3() {
		return r3;
	}	

	//setters
	public void setPid(int pid) {
		this.pid = pid;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setCurrentStatus(int currentStatus) {
		this.currentStatus = currentStatus;
	}

	public void setRegisters() {
		//six register contents generates
		Random random = new Random();

		this.pc = random.nextInt();
		this.sp = random.nextInt();
		this.r0 = random.nextInt();
		this.r1 = random.nextInt();
		this.r2 = random.nextInt();
		this.r3 = random.nextInt();
	}
}