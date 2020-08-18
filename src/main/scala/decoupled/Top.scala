package decoupled
import chisel3._
import chisel3.util._

class Top extends Module {
  val io = IO(new Bundle {
    val addr_in = Input(UInt(32.W))
    val data_in = Input(UInt(32.W))
    val reg1 = Output(UInt(32.W))
    val reg2 = Output(UInt(32.W))
  })

  val moduleA = Module(new ModuleA)
  val moduleB = Module(new ModuleB)

  moduleA.io.bundle <> moduleB.io.bundle
  moduleA.io.top_addr := io.addr_in
  moduleA.io.top_data := io.data_in


  io.reg1 := moduleB.io.reg1_data
  io.reg2 := moduleB.io.reg2_data
}
