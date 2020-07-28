package cn.uce.omg.portal.util;

public class Constant {
	public static enum JiraScore {
		One, Two, Three, Four, Five;
		private static String[] ids = {"11410", "11411", "11412", "11413","11414"};
		private static String[] scores = {"1", "2", "3", "4", "5"};
		private JiraScore() {
		}
		public String ids() {
			return ids[ordinal()];
		}
		public String scores() {
			return scores[ordinal()];
		}
	}

}
