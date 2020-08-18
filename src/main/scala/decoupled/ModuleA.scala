package decoupled
import chisel3._
import chisel3.util._

// Decoupled provides port interface of three signals
// -> ready (Input)
// -> valid (Output)
// -> bits (Output)

// Let's assume Module A will communicate with Module B using the Decoupled interface
// In our case Module A will send some data and Module B will receive it.
// Ready/Valid acts as an handshaking protocol
// bits contains the actual data payload

// Let's create a bundle class that contains the data payload Module A will send
class ModuleABundle extends Bundle {
  val address = UInt(32.W)
  val data = UInt(32.W)
}


class ModuleA extends Module {
  val io = IO(Decoupled(new ModuleABundle)) // here we create a Decoupled interface.
  // using Decoupled(new ModuleABundle) will add the following signals to our bundle
  // -> ready (Input)
  // -> valid (Output)
  // -> bits  (Output)
  //    -> address (Output)
  //    -> data    (Output)

  // Even though valid must be set only when the Module A has to send the data
  // We assume Module A will always send valid data
  io.valid := 1.U

  // Now we need to check the Module B which is the receiver
  // if it is ready to receive the data.
  // If Module B is ready to receive the data only then will we send it some data
  // otherwise we will just send zeros.

  when(io.ready) {
    io.bits.address := 4.U
    io.bits.data := "h123abcde".U
  } .otherwise {
    io.bits.address := 0.U
    io.bits.data := 0.U
  }
}
