package coinffeine.peer.exchange.micropayment

import akka.testkit.TestProbe

import coinffeine.common.akka.test.AkkaSpec
import coinffeine.model.currency._
import coinffeine.model.exchange.Exchange
import coinffeine.peer.exchange.ExchangeActor.ExchangeUpdate

trait ProgressExpectations[C <: FiatCurrency] { this: AkkaSpec =>

  protected def listener: TestProbe
  protected def exchange: Exchange[C]

  def expectProgress(signatures: Int): Unit = {
    val progress = listener.expectMsgType[ExchangeUpdate].exchange.progress
    withClue(s"Expecting $signatures signatures: ") {
      val actualSignatures = stepOf(progress.bitcoinsTransferred,
        exchange.amounts.intermediateSteps.map(_.progress.bitcoinsTransferred))
      actualSignatures shouldBe signatures
    }
  }

  private def stepOf[A](value: A, steps: Seq[A]): Int = steps.indexOf(value) + 1
}
