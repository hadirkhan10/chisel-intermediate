package decoupled
import chisel3._

object ModuleAMain extends App {
  iotesters.Driver.execute(args, () => new ModuleA) {
    c => new ModuleATester(c)
  }
}

object ModuleBMain extends App {
  iotesters.Driver.execute(args, () => new ModuleB) {
    c => new ModuleBTester(c)
  }
}

object TopMain extends App {
  iotesters.Driver.execute(args, () => new Top) {
    c => new TopTester(c)
  }
}