package parser;

public class RegexConstant {

	public static final String CREATE_TABLE_REGEX="((create)\\s+(table)\\s+(\\w+.)?(\\w+)\\s*)([(](\\s*\\w+\\s+(int|varchar|boolean)(\\s+primary\\s+key|\\s+foreign\\s+key\\s+references\\s+\\w+\\s*[(]\\s*\\w+\\s*[)])?\\s*,?)+\\s*[)];)";
	
	public static final String GET_COLUMNS_REGEX="([(]\\s*[(\\w+\\s+(INT|varchar|float|boolean),[)]]+)";
}
