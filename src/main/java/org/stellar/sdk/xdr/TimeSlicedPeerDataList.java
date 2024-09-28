// Automatically generated by xdrgen
// DO NOT EDIT or your changes may be overwritten

package org.stellar.sdk.xdr;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.stellar.sdk.Base64Factory;

/**
 * TimeSlicedPeerDataList's original definition in the XDR file is:
 *
 * <pre>
 * typedef TimeSlicedPeerData TimeSlicedPeerDataList&lt;25&gt;;
 * </pre>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeSlicedPeerDataList implements XdrElement {
  private TimeSlicedPeerData[] TimeSlicedPeerDataList;

  public void encode(XdrDataOutputStream stream) throws IOException {
    int TimeSlicedPeerDataListSize = getTimeSlicedPeerDataList().length;
    stream.writeInt(TimeSlicedPeerDataListSize);
    for (int i = 0; i < TimeSlicedPeerDataListSize; i++) {
      TimeSlicedPeerDataList[i].encode(stream);
    }
  }

  public static TimeSlicedPeerDataList decode(XdrDataInputStream stream) throws IOException {
    TimeSlicedPeerDataList decodedTimeSlicedPeerDataList = new TimeSlicedPeerDataList();
    int TimeSlicedPeerDataListSize = stream.readInt();
    decodedTimeSlicedPeerDataList.TimeSlicedPeerDataList =
        new TimeSlicedPeerData[TimeSlicedPeerDataListSize];
    for (int i = 0; i < TimeSlicedPeerDataListSize; i++) {
      decodedTimeSlicedPeerDataList.TimeSlicedPeerDataList[i] = TimeSlicedPeerData.decode(stream);
    }
    return decodedTimeSlicedPeerDataList;
  }

  public static TimeSlicedPeerDataList fromXdrBase64(String xdr) throws IOException {
    byte[] bytes = Base64Factory.getInstance().decode(xdr);
    return fromXdrByteArray(bytes);
  }

  public static TimeSlicedPeerDataList fromXdrByteArray(byte[] xdr) throws IOException {
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(xdr);
    XdrDataInputStream xdrDataInputStream = new XdrDataInputStream(byteArrayInputStream);
    return decode(xdrDataInputStream);
  }
}
