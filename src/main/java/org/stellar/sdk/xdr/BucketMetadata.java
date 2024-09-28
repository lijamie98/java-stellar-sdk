// Automatically generated by xdrgen
// DO NOT EDIT or your changes may be overwritten

package org.stellar.sdk.xdr;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.stellar.sdk.Base64Factory;

/**
 * BucketMetadata's original definition in the XDR file is:
 *
 * <pre>
 * struct BucketMetadata
 * {
 *     // Indicates the protocol version used to create / merge this bucket.
 *     uint32 ledgerVersion;
 *
 *     // reserved for future use
 *     union switch (int v)
 *     {
 *     case 0:
 *         void;
 *     case 1:
 *         BucketListType bucketListType;
 *     }
 *     ext;
 * };
 * </pre>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class BucketMetadata implements XdrElement {
  private Uint32 ledgerVersion;
  private BucketMetadataExt ext;

  public void encode(XdrDataOutputStream stream) throws IOException {
    ledgerVersion.encode(stream);
    ext.encode(stream);
  }

  public static BucketMetadata decode(XdrDataInputStream stream) throws IOException {
    BucketMetadata decodedBucketMetadata = new BucketMetadata();
    decodedBucketMetadata.ledgerVersion = Uint32.decode(stream);
    decodedBucketMetadata.ext = BucketMetadataExt.decode(stream);
    return decodedBucketMetadata;
  }

  public static BucketMetadata fromXdrBase64(String xdr) throws IOException {
    byte[] bytes = Base64Factory.getInstance().decode(xdr);
    return fromXdrByteArray(bytes);
  }

  public static BucketMetadata fromXdrByteArray(byte[] xdr) throws IOException {
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(xdr);
    XdrDataInputStream xdrDataInputStream = new XdrDataInputStream(byteArrayInputStream);
    return decode(xdrDataInputStream);
  }

  /**
   * BucketMetadataExt's original definition in the XDR file is:
   *
   * <pre>
   * union switch (int v)
   *     {
   *     case 0:
   *         void;
   *     case 1:
   *         BucketListType bucketListType;
   *     }
   * </pre>
   */
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder(toBuilder = true)
  public static class BucketMetadataExt implements XdrElement {
    private Integer discriminant;
    private BucketListType bucketListType;

    public void encode(XdrDataOutputStream stream) throws IOException {
      stream.writeInt(discriminant);
      switch (discriminant) {
        case 0:
          break;
        case 1:
          bucketListType.encode(stream);
          break;
      }
    }

    public static BucketMetadataExt decode(XdrDataInputStream stream) throws IOException {
      BucketMetadataExt decodedBucketMetadataExt = new BucketMetadataExt();
      Integer discriminant = stream.readInt();
      decodedBucketMetadataExt.setDiscriminant(discriminant);
      switch (decodedBucketMetadataExt.getDiscriminant()) {
        case 0:
          break;
        case 1:
          decodedBucketMetadataExt.bucketListType = BucketListType.decode(stream);
          break;
      }
      return decodedBucketMetadataExt;
    }

    public static BucketMetadataExt fromXdrBase64(String xdr) throws IOException {
      byte[] bytes = Base64Factory.getInstance().decode(xdr);
      return fromXdrByteArray(bytes);
    }

    public static BucketMetadataExt fromXdrByteArray(byte[] xdr) throws IOException {
      ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(xdr);
      XdrDataInputStream xdrDataInputStream = new XdrDataInputStream(byteArrayInputStream);
      return decode(xdrDataInputStream);
    }
  }
}
