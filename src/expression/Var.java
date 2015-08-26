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
					addError(enums.Error.NullSteatment, line);
					assignments.add(new Assignment("null", currentLine, branch));
	
				}
			}else{
				invocations.add(new Invocation(variable, currentLine, branch));
			}
		}
		
	}

	@Override
	public String toString() {
		return "Declaration";
	}
	public boolean isValid() {
		boolean valid = true;
		for (Assignment assignment : assignments) 
			if(!assignment.isValid()){
				for(int key : assignment.getAllErrors().keySet())
					addError(assignment.getAllErrors().get(key), key);
				valid = false;
			}
		for (Invocation invocation : invocations) {
			if(!invocation.isValid()){
				for(int key : invocation.getAllErrors().keySet())
					addError(invocation.getAllErrors().get(key), key);
				valid = false;
			}
		}
		return valid;
	}

}
