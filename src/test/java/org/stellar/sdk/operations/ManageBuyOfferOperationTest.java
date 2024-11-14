package org.stellar.sdk.operations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.stellar.sdk.Asset.create;

import java.math.BigDecimal;
import org.junit.Test;
import org.stellar.sdk.Asset;
import org.stellar.sdk.AssetTypeCreditAlphaNum4;
import org.stellar.sdk.AssetTypeNative;
import org.stellar.sdk.KeyPair;
import org.stellar.sdk.Price;

public class ManageBuyOfferOperationTest {
  @Test
  public void testManageBuyOfferOperation() {
    // GC5SIC4E3V56VOHJ3OZAX5SJDTWY52JYI2AFK6PUGSXFVRJQYQXXZBZF
    KeyPair source =
        KeyPair.fromSecretSeed("SC4CGETADVYTCR5HEAVZRB3DZQY5Y4J7RFNJTRA6ESMHIPEZUSTE2QDK");
    // GBCP5W2VS7AEWV2HFRN7YYC623LTSV7VSTGIHFXDEJU7S5BAGVCSETRR
    KeyPair issuer =
        KeyPair.fromSecretSeed("SA64U7C5C7BS5IHWEPA7YWFN3Z6FE5L6KAMYUIT4AQ7KVTVLD23C6HEZ");

    Asset selling = new AssetTypeNative();
    Asset buying = create(null, "USD", issuer.getAccountId());
    BigDecimal amount = new BigDecimal("0.00001");
    String price = "0.85334384"; // n=5333399 d=6250000
    Price priceObj = Price.fromString(price);
    long offerId = 1;

    ManageBuyOfferOperation operation =
        ManageBuyOfferOperation.builder()
            .selling(selling)
            .buying(buying)
            .amount(amount)
            .price(priceObj)
            .offerId(offerId)
            .sourceAccount(source.getAccountId())
            .build();

    org.stellar.sdk.xdr.Operation xdr = operation.toXdr();
    ManageBuyOfferOperation parsedOperation =
        (ManageBuyOfferOperation) ManageBuyOfferOperation.fromXdr(xdr);

    assertEquals(100L, xdr.getBody().getManageBuyOfferOp().getBuyAmount().getInt64().longValue());
    assertTrue(parsedOperation.getSelling() instanceof AssetTypeNative);
    assertTrue(parsedOperation.getBuying() instanceof AssetTypeCreditAlphaNum4);
    assertEquals(parsedOperation.getBuying(), buying);
    assertEquals(new BigDecimal("0.0000100"), parsedOperation.getAmount());
    assertEquals(new Price(5333399, 6250000), parsedOperation.getPrice());
    assertEquals(priceObj.getNumerator(), 5333399);
    assertEquals(priceObj.getDenominator(), 6250000);
    assertEquals(offerId, parsedOperation.getOfferId());

    assertEquals(
        "AAAAAQAAAAC7JAuE3XvquOnbsgv2SRztjuk4RoBVefQ0rlrFMMQvfAAAAAwAAAAAAAAAAVVTRAAAAAAARP7bVZfAS1dHLFv8YF7W1zlX9ZTMg5bjImn5dCA1RSIAAAAAAAAAZABRYZcAX14QAAAAAAAAAAE=",
        operation.toXdrBase64());
  }

  @Test
  public void testManageBuyOfferOperationWithConstructorPrice() {
    // See https://github.com/stellar/java-stellar-sdk/issues/292
    // GC5SIC4E3V56VOHJ3OZAX5SJDTWY52JYI2AFK6PUGSXFVRJQYQXXZBZF
    KeyPair source =
        KeyPair.fromSecretSeed("SC4CGETADVYTCR5HEAVZRB3DZQY5Y4J7RFNJTRA6ESMHIPEZUSTE2QDK");
    // GBCP5W2VS7AEWV2HFRN7YYC623LTSV7VSTGIHFXDEJU7S5BAGVCSETRR
    KeyPair issuer =
        KeyPair.fromSecretSeed("SA64U7C5C7BS5IHWEPA7YWFN3Z6FE5L6KAMYUIT4AQ7KVTVLD23C6HEZ");

    Asset selling = new AssetTypeNative();
    Asset buying = create(null, "USD", issuer.getAccountId());
    BigDecimal amount = new BigDecimal("0.00001");
    Price price = new Price(10000000, 50);
    long offerId = 1;

    ManageBuyOfferOperation operation =
        ManageBuyOfferOperation.builder()
            .selling(selling)
            .buying(buying)
            .amount(amount)
            .price(price)
            .offerId(offerId)
            .sourceAccount(source.getAccountId())
            .build();

    org.stellar.sdk.xdr.Operation xdr = operation.toXdr();
    ManageBuyOfferOperation parsedOperation =
        (ManageBuyOfferOperation) ManageBuyOfferOperation.fromXdr(xdr);
    assertEquals(operation, parsedOperation);
  }
}
