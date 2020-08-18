package decoupled
import chisel3._

object ModuleAMain extends App {
  iotesters.Driver.execute(args, () => new ModuleA) {
    c => new ModuleATester(c)
  }
}
