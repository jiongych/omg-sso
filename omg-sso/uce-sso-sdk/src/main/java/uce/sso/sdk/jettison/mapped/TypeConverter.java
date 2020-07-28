package uce.sso.sdk.jettison.mapped;

public abstract interface TypeConverter {
	public abstract Object convertToJSONPrimitive(String paramString);
}