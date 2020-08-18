package decoupled
import chisel3._
import chisel3.util._

// here we create a Module B that will be the receiver of the data payload Module A sends.
// Since this module will take the same wires as input from Module A, we will use the same
// ModuleABundle but this time flip it using the Flipped() that will reverse the direction
// of the Decoupled(new ModuleABundle)

class ModuleB extends Module {
  val io = IO(Flipped(Decoupled(new ModuleABundle)))
  // this will create the following bundle
  // -> ready (Output)
  // -> valid (Input)
  // -> bits  (Input)
  //    -> address (Input)
  //    -> data    (Input)

  // here we create two registers just for storing the data received from Module A
  val registers = Reg(Vec(2, UInt(32.W)))

  // Here we assume Module B will always be ready to receive the data from Module A.
  // although this might not be true in a real world scenario but ideally we assume this
  // for this example.
  io.ready := 1.U

  // The Module B only receives the data if Module A sets a valid signal
  // which tells that Module A is sending a valid data.
  when(io.valid) {
    registers(io.bits.address) := io.bits.data
  }
}
