package com.sosnoski.concur.article5scala

import akka.actor._

/** Simple hello from an actor in Scala. */
object Hello1 extends App {

  val system = ActorSystem("actor-demo-scala")
  //创建actor系统
  //Props 中为创建Actor需要的配置信息
  //或者Props(classOf[Hello])传递的是Hello的构造方法的调用
  val hello = system.actorOf(Props[Hello]) //在系统内创建actor，返回一个actor引用

  hello ! "Bob" //使用actor的引用向actor发送消息 同 tell()方法
  Thread sleep 1000
  //关闭actor系统
  system shutdown

  class Hello extends Actor {
    def receive = {
      //receive方法实现了接受消息处理的方式
      case name: String => println(s"Hello $name")
    }
  }

}