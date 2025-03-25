package ast.factories;

import ast.Type;
import ast.types.Array;

public class TypesFactory {

    public static Array createArrayType(Type type, int size) {
        if(type instanceof Array a1){
            return new Array(createArrayType(a1.getType(), size), a1.getSize());
        } else return new Array(type, size);
    }
}
