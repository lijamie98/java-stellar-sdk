package org.stellar.sdk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.LinkedList;
import org.junit.Test;
import org.stellar.sdk.xdr.Duration;
import org.stellar.sdk.xdr.Int64;
import org.stellar.sdk.xdr.PreconditionType;
import org.stellar.sdk.xdr.Preconditions;
import org.stellar.sdk.xdr.PreconditionsV2;
import org.stellar.sdk.xdr.SequenceNumber;
import org.stellar.sdk.xdr.SignerKey;
import org.stellar.sdk.xdr.SignerKeyType;
import org.stellar.sdk.xdr.TimePoint;
import org.stellar.sdk.xdr.Uint256;
import org.stellar.sdk.xdr.Uint32;
import org.stellar.sdk.xdr.Uint64;
import org.stellar.sdk.xdr.XdrDataInputStream;
import org.stellar.sdk.xdr.XdrDataOutputStream;
import org.stellar.sdk.xdr.XdrUnsignedHyperInteger;
import org.stellar.sdk.xdr.XdrUnsignedInteger;

public class TransactionPreconditionsTest {

  @Test
  public void itConvertsFromXdr() throws IOException {

    Preconditions.PreconditionsBuilder preconditionsBuilder = Preconditions.builder();
    preconditionsBuilder.discriminant(PreconditionType.PRECOND_V2);
    PreconditionsV2.PreconditionsV2Builder v2Builder = PreconditionsV2.builder();

    v2Builder.extraSigners(new SignerKey[] {});
    v2Builder.minSeqAge(new Duration(new Uint64(new XdrUnsignedHyperInteger(2L))));
    v2Builder.ledgerBounds(
        org.stellar.sdk.xdr.LedgerBounds.builder()
            .minLedger(new Uint32(new XdrUnsignedInteger(1)))
            .maxLedger(new Uint32(new XdrUnsignedInteger(2)))
            .build());
    v2Builder.minSeqNum(new SequenceNumber(new Int64(4L)));
    v2Builder.minSeqLedgerGap(new Uint32(new XdrUnsignedInteger(0)));
    preconditionsBuilder.v2(v2Builder.build());
    Preconditions xdr = preconditionsBuilder.build();

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    xdr.encode(new XdrDataOutputStream(baos));
    xdr =
        Preconditions.decode(new XdrDataInputStream(new ByteArrayInputStream(baos.toByteArray())));

    TransactionPreconditions transactionPreconditions = TransactionPreconditions.fromXdr(xdr);

    assertEquals(transactionPreconditions.getMinSeqAge().longValue(), 2);
    assertEquals(transactionPreconditions.getLedgerBounds().getMinLedger(), 1);
    assertEquals(transactionPreconditions.getLedgerBounds().getMaxLedger(), 2);
    assertEquals(transactionPreconditions.getMinSeqNumber(), Long.valueOf(4));
    assertEquals(transactionPreconditions.getMinSeqLedgerGap(), 0);
    assertEquals(transactionPreconditions.getExtraSigners().size(), 0);
  }

  @Test
  public void itRoundTripsFromV2ToV1IfOnlyTimeboundsPresent() throws IOException {

    Preconditions.PreconditionsBuilder preconditionsBuilder = Preconditions.builder();
    preconditionsBuilder.discriminant(PreconditionType.PRECOND_V2);
    PreconditionsV2.PreconditionsV2Builder v2Builder = PreconditionsV2.builder();
    org.stellar.sdk.xdr.TimeBounds xdrTimeBounds =
        org.stellar.sdk.xdr.TimeBounds.builder()
            .minTime(new TimePoint(new Uint64(new XdrUnsignedHyperInteger(1L))))
            .maxTime(new TimePoint(new Uint64(new XdrUnsignedHyperInteger(2L))))
            .build();
    v2Builder.timeBounds(xdrTimeBounds);
    v2Builder.minSeqLedgerGap(new Uint32(new XdrUnsignedInteger(0)));
    v2Builder.minSeqAge(new Duration(new Uint64(new XdrUnsignedHyperInteger(0L))));
    v2Builder.extraSigners(new SignerKey[] {});
    preconditionsBuilder.v2(v2Builder.build());
    // create V2 Precond with just timebounds
    Preconditions xdr = preconditionsBuilder.build();

    // serialize to binary
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    xdr.encode(new XdrDataOutputStream(baos));
    xdr =
        Preconditions.decode(new XdrDataInputStream(new ByteArrayInputStream(baos.toByteArray())));

    // marshal it to pojo
    TransactionPreconditions transactionPreconditions = TransactionPreconditions.fromXdr(xdr);
    assertEquals(transactionPreconditions.getTimeBounds(), new TimeBounds(1L, 2L));

    // marshal the pojo with just timebounds back to xdr, since only timebounds, precond type should
    // be optimized to V!(PRECOND_TIME)
    xdr = transactionPreconditions.toXdr();
    assertEquals(xdr.getDiscriminant(), PreconditionType.PRECOND_TIME);
    assertEquals(xdr.getTimeBounds(), xdrTimeBounds);
    assertNull(xdr.getV2());
  }

  @Test
  public void itConvertsToV2Xdr() throws IOException {

    byte[] payload =
        Util.hexToBytes(
            "0102030405060708090a0b0c0d0e0f101112131415161718191a1b1c1d1e1f20".toUpperCase());
    SignerKey signerKey =
        SignerKey.builder()
            .discriminant(SignerKeyType.SIGNER_KEY_TYPE_ED25519_SIGNED_PAYLOAD)
            .ed25519SignedPayload(
                SignerKey.SignerKeyEd25519SignedPayload.builder()
                    .payload(payload)
                    .ed25519(
                        new Uint256(
                            StrKey.decodeEd25519PublicKey(
                                "GDW6AUTBXTOC7FIKUO5BOO3OGLK4SF7ZPOBLMQHMZDI45J2Z6VXRB5NR")))
                    .build())
            .build();

    TransactionPreconditions preconditions =
        TransactionPreconditions.builder()
            .timeBounds(new TimeBounds(1, 2))
            .minSeqNumber(3L)
            .extraSigners(Arrays.asList(signerKey, signerKey, signerKey))
            .build();

    Preconditions xdr = preconditions.toXdr();

    // serialize to binary
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    xdr.encode(new XdrDataOutputStream(baos));
    xdr =
        Preconditions.decode(new XdrDataInputStream(new ByteArrayInputStream(baos.toByteArray())));

    assertEquals(xdr.getDiscriminant(), PreconditionType.PRECOND_V2);
    assertEquals(
        xdr.getV2().getTimeBounds().getMinTime().getTimePoint().getUint64().getNumber().longValue(),
        1L);
    assertEquals(
        xdr.getV2().getTimeBounds().getMaxTime().getTimePoint().getUint64().getNumber().longValue(),
        2L);
    assertEquals(xdr.getV2().getMinSeqNum().getSequenceNumber().getInt64(), Long.valueOf(3));
    // xdr encoding requires non-null for min ledger gap
    assertEquals(xdr.getV2().getMinSeqLedgerGap().getUint32().getNumber().intValue(), 0);
    // xdr encoding requires non-null for min seq age
    assertEquals(xdr.getV2().getMinSeqAge().getDuration().getUint64().getNumber().longValue(), 0);
    assertEquals(xdr.getV2().getExtraSigners().length, 3);
  }

  @Test
  public void itConvertsOnlyTimeBoundsXdr() throws IOException {
    TransactionPreconditions preconditions =
        TransactionPreconditions.builder().timeBounds(new TimeBounds(1, 2)).build();

    Preconditions xdr = preconditions.toXdr();

    // serialize to binary
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    xdr.encode(new XdrDataOutputStream(baos));
    xdr =
        Preconditions.decode(new XdrDataInputStream(new ByteArrayInputStream(baos.toByteArray())));

    assertEquals(xdr.getDiscriminant(), PreconditionType.PRECOND_TIME);
    assertEquals(
        xdr.getTimeBounds().getMinTime().getTimePoint().getUint64().getNumber().longValue(), 1L);
    assertEquals(
        xdr.getTimeBounds().getMaxTime().getTimePoint().getUint64().getNumber().longValue(), 2L);
    assertNull(xdr.getV2());
  }

  @Test
  public void itConvertsNullTimeBoundsXdr() throws IOException {
    // there was precedence in the sdk test coverage in TransactionTest.java for edge case of
    // passing a null timebounds
    // into a transaction, which occurrs when infinite timeout is set and timebounds is not set
    // through TransactionBuilder.
    // TransactionPreconditions continues to support that edge case.
    TransactionPreconditions preconditions = TransactionPreconditions.builder().build();

    Preconditions xdr = preconditions.toXdr();

    // serialize to binary
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    xdr.encode(new XdrDataOutputStream(baos));
    xdr =
        Preconditions.decode(new XdrDataInputStream(new ByteArrayInputStream(baos.toByteArray())));

    assertEquals(xdr.getDiscriminant(), PreconditionType.PRECOND_NONE);
    assertNull(xdr.getTimeBounds());
  }

  @Test
  public void itChecksValidityWhenTimebounds() {
    TransactionPreconditions preconditions =
        TransactionPreconditions.builder().timeBounds(new TimeBounds(1, 2)).build();
    preconditions.validate();
  }

  @Test
  public void itChecksNonValidityOfTimeBounds() {
    TransactionPreconditions preconditions = TransactionPreconditions.builder().build();
    try {
      preconditions.validate();
      fail();
    } catch (IllegalStateException ignored) {
    }
  }

  @Test
  public void itChecksNonValidityOfExtraSignersSize() {
    TransactionPreconditions preconditions =
        TransactionPreconditions.builder()
            .timeBounds(new TimeBounds(1, 2))
            .extraSigners(
                Arrays.asList(
                    SignerKey.builder().build(),
                    SignerKey.builder().build(),
                    SignerKey.builder().build()))
            .build();
    try {
      preconditions.validate();
      fail();
    } catch (IllegalStateException ignored) {
    }
  }

  @Test
  public void itChecksValidityWhenNoTimeboundsSet() {
    TransactionPreconditions preconditions = TransactionPreconditions.builder().build();
    try {
      preconditions.validate();
      fail();
    } catch (IllegalStateException exception) {
      assertTrue(exception.getMessage().contains("Invalid preconditions, must define timebounds"));
    }
  }

  @Test
  public void itChecksV2Status() {
    Preconditions.PreconditionsBuilder preconditionsBuilder = Preconditions.builder();
    preconditionsBuilder.discriminant(PreconditionType.PRECOND_V2);
    PreconditionsV2.PreconditionsV2Builder v2Builder = PreconditionsV2.builder();

    v2Builder.extraSigners(new SignerKey[] {});
    v2Builder.minSeqAge(new Duration(new Uint64(new XdrUnsignedHyperInteger(2L))));
    v2Builder.ledgerBounds(
        org.stellar.sdk.xdr.LedgerBounds.builder()
            .minLedger(new Uint32(new XdrUnsignedInteger(1)))
            .maxLedger(new Uint32(new XdrUnsignedInteger(2)))
            .build());
    v2Builder.minSeqNum(new SequenceNumber(new Int64(4L)));
    preconditionsBuilder.v2(v2Builder.build());
    Preconditions xdr = preconditionsBuilder.build();

    TransactionPreconditions transactionPreconditions = TransactionPreconditions.fromXdr(xdr);
    assertTrue(transactionPreconditions.hasV2());
  }

  @Test
  public void testSetTimeBoundsOnly() {
    TimeBounds timeBounds = new TimeBounds(1, 2);
    TransactionPreconditions preconditions =
        TransactionPreconditions.builder().timeBounds(timeBounds).build();
    assertEquals(timeBounds, preconditions.getTimeBounds());
    assertEquals(0, preconditions.getExtraSigners().size());
    assertEquals(BigInteger.ZERO, preconditions.getMinSeqAge());
    assertNull(preconditions.getLedgerBounds());
    assertNull(preconditions.getMinSeqNumber());
    assertEquals(0, preconditions.getMinSeqLedgerGap());
    assertFalse(preconditions.hasV2());
  }

  @Test
  public void testEquals() {
    TimeBounds timeBounds = new TimeBounds(1, 2);
    TransactionPreconditions preconditions0 =
        TransactionPreconditions.builder().timeBounds(timeBounds).build();
    TransactionPreconditions preconditions1 =
        TransactionPreconditions.fromXdr(preconditions0.toXdr());
    assertEquals(preconditions0, preconditions1);
    TransactionPreconditions preconditions2 =
        new TransactionPreconditions(
            timeBounds, null, null, BigInteger.ZERO, 0, new LinkedList<>());
    assertEquals(preconditions0, preconditions2);
  }
}
