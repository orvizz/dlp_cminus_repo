package ast;

public interface Expression extends Locatable {

    public boolean getLvalue();
    public void setLvalue(boolean value);
}
