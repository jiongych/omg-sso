package uce.sso.sdk.util.codec;

public abstract interface BinaryDecoder extends Decoder {
	public abstract byte[] decode(byte[] paramArrayOfByte) throws DecoderException;
}
