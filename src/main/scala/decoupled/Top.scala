package decoupled
import chisel3._
import chisel3.util._

class Top extends Module {
  val io = IO(new Bundle {
    val testOut = Output(UInt(1.W))
  })

  val moduleA = Module(new ModuleA)
  val moduleB = Module(new ModuleB)

  moduleA.io <> moduleB.io

  io.testOut := 1.U
}
