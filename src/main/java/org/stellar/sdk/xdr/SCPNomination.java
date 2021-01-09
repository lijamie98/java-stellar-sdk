// Automatically generated by xdrgen
// DO NOT EDIT or your changes may be overwritten

package org.stellar.sdk.xdr;


import java.io.IOException;

import com.google.common.base.Objects;
import java.util.Arrays;

// === xdr source ============================================================

//  struct SCPNomination
//  {
//      Hash quorumSetHash; // D
//      Value votes<>;      // X
//      Value accepted<>;   // Y
//  };

//  ===========================================================================
public class SCPNomination implements XdrElement {
  public SCPNomination () {}
  private Hash quorumSetHash;
  public Hash getQuorumSetHash() {
    return this.quorumSetHash;
  }
  public void setQuorumSetHash(Hash value) {
    this.quorumSetHash = value;
  }
  private Value[] votes;
  public Value[] getVotes() {
    return this.votes;
  }
  public void setVotes(Value[] value) {
    this.votes = value;
  }
  private Value[] accepted;
  public Value[] getAccepted() {
    return this.accepted;
  }
  public void setAccepted(Value[] value) {
    this.accepted = value;
  }
  public static void encode(XdrDataOutputStream stream, SCPNomination encodedSCPNomination) throws IOException{
    Hash.encode(stream, encodedSCPNomination.quorumSetHash);
    int votessize = encodedSCPNomination.getVotes().length;
    stream.writeInt(votessize);
    for (int i = 0; i < votessize; i++) {
      Value.encode(stream, encodedSCPNomination.votes[i]);
    }
    int acceptedsize = encodedSCPNomination.getAccepted().length;
    stream.writeInt(acceptedsize);
    for (int i = 0; i < acceptedsize; i++) {
      Value.encode(stream, encodedSCPNomination.accepted[i]);
    }
  }
  public void encode(XdrDataOutputStream stream) throws IOException {
    encode(stream, this);
  }
  public static SCPNomination decode(XdrDataInputStream stream) throws IOException {
    SCPNomination decodedSCPNomination = new SCPNomination();
    decodedSCPNomination.quorumSetHash = Hash.decode(stream);
    int votessize = stream.readInt();
    decodedSCPNomination.votes = new Value[votessize];
    for (int i = 0; i < votessize; i++) {
      decodedSCPNomination.votes[i] = Value.decode(stream);
    }
    int acceptedsize = stream.readInt();
    decodedSCPNomination.accepted = new Value[acceptedsize];
    for (int i = 0; i < acceptedsize; i++) {
      decodedSCPNomination.accepted[i] = Value.decode(stream);
    }
    return decodedSCPNomination;
  }
  @Override
  public int hashCode() {
    return Objects.hashCode(this.quorumSetHash, Arrays.hashCode(this.votes), Arrays.hashCode(this.accepted));
  }
  @Override
  public boolean equals(Object object) {
    if (!(object instanceof SCPNomination)) {
      return false;
    }

    SCPNomination other = (SCPNomination) object;
    return Objects.equal(this.quorumSetHash, other.quorumSetHash) && Arrays.equals(this.votes, other.votes) && Arrays.equals(this.accepted, other.accepted);
  }

  public static final class Builder {
    private Hash quorumSetHash;
    private Value[] votes;
    private Value[] accepted;

    public Builder quorumSetHash(Hash quorumSetHash) {
      this.quorumSetHash = quorumSetHash;
      return this;
    }

    public Builder votes(Value[] votes) {
      this.votes = votes;
      return this;
    }

    public Builder accepted(Value[] accepted) {
      this.accepted = accepted;
      return this;
    }

    public SCPNomination build() {
      SCPNomination val = new SCPNomination();
      val.setQuorumSetHash(quorumSetHash);
      val.setVotes(votes);
      val.setAccepted(accepted);
      return val;
    }
  }
}
