package decoupled
import chisel3.iotesters._

class TopTester(c: Top) extends PeekPokeTester(c) {
  step(10)
}
