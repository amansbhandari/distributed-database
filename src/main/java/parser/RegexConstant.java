package parser;

public class RegexConstant {

	public static final String CREATE_TABLE_REGEX="((create)\\s+(table)\\s+(\\w+.)?(\\w+)\\s*)([(](\\s*\\w+\\s+(int|varchar|boolean)(\\s+primary\\s+key|\\s+foreign\\s+key\\s+references\\s+\\w+\\s*[(]\\s*\\w+\\s*[)])?\\s*,?)+\\s*[)];)";
	
	public static final String GET_COLUMNS_REGEX="([(]\\s*[(\\w+\\s+(INT|varchar|float|boolean),[)]]+)";
	
	public static final String INSERT_WITH_COLUMN="((?i)(INSERT INTO)\\s+(\\S+)\\s+\\((.*?)\\)\\s+(VALUES).*\\s+\\((.*?)\\);)";
	
	public static final String INSERT_WITHOUT_COLUMN="((?i)(INSERT INTO)\\s+(\\S+).*\\s+(VALUES)\\s+\\((.*?)\\);)";
	
	public static final String CEHCK_COLUMN_REGEX="([)]\\s+Values)";
	
	public static final String DELETE_REGEX="(?i)(DELETE FROM)\\s+(\\w+)(\\s+(where)?\\s+(.*)?)?;";
	
	public static final String SELECT_REGEX="((select)\\s+([*]|\\w+)\\s+(from)\\s+(\\w+)\\s*(where\\s+\\w+(=|>|<)\\w+)?\\s+;)";
	
}
