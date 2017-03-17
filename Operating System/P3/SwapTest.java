import java.util.Scanner;
import java.util.ArrayList;
import java.util.Stack;

public class SwapTest {

	private Segment head;
	private ArrayList<Job> jobContainer;
	private Stack<Segment> tracker;
	
	public SwapTest() {
		head = new Segment(0, 0, 100, null);
		jobContainer = new ArrayList<>();
		tracker = new Stack<>();
	}

	//helper functions
	private Job findJob(int n) {
		for(Job process: jobContainer) {
			if(n == process.getPid()) {
				return process;
			}
		}
		return null;
	}

	private Segment findSegment(int n) {
		for(Segment temp = head; temp != null; temp = temp.getNext()) {
			if(temp.getPid() == n) {
				return temp;
			}
		}
		return null;
	}

	private boolean isHole(int n) {
		return n == 0;
	}

	private Segment findNextHole(int jobSize) {
		Segment temp = head;
		for(temp = head; temp != null; temp = temp.getNext()) {
			if(temp.getPid() == 0 && temp.getLength() >= jobSize) {
				return temp;
			}
		}
		return null;
	}

	private Segment findSmallestHole(int jobSize) {
		Segment temp = head;
		Segment smallestHole = findNextHole(jobSize);

		for(temp = head; temp != null; temp = temp.getNext()) {
			if(temp.getPid() == 0 && temp.getLength() >= jobSize) {
				if(temp.getLength() <= smallestHole.getLength()) {
					smallestHole = temp;
				}
			}
		}

		if(smallestHole.getPid() != 0) {
			return null;
		}
		
		return smallestHole;
	}

	private Segment findLargestHole(int jobSize) {
		Segment temp = head;
		Segment largestHole = findNextHole(jobSize);
		for(temp = head; temp != null; temp = temp.getNext()) {
			if(temp.getPid() == 0 && temp.getLength() >= jobSize) {
				if(temp.getLength() >= largestHole.getLength()) {
					largestHole = temp;
				}
			}
		}

		if(largestHole.getPid() != 0) {
			return null;
		}
			
		return largestHole;
	}

	private void fixHole(Segment hole, int start, int length) {
		hole.setStart(start);
		hole.setLength(length);
	}

	/*
		first fit allocation for job number n
		just find the space by order
	*/
	public boolean ff(int n) {
		Job job = findJob(n);
		boolean result = false;

		//original
		int pid = head.getPid();
		int start = head.getStart();
		int length = head.getLength();

		//new 
		int newPid = job.getPid();
		int newLength = job.getSize();
		
		if(job != null) {
			if(findSegment(n) == null) {
				//allocate
				if(isHole(pid) && length > newLength) {
					//allocate to front
					Segment newItem = new Segment(newPid, start, newLength, head);
					fixHole(head, newLength, length - newLength);
					head = newItem;
				}else {
					Segment hole = findNextHole(newLength);
					Segment curNode = head;
					Segment newItem = null;

					if (hole == null) {
						System.out.println("There is no space for allocation.");
						return false;
					}

					if(curNode != hole) {
						while(curNode.getNext() != hole) {
							curNode = curNode.getNext();
						}
						//allocate between curNode and hole
						int	startPt = curNode.getStart() + curNode.getLength();
						newItem = new Segment(newPid, startPt, newLength, hole);
						fixHole(hole, startPt + newLength, hole.getLength() - newItem.getLength());
						//deallocate curNode link
						curNode.setNext(newItem);
						tracker.push(hole);
					}else {
						Segment next = curNode.getNext();
						if(curNode.getLength() > newLength) {
							//create hole
							next = new Segment(0, curNode.getStart() + newLength, curNode.getLength() - newLength, curNode.getNext());
						}
						
						newItem = new Segment(newPid, curNode.getStart(), newLength, next);
						head = newItem;
						if(next.getPid() == 0) {
							tracker.push(next);
						}else {
							tracker.push(null);
						}
					}

				}
				result = true;
			}else {
				System.out.println("The process is already allocated");
			}
		}else {
			System.out.println("Process does not exist");
		}
		return result;
	}

	/*
		next fit allocation for job number n
		find the space from the last position
	*/
	public boolean nf(int n) {
		boolean result = false;

		Job job = findJob(n);

		//original
		int pid = head.getPid();
		int start = head.getStart();
		int length = head.getLength();

		//new 
		int newPid = job.getPid();
		int newLength = job.getSize();
		
		if(job != null) {
			if(findSegment(n) == null) {
				//allocate
				if(isHole(pid) && head.getNext() == null && length > newLength) {
					//allocate to front
					Segment newItem = new Segment(newPid, start, newLength, head);
					fixHole(head, newLength, length - newLength);
					head = newItem;
					tracker.push(head.getNext());
				 }else {
					Segment hole = tracker.pop();
					//check size
					 if(hole.getLength() < newLength) {
					 	hole = findNextHole(newLength);
					 }
					 if(hole == null) {
					 	System.out.println("There is no space for allocation");
					 	return false;
					 }
					Segment curNode = head;
					Segment newItem = null;

					if(curNode != hole) {
						while(curNode.getNext() != hole) {
							curNode = curNode.getNext();
						}
						//allocate between curNode and hole
						int	startPt = curNode.getStart() + curNode.getLength();
						newItem = new Segment(newPid, startPt, newLength, hole);
						fixHole(hole, startPt + newLength, hole.getLength() - newItem.getLength());
						//deallocate curNode link
						curNode.setNext(newItem);
						tracker.push(hole);
					}else {
						Segment next = curNode.getNext();
						if(curNode.getLength() > newLength) {
							//create hole
							next = new Segment(0, curNode.getStart() + newLength, curNode.getLength() - newLength, curNode.getNext());
						}
						
						newItem = new Segment(newPid, curNode.getStart(), newLength, next);
						head = newItem;
						if(next.getPid() == 0) {
							tracker.push(next);
						}else {
							tracker.push(null);
						}
					}
				}
				result = true;
			}else {
				System.out.println("The process is already allocated");
			}
		}else {
			System.out.println("Process does not exist");
		}
		return result;
	}

	/*
		best fit allocation for job number n
		just find the smallest hole
	*/
	public boolean bf(int n) {
		boolean result = false;
		Job job = findJob(n);
		//original
		int pid = head.getPid();
		int start = head.getStart();
		int length = head.getLength();

		//new 
		int newPid = job.getPid();
		int newLength = job.getSize();
		
		if(job != null) {
			if(findSegment(n) == null) {
				//allocate
				if(isHole(pid) && head.getNext() == null && length > newLength) {
					//allocate to front
					Segment newItem = new Segment(newPid, start, newLength, head);
					fixHole(head, newLength, length - newLength);
					head = newItem;
				 }else {
					Segment hole = findSmallestHole(newLength);
					 if(hole == null) {
					 	System.out.println("There is no space for allocation");
					 	return false;
					 }
					Segment curNode = head;
					Segment newItem = null;

					if(curNode != hole) {
						while(curNode.getNext() != hole) {
							curNode = curNode.getNext();
						}
						//allocate between curNode and hole
						int	startPt = curNode.getStart() + curNode.getLength();
						newItem = new Segment(newPid, startPt, newLength, hole);
						fixHole(hole, startPt + newLength, hole.getLength() - newItem.getLength());
						//deallocate curNode link
						curNode.setNext(newItem);
						tracker.push(hole);
					}else {
						Segment next = curNode.getNext();
						if(curNode.getLength() > newLength) {
							//create hole
							next = new Segment(0, curNode.getStart() + newLength, curNode.getLength() - newLength, curNode.getNext());
						}
						
						newItem = new Segment(newPid, curNode.getStart(), newLength, next);
						head = newItem;
						if(next.getPid() == 0) {
							tracker.push(next);
						}else {
							tracker.push(null);
						}
					}
				}
				result = true;
			}else {
				System.out.println("The process is already allocated");
			}
		}else {
			System.out.println("Process does not exist");
		}
		return result;
	}

	/*
		worst fit allocation for job number n
		find the largest hole
	*/
	public boolean wf(int n) {
		boolean result = false;
		Job job = findJob(n);
		//original
		int pid = head.getPid();
		int start = head.getStart();
		int length = head.getLength();

		//new 
		int newPid = job.getPid();
		int newLength = job.getSize();
		
		if(job != null) {
			if(findSegment(n) == null) {
				//allocate
				if(isHole(pid) && head.getNext() == null && length > newLength) {
					//allocate to front
					Segment newItem = new Segment(newPid, start, newLength, head);
					fixHole(head, newLength, length - newLength);
					head = newItem;
				 }else {
					Segment hole = findLargestHole(newLength);
					 if(hole == null) {
					 	System.out.println("There is no space for allocation");
					 	return false;
					 }
					Segment curNode = head;
					Segment newItem = null;

					if(curNode != hole) {
						while(curNode.getNext() != hole) {
							curNode = curNode.getNext();
						}
						//allocate between curNode and hole
						int	startPt = curNode.getStart() + curNode.getLength();
						newItem = new Segment(newPid, startPt, newLength, hole);
						fixHole(hole, startPt + newLength, hole.getLength() - newItem.getLength());
						//deallocate curNode link
						curNode.setNext(newItem);
						tracker.push(hole);
					}else {
						Segment next = curNode.getNext();
						if(curNode.getLength() > newLength) {
							//create hole
							next = new Segment(0, curNode.getStart() + newLength, curNode.getLength() - newLength, curNode.getNext());
						}
						
						newItem = new Segment(newPid, curNode.getStart(), newLength, next);
						head = newItem;
						if(next.getPid() == 0) {
							tracker.push(next);
						}else {
							tracker.push(null);
						}
					}
				}
				result = true;
			}else {
				System.out.println("The process is already allocated");
			}
		}else {
			System.out.println("Process does not exist");
		}
		return result;
	}

	/*
		deallocation for job number n
	*/
	public boolean de(int n) {
		boolean result = false;
		//find the node
		Segment segment = findSegment(n);
		Segment prev = head;
		
		if(segment != null || segment.getPid() != 0) {

			//if the segment is at the beginning of the list
			if(segment == head) {
				//after deallocation, there will be a hole at the beginning
				Segment hole = null;
				Segment nextNode = head.getNext();
				int length = head.getLength();

				//check if next one is hole as well, if it is, combine
				if(isHole(head.getNext().getPid())) {
					if(head.getNext().getNext() != null) {
						nextNode = head.getNext().getNext();
					}else {
						nextNode = null;
					}
					length = head.getLength() + head.getNext().getLength();
				}

				//create a hole
				hole = new Segment(0, 0, length, nextNode);
				head = hole;

			}
			//if the segment is at the end of the list
			else if(segment.getNext().getPid() == 0 && segment.getNext().getNext() == null) {
				//after deallocation, the hole next to it will combine
				prev = head;
				while(prev.getNext() != segment) {
					prev = prev.getNext();
				}

				//3. when segment is between regular node and hole
				if(!isHole(prev.getPid()) && isHole(segment.getNext().getPid())) {
					prev.setNext(segment.getNext());
					//increase hole/change start
					segment.getNext().setStart(segment.getStart());
					segment.getNext().setLength(segment.getNext().getLength() + segment.getLength());
				}

				//4. when segment is between hole and hole
				if(isHole(prev.getPid()) && isHole(segment.getNext().getPid())) {
					//combine hole
					Segment next = null;
					if(segment.getNext().getNext() != null) {
						next = segment.getNext().getNext();
					}
					prev.setNext(next);
					//increase hole
					prev.setLength(segment.getNext().getLength() + prev.getLength() + segment.getLength());
					segment.getNext().setNext(null);
				}

			}
			else {
				prev = head;
				while(prev.getNext() != segment) {
					prev = prev.getNext();
				}
				//1. when segment is between two regular node
				if(!isHole(prev.getPid()) && !isHole(segment.getNext().getPid())) {
					//prev.setNext(segment.getNext());

					//add a hole
					Segment alterHole = new Segment(0, segment.getStart(), segment.getLength(), segment.getNext());
					prev.setNext(alterHole);

					// //change other
					// for(Segment temp = segment.getNext(); temp != null; temp = temp.getNext()) {
					// 	temp.setStart(temp.getStart() - segment.getLength());
					// }

					// //change the last hole length
					// Segment temp = head;
					// while(temp.getNext() != null) { temp = temp.getNext(); }
					// temp.setLength(segment.getLength() + temp.getLength());
				}

				//2. when segment is between hole and regular node
				if(isHole(prev.getPid()) && !isHole(segment.getNext().getPid())) {
					prev.setNext(segment.getNext());
					//increase hole
					prev.setLength(prev.getLength() + segment.getLength());
				}

				//3. when segment is between regular node and hole
				if(!isHole(prev.getPid()) && isHole(segment.getNext().getPid())) {
					prev.setNext(segment.getNext());
					//increase hole/change start
					segment.getNext().setStart(segment.getStart());
					segment.getNext().setLength(segment.getNext().getLength() + segment.getLength());

					//change other
					for(Segment temp = segment.getNext(); temp != null; temp = temp.getNext()) {
						temp.setStart(temp.getStart() - segment.getLength());
					}
				}

				//4. when segment is between hole and hole
				if(isHole(prev.getPid()) && isHole(segment.getNext().getPid())) {
					//combine hole
					Segment next = null;
					if(segment.getNext().getNext() != null) {
						next = segment.getNext().getNext();
					}
					prev.setNext(next);
					//increase hole
					prev.setLength(segment.getNext().getLength() + prev.getLength() + segment.getLength());
					segment.getNext().setNext(null);
				}
			}

			segment.setNext(null);	
			result = true;
		}else {
			result = false;
		}

		return result;
	}

	/*
		This method adds job number n of size s to the list of jobs
	*/
	public void add(int n, int s) {
		Job process = findJob(n);
		if(process == null) {
			jobContainer.add(new Job(n, s));
		}else {
			System.out.println("This process is already existed");
		}
	}

	/*
		This method prints the list of jobs
	*/
	public void printJobs() {
		for(Job p: jobContainer) {
			System.out.print(p + " ");
		}
		System.out.print("\n");
	}

	/*
		This method prints the current contents of the linked list of segments
	*/
	public void printList() {
		for(Segment temp = head; temp != null; temp = temp.getNext()) {
			System.out.print(temp + " ");
		}
		System.out.print("\n");
	}

	public static void main(String[] args) {
		
		SwapTest st = new SwapTest();
		Scanner keyboard = new Scanner(System.in);
		String input;
		String[] command;

		while(true) {
			System.out.print("> ");
			input = keyboard.nextLine();

			command = input.split(" ");

			//command[0] should be methods
			//command[1] should be job number
			//comand[2] should be size/ or more job numbers..etc
			switch(command[0]) {
				case "add":
					//add needs two commands, job number n and size s
					if(command.length == 3) {
						st.add(Integer.parseInt(command[1]), Integer.parseInt(command[2]));
					}else {
						System.out.println("Too much/less info input.");
					}
					break;
				
				case "jobs":
					//just one command
					if(command.length == 1) {
						st.printJobs();
					}else {
						System.out.println("Too many commands");
					}
					break;

				case "list":
					//just one command
					if(command.length == 1) {
						st.printList();
					}else {
						System.out.println("Too many commands");
					}
					break;

				//could be mutilple commands for rest of commands
				case "ff":
					if(command.length > 1) {
						try {
							for(int i = 1; i < command.length; i++) {
								st.ff(Integer.parseInt(command[i]));
							}
						}catch(Exception e) {
							//System.err.println(e);
							System.out.println("Invalid input argument.");
						}
					}else {
						System.out.println("Not enough information");
					}
					break;

				case "nf":
					if(command.length > 1) {
						try {
							for(int i = 1; i < command.length; i++) {
								st.nf(Integer.parseInt(command[i]));
							}
						}catch(Exception e) {
							System.out.println("Invalid input argument.");
						}
					}else {
						System.out.println("Not enough information");
					}
					break;
				case "bf":
					if(command.length > 1) {
						try {
							for(int i = 1; i < command.length; i++) {
								st.bf(Integer.parseInt(command[i]));
							}
						}catch(Exception e) {
							System.out.println("Invalid input argument.");
						}
					}else {
						System.out.println("Not enough information");
					}
					break;
				
				case "wf":
					if(command.length > 1) {
						try {
							for(int i = 1; i < command.length; i++) {
								st.wf(Integer.parseInt(command[i]));
							}
						}catch(Exception e) {
							System.out.println("Invalid input argument.");
						}
					}else {
						System.out.println("Not enough information");
					}
					break;
				
				case "de":
					if(command.length > 1) {
						try {
							for(int i = 1; i < command.length; i++) {
								st.de(Integer.parseInt(command[i]));
							}
						}catch(Exception e) {
							System.out.println("Invalid input argument.");
						}
					}else {
						System.out.println("Not enough information");
					}
					break;

				case "exit":
					System.exit(0);
					break;

				default:
					System.out.println("Invalid command");
					break;
			}
		}

	}
}