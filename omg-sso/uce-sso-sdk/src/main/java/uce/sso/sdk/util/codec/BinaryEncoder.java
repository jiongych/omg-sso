package uce.sso.sdk.util.codec;

public abstract interface BinaryEncoder extends Encoder
{
  public abstract byte[] encode(byte[] paramArrayOfByte)
    throws EncoderException;
}
