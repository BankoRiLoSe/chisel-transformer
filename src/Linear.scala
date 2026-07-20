import chisel3._

class Linear(inFeatures: Int, outFeatures: Int) extends Module {

  val io = IO(new Bundle {
    val x = Input(Vec(inFeatures, SInt(16.W)))
    val w = Input(Vec(outFeatures, Vec(inFeatures, SInt(16.W))))
    val bias = Input(Vec(outFeatures, SInt(32.W)))
    val y = Output(Vec(outFeatures, SInt(32.W)))
  })
  for (j <- 0 until outFeatures) {
    val products = Wire(Vec(inFeatures, SInt(32.W)))
    for (i <- 0 until inFeatures) {
      products(i) := io.x(i) * io.w(j)(i)
    }
    io.y(j) := products.reduce(_ + _) + io.bias(j)
  }
}
