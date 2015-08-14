package expression;

import java.util.ArrayList;
import java.util.List;

public class Var extends SimpleExpression {
	List<Assignment> assignments = new ArrayList<Assignment>();
	List<Invocation> invocations = new ArrayList<Invocation>();
	public Var(String statement, int currentLine, String branch) {
		super(statement, currentLine);
		this.branch = branch;
		statement = statement.replaceFirst(enums.Instruction.VAR.toString(), "");
		String[] variables = statement.split(",");
		for (String variable : variables) {
			if(variable.indexOf('=')!=-1){
				try {
					assignments.add(new Assignment(variable, currentLine, branch));
				} catch (IllegalStateException e) {
					errors.add(enums.Error.NullSteatment);
					assignments.add(new Assignment("null", currentLine, branch));
	
				}
			}else{
				invocations.add(new Invocation(variable, currentLine, branch));
			}
		}
		
	}

	@Override
	public String toString() {
		return "declaration of variables";
	}
	public boolean isValid() {
		for (Assignment assignment : assignments) {
			if(!assignment.isValid()){
				errors.addAll(assignment.errors);
				return false;
			}
		}
		for (Invocation invocation : invocations) {
			if(!invocation.isValid()){
				errors.addAll(invocation.errors);
				return false;
			}
		}
		return true;
	}

}
