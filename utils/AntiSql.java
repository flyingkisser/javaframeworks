package frameworks.utils;

public class AntiSql {
	public static AntiSql self = null;

	public static AntiSql getInstance() {
		if (self == null)
			self = new AntiSql();
		return self;
	}

	public boolean sqlValidate(String str) {
		str = str.toLowerCase();
//		String badStr = "'|exec|execute|insert|select|delete|update|count|drop|"
//				+ "table|from|use|group_concat|column_name|"
//				+ "information_schema.columns|table_schema|union|where|order|by|*|"
//				+ "chr|mid|truncate|char|declare|;|-|--|+|,|like|//|/|%|#";
		String badStr = "exec|execute|insert|select|delete|update|count|drop|"
				+ "table|from|use|group_concat|column_name|"
				+ "information_schema.columns|table_schema|union|where|*|"
				+ "chr|mid|truncate|declare|;|--|//|/|%|#";
		String[] badStrs = badStr.split("\\|");
		for (int i = 0; i < badStrs.length; i++) {
			if (str.indexOf(badStrs[i]) >= 0) {
				return true;
			}
		}
		return false;
	}

	public static void test() {
		boolean a = AntiSql.getInstance().sqlValidate("a and b");
		if (a) {

		}
	}
}
