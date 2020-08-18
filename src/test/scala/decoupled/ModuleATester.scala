package decoupled
import chisel3.iotesters._

class ModuleATester(c: ModuleA) extends PeekPokeTester(c) {
  step(10)
}
