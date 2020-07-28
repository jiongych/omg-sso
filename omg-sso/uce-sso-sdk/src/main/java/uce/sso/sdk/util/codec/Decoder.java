package uce.sso.sdk.util.codec;

public abstract interface Decoder {
	public abstract Object decode(Object paramObject) throws DecoderException;
}
