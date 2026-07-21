import chisel3._

class Requantize(accWidth: Int, outputWidth: Int, shift: Int) extends Module {
  val io = IO(new Bundle {
    val in = Input(SInt(accWidth.W))
    val out = Output(SInt(outputWidth.W))
  })

  val shifted = Wire(SInt(accWidth.W))
  shifted := io.in >> shift

  val saturatingCast = Module(new SaturatingCast(accWidth, outputWidth))
  saturatingCast.io.in := shifted

  io.out := saturatingCast.io.out
}
