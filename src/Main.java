import ast.errorhandler.ErrorHandler;
import ast.program.Program;
import codegeneration.ExecuteCGVisitor;
import codegeneration.OffsetVisitor;
import introspector.model.IntrospectorModel;
import introspector.view.IntrospectorView;
import parser.*;

import org.antlr.v4.runtime.*;
import semantic.IdentificationVisitor;
import semantic.TypeCheckingVisitor;

import java.io.FileWriter;
import java.io.IOException;

public class Main {
	
	public static void main(String... args) throws Exception {
		   if (args.length<1) {
		        System.err.println("Please, pass me the input file.");
		        return;
		   }
		   if (args.length<2) {
			   System.err.println("Please, pass me the output file.");
			   return;
		   }
		   		 			
		 // create a lexer that feeds off of input CharStream
		CharStream input = CharStreams.fromFileName(args[0]);
		CmmLexer lexer = new CmmLexer(input);

		// create a parser that feeds off the tokens buffer
		CommonTokenStream tokens = new CommonTokenStream(lexer); 
		CmmParser parser = new CmmParser(tokens);

		Program ast = parser.program().ast;
		ast.accept(new IdentificationVisitor(), null);
		ast.accept(new TypeCheckingVisitor(), null);
		ast.accept(new OffsetVisitor(), null);

		try(FileWriter fw = new FileWriter(args[1], false)) {
			ast.accept(new ExecuteCGVisitor(fw, args[0]), null);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

		if (ErrorHandler.getInstance().anyErrors())
			ErrorHandler.getInstance().showErrors(System.err);
		else {
// * The AST is shown if no errors exist
			IntrospectorModel model=new IntrospectorModel(
					"Program", ast);
			new IntrospectorView("Introspector", model);
		}
	}
	

}
