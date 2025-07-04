package ast.types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import ast.Definition;
import ast.Type;
import ast.program.VarDefinition;
import semantic.Visitor;

public class Struct extends AbstractType {
	final List<RecordField> fields;
	
	public Struct(List<RecordField> fields) {
		this.fields = new ArrayList<>(fields);
		checkFields();
	}

	public void checkFields() {
		HashMap<String, Type> fieldsMap = new HashMap<>();
		fields.forEach(f -> {
			if(fieldsMap.containsKey(f.name)) {
				new ErrorType(f.getLine(), f.getColumn(), "Duplicate field name: " + f.name);
			}
			else fieldsMap.put(f.name, f.type);
		});
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Struct [ Fields: ");
		fields.forEach(f -> {
			sb.append("\n\t- " + f.toString());
		});
		sb.append(" ]");
		return sb.toString();
	}

	@Override
	public <TP, TR> TR accept(Visitor<TP, TR> v, TP param) {
		return v.visit(this, param);
	}

	public List<RecordField> getFields() {
		return fields;
	}

	public String typeExpression() {
		return "Struct";
	}

	@Override
	public Type dot(String name, int line, int column) {
		Optional<RecordField> var = fields.stream().filter(f -> f.name.equals(name)).findFirst();
		if(var.isPresent()) {
			return var.get().type;
		} else {
			return new ErrorType(line, column, "Field not found: " + name);
		}
	}

	public int getBytes() {
		return fields.stream().mapToInt(RecordField::getBytes).sum();
	}

	public RecordField getField(String fieldName) {
		return fields.stream().filter(f -> f.name.equals(fieldName)).findFirst().orElse(null);
	}
}
