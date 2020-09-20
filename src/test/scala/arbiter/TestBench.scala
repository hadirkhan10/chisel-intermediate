package arbiter
import chisel3._

object TopMain extends App {
  iotesters.Driver.execute(args, () => new Top) {
    c => new TopTester(c)
  }
}
