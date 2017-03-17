public class INode {
	private String fileName;
	private int fileStart;
	private int totalBlock;

	public INode(String fileName, int fileStart, int totalBlock) {
		this.fileName = fileName;
		this.fileStart = fileStart;
		this.totalBlock = totalBlock;
	}

	public String getFileName() {
		return fileName;
	}

	public int getFileStart() {
		return fileStart;
	}

	public int getBlockNum() {
		return totalBlock;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFileStart(int fileStart) {
		this.fileStart = fileStart;
	}

	public void setTotalBlock(int totalBlock) {
		this.totalBlock = totalBlock;
	}
}