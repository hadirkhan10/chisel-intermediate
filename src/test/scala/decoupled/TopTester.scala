package decoupled
import chisel3.iotesters._

class TopTester(c: Top) extends PeekPokeTester(c) {
  poke(c.io.addr_in, 0)
  poke(c.io.data_in, 20)
  step(3)
  expect(c.io.reg1, 20)
}
