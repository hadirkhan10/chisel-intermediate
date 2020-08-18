package decoupled
import chisel3.iotesters._

class ModuleBTester(c: ModuleB) extends PeekPokeTester(c) {
  step(10)
}
