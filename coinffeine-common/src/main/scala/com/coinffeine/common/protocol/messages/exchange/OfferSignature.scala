package com.coinffeine.common.protocol.messages.exchange

import com.google.bitcoin.crypto.TransactionSignature

import com.coinffeine.common.protocol.messages.PublicMessage
import com.coinffeine.common.protocol.TransactionSignatureUtils

case class OfferSignature(exchangeId: String, signature: TransactionSignature)
  extends PublicMessage {

  override def equals(that: Any) = that match {
    case offerSignature: OfferSignature => (offerSignature.exchangeId == exchangeId) &&
      TransactionSignatureUtils.equals(offerSignature.signature, signature)
    case _ => false
  }
}
