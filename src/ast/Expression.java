package ast;

public interface Expression extends Locatable {

    boolean getLvalue();
    void setLvalue(boolean value);
    Type getType();
    void setType(Type type);
}
