package ast;

public abstract class AbstractLocatable implements Locatable {

	final private int line;
	final private int column;
	
	public AbstractLocatable(int line, int column) {
		this.line = line;
		this.column = column;
	}
	@Override
	public int getLine() {
		return line;
	}

	@Override
	public int getColumn() {
		return column;
	}

}
