package ast;

public interface Definition extends Locatable {
    void setScope(int scope);
    int getScope();
    Type getType();
    String getName();
}
