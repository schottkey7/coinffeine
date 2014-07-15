package com.coinffeine.client.handshake

import scala.concurrent.duration._

import coinffeine.protocol.messages.handshake.ExchangeAborted
import com.coinffeine.client.handshake.HandshakeActor._
import com.coinffeine.common.ProtocolConstants

class BrokerAbortionHandshakeActorTest extends HandshakeActorTest("broker-aborts") {

  override def protocolConstants = ProtocolConstants(
    commitmentConfirmations = 1,
    resubmitRefundSignatureTimeout = 10 seconds,
    refundSignatureAbortTimeout = 10 seconds
  )

  "Handshakes aborted by the broker" should "make the handshake to fail" in {
    givenActorIsInitialized()
    gateway.send(actor, fromBroker(ExchangeAborted(exchange.id, "test abortion")))
    val result = listener.expectMsgClass(classOf[HandshakeFailure])
    result.e.toString should include ("test abortion")
  }

  it should "terminate the handshake" in {
    listener.expectTerminated(actor)
  }
}
