// Automatically generated by xdrgen
// DO NOT EDIT or your changes may be overwritten

package org.stellar.sdk.xdr;


import java.io.IOException;

import com.google.common.base.Objects;

// === xdr source ============================================================

//  union CreateAccountResult switch (CreateAccountResultCode code)
//  {
//  case CREATE_ACCOUNT_SUCCESS:
//      void;
//  default:
//      void;
//  };

//  ===========================================================================
public class CreateAccountResult implements XdrElement {
  public CreateAccountResult () {}
  CreateAccountResultCode code;
  public CreateAccountResultCode getDiscriminant() {
    return this.code;
  }
  public void setDiscriminant(CreateAccountResultCode value) {
    this.code = value;
  }

  public static final class Builder {
    private CreateAccountResultCode discriminant;

    public Builder discriminant(CreateAccountResultCode discriminant) {
      this.discriminant = discriminant;
      return this;
    }

    public CreateAccountResult build() {
      CreateAccountResult val = new CreateAccountResult();
      val.setDiscriminant(discriminant);
      return val;
    }
  }

  public static void encode(XdrDataOutputStream stream, CreateAccountResult encodedCreateAccountResult) throws IOException {
  //Xdrgen::AST::Identifier
  //CreateAccountResultCode
  stream.writeInt(encodedCreateAccountResult.getDiscriminant().getValue());
  switch (encodedCreateAccountResult.getDiscriminant()) {
  case CREATE_ACCOUNT_SUCCESS:
  break;
  default:
  break;
  }
  }
  public void encode(XdrDataOutputStream stream) throws IOException {
    encode(stream, this);
  }
  public static CreateAccountResult decode(XdrDataInputStream stream) throws IOException {
  CreateAccountResult decodedCreateAccountResult = new CreateAccountResult();
  CreateAccountResultCode discriminant = CreateAccountResultCode.decode(stream);
  decodedCreateAccountResult.setDiscriminant(discriminant);
  switch (decodedCreateAccountResult.getDiscriminant()) {
  case CREATE_ACCOUNT_SUCCESS:
  break;
  default:
  break;
  }
    return decodedCreateAccountResult;
  }
  @Override
  public int hashCode() {
    return Objects.hashCode(this.code);
  }
  @Override
  public boolean equals(Object object) {
    if (!(object instanceof CreateAccountResult)) {
      return false;
    }

    CreateAccountResult other = (CreateAccountResult) object;
    return Objects.equal(this.code, other.code);
  }
}
