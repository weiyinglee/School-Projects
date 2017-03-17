import java.io.*;
import java.util.*;

public class ProcessScheduler {

	private ArrayList<Process> queue;

	public ProcessScheduler(String filename) {
		queue = new ArrayList<>();
		BufferedReader br = null;
		FileReader fr = null;

		try {
			fr = new FileReader(filename);
			br = new BufferedReader(fr);

			String sCurrentLine;
			while((sCurrentLine = br.readLine()) != null) {
				String[] parts = sCurrentLine.split(",");
				queue.add(new Process(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
			}
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(br != null) {
					br.close();
				}
				if(fr != null) {
					fr.close();
				}
			}catch(IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	//helper function
	private int totalCycle(ArrayList<Process> list) {
		int total = 0;
		for(Process p: list) {
			total += p.getCycle();
		}
		return total;
	}

	private ArrayList<Process> copyList(ArrayList<Process> list) {
		ArrayList<Process> result = new ArrayList<>();
		for(Process p: list) {
			result.add(new Process(p.getPid(), p.getCycle()));
		}
		return result;
	}

	private Process randomSelected(int totalCycle, ArrayList<Process> list) {
		Random random = new Random();
		int draw = random.nextInt(totalCycle) + 1;
		int start = 1;
		int end = 0;
		for(Process p: list) {
			end = start + p.getCycle();
			//within this range
			if(draw >= start && draw < end) {
				return p;
			}
			start = end;
		}
		return null;
	}

	public void fcfsScheduler() {
		int cycleTime = 0;
		int totalTime = 0;

		System.out.println("Running First-come, first-served scheduler.");
		for(Process p: queue) {
			cycleTime += p.getCycle();
			System.out.println("Process " + p.getPid() + " finishes on cycle " + cycleTime);
			totalTime += cycleTime;
		}
		System.out.printf("%s%.2f\n", "Average turnaround time: ", (double)(totalTime / queue.size()));
	}

	public void shortestFirstSchedule() {

		ArrayList<Process> tempList = copyList(queue);

		Collections.sort(tempList, new Comparator<Process>() {
			@Override
			public int compare(Process p1, Process p2) {
				return p1.getCycle() - p2.getCycle();
			}
		});

		int cycleTime = 0;
		int totalTime = 0;

		System.out.println("Running shortest first scheduler.");
		for(Process p: tempList) {
			cycleTime += p.getCycle();
			System.out.println("Process " + p.getPid() + " finishes on cycle " + cycleTime);
			totalTime += cycleTime;
		}
		System.out.printf("%s%.2f\n", "Average turnaround time: ", (double)(totalTime / queue.size()));
	}

	public void roundRobinScheduler(int quantum) {
		int cycleTime = 0;
		int totalTime = 0;
		int totalCycles = totalCycle(queue);

		ArrayList<Process> tempList = copyList(queue);

		System.out.println("Running round robin scheduler with quantum " + quantum);
		
		//calculate the cycleTime and totalTime
		while(totalCycles > 0) {
			for(Process p: tempList) {
				if(p.getCycle() > 0) {
					if(p.getCycle() < quantum) {
						cycleTime += p.getCycle();
						p.setCycle(p.getCycle() - p.getCycle());
					}else {
						p.setCycle(p.getCycle() - quantum);
						cycleTime += quantum;						
					}
					if(p.getCycle() == 0) {
						System.out.println("Process " + p.getPid() + " finishes on cycle " + cycleTime);
						totalTime += cycleTime;
					}
				}
			}
			totalCycles = totalCycle(tempList);
		}

		System.out.printf("%s%.2f\n", "Average turnaround time: ", (double)(totalTime / queue.size()));
	}

	public void randomScheduler() {
		int quantum = 50;
		int cycleTime = 0;
		int totalTime = 0;
		int totalCycles = totalCycle(queue);
		Process chance = null;

		ArrayList<Process> tempList = copyList(queue);

		System.out.println("Running random scheduler with quantum 50.");
		
		//calculate the cycleTime and totalTime
		while(totalCycles > 0) {
			chance = randomSelected(totalCycles, tempList);
			for(Process p: tempList) {
				if(p.getPid() == chance.getPid()) {
					if(p.getCycle() > 0) {
						if(p.getCycle() < 50) {
							cycleTime += p.getCycle();
							p.setCycle(p.getCycle() - p.getCycle());
						}else {
							p.setCycle(p.getCycle() - 50);
							cycleTime += 50;
						}
						if(p.getCycle() == 0) {
							System.out.println("Process " + p.getPid() + " finishes on cycle " + cycleTime);
							totalTime += cycleTime;
						}
					}
				}
			}
			totalCycles = totalCycle(tempList);
		}

		System.out.printf("%s%.2f\n", "Average turnaround time: ", (double)(totalTime / queue.size()));
	}

	public static void main(String[] args) {
		ProcessScheduler ps = new ProcessScheduler(args[0]);
		ps.fcfsScheduler();
		System.out.print("\n");
		ps.shortestFirstSchedule();
		System.out.print("\n");
		ps.roundRobinScheduler(50);
		System.out.print("\n");
		ps.roundRobinScheduler(100);
		System.out.print("\n");
		ps.randomScheduler();
	}
}