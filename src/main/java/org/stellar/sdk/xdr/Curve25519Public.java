// Automatically generated by xdrgen
// DO NOT EDIT or your changes may be overwritten

package org.stellar.sdk.xdr;


import java.io.IOException;

import java.util.Arrays;

// === xdr source ============================================================

//  struct Curve25519Public
//  {
//      opaque key[32];
//  };

//  ===========================================================================
public class Curve25519Public implements XdrElement {
  public Curve25519Public () {}
  private byte[] key;
  public byte[] getKey() {
    return this.key;
  }
  public void setKey(byte[] value) {
    this.key = value;
  }
  public static void encode(XdrDataOutputStream stream, Curve25519Public encodedCurve25519Public) throws IOException{
    int keysize = encodedCurve25519Public.key.length;
    stream.write(encodedCurve25519Public.getKey(), 0, keysize);
  }
  public void encode(XdrDataOutputStream stream) throws IOException {
    encode(stream, this);
  }
  public static Curve25519Public decode(XdrDataInputStream stream) throws IOException {
    Curve25519Public decodedCurve25519Public = new Curve25519Public();
    int keysize = 32;
    decodedCurve25519Public.key = new byte[keysize];
    stream.read(decodedCurve25519Public.key, 0, keysize);
    return decodedCurve25519Public;
  }
  @Override
  public int hashCode() {
    return Arrays.hashCode(this.key);
  }
  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Curve25519Public)) {
      return false;
    }

    Curve25519Public other = (Curve25519Public) object;
    return Arrays.equals(this.key, other.key);
  }

  public static final class Builder {
    private byte[] key;

    public Builder key(byte[] key) {
      this.key = key;
      return this;
    }

    public Curve25519Public build() {
      Curve25519Public val = new Curve25519Public();
      val.setKey(key);
      return val;
    }
  }
}
