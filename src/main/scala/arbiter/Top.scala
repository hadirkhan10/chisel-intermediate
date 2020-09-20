package arbiter
import chisel3._
import chisel3.util._

class H2D extends Bundle {
  val a_address = UInt(32.W)
  val a_data = UInt(32.W)
}


class Producer1 extends Module {
  val io = IO(new Bundle {
    val a_addr_in = Input(UInt(32.W))
    val a_data_in = Input(UInt(32.W))
    val tl_h2d = Decoupled(new H2D)
  })

  when(io.tl_h2d.ready) {
    io.tl_h2d.bits.a_address := io.a_addr_in
    io.tl_h2d.bits.a_data := io.a_data_in
  } .otherwise {
    io.tl_h2d.bits.a_address := 0.U
    io.tl_h2d.bits.a_data := 0.U
  }
  io.tl_h2d.valid := 1.U
}

class Producer2 extends Module {
  val io = IO(new Bundle {
    val a_addr_in = Input(UInt(32.W))
    val a_data_in = Input(UInt(32.W))
    val tl_h2d = Decoupled(new H2D)
  })

  when(io.tl_h2d.ready) {
    io.tl_h2d.bits.a_address := io.a_addr_in
    io.tl_h2d.bits.a_data := io.a_data_in
  } .otherwise {
    io.tl_h2d.bits.a_address := 0.U
    io.tl_h2d.bits.a_data := 0.U
  }
  io.tl_h2d.valid := 1.U
}

class Consumer extends Module {
  val io = IO(new Bundle {
    val tl_h2d_i = Flipped(Decoupled(new H2D))
    val out = Output(UInt(32.W))
  })
  val reg = RegInit(0.U(32.W))
  reg := io.tl_h2d_i.bits.a_data
  io.out := reg
  io.tl_h2d_i.ready := 1.U
}

class Top extends Module {
  val io = IO(new Bundle {
    val addr = Input(UInt(32.W))
    val data = Input(UInt(32.W))
    val out = Output(UInt(32.W))
  })

  val prod1 = Module(new Producer1)
  val prod2 = Module(new Producer2)
  val cons  = Module(new Consumer)

  prod1.io.a_addr_in := io.addr
  prod1.io.a_data_in := io.data

  prod2.io.a_addr_in := 0.U
  prod2.io.a_data_in := 30.U

  val arb = Module(new Arbiter(new H2D, 2))
  arb.io.in(0) <> prod2.io.tl_h2d
  arb.io.in(1) <> prod1.io.tl_h2d
  cons.io.tl_h2d_i <> arb.io.out

  io.out := cons.io.out
}
