package com.sosnoski.concur.article5scala

import akka.actor._

/** Hellos with properties passed to constructor, using messages to communicate. */
object Hello3 extends App {

  import Greeter._

  val system = ActorSystem("actor-demo-scala")
  private val of: ActorRef = system.actorOf(props("Bob", "Howya doing"))
  val bob = of
  val alice = system.actorOf(props("Alice", "Happy to meet you"))
  bob ! Greet(alice)
  println("alice"+alice.hashCode())
  println("bob"+bob.hashCode())


  //alice ! Greet(bob)
  Thread sleep 1000
  system shutdown

  object Greeter {

    case class Greet(peer: ActorRef)

    case object AskName

    case class TellName(name: String)

    def props(name: String, greeting: String) = Props(new Greeter(name, greeting))
  }

  class Greeter(myName: String, greeting: String) extends Actor {

    import Greeter._

    def receive = {
      case Greet(peer) => peer ! AskName
      //发送给 actor 的每个消息都包含由 Akka 提供的一些附加信息，最特别的是消息发送方的 ActorRef。
      // 您可在消息处理过程中的任何时刻，通过调用在 actor 基类上定义的 sender() 方法
      // 来访问这些发送方的信息。Greeter actor 在处理 AskName 消息的过程中会使用发送方引用，
      // 以便将 TellName 响应发送给正确的 actor。
      case AskName => sender ! TellName(myName); println(sender.hashCode()) //sender是AskName的发送方
      case TellName(name) => println(s"$greeting, $name")
    }
  }

}