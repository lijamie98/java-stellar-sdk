// Automatically generated by xdrgen
// DO NOT EDIT or your changes may be overwritten

package org.stellar.sdk.xdr;


import java.io.IOException;

import com.google.common.base.Objects;

// === xdr source ============================================================

//  struct AllowTrustOp
//  {
//      AccountID trustor;
//      union switch (AssetType type)
//      {
//      // ASSET_TYPE_NATIVE is not allowed
//      case ASSET_TYPE_CREDIT_ALPHANUM4:
//          AssetCode4 assetCode4;
//  
//      case ASSET_TYPE_CREDIT_ALPHANUM12:
//          AssetCode12 assetCode12;
//  
//          // add other asset types here in the future
//      }
//      asset;
//  
//      // 0, or any bitwise combination of TrustLineFlags
//      uint32 authorize;
//  };

//  ===========================================================================
public class AllowTrustOp implements XdrElement {
  public AllowTrustOp () {}
  private AccountID trustor;
  public AccountID getTrustor() {
    return this.trustor;
  }
  public void setTrustor(AccountID value) {
    this.trustor = value;
  }
  private AllowTrustOpAsset asset;
  public AllowTrustOpAsset getAsset() {
    return this.asset;
  }
  public void setAsset(AllowTrustOpAsset value) {
    this.asset = value;
  }
  private Uint32 authorize;
  public Uint32 getAuthorize() {
    return this.authorize;
  }
  public void setAuthorize(Uint32 value) {
    this.authorize = value;
  }
  public static void encode(XdrDataOutputStream stream, AllowTrustOp encodedAllowTrustOp) throws IOException{
    AccountID.encode(stream, encodedAllowTrustOp.trustor);
    AllowTrustOpAsset.encode(stream, encodedAllowTrustOp.asset);
    Uint32.encode(stream, encodedAllowTrustOp.authorize);
  }
  public void encode(XdrDataOutputStream stream) throws IOException {
    encode(stream, this);
  }
  public static AllowTrustOp decode(XdrDataInputStream stream) throws IOException {
    AllowTrustOp decodedAllowTrustOp = new AllowTrustOp();
    decodedAllowTrustOp.trustor = AccountID.decode(stream);
    decodedAllowTrustOp.asset = AllowTrustOpAsset.decode(stream);
    decodedAllowTrustOp.authorize = Uint32.decode(stream);
    return decodedAllowTrustOp;
  }
  @Override
  public int hashCode() {
    return Objects.hashCode(this.trustor, this.asset, this.authorize);
  }
  @Override
  public boolean equals(Object object) {
    if (!(object instanceof AllowTrustOp)) {
      return false;
    }

    AllowTrustOp other = (AllowTrustOp) object;
    return Objects.equal(this.trustor, other.trustor) && Objects.equal(this.asset, other.asset) && Objects.equal(this.authorize, other.authorize);
  }

  public static final class Builder {
    private AccountID trustor;
    private AllowTrustOpAsset asset;
    private Uint32 authorize;

    public Builder trustor(AccountID trustor) {
      this.trustor = trustor;
      return this;
    }

    public Builder asset(AllowTrustOpAsset asset) {
      this.asset = asset;
      return this;
    }

    public Builder authorize(Uint32 authorize) {
      this.authorize = authorize;
      return this;
    }

    public AllowTrustOp build() {
      AllowTrustOp val = new AllowTrustOp();
      val.setTrustor(trustor);
      val.setAsset(asset);
      val.setAuthorize(authorize);
      return val;
    }
  }

  public static class AllowTrustOpAsset {
    public AllowTrustOpAsset () {}
    AssetType type;
    public AssetType getDiscriminant() {
      return this.type;
    }
    public void setDiscriminant(AssetType value) {
      this.type = value;
    }
    private AssetCode4 assetCode4;
    public AssetCode4 getAssetCode4() {
      return this.assetCode4;
    }
    public void setAssetCode4(AssetCode4 value) {
      this.assetCode4 = value;
    }
    private AssetCode12 assetCode12;
    public AssetCode12 getAssetCode12() {
      return this.assetCode12;
    }
    public void setAssetCode12(AssetCode12 value) {
      this.assetCode12 = value;
    }

    public static final class Builder {
      private AssetType discriminant;
      private AssetCode4 assetCode4;
      private AssetCode12 assetCode12;

      public Builder discriminant(AssetType discriminant) {
        this.discriminant = discriminant;
        return this;
      }

      public Builder assetCode4(AssetCode4 assetCode4) {
        this.assetCode4 = assetCode4;
        return this;
      }

      public Builder assetCode12(AssetCode12 assetCode12) {
        this.assetCode12 = assetCode12;
        return this;
      }

      public AllowTrustOpAsset build() {
        AllowTrustOpAsset val = new AllowTrustOpAsset();
        val.setDiscriminant(discriminant);
        val.setAssetCode4(assetCode4);
        val.setAssetCode12(assetCode12);
        return val;
      }
    }

    public static void encode(XdrDataOutputStream stream, AllowTrustOpAsset encodedAllowTrustOpAsset) throws IOException {
    //Xdrgen::AST::Identifier
    //AssetType
    stream.writeInt(encodedAllowTrustOpAsset.getDiscriminant().getValue());
    switch (encodedAllowTrustOpAsset.getDiscriminant()) {
    case ASSET_TYPE_CREDIT_ALPHANUM4:
    AssetCode4.encode(stream, encodedAllowTrustOpAsset.assetCode4);
    break;
    case ASSET_TYPE_CREDIT_ALPHANUM12:
    AssetCode12.encode(stream, encodedAllowTrustOpAsset.assetCode12);
    break;
    }
    }
    public void encode(XdrDataOutputStream stream) throws IOException {
      encode(stream, this);
    }
    public static AllowTrustOpAsset decode(XdrDataInputStream stream) throws IOException {
    AllowTrustOpAsset decodedAllowTrustOpAsset = new AllowTrustOpAsset();
    AssetType discriminant = AssetType.decode(stream);
    decodedAllowTrustOpAsset.setDiscriminant(discriminant);
    switch (decodedAllowTrustOpAsset.getDiscriminant()) {
    case ASSET_TYPE_CREDIT_ALPHANUM4:
    decodedAllowTrustOpAsset.assetCode4 = AssetCode4.decode(stream);
    break;
    case ASSET_TYPE_CREDIT_ALPHANUM12:
    decodedAllowTrustOpAsset.assetCode12 = AssetCode12.decode(stream);
    break;
    }
      return decodedAllowTrustOpAsset;
    }
    @Override
    public int hashCode() {
      return Objects.hashCode(this.assetCode4, this.assetCode12, this.type);
    }
    @Override
    public boolean equals(Object object) {
      if (!(object instanceof AllowTrustOpAsset)) {
        return false;
      }

      AllowTrustOpAsset other = (AllowTrustOpAsset) object;
      return Objects.equal(this.assetCode4, other.assetCode4) && Objects.equal(this.assetCode12, other.assetCode12) && Objects.equal(this.type, other.type);
    }

  }
}
