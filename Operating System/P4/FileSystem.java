import java.util.*;

public class FileSystem {

	private int[] fat;
	private ArrayList<INode> list;
	private long bitmap;

	public FileSystem() {
		fat = new int[64];
		list = new ArrayList<>();
		bitmap = 0;
	}

	//helper functions
	private int findIndex(String filename) {
		int index = 0;
		INode node = new INode(filename, 0, 0);
		for(INode i: list) {
			if(i.getFileName().equals(node.getFileName())) {
				break;
			}
			index++;
		}
		return index;
	}

	/*
		This command will attempt to put the file with the given name and size (in blocks) in to the file
		system. To do this, you must add an i-node and fill in the pointers for each block then update the
		bitmap. If there is already an i-node with this file name or if there are not enough available blocks,
		do not allow the operation. When filling in the blocks, search sequentially from the start of the file
		system and fill in as you find empty blocks.
	*/
	public void put(String filename, int fileSize) {
		for(int i = 0; i < list.size(); i++) {
			if(filename.equals(list.get(i).getFileName())) {
				System.out.println("The file is already existed.");
				return;
			}
		}
		ArrayList<Integer> blocks = new ArrayList<>();
		for(int i = 0; i < 64; i++) {
			if(blocks.size() < fileSize) {
				//if the bitmap is free then add to the blocks
				if(((bitmap >> i) & 1) == 0) {
					blocks.add(i);
				}
			}else {
				break;
			}
		}
		if(blocks.size() == fileSize) {
			//allocate
			list.add(new INode(filename, blocks.get(0), fileSize));
			for(int i = 0; i < fileSize; i++) {
				int curFreeBlockIndex = blocks.get(i);
				//set all the allocation blocks
				bitmap |= 1L << curFreeBlockIndex;
				if(i == fileSize - 1) {
					fat[curFreeBlockIndex] = -1;
				}else {
					fat[curFreeBlockIndex] = blocks.get(i + 1);
				}
			}
		}else {
			System.out.println("There do not have enough blocks free.");
		}

	}

	/*
		This command should delete the file with the given name. To delete the file, remove the i-node and
		clear the bitmap for the appropriate blocks. It is not necessary to clear the FAT because the bitmap
		will indicate that the blocks are available and no i-nodes point to those blocks any more.
	*/
	public void del(String filename) {
		int index = findIndex(filename);
		if(index == -1) {
			System.out.println("Cannot find the file");
		}else {
			//deallocate the inode
			INode node = list.remove(index);
			int nextStart = node.getFileStart();
			while(nextStart != -1) {
				//set the free block
				bitmap &= ~(1L << nextStart);
				nextStart = fat[nextStart];
			}
		}
	}

	public void printBitmap() {
		String bitsmap = String.format("%64s", Long.toBinaryString(bitmap)).replace(' ', '0');

		for(int i = 0; i < 64; i += 8) {
			System.out.printf("%2d ", i);
			for(int j = i; j < i + 8; j++) {
				System.out.print(bitsmap.charAt(63 - j));
			}
			System.out.print("\n");
		}
	}

	public void printINodes() {
		if(!list.isEmpty()) {
			for(INode node: list) {
				int nextStart = node.getFileStart();
				System.out.print(node.getFileName() + ": ");
				while(nextStart != -1) {
					System.out.print(nextStart);
					nextStart = fat[nextStart];
					if(nextStart != -1) {
						System.out.print(" -> ");
					}
				}
				System.out.print("\n");
			}			
		}else {
			System.out.println("There is no file in table");
		}
	}

	public static void main(String[] args) {
		FileSystem fs = new FileSystem();
		Scanner keyboard = new Scanner(System.in);
		String input;
		String[] command;

		while(true) {
			System.out.print("> ");
			input = keyboard.nextLine();

			command = input.split(" ");

			switch(command[0]) {
				case "put":
					//need three commands
					if(command.length == 3) {
						try {
							String name = command[1];
							int size = Integer.parseInt(command[2]);
							fs.put(name, size);
						}catch(IllegalArgumentException e) {
							System.out.println("Wrong inputs");
						}
					}else {
						System.out.println("Wrong arguments");
					}
					break;
				case "del":
					//need two commands
					if(command.length == 2) {
						String name = command[1];
						fs.del(name);
					}else {
						System.out.println("Wrong arguments");
					}
					break;
				case "bitmap":
					//just one command
					if(command.length == 1) {
						fs.printBitmap();
					}else {
						System.out.println("Too many commands");
					}
					break;
				case "inodes":
					//just one command
					if(command.length == 1) {
						fs.printINodes();
					}else {
						System.out.println("Too many commands");
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