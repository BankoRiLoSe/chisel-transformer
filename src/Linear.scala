import chisel3._

class Linear(inFeatures: Int, outFeatures: Int, dataWidth: Int = 16, accWidth: Int = 32)
    extends Module {

  val io = IO(new Bundle {
    val x = Input(Vec(inFeatures, SInt(dataWidth.W)))
    val w = Input(Vec(outFeatures, Vec(inFeatures, SInt(dataWidth.W))))
    val bias = Input(Vec(outFeatures, SInt(accWidth.W)))
    val y = Output(Vec(outFeatures, SInt(accWidth.W)))
  })
  for (j <- 0 until outFeatures) {
    val products = Wire(Vec(inFeatures, SInt(accWidth.W)))
    for (i <- 0 until inFeatures) {
      products(i) := io.x(i) * io.w(j)(i)
    }
    io.y(j) := products.reduce(_ + _) + io.bias(j)
  }
}
