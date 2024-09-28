// Automatically generated by xdrgen
// DO NOT EDIT or your changes may be overwritten

package org.stellar.sdk.xdr;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.stellar.sdk.Base64Factory;

/**
 * HotArchiveBucketEntryType's original definition in the XDR file is:
 *
 * <pre>
 * enum HotArchiveBucketEntryType
 * {
 *     HOT_ARCHIVE_METAENTRY = -1, // Bucket metadata, should come first.
 *     HOT_ARCHIVE_ARCHIVED = 0,   // Entry is Archived
 *     HOT_ARCHIVE_LIVE = 1,       // Entry was previously HOT_ARCHIVE_ARCHIVED, or HOT_ARCHIVE_DELETED, but
 *                                 // has been added back to the live BucketList.
 *                                 // Does not need to be persisted.
 *     HOT_ARCHIVE_DELETED = 2     // Entry deleted (Note: must be persisted in archive)
 * };
 * </pre>
 */
public enum HotArchiveBucketEntryType implements XdrElement {
  HOT_ARCHIVE_METAENTRY(-1),
  HOT_ARCHIVE_ARCHIVED(0),
  HOT_ARCHIVE_LIVE(1),
  HOT_ARCHIVE_DELETED(2);

  private final int value;

  HotArchiveBucketEntryType(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }

  public static HotArchiveBucketEntryType decode(XdrDataInputStream stream) throws IOException {
    int value = stream.readInt();
    switch (value) {
      case -1:
        return HOT_ARCHIVE_METAENTRY;
      case 0:
        return HOT_ARCHIVE_ARCHIVED;
      case 1:
        return HOT_ARCHIVE_LIVE;
      case 2:
        return HOT_ARCHIVE_DELETED;
      default:
        throw new IllegalArgumentException("Unknown enum value: " + value);
    }
  }

  public void encode(XdrDataOutputStream stream) throws IOException {
    stream.writeInt(value);
  }

  public static HotArchiveBucketEntryType fromXdrBase64(String xdr) throws IOException {
    byte[] bytes = Base64Factory.getInstance().decode(xdr);
    return fromXdrByteArray(bytes);
  }

  public static HotArchiveBucketEntryType fromXdrByteArray(byte[] xdr) throws IOException {
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(xdr);
    XdrDataInputStream xdrDataInputStream = new XdrDataInputStream(byteArrayInputStream);
    return decode(xdrDataInputStream);
  }
}
