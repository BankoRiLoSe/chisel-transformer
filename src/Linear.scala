import chisel3._

class Linear4x2 extends Module {
  val io = IO(new Bundle {
    val x = Input(Vec(4, SInt(16.W)))
    val w = Input(Vec(2, Vec(4, SInt(16.W))))
    val bias = Input(Vec(2, SInt(32.W)))
    val y = Output(Vec(2, SInt(32.W)))
  })
  for (j <- 0 until 2) {
    val products = Wire(Vec(4, SInt(32.W)))
    for (i <- 0 until 4) {
      products(i) := io.x(i) * io.w(j)(i)
    }
    io.y(j) := products.reduce(_ + _) + io.bias(j)
  }
}
