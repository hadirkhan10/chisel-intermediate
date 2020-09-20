package arbiter
import chisel3.iotesters._

class TopTester(c: Top) extends PeekPokeTester(c) {
  poke(c.io.addr, 0)
  poke(c.io.data, 20)
  step(10)
  expect(c.io.out, 20)
}
