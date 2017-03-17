import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ProcessTable {

	private ArrayList<Process> list;

	//constructor
	public ProcessTable() {
		list = new ArrayList<>();

		//default information for the process
		int pid = 1;
		String program = "init";
		String user = "root";
		int status = 0;

		//six register contents generates
		Random random = new Random();

		int pc = random.nextInt();
		int sp = random.nextInt();
		int r0 = random.nextInt();
		int r1 = random.nextInt();
		int r2 = random.nextInt();
		int r3 = random.nextInt();
	
		//add the first process into list
		list.add(new Process(pid, program, user, status, pc, sp, r0, r1, r2, r3));
	}

	/* helper methods */
	//convert int hex to proper hexFormat
	private String hexFormat(int value) {
		return "0x" + Integer.toHexString(value);
	}

	//check if the PID is duplicated
	private boolean checkPID(int pid) {
		for(Process p: list) {
			if(p.getPid() == pid) {
				return false;
			}
		}
		return true;
	}

	//generate the unique pid
	private int pidGenerator() {
		int pid = 1;
		while(!checkPID(pid)) {
			pid++;
		}
		return pid;
	}

	//find the currently running process (find the process that is status 0)
	private Process currentProcess() {
		Process curProcess = null;
		for(Process p: list) {
			if(p.getCurrentStatus() == 0) {
				curProcess = p;
			}
		}
		return curProcess;
	}

	//check the privilege of the user to kill
	private boolean userPrivilegeToKill(String seletedProcessUser) {
		String curProcessName = currentProcess().getUsername();
		if(curProcessName.equals("root") || curProcessName.equals(seletedProcessUser)) {
			return true;
		}
		return false;
	}

	//find the next ready state
	private Process nextReadyProcess() {

		Process process = null;
		int index = list.indexOf(currentProcess());

		//find the ready state after current process
		while(list.get(index).getCurrentStatus() != 1) {
			index++;
			if(index >= list.size()) {
				index = 0;
				break;
			}
		}

		//re-loop the list until the current process
		while(list.get(index).getCurrentStatus() != 1) {
			index++;
			if(index >= list.indexOf(currentProcess())) {
				index = -1;
				break;
			}
		}

		if(index != -1) {
			process = list.get(index); 
		}

		return process;
	}

	//commands
	/*
		fork: make a copy of the current running process
			1. status 1
			2. new unique pid
			3. same program, user, and register contents as current running process

		method: store the new forked process into list
	*/
	public void fork() {

		int status = 1;				//satisfy 1.
		int pid = pidGenerator();	//satisfy 2.

		//satisfy 3.
		try {
			String program = currentProcess().getProgram();
			String user = currentProcess().getUsername();
			int pc = currentProcess().getPC();
			int sp = currentProcess().getSP();
			int r0 = currentProcess().getR0();
			int r1 = currentProcess().getR1();
			int r2 = currentProcess().getR2();
			int r3 = currentProcess().getR3();

			//store the new forked process into list
			list.add(new Process(pid, program, user, status, pc, sp, r0, r1, r2, r3));
		}catch(NullPointerException e) {
			System.out.println("There is no running process");
		}
	}

	/*
		kill <pid>: kill the process with the specified pid
			1. root can kill any process
			2. same user only kill its own processes

		method: remove the proper selected process in the list 
	*/
	public void kill(int pid) {
		if(currentProcess().getPid() == pid) {
			//kill the current process
			exit();
		}else {
			//remove the process with inputed pid in the list
			for(int i = 0; i < list.size(); i++) {
				if(list.get(i).getPid() == pid) {
					//check the privilige
					if(userPrivilegeToKill(list.get(i).getUsername())) {
						list.remove(i);
					}else {
						System.out.println("Error: User has no privilege to kill");
					}
				}
			}			
		}
	}

	/*
		execve <program> <user>: switch the program and user name for the current running program.
			1. change the program and username 
			2. set all the register contents(process) to newly values

		method: modify the current process in the list
	*/
	public void execve(String program, String user) {
		//satify 1.
		currentProcess().setProgram(program);
		currentProcess().setUsername(user);
		//satify 2.
		currentProcess().setRegisters();
	}

	/*
		block: put the current running process in to blocked state (2)
			1. Unload from the CPU (since it's not running Process anymore)
			2. Randomly choose a ready process to be running and load to the CPU

		method: modify the current process in the list and randomly choose a ready stated process into running state	
	*/
	public void block() {
		Process oldProcess = currentProcess();
		try {
			nextReadyProcess().setCurrentStatus(0);
			oldProcess.setCurrentStatus(2);
		}catch(NullPointerException e) {
			System.out.println("Error: There is no ready state to run");
		}
	}

	/*
		yield: put the current running process to ready state (1)
			1. Unload from the CPU (since it's not running process anymore)
			2. Randomly choose other ready process to be running and load to the CPU

		method: modify the current process in the list and randomly choose a ready stated process into running state
	*/
	public void yield() {
		Process oldProcess = currentProcess();
		try {
			nextReadyProcess().setCurrentStatus(0);
			oldProcess.setCurrentStatus(1);
		}catch(NullPointerException e) {
			System.out.println("Error: There is no ready state to run");
		}
	}

	/*
		exit: remove the current running process
			1. Unload from the CPU (since it's removed)
			2. Randomly choose other ready process to be running and load to the CPU

		method: remove the current running process from the list, and randomly choose a ready stated process into running state
	*/
	public void exit() {
		Process oldProcess = currentProcess();
		try {
			nextReadyProcess().setCurrentStatus(0);
			list.remove(list.indexOf(oldProcess));
		}catch(NullPointerException e) {
			System.out.println("Error: There is no ready state to run");
		}
	}

	/*
		unblock <pid>: move the selected blocked process to the ready state
		method: modify the selected blocked process to ready state
	*/
	public void unblock(int pid) {
		if(currentProcess().getPid() == pid) {
			//unblock the current process, which is impossible
			System.out.println("Error: The currently running process is not blocked.");
		}else {
			//unblock the process with inputed pid in the list
			for(int i = 0; i < list.size(); i++) {
				if(list.get(i).getPid() == pid) {
					//check the privilige
					if(list.get(i).getCurrentStatus() == 2) {
						list.get(i).setCurrentStatus(1);
					}else {
						System.out.println("Error: The selected process is not blocked.");
					}
				}
			}			
		}
	}

	//print: print the CPU and process table contents
	public void print() {
		/* CPU block */
		//Titles
		System.out.println("CPU: ");
		//Contents
		System.out.printf("%s%s", " PC = ", hexFormat(currentProcess().getPC()));
		System.out.printf("%10s%s\n", " SP = ", hexFormat(currentProcess().getSP()));
		System.out.printf("%s%s", " R0 = ", hexFormat(currentProcess().getR0()));
		System.out.printf("%10s%s\n", " R1 = ", hexFormat(currentProcess().getR1()));
		System.out.printf("%s%s", " R2 = ", hexFormat(currentProcess().getR2()));
		System.out.printf("%10s%s\n", " R3 = ", hexFormat(currentProcess().getR3()));
		
		System.out.print("\n");

		/* Process Table block */
		//Titles
		System.out.println("Process Table: ");
		//Contents titles		
		System.out.printf("%s", " PID");
		System.out.printf("%15s", "Program"); 	
		System.out.printf("%15s", "User");		
		System.out.printf("%15s", "Status");		
		System.out.printf("%15s", "PC");
		System.out.printf("%15s", "SP");	
		System.out.printf("%15s", "R0");	
		System.out.printf("%15s", "R1");	
		System.out.printf("%15s", "R2");	
		System.out.printf("%15s", "R3\n");
		//Contents
		for(Process p: list) {
			System.out.printf("%4d", p.getPid());
			System.out.printf("%15s", p.getProgram());
			System.out.printf("%15s", p.getUsername());
			System.out.printf("%15d", p.getCurrentStatus());
			System.out.printf("%15s", hexFormat(p.getPC()));
			System.out.printf("%15s", hexFormat(p.getSP()));
			System.out.printf("%15s", hexFormat(p.getR0()));
			System.out.printf("%15s", hexFormat(p.getR1()));
			System.out.printf("%15s", hexFormat(p.getR2()));
			System.out.printf("%15s", hexFormat(p.getR3()));
			System.out.print("\n");
		}
	}

	//static helper methods
	private static ArrayList<String> multiCommands(String command) {
		
		ArrayList<String> result = new ArrayList<>();
		String packet = "";

		for(int i = 0; i < command.length(); i++) {
			if(command.charAt(i) != ' ') {
				packet += command.charAt(i);
			}else{
				result.add(packet);
				packet = "";
			}
		}

		result.add(packet);

		return result;
	}

	private static boolean checkCommandsNumber(int size, int number) {
		if(size > number) {
			System.out.println("Invalid Input: Extra commands");
			return false;
		}
		return true;
	}

	public static void main(String args[]) {

		ProcessTable pt = new ProcessTable();
		Scanner keybaord = new Scanner(System.in);

		String command;

		//enter commands
		while(true) {
			System.out.print("> ");
			command = keybaord.nextLine();

			switch(command) {
				case "fork":
					pt.fork();
					break;
				case "block":
					pt.block();
					break;
				case "yield":
					pt.yield();
					break;
				case "exit":
					pt.exit();
					break;
				case "print":
					pt.print();
					break;
				default:
					//handle the different commands which have multiple keywords
					ArrayList<String> commandPacket = multiCommands(command);
					switch(commandPacket.get(0)) {
						case "execve":
							if(checkCommandsNumber(commandPacket.size(), 3)) {
								try {
									pt.execve(commandPacket.get(1), commandPacket.get(2));
								}catch(Exception e) {
									System.out.println("Invalid Input");
								}	
							}
							break;
						case "kill":
							if(checkCommandsNumber(commandPacket.size(), 2)) {
								try {
									pt.kill(Integer.parseInt(commandPacket.get(1)));
								}catch(Exception e) {
									System.out.println("Invalid Input");
								}								
							}
							break;
						case "unblock":
							if(checkCommandsNumber(commandPacket.size(), 2)) {
								try {
									pt.unblock(Integer.parseInt(commandPacket.get(1)));
								}catch(Exception e) {
									System.out.println("Invalid Input");
								}
							}
							break;
						default:
							System.out.println("Invalid Input");
							break;
					}
					break;
			}
		}
	}
}