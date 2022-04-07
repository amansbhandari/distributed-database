package parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import parser.exception.InvalidQueryException;

public class QueryParser {

	private String errorMessage;

	public boolean parseQuery(String query) {

		boolean isQueryValid = false;

		if (query == null || query.trim().equals("")) {
			this.errorMessage = "Invalid sql query";
		}

		if (query.toLowerCase().contains("create")) {

			isQueryValid = parse(query);

			if (!isQueryValid) {
				this.errorMessage = "Invalid create query syntax";
			}

		}

		return isQueryValid;
	}

	
	private boolean parse(String query) {
		boolean isQueryValid = false;

		final Pattern pattern = Pattern.compile(RegexConstant.CREATE_TABLE_REGEX,
				Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
		final Matcher matcher = pattern.matcher(query);

		while (matcher.find()) {
			isQueryValid = true;
		}
		
	return isQueryValid;	
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

}
