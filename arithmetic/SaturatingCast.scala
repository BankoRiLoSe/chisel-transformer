import chisel3._

class SaturatingCast(accWidth: Int, outputWidth: Int) extends Module {
  val io = IO(new Bundle {
    val in = Input(SInt(accWidth.W))
    val out = Output(SInt(outputWidth.W))
  })

  val maxValue = ((BigInt(1) << (outputWidth - 1)) - 1).S(accWidth.W)
  val minValue = (-(BigInt(1) << (outputWidth - 1))).S(accWidth.W)

  when(io.in > maxValue) {
    io.out := maxValue(outputWidth - 1, 0).asSInt
  }.elsewhen(io.in < minValue) {
    io.out := minValue(outputWidth - 1, 0).asSInt
  }.otherwise {
    io.out := io.in(outputWidth - 1, 0).asSInt
  }
}
